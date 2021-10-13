package DAO;

import Controller.Converter;
import Model.BasicRoute;
import Model.Day;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.Exodus;
import Model.FirstTripPeriod;
import Model.IntervalDay;
import Model.IntervalTripPeriod;
import Model.MisconductTripPeriod;
import Model.TripPeriod;
import Model.TripVoucher;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class MisconductsDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;
    private Converter converter;

    public MisconductsDao() {
        converter = new Converter();
    }

    public ArrayList<MisconductTripPeriod> getMisconductTripPeriods(DetailedRoutesPager detailedRoutesPager) {
        ArrayList<MisconductTripPeriod> misconductTripPeriods = new ArrayList();

        ArrayList<String> routeNumbers = detailedRoutesPager.getRouteNumbers();
        DetailedRoute detailedRoute = new DetailedRoute();
        for (String routeNumber : routeNumbers) {
            detailedRoute = new DetailedRoute();

            StringBuilder query = new StringBuilder();
            StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT exodus_number, date_stamp, t2.number, base_number, bus_number, driver_number, driver_name, notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number=");
            StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(detailedRoutesPager.getDateStamps());

            query = queryBuilderInitialPart.append(routeNumber).
                    append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                    append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ;");

            try {
                connection = dataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query.toString());

                while (resultSet.next()) {

                    String dateStamp = resultSet.getString("date_stamp");
                    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                    if (!detailedRoute.getDays().containsKey(date)) {
                        IntervalDay newDay = new IntervalDay();
                        newDay.setDateStamp(dateStamp);
                        detailedRoute.getDays().put(date, newDay);
                    }
                    Day day = detailedRoute.getDays().get(date);

                    short exodusNumber = resultSet.getShort("exodus_number");
                    if (!day.getExoduses().containsKey(exodusNumber)) {
                        Exodus newExodus = new Exodus();
                        newExodus.setNumber(exodusNumber);
                        day.getExoduses().put(exodusNumber, newExodus);
                    }

                    Exodus exodus = day.getExoduses().get(exodusNumber);
                    String tripVoucherNumber = resultSet.getString("number");
                    if (!exodus.getTripVouchers().containsKey(tripVoucherNumber)) {
                        TripVoucher newTripVoucher = new TripVoucher();
                        newTripVoucher.setNumber(tripVoucherNumber);
                        newTripVoucher.setNotes(resultSet.getString("notes"));
                        newTripVoucher.setBaseNumber(resultSet.getShort("base_number"));
                        exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                    }
                    TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                    MisconductTripPeriod newTripPeriod = new MisconductTripPeriod();

                    newTripPeriod.setExodusNumber(exodusNumber);
                    newTripPeriod.setRouteNumber(routeNumber);
                    newTripPeriod.setDateStamp(dateStamp);
                    newTripPeriod.setType(resultSet.getString("type"));
                    newTripPeriod.setBusNumber(resultSet.getString("bus_number"));
                    newTripPeriod.setDriverNumber(resultSet.getShort("driver_number"));
                    newTripPeriod.setDriverName(resultSet.getString("driver_name"));
                    newTripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled")));
                    newTripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));
                    newTripPeriod.setStartTimeDifference(resultSet.getString("start_time_difference"));
                    newTripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled")));
                    newTripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));
                    newTripPeriod.setArrivalTimeDifference(resultSet.getString("arrival_time_difference"));
                    tripVoucher.getTripPeriods().add(newTripPeriod);

                }

                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
            detailedRoute.calculateIntervalsData();
            ArrayList<MisconductTripPeriod> misconuctTripPeriodsFromDetailedRoute = getMisconuctTripPeriodsFromDetailedRoute(detailedRoute);
            misconductTripPeriods.addAll(misconuctTripPeriodsFromDetailedRoute);
        }

        return misconductTripPeriods;

    }

    private StringBuilder buildStringFromArrayList(ArrayList<String> arrayList) {

        StringBuilder stringBuilder = new StringBuilder("(");
        if (arrayList.isEmpty()) {
            stringBuilder.append(")");
            return stringBuilder;
        }
        int x = 0;
        for (String entry : arrayList) {
            if (x == 0) {
                stringBuilder.append("'").append(entry).append("'");
            } else {
                stringBuilder.append(",'").append(entry).append("'");
            }
            if (x == arrayList.size() - 1) {
                stringBuilder.append(")");
            }
            x++;
        }
        return stringBuilder;
    }

    private ArrayList<MisconductTripPeriod> getMisconuctTripPeriodsFromDetailedRoute(DetailedRoute detailedRoute) {
        ArrayList returnArrayList = new ArrayList();
        // System.out.println("Route #:" + detailedRoute.getNumber());

        TreeMap<Date, Day> days = detailedRoute.getDays();
        for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
            String dateStamp = dayEntry.getValue().getDateStamp();
            TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
            for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
                for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                    ArrayList<TripPeriod> tripPeriods = tripVoucherEntry.getValue().getTripPeriods();
                    for (TripPeriod tripPeriod : tripPeriods) {
                        IntervalTripPeriod tripPeriodM = (IntervalTripPeriod) tripPeriod;

                        if (tripPeriodM.getMisconduct() != null) {
                            if (!tripPeriodM.getMisconduct().equals("") || !tripPeriodM.getRunOver().equals("")) {
                                tripPeriodM.setBaseNumber(tripVoucherEntry.getValue().getBaseNumber());
                                MisconductTripPeriod tripPeriodMB = (MisconductTripPeriod) tripPeriod;
                                returnArrayList.add(tripPeriodM);
                            }
                        }
                    }
                }
            }
        }
        return returnArrayList;
    }

    public ArrayList<FirstTripPeriod> getMisconductedFirstTripPeriods(DetailedRoutesPager detailedRoutesPager, int requestMisconductTimeBound) {
        ArrayList<FirstTripPeriod> misocnductedFirstTripPeriods = new ArrayList();
        ArrayList<String> routeNumbers = detailedRoutesPager.getRouteNumbers();
        BasicRoute basicRoute = new BasicRoute();
        for (String routeNumber : routeNumbers) {
            basicRoute = new BasicRoute();

            StringBuilder query = new StringBuilder();
            StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT exodus_number, date_stamp, base_number, t2.number, bus_number, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number=");
            StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(detailedRoutesPager.getDateStamps());

            query = queryBuilderInitialPart.append(routeNumber).
                    append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                    append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ;");

            try {
                connection = dataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query.toString());

                while (resultSet.next()) {

                    String dateStamp = resultSet.getString("date_stamp");
                    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                    if (!basicRoute.getDays().containsKey(date)) {
                        IntervalDay newDay = new IntervalDay();
                        newDay.setDateStamp(dateStamp);
                        basicRoute.getDays().put(date, newDay);
                    }
                    Day day = basicRoute.getDays().get(date);

                    short exodusNumber = resultSet.getShort("exodus_number");
                    if (!day.getExoduses().containsKey(exodusNumber)) {
                        Exodus newExodus = new Exodus();
                        newExodus.setNumber(exodusNumber);
                        day.getExoduses().put(exodusNumber, newExodus);
                    }

                    Exodus exodus = day.getExoduses().get(exodusNumber);
                    String tripVoucherNumber = resultSet.getString("number");
                    if (!exodus.getTripVouchers().containsKey(tripVoucherNumber)) {
                        TripVoucher newTripVoucher = new TripVoucher();
                        newTripVoucher.setNumber(tripVoucherNumber);

                        exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                    }
                    TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                    if (tripVoucher.getTripPeriods().size() < 2) {
                        TripPeriod newTripPeriod = new TripPeriod();

                        newTripPeriod.setType(resultSet.getString("type"));
                        newTripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled")));
                        newTripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));
                        newTripPeriod.setStartTimeDifference(resultSet.getString("start_time_difference"));
                        newTripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled")));
                        newTripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));
                        newTripPeriod.setArrivalTimeDifference(resultSet.getString("arrival_time_difference"));
                        tripVoucher.getTripPeriods().add(newTripPeriod);
                        if (tripVoucher.getTripPeriods().size() == 2) {
                            FirstTripPeriod firstTripPeriod = calculateFirstTripPeriodAndBaseMisconduct(tripVoucher.getTripPeriods().get(0), tripVoucher.getTripPeriods().get(1), requestMisconductTimeBound);
                            if (firstTripPeriod != null) {
                                firstTripPeriod.setRouteNumber(routeNumber);
                                firstTripPeriod.setDateStamp(dateStamp);
                                firstTripPeriod.setBusNumber(resultSet.getString("bus_number"));
                                firstTripPeriod.setBaseNumber(resultSet.getShort("base_number"));
                                firstTripPeriod.setExodusNumber(exodusNumber);
                                if (exodus.getTripVouchers().size() > 1) {
                                    firstTripPeriod.setBrokenExodus(true);
                                }
                                misocnductedFirstTripPeriods.add(firstTripPeriod);

                            }
                        }
                    }

                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return misocnductedFirstTripPeriods;
    }

    private FirstTripPeriod calculateFirstTripPeriodAndBaseMisconduct(TripPeriod baseTripPeriod, TripPeriod firstTripPeriod, int requestMisconductTimeBound) {
        Duration firstTripPeriodStartTimeDifference = converter.convertStringToDuration(firstTripPeriod.getStartTimeDifference());
        if (firstTripPeriodStartTimeDifference != null) {

            if (firstTripPeriodStartTimeDifference.getSeconds() > (requestMisconductTimeBound * 60)) {
                Duration baseTripPeriodStartTimeDifference = converter.convertStringToDuration(baseTripPeriod.getStartTimeDifference());
                if (baseTripPeriodStartTimeDifference != null && baseTripPeriodStartTimeDifference.getSeconds() > (requestMisconductTimeBound * 60)) {
                    FirstTripPeriod misconductedFirstTripPeriod = new FirstTripPeriod();
                    misconductedFirstTripPeriod.setStartTimeScheduled(firstTripPeriod.getStartTimeScheduled());
                    misconductedFirstTripPeriod.setStartTimeActual(firstTripPeriod.getStartTimeActual());
                    misconductedFirstTripPeriod.setStartTimeDifference(firstTripPeriod.getStartTimeDifference());
                    misconductedFirstTripPeriod.setBaseTripStartTimeScheduled(baseTripPeriod.getStartTimeScheduled());
                    misconductedFirstTripPeriod.setBaseTripStartTimeActual(baseTripPeriod.getStartTimeActual());
                    misconductedFirstTripPeriod.setBaseTripStartTimeDifference(baseTripPeriod.getStartTimeDifference());

                    return misconductedFirstTripPeriod;
                }
            }

        }
        return null;
    }
