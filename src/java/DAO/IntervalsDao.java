package DAO;

import Controller.Converter;
import Model.Day;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.Exodus;
import Model.IntervalDay;
import Model.IntervalTripPeriod;
import Model.TripVoucher;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IntervalsDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;

    private Connection connection;
    private Converter converter;

    public IntervalsDao() {
        converter = new Converter();
    }

    public DetailedRoute getRoutesForIntervals(DetailedRoutesPager detailedRoutesPager) {
        DetailedRoute detailedRoute = new DetailedRoute();
        detailedRoute.setNumber(detailedRoutesPager.getCurrentRoute());

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT exodus_number, date_stamp, t2.number,  notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number=");
        StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(detailedRoutesPager.getDateStamps());

        query = queryBuilderInitialPart.append("'" + detailedRoutesPager.getCurrentRoute() + "'").
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
                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                }
                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                IntervalTripPeriod newTripPeriod = new IntervalTripPeriod();

                newTripPeriod.setExodusNumber(exodusNumber);
                newTripPeriod.setType(resultSet.getString("type"));
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
        return detailedRoute;
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

    public TreeMap<Float, DetailedRoute> getRoutesForIntervalsForExcelExport(DetailedRoutesPager intervalsPager) {
        TreeMap<Float, DetailedRoute> intervalsRoutes = new TreeMap<>();

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT route_number, bus_number, driver_name, exodus_number, date_stamp, t2.number,  notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");

        StringBuilder queryBuilderRouteNumberPart = buildStringFromArrayList(intervalsPager.getRouteNumbers());
        StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(intervalsPager.getDateStamps());

        query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ;");

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");
                String routeKey = routeNumber.replace("-", ".");
                float routeNumberFloat = Float.valueOf(routeKey);
                if (!intervalsRoutes.containsKey(routeNumberFloat)) {
                    DetailedRoute intervalsRoute = new DetailedRoute();
                    intervalsRoute.setNumber(routeNumber);
                    intervalsRoutes.put(routeNumberFloat, intervalsRoute);
                }
                DetailedRoute intervalsRoute = intervalsRoutes.get(routeNumberFloat);
                String dateStamp = resultSet.getString("date_stamp");
                Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                if (!intervalsRoute.getDays().containsKey(date)) {
                    IntervalDay newDay = new IntervalDay();
                    newDay.setDateStamp(dateStamp);
                    intervalsRoute.getDays().put(date, newDay);
                }
                Day day = intervalsRoute.getDays().get(date);

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
                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                }
                TripVoucher tripVoucher = exodus.getTripVouchers().get(tripVoucherNumber);

                IntervalTripPeriod newTripPeriod = new IntervalTripPeriod();

                newTripPeriod.setExodusNumber(exodusNumber);
                newTripPeriod.setType(resultSet.getString("type"));
                newTripPeriod.setBusNumber(resultSet.getString("bus_number"));
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
        return intervalsRoutes;
    }

}
