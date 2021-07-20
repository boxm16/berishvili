package DAO;

import Controller.Converter;
import Model.BasicRoute;
import Model.Day;
import Model.Exodus;
import Model.RouteData;
import Model.RoutesBlock;
import Model.TripPeriod;
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

    public TreeMap<Float, BasicRoute> getSelectedRoutes(RoutesBlock block) {
        converter = new Converter();
        TreeMap<Float, BasicRoute> routes = block.getRoutes();
        for (Map.Entry<Float, BasicRoute> routeEntry : routes.entrySet()) {
            System.out.println(routeEntry.getValue().getNumber());

            StringBuilder sql = new StringBuilder("SELECT * FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE ");
            TreeMap<Date, Day> days = routeEntry.getValue().getDays();
            int indx = 0;
            for (Map.Entry<Date, Day> day : days.entrySet()) {
                if (indx == 0) {
                    sql = sql.append("route_number='").append(routeEntry.getValue().getNumber()).append("' AND date_stamp='").append(day.getValue().getDateStamp()).append("'");
                    indx++;
                }
                sql = sql.append(" OR route_number='").append(routeEntry.getValue().getNumber()).append("' AND date_stamp='").append(day.getValue().getDateStamp()).append("'");
            }
            sql = sql.append("ORDER BY prefix, suffix;");
            // System.out.println(sql.toString());
            try {
                connection = dataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql.toString());

                while (rs.next()) {
                    String dateStamp = rs.getString("date_stamp");
                    short exodusNumber = rs.getShort("exodus_number");
                    String tripVoucherNumber = rs.getString("trip_voucher_number");

                    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                    Day day = days.get(date);
                    if (!day.getExoduses().containsKey(exodusNumber)) {
                        Exodus exodus = new Exodus();
                        exodus.setNumber(exodusNumber);
                        day.getExoduses().put(exodusNumber, exodus);
                    }
                    TreeMap<String, TripVoucher> tripVouchers = day.getExoduses().get(exodusNumber).getTripVouchers();
                    if (!tripVouchers.containsKey(tripVoucherNumber)) {
                        TripVoucher tripVoucher = new TripVoucher();
                        tripVoucher.setNumber(tripVoucherNumber);
                        tripVoucher.setBusNumber(rs.getString("bus_number"));
                        tripVoucher.setBusType(rs.getString("bus_type"));
                        tripVoucher.setDriverNumber(rs.getString("driver_number"));
                        tripVoucher.setDriverName(rs.getString("driver_name"));
                        tripVoucher.setNotes(rs.getString("notes"));
                        tripVouchers.put(tripVoucherNumber, tripVoucher);
                    }

                    TripPeriod tripPeriod = new TripPeriod();
                    tripPeriod.setType(rs.getString("type"));
                    tripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(rs.getString("start_time_scheduled")));
                    tripPeriod.setStartTimeActual(converter.convertStringTimeToDate(rs.getString("start_time_actual")));
                    tripPeriod.setStartTimeDifference(rs.getString("start_time_difference"));

                    tripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(rs.getString("arrival_time_scheduled")));
                    tripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(rs.getString("arrival_time_actual")));
                    tripPeriod.setArrivalTimeDifference(rs.getString("arrival_time_difference"));

                    tripVouchers.get(tripVoucherNumber).getTripPeriods().add(tripPeriod);
                }

                connection.close();
                statement.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return routes;
    }

    public TripPeriodsFilter getSelectedRoutesTripPeriodsFilter(RoutesBlock block) {
        converter = new Converter();
        TreeMap<Float, BasicRoute> routes = block.getRoutes();
        TripPeriodsFilter tripPeriodsFilter = new TripPeriodsFilter();

        for (Map.Entry<Float, BasicRoute> routeEntry : routes.entrySet()) {
            StringBuilder sql = new StringBuilder("SELECT * FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE ");
            TreeMap<Date, Day> days = routeEntry.getValue().getDays();
            int indx = 0;
            for (Map.Entry<Date, Day> day : days.entrySet()) {
                if (indx == 0) {
                    sql = sql.append("route_number='").append(routeEntry.getValue().getNumber()).append("' AND date_stamp='").append(day.getValue().getDateStamp()).append("'");
                    indx++;
                }
                sql = sql.append(" OR route_number='").append(routeEntry.getValue().getNumber()).append("' AND date_stamp='").append(day.getValue().getDateStamp()).append("'");
            }
            sql = sql.append("ORDER BY prefix, suffix;");
            // System.out.println(sql.toString());
            try {
                connection = dataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(sql.toString());
                tripPeriodsFilter.addRouteNumber(routeEntry.getValue().getNumber());

                while (rs.next()) {

                    tripPeriodsFilter.addDateStamp(converter.convertDateStampDatabaseFormatToExcelFormat(rs.getString("date_stamp")));
                    tripPeriodsFilter.addBusNumber(rs.getString("bus_number"));
                    tripPeriodsFilter.addExodusNumber(rs.getString("exodus_number"));
                    tripPeriodsFilter.addDriverName(rs.getString("driver_name"));
                    tripPeriodsFilter.addTripPeriodType(rs.getString("type"));
                    tripPeriodsFilter.addStartTimeScheduled(rs.getString("start_time_scheduled"));
                    tripPeriodsFilter.addStartTimeActual(rs.getString("start_time_actual"));
                    tripPeriodsFilter.addArrivalTimeScheduled(rs.getString("arrival_time_scheduled"));
                    tripPeriodsFilter.addArrivalTimeActual(rs.getString("arrival_time_actual"));

                }

                connection.close();
                statement.close();
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        return tripPeriodsFilter;
    }

    public TreeMap<Float, BasicRoute> getFilteredRoutes(TripPeriodsFilter tripPeriodsFilter) {
        TreeMap<Float, BasicRoute> filteredRoutes = new TreeMap<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE ");
        int indx = 0;
        for (Map.Entry<String, String> routeEntry : tripPeriodsFilter.getRouteNumbers().entrySet()) {
            if (routeEntry.getValue().equals("checked")) {
                TreeMap<String, String> dateStamps = tripPeriodsFilter.getDateStamps();

                for (Map.Entry<String, String> dateStampEntry : dateStamps.entrySet()) {
                    if (dateStampEntry.getValue().equals("checked")) {
                        if (indx == 0) {
                            sql = sql.append(" route_number='").append(routeEntry.getKey()).append("' AND date_stamp='").append(converter.convertDateStampExcelFormatToDatabaseFormat(dateStampEntry.getKey())).append("'");
                            indx++;
                        } else {
                            sql = sql.append(" OR route_number='").append(routeEntry.getKey()).append("' AND date_stamp='").append(converter.convertDateStampExcelFormatToDatabaseFormat(dateStampEntry.getKey())).append("'");
                        }
                    }
                }
            }
        }
        sql = sql.append(" ORDER BY prefix, suffix;");

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql.toString());
            while (rs.next()) {

                String routeNumber = rs.getString("number");
                String dateStamp = rs.getString("date_stamp");
                String dateStampExcelFormat = converter.convertDateStampDatabaseFormatToExcelFormat(dateStamp);
                String busNumber = rs.getString("bus_number");
                short exodusNumber = rs.getShort("exodus_number");

                String tripVoucherNumber = rs.getString("trip_voucher_number");
                String driverName = rs.getString("driver_name");
                String tripPeriodType = rs.getString("type");
                String tripPeriodTypeExcelFormat = converter.covertTripPeriodTypeFromDBFormatToExcelFormat(tripPeriodType);
                String startTimeScheduled = rs.getString("start_time_scheduled");
                String startTimeActual = rs.getString("start_time_actual");
                if (startTimeActual == null) {
                    startTimeActual = "";
                }
//startTimeActual = converter.convertTimeStampFromDBFormatToExcelFormat(startTimeActual);

                String arrivalTimeScheduled = rs.getString("arrival_time_scheduled");
                String arrivalTimeActual = rs.getString("arrival_time_actual");
                if (arrivalTimeActual == null) {
                    arrivalTimeActual = "";
                }
                //  arrivalTimeActual = converter.convertTimeStampFromDBFormatToExcelFormat(arrivalTimeActual);

                //here is actual selection of selected
                float routeNumberIndex = converter.convertRouteNumber(routeNumber);

                if ((tripPeriodsFilter.getRouteNumbers().containsKey(routeNumber) && tripPeriodsFilter.getRouteNumbers().get(routeNumber).equals("checked"))
                        && (tripPeriodsFilter.getDateStamps().containsKey(dateStampExcelFormat) && tripPeriodsFilter.getDateStamps().get(dateStampExcelFormat).equals("checked"))
                        && (tripPeriodsFilter.getBusNumbers().containsKey(busNumber) && tripPeriodsFilter.getBusNumbers().get(busNumber).equals("checked"))
                        && (tripPeriodsFilter.getExodusNumbers().containsKey(rs.getString("exodus_number")) && tripPeriodsFilter.getExodusNumbers().get(rs.getString("exodus_number")).equals("checked"))
                        && (tripPeriodsFilter.getDriverNames().containsKey(driverName) && tripPeriodsFilter.getDriverNames().get(driverName).equals("checked"))
                        && (tripPeriodsFilter.getTripPeriodTypes().containsKey(tripPeriodTypeExcelFormat) && tripPeriodsFilter.getTripPeriodTypes().get(tripPeriodTypeExcelFormat).equals("checked"))
                        && (tripPeriodsFilter.getStartTimesScheduled().containsKey(startTimeScheduled) && tripPeriodsFilter.getStartTimesScheduled().get(startTimeScheduled).equals("checked"))
                        && (tripPeriodsFilter.getStartTimesActual().containsKey(startTimeActual) && tripPeriodsFilter.getStartTimesActual().get(startTimeActual).equals("checked"))
                        && (tripPeriodsFilter.getArrivalTimesScheduled().containsKey(arrivalTimeScheduled) && tripPeriodsFilter.getArrivalTimesScheduled().get(arrivalTimeScheduled).equals("checked"))
                        && (tripPeriodsFilter.getArrivalTimesActual().containsKey(arrivalTimeActual) && tripPeriodsFilter.getArrivalTimesActual().get(arrivalTimeActual).equals("checked"))) {

                    if (!filteredRoutes.containsKey(routeNumberIndex)) {
                        BasicRoute route = new BasicRoute();
                        route.setNumber(routeNumber);
                        filteredRoutes.put(routeNumberIndex, route);
                    }

                    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                    TreeMap<Date, Day> days = filteredRoutes.get(routeNumberIndex).getDays();
                    if (!days.containsKey(date)) {
                        Day day = new Day();
                        day.setDateStamp(dateStamp);
                        days.put(date, day);
                    }
                    TreeMap<Short, Exodus> exoduses = days.get(date).getExoduses();
                    short exodusNumberShort = exodusNumber;
                    if (!exoduses.containsKey(exodusNumberShort)) {
                        Exodus exodus = new Exodus();
                        exodus.setNumber(exodusNumberShort);
                        exoduses.put(exodusNumber, exodus);
                    }
                    TreeMap<String, TripVoucher> tripVouchers = exoduses.get(exodusNumberShort).getTripVouchers();
                    if (!tripVouchers.containsKey(tripVoucherNumber)) {
                        TripVoucher tripVoucher = new TripVoucher();
                        tripVoucher.setBusNumber(busNumber);
                        tripVoucher.setDriverName(driverName);
                        tripVouchers.put(tripVoucherNumber, tripVoucher);
                    }

                    ArrayList<TripPeriod> tripPeriods = tripVouchers.get(tripVoucherNumber).getTripPeriods();
                    if (tripPeriodsFilter.getStartTimesScheduled().containsKey(startTimeScheduled)
                            && tripPeriodsFilter.getStartTimesActual().containsKey(startTimeActual)
                            && tripPeriodsFilter.getArrivalTimesScheduled().containsKey(arrivalTimeScheduled)
                            && tripPeriodsFilter.getArrivalTimesActual().containsKey(arrivalTimeActual)) {
                        TripPeriod tripPeriod = new TripPeriod();
                        tripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(startTimeScheduled));
                        tripPeriod.setStartTimeActual(converter.convertStringTimeToDate(startTimeActual));
                        tripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(arrivalTimeScheduled));
                        tripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(arrivalTimeActual));
                        tripPeriod.setType(tripPeriodType);
                        tripPeriods.add(tripPeriod);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }

        return filteredRoutes;
    }

}