//--------------------------------to be deleted after-------------------------------

    public ArrayList<FirstTripPeriod> getMisconductedFirstTripPeriodsMinusVersion(DetailedRoutesPager detailedRoutesPager, int requestMisconductTimeBound) {

        ArrayList<FirstTripPeriod> misocnductedFirstTripPeriods = new ArrayList();
        ArrayList<String> routeNumbers = detailedRoutesPager.getRouteNumbers();
        BasicRoute basicRoute = new BasicRoute();
        for (String routeNumber : routeNumbers) {
            basicRoute = new BasicRoute();

            StringBuilder query = new StringBuilder();
            StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT exodus_number, date_stamp, base_number, t2.number, bus_number, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number=");
            StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(detailedRoutesPager.getDateStamps());

            query = queryBuilderInitialPart.append(routeNumber).
                    append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                    append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ;");

            try {
                connection = dataBaseConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query.toString());

                while (resultSet.next()) {

                    String dateStamp = resultSet.getString("date_stamp");
                    Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                    if (!basicRoute.getDays().containsKey(date)) {
                        IntervalDay newDay = new IntervalDay();
                        newDay.setDateStamp(dateStamp);
                        basicRoute.getDays().put(date, newDay);
                    }
                    Day day = basicRoute.getDays().get(date);

                    short exodusNumber = resultSet.getShort("exodus_number");
                    if (!day.getExoduses().containsKey(exodusNumber)) {
                        Exodus newExodus = new Exodus();
                        newExodus.setNumber(exodusNumber);
                        day.getExoduses().put(exodusNumber, newExodus);
                    }

                    Exodus exodus = day.getExoduses().get(exodusNumber);
                    String tripVoucherNumber = resultSet.getString("number");
                    if (!exodus.getTripVouchers().containsKey(tripVoucherNumber)) {
                        TripVoucher newTripVoucher = new TripVoucher();
                        newTripVoucher.setNumber(tripVoucherNumber);

                        exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                    }
                    TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                    if (tripVoucher.getTripPeriods().size() < 2) {
                        TripPeriod newTripPeriod = new TripPeriod();

                        newTripPeriod.setType(resultSet.getString("type"));
                        newTripPeriod.setStartTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled")));
                        newTripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));
                        newTripPeriod.setStartTimeDifference(resultSet.getString("start_time_difference"));
                        newTripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled")));
                        newTripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));
                        newTripPeriod.setArrivalTimeDifference(resultSet.getString("arrival_time_difference"));
                        tripVoucher.getTripPeriods().add(newTripPeriod);
                        if (tripVoucher.getTripPeriods().size() == 2) {
                            FirstTripPeriod firstTripPeriod = calculateFirstTripPeriodAndBaseMisconductMinusVersion(tripVoucher.getTripPeriods().get(0), tripVoucher.getTripPeriods().get(1), requestMisconductTimeBound);
                            if (firstTripPeriod != null) {
                                firstTripPeriod.setRouteNumber(routeNumber);
                                firstTripPeriod.setDateStamp(dateStamp);
                                firstTripPeriod.setBusNumber(resultSet.getString("bus_number"));
                                firstTripPeriod.setBaseNumber(resultSet.getShort("base_number"));
                                firstTripPeriod.setExodusNumber(exodusNumber);
                                if (exodus.getTripVouchers().size() > 1) {
                                    firstTripPeriod.setBrokenExodus(true);
                                }
                                misocnductedFirstTripPeriods.add(firstTripPeriod);

                            }
                        }
                    }

                }
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return misocnductedFirstTripPeriods;
    }

    private FirstTripPeriod calculateFirstTripPeriodAndBaseMisconductMinusVersion(TripPeriod baseTripPeriod, TripPeriod firstTripPeriod, int requestMisconductTimeBound) {
        Duration firstTripPeriodStartTimeDifference = converter.convertStringToDuration(firstTripPeriod.getStartTimeDifference());
        if (firstTripPeriodStartTimeDifference != null) {

            if (firstTripPeriodStartTimeDifference.getSeconds() > (requestMisconductTimeBound * 60) || firstTripPeriodStartTimeDifference.getSeconds() < (requestMisconductTimeBound * 60 * (-1))) {
                Duration baseTripPeriodStartTimeDifference = converter.convertStringToDuration(baseTripPeriod.getStartTimeDifference());
                if (baseTripPeriodStartTimeDifference != null && (baseTripPeriodStartTimeDifference.getSeconds() > (requestMisconductTimeBound * 60) || baseTripPeriodStartTimeDifference.getSeconds() < (requestMisconductTimeBound * 60 * (-1)))) {
                    FirstTripPeriod misconductedFirstTripPeriod = new FirstTripPeriod();
                    misconductedFirstTripPeriod.setStartTimeScheduled(firstTripPeriod.getStartTimeScheduled());
                    misconductedFirstTripPeriod.setStartTimeActual(firstTripPeriod.getStartTimeActual());
                    misconductedFirstTripPeriod.setStartTimeDifference(firstTripPeriod.getStartTimeDifference());
                    misconductedFirstTripPeriod.setBaseTripStartTimeScheduled(baseTripPeriod.getStartTimeScheduled());
                    misconductedFirstTripPeriod.setBaseTripStartTimeActual(baseTripPeriod.getStartTimeActual());
                    misconductedFirstTripPeriod.setBaseTripStartTimeDifference(baseTripPeriod.getStartTimeDifference());

                    return misconductedFirstTripPeriod;
                }
            }

        }
        return null;

    }

}
