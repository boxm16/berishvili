package DAO;

import Model.BasicRoute;
import Model.Day;
import Model.Exodus;
import Model.RouteData;
import Model.TripPeriod;
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

            return returnCode;//this is No error code
        } catch (SQLException ex) {
            Logger.getLogger(TechDao.class.getName()).log(Level.SEVERE, null, ex);
            model.addAttribute("errorMessage", ex.getMessage());
            return 0.03f;//this is error code for exception error
        }
    }
}
