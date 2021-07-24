package DAO;

import Controller.Converter;
import Model.BasicRoute;
import Model.Day;
import Model.Exodus;
import Model.RouteData;
import Model.TripPeriod;
import Model.TripPeriod2X;
import Model.TripPeriodsFilter;
import Model.TripVoucher;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;

@Repository
public class RouteDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;
    private Converter converter;

    public TreeMap<Float, RouteData> getRoutesDataFromDB() {
        TreeMap<Float, RouteData> routes = new TreeMap<>();
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM route");
            while (resultSet.next()) {
                RouteData route = new RouteData();
                String routeNumber = resultSet.getString("number");
                String routeKey = routeNumber.replace("-", ".");
                String aPoint = resultSet.getString("a_point");
                String bPoint = resultSet.getString("b_point");
                String scheme = resultSet.getString("scheme");
                route.setNumber(routeNumber);
                route.setaPoint(aPoint);
                route.setbPoint(bPoint);
                route.setScheme(scheme);
                routes.put(Float.valueOf(routeKey), route);
            }
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return routes;
    }

    public <K, V> void insertRoutes(TreeMap<K, V> routes) {
        try {
            connection = dataBaseConnection.getConnection();
            PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO route (number, prefix, suffix) VALUES (?,?,?)");
            String prefix;
            String suffix;
            for (Map.Entry<K, V> entry : routes.entrySet()) {
                BasicRoute route = (BasicRoute) entry.getValue();
                String routeNumber = route.getNumber();

                if (routeNumber.contains("-")) {
                    prefix = routeNumber.split("-")[0];
                    suffix = routeNumber.split("-")[1];
                } else {
                    prefix = routeNumber;
                    suffix = null;
                }

                insertStatement.setString(1, routeNumber);
                insertStatement.setString(2, prefix);
                insertStatement.setString(3, suffix);
                insertStatement.addBatch();
            }
            insertStatement.executeBatch();

            insertStatement.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("New (unregistered) routes from excel file has been inserted (registered) successfully into database");
    }

    public void updateRoute(RouteData route) {
        try {
            connection = dataBaseConnection.getConnection();
            PreparedStatement updateStatement = connection.prepareStatement("UPDATE route SET a_point = ?, b_point=?, scheme =? where number = ?");
            updateStatement.setString(1, route.getaPoint());
            updateStatement.setString(2, route.getbPoint());
            updateStatement.setString(3, route.getScheme());
            updateStatement.setString(4, route.getNumber());
            updateStatement.execute();
            System.out.println("Route #" + route.getNumber() + "hase been  updated");
            updateStatement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public Float insertUploadedData(TreeMap<Float, BasicRoute> basicRoutes, ModelMap model) {
        float returnCode = 0.00f;
        TreeMap<Float, RouteData> routesDataFromDB = getRoutesDataFromDB();
        //first i need to find if new data from excel file hase inside new routes
        TreeMap<Float, BasicRoute> unregisteredRoutes = new TreeMap<>();
        for (Map.Entry<Float, BasicRoute> routeEntry : basicRoutes.entrySet()) {
            if (!routesDataFromDB.containsKey(routeEntry.getKey())) {
                unregisteredRoutes.put(routeEntry.getKey(), routeEntry.getValue());
            }
        }

        //so, if new data from excel file hase inside new(unregistered) routes i need to insret them
        //and parallily saving those new(unregistered) routes into model, to send on index.htm
        if (unregisteredRoutes.size() > 0) {
            String unregisteredRoutesMessage = "";
            if (unregisteredRoutes.size() > 10) {
                unregisteredRoutesMessage = "ატვირთულ ფაილში იძებნება 10-ზე მეტი ახალი (მონაცემთა ბაზაში დაურეგისტრირებული) მარშრუტი<br> <a href=\"routes.htm\">გადადი</a> მარშრუტების გვერდზე და შეავსე ახალი მარშრუტის(მარშრუტების) მონაცემები.";

            } else {
                unregisteredRoutesMessage = "ატვირთულ ფაილში იძებნება ახალი (მონაცემთა ბაზაში დაურეგისტრირებული) მარშრუტი(მარშრუტები)<br>";
                for (Map.Entry<Float, BasicRoute> unregisteredRoutesSet : unregisteredRoutes.entrySet()) {
                    unregisteredRoutesMessage += "მარშრუტი #" + unregisteredRoutesSet.getValue().getNumber() + "<br>";
                }
                unregisteredRoutesMessage += "<a href=\"routes.htm\">გადადი</a> მარშრუტების გვერდზე და შეავსე ახალი მარშრუტის(მარშრუტების) მონაცემები.";
            }
            model.addAttribute("unregisteredRoutesMessage", unregisteredRoutesMessage);

            insertRoutes(unregisteredRoutes);
            returnCode = 0.04f;
        }

        //and now i start insering all data
        try {

            connection = dataBaseConnection.getConnection();
            connection.setAutoCommit(false);
            //Statements to insert records
            PreparedStatement deletePreparedStatement = connection.prepareStatement("DELETE FROM trip_voucher WHERE number=?");
            PreparedStatement tripVoucherInsertionPreparedStatement = connection.prepareStatement("INSERT INTO trip_voucher (number, route_number, date_stamp, exodus_number, driver_number, driver_name, bus_number, bus_type, notes) VALUES(?,?,?,?,?,?,?,?,?);");
            PreparedStatement tripPeriodInsertionPreparedStatement = connection.prepareStatement("INSERT INTO trip_period (trip_voucher_number, type, start_time_scheduled, start_time_actual, start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference) VALUES (?,?,?,?,?,?,?,?);");

            for (Map.Entry<Float, BasicRoute> routeEntry : basicRoutes.entrySet()) {
                System.out.println("Inserting route data. Route Number:" + routeEntry.getValue().getNumber());
                TreeMap<Date, Day> days = routeEntry.getValue().getDays();
                for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                    TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
                    for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                        TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
                        for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                            deletePreparedStatement.setString(1, tripVoucherEntry.getValue().getNumber());
                            deletePreparedStatement.addBatch();

                            tripVoucherInsertionPreparedStatement.setString(1, tripVoucherEntry.getValue().getNumber());
                            tripVoucherInsertionPreparedStatement.setString(2, routeEntry.getValue().getNumber());
                            tripVoucherInsertionPreparedStatement.setString(3, dayEntry.getValue().getDateStamp());
                            tripVoucherInsertionPreparedStatement.setShort(4, exodusEntry.getValue().getNumber());

                            tripVoucherInsertionPreparedStatement.setString(5, tripVoucherEntry.getValue().getDriverNumber());
                            tripVoucherInsertionPreparedStatement.setString(6, tripVoucherEntry.getValue().getDriverName());

                            tripVoucherInsertionPreparedStatement.setString(7, tripVoucherEntry.getValue().getBusNumber());
                            tripVoucherInsertionPreparedStatement.setString(8, tripVoucherEntry.getValue().getBusType());

                            tripVoucherInsertionPreparedStatement.setString(9, tripVoucherEntry.getValue().getNotes());
                            tripVoucherInsertionPreparedStatement.addBatch();
                            //now trip Period
                            ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                            for (TripPeriod tripPeriod : tripPeriods) {
                                tripPeriodInsertionPreparedStatement.setString(1, tripVoucherEntry.getValue().getNumber());
                                tripPeriodInsertionPreparedStatement.setString(2, tripPeriod.getType());

                                tripPeriodInsertionPreparedStatement.setObject(3, tripPeriod.getStartTimeScheduled());
                                tripPeriodInsertionPreparedStatement.setObject(4, tripPeriod.getStartTimeActual());
                                tripPeriodInsertionPreparedStatement.setString(5, tripPeriod.getStartTimeDifference());
                                tripPeriodInsertionPreparedStatement.setObject(6, tripPeriod.getArrivalTimeScheduled());
                                tripPeriodInsertionPreparedStatement.setObject(7, tripPeriod.getArrivalTimeActual());
                                tripPeriodInsertionPreparedStatement.setString(8, tripPeriod.getArrivalTimeDifference());
                                tripPeriodInsertionPreparedStatement.addBatch();
                            }

                        }
                    }
                    //Executing the batch
                    deletePreparedStatement.executeBatch();
                    tripVoucherInsertionPreparedStatement.executeBatch();
                    tripPeriodInsertionPreparedStatement.executeBatch();
                }

            }
            //Saving the changes
            connection.commit();
            deletePreparedStatement.close();
            tripVoucherInsertionPreparedStatement.close();
            tripPeriodInsertionPreparedStatement.close();
            connection.close();
            return returnCode;//this is No error code
        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage", ex.getMessage());
            return 0.03f;//this is error code for exception error
        }
    }

    public ArrayList<TripPeriod2X> getFilteredTripPeriods(TripPeriodsFilter tripPeriodsFilter) {
        converter = new Converter();
        ArrayList<TripPeriod2X> tripPeriods = new ArrayList<>();
        if (tripPeriodsFilter.isBlank()) {
            //return empty;
            return tripPeriods;
        }
        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT route_number, date_stamp,  bus_number, exodus_number, driver_name, type, start_time_scheduled, start_time_actual, arrival_time_scheduled, arrival_time_actual FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");
        StringBuilder queryBuilderRouteNumberPart = buildStringFromTreeMap(tripPeriodsFilter.getRouteNumbers());
        StringBuilder queryBuilderDateStampPart = buildStringFromTreeMap(tripPeriodsFilter.getDateStamps());
        if (tripPeriodsFilter.isInitial()) {
            query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                    append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                    append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled LIMIT 200;");
        } else {
            StringBuilder queryBuilderBusNumberPart = buildStringFromTreeMap(tripPeriodsFilter.getBusNumbers());
            StringBuilder queryBuilderExodusNumberPart = buildStringFromTreeMap(tripPeriodsFilter.getBusNumbers());
            StringBuilder queryBuilderDriverNamePart = buildStringFromTreeMap(tripPeriodsFilter.getBusNumbers());
            StringBuilder queryBuilderTripPeriodTypePart = buildStringFromTreeMap(tripPeriodsFilter.getTripPeriodTypes());
            StringBuilder queryBuilderStartTimeScheduledPart = buildStringFromTreeMap(tripPeriodsFilter.getStartTimesScheduled());
            StringBuilder queryBuilderStartTimeActualPart = buildStringFromTreeMap(tripPeriodsFilter.getStartTimesActual());
            StringBuilder queryBuilderArrivalTimeScheduledPart = buildStringFromTreeMap(tripPeriodsFilter.getArrivalTimesScheduled());
            StringBuilder queryBuilderArrivalTimeActualPart = buildStringFromTreeMap(tripPeriodsFilter.getArrivalTimesActual());

            query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                    append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                    append(" AND bus_number IN ").append(queryBuilderBusNumberPart).
                    append(" AND exodus_number IN ").append(queryBuilderExodusNumberPart).
                    append(" AND driver_name IN ").append(queryBuilderDriverNamePart).
                    append(" AND type IN ").append(queryBuilderTripPeriodTypePart).
                    append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled LIMIT 200;");
        }

        //--------------
        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                TripPeriod2X tripPeriod = new TripPeriod2X();
                tripPeriod.setRouteNumber(resultSet.getString("route_number"));
                tripPeriod.setDateStamp(resultSet.getString("date_stamp"));
                tripPeriod.setBusNumber(resultSet.getString("bus_number"));
                tripPeriod.setExodusNumber(resultSet.getShort("exodus_number"));
                tripPeriod.setDriverName(resultSet.getString("driver_name"));
                tripPeriod.setType(resultSet.getString("type"));
                tripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled")));
                tripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));
                tripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled")));
                tripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));

                tripPeriods.add(tripPeriod);

            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tripPeriods;

    }

    private StringBuilder buildStringFromTreeMap(TreeMap<String, String> treeMap) {
        StringBuilder stringBuilder = new StringBuilder("(");
        if (treeMap.isEmpty()) {
            stringBuilder.append(")");
            return stringBuilder;
        }
        int x = 0;
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            String string = entry.getKey();
            if (x == 0) {
                stringBuilder.append("'").append(string).append("'");
            } else {
                stringBuilder.append(", '").append(string).append("'");
            }
            if (x == treeMap.size() - 1) {
                stringBuilder.append(")");
            }
            x++;
        }

        return stringBuilder;
    }
}
