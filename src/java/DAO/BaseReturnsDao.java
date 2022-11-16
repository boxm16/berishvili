package DAO;

import Controller.Converter;
import Model.BaseReturn;
import Model.Day;
import Model.DetailedDay;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.DetailedTripPeriod;
import Model.Exodus;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseReturnsDao {

    @Autowired
    private DataBaseConnection dataBaseConnection;
    private Connection connection;

    private Converter converter;

    public BaseReturnsDao() {
        converter = new Converter();
    }

    public TreeMap<Float, DetailedRoute> getBaseReturnRoute(DetailedRoutesPager baseRerturnsPager) {
        //this function is the same as in detailedROuteDao, 'getDetailedRoutes' , just some little changes, , may be this will be the way this method will look in V_3.0
        TreeMap<Float, DetailedRoute> detailedRoutes = new TreeMap<>();

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT route_number, base_number, exodus_number, date_stamp, t2.number, driver_number, driver_name, notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference, base_leaving_time_scheduled, base_leaving_time_actual, base_leaving_time_redacted, base_return_time_scheduled, base_return_time_actual, base_return_time_redacted FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number =");
        StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(baseRerturnsPager.getDateStamps());

        query = queryBuilderInitialPart.append("'" + baseRerturnsPager.getCurrentRoute() + "'").
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ");

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");
                String routeKey = routeNumber.replace("-", ".");
                float routeNumberFloat = Float.valueOf(routeKey);
                if (!detailedRoutes.containsKey(routeNumberFloat)) {
                    DetailedRoute detailedRoute = new DetailedRoute();
                    detailedRoute.setNumber(routeNumber);
                    detailedRoutes.put(routeNumberFloat, detailedRoute);
                }
                DetailedRoute detailedRoute = detailedRoutes.get(routeNumberFloat);
                String dateStamp = resultSet.getString("date_stamp");
                Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                if (!detailedRoute.getDays().containsKey(date)) {
                    DetailedDay newDay = new DetailedDay();
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
                    BaseReturn newTripVoucher = new BaseReturn();
                    newTripVoucher.setNumber(tripVoucherNumber);
                    newTripVoucher.setDriverNumber(resultSet.getString("driver_number"));
                    newTripVoucher.setDriverName(resultSet.getString("driver_name"));
                    newTripVoucher.setNotes(resultSet.getString("notes"));
                    newTripVoucher.setBaseNumber(resultSet.getShort("base_number"));

                    newTripVoucher.setBaseLeavingTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("base_leaving_time_scheduled")));
                    newTripVoucher.setBaseLeavingTimeActual(converter.convertStringTimeToDate(resultSet.getString("base_leaving_time_actual")));
                    newTripVoucher.setBaseLeavingTimeRedacted(converter.convertStringTimeToDate(resultSet.getString("base_leaving_time_redacted")));

                    newTripVoucher.setBaseReturnTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("base_return_time_scheduled")));
                    newTripVoucher.setBaseReturnTimeActual(converter.convertStringTimeToDate(resultSet.getString("base_return_time_actual")));
                    newTripVoucher.setBaseReturnTimeRedacted(converter.convertStringTimeToDate(resultSet.getString("base_return_time_redacted")));

                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                }
                BaseReturn tripVoucherBaseReturns = (BaseReturn) exodus.getTripVouchers().get(tripVoucherNumber);

                DetailedTripPeriod newTripPeriod = new DetailedTripPeriod();

                newTripPeriod.setType(resultSet.getString("type"));
                LocalDateTime startTimeScheduled = converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled"));
                newTripPeriod.setStartTimeScheduled(startTimeScheduled);
                newTripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));
                newTripPeriod.setStartTimeDifference(resultSet.getString("start_time_difference"));
                newTripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled")));
                newTripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));
                newTripPeriod.setArrivalTimeDifference(resultSet.getString("arrival_time_difference"));
                tripVoucherBaseReturns.getTripPeriodsV3().put(startTimeScheduled, newTripPeriod);
                if (tripVoucherBaseReturns.getTripPeriodsV3().size() > 2) {
                    tripVoucherBaseReturns.getTripPeriodsV3().pollFirstEntry();
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detailedRoutes;
    }

    public TreeMap<Float, DetailedRoute> getBaseReturnData(DetailedRoutesPager baseRerturnsPager) {
        //this function is the same as in detailedROuteDao, 'getDetailedRoutes' , just some little changes, , may be this will be the way this method will look in V_3.0
        TreeMap<Float, DetailedRoute> detailedRoutes = new TreeMap<>();

        StringBuilder query = new StringBuilder();
        StringBuilder queryBuilderInitialPart = new StringBuilder("SELECT route_number,base_number, exodus_number, date_stamp, t2.number, driver_number, driver_name, notes, type, start_time_scheduled, start_time_actual,start_time_difference, arrival_time_scheduled, arrival_time_actual, arrival_time_difference, base_leaving_time_scheduled, base_leaving_time_actual, base_leaving_time_redacted, base_return_time_scheduled, base_return_time_actual, base_return_time_redacted FROM route t1 INNER JOIN trip_voucher t2 ON t1.number=t2.route_number INNER JOIN trip_period t3 ON t2.number=t3.trip_voucher_number WHERE route_number IN ");

        StringBuilder queryBuilderRouteNumberPart = buildStringFromArrayList(baseRerturnsPager.getRouteNumbers());
        StringBuilder queryBuilderDateStampPart = buildStringFromArrayList(baseRerturnsPager.getDateStamps());

        query = queryBuilderInitialPart.append(queryBuilderRouteNumberPart).
                append(" AND date_stamp IN ").append(queryBuilderDateStampPart).
                append(" ORDER BY prefix, suffix, date_stamp, exodus_number, start_time_scheduled ");

        try {
            connection = dataBaseConnection.getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query.toString());

            while (resultSet.next()) {
                String routeNumber = resultSet.getString("route_number");
                String routeKey = routeNumber.replace("-", ".");
                float routeNumberFloat = Float.valueOf(routeKey);
                if (!detailedRoutes.containsKey(routeNumberFloat)) {
                    DetailedRoute detailedRoute = new DetailedRoute();
                    detailedRoute.setNumber(routeNumber);
                    detailedRoutes.put(routeNumberFloat, detailedRoute);
                }
                DetailedRoute detailedRoute = detailedRoutes.get(routeNumberFloat);
                String dateStamp = resultSet.getString("date_stamp");
                Date date = converter.convertDateStampDatabaseFormatToDate(dateStamp);
                if (!detailedRoute.getDays().containsKey(date)) {
                    DetailedDay newDay = new DetailedDay();
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
                    BaseReturn newTripVoucher = new BaseReturn();
                    newTripVoucher.setNumber(tripVoucherNumber);
                    newTripVoucher.setDriverNumber(resultSet.getString("driver_number"));
                    newTripVoucher.setDriverName(resultSet.getString("driver_name"));
                    newTripVoucher.setNotes(resultSet.getString("notes"));
                    newTripVoucher.setBaseNumber(resultSet.getShort("base_number"));

                    newTripVoucher.setBaseLeavingTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("base_leaving_time_scheduled")));
                    newTripVoucher.setBaseLeavingTimeActual(converter.convertStringTimeToDate(resultSet.getString("base_leaving_time_actual")));
                    newTripVoucher.setBaseLeavingTimeRedacted(converter.convertStringTimeToDate(resultSet.getString("base_leaving_time_redacted")));

                    newTripVoucher.setBaseReturnTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("base_return_time_scheduled")));
                    newTripVoucher.setBaseReturnTimeActual(converter.convertStringTimeToDate(resultSet.getString("base_return_time_actual")));
                    newTripVoucher.setBaseReturnTimeRedacted(converter.convertStringTimeToDate(resultSet.getString("base_return_time_redacted")));

                    exodus.getTripVouchers().put(tripVoucherNumber, newTripVoucher);
                }
                BaseReturn tripVoucherBaseReturns = (BaseReturn) exodus.getTripVouchers().get(tripVoucherNumber);

                DetailedTripPeriod newTripPeriod = new DetailedTripPeriod();

                newTripPeriod.setType(resultSet.getString("type"));
                LocalDateTime startTimeScheduled = converter.convertStringTimeToDate(resultSet.getString("start_time_scheduled"));
                newTripPeriod.setStartTimeScheduled(startTimeScheduled);
                newTripPeriod.setStartTimeActual(converter.convertStringTimeToDate(resultSet.getString("start_time_actual")));
                newTripPeriod.setStartTimeDifference(resultSet.getString("start_time_difference"));
                newTripPeriod.setArrivalTimeScheduled(converter.convertStringTimeToDate(resultSet.getString("arrival_time_scheduled")));
                newTripPeriod.setArrivalTimeActual(converter.convertStringTimeToDate(resultSet.getString("arrival_time_actual")));
                newTripPeriod.setArrivalTimeDifference(resultSet.getString("arrival_time_difference"));
                tripVoucherBaseReturns.getTripPeriodsV3().put(startTimeScheduled, newTripPeriod);
                if (tripVoucherBaseReturns.getTripPeriodsV3().size() > 2) {
                    tripVoucherBaseReturns.getTripPeriodsV3().pollFirstEntry();
                }
            }

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(RouteDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return detailedRoutes;
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
}
