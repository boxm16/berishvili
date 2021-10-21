package Controller;

import DAO.GuarantyVDao;
import DAO.RouteDao;
import Model.Day;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.DetailedTripPeriod;
import Model.GuarantyTripsData;
import Model.IntervalDay;
import Model.IntervalTripPeriod;
import Model.RouteData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuarantyVController {

    @Autowired
    private GuarantyVDao guarantyVDao;
    @Autowired
    private RouteDao routeDao;

    //----------
    @RequestMapping(value = "guarantyTripsInitialRequest")
    public String guarantyTripsInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model) {
        DetailedRoutesPager guarantyRoutesPager = createDetailedRoutesPager(routeDates);
        TreeMap<Float, DetailedRoute> routesForIntervalsForExcelExport = guarantyVDao.getRoutesForIntervalsForExcelExport(guarantyRoutesPager);
        TreeMap<Float, RouteData> routesDataFromDB = routeDao.getRoutesDataFromDB();
        ArrayList guarantyData = getGuarantyDataV(routesForIntervalsForExcelExport, routesDataFromDB);

        model.addAttribute("guarantyData", guarantyData);
        return "guarantyTrips";
    }

    private DetailedRoutesPager createDetailedRoutesPager(String routeDates) {

        DetailedRoutesPager detailedRoutesPager = new DetailedRoutesPager("intervals");

        //trimming and cleaning input
        routeDates = routeDates.trim();
        if (routeDates.length() == 0) {
            return detailedRoutesPager;
        }
        if (routeDates.substring(routeDates.length() - 1, routeDates.length()).equals(",")) {
            routeDates = routeDates.substring(0, routeDates.length() - 1).trim();
        }
        String[] routeDatesArray = routeDates.split(",");
        for (String routeDate : routeDatesArray) {

            String[] routeDateArray = routeDate.split(":");
            String routeNumber = routeDateArray[0];
            String dateStamp = routeDateArray[1];
            detailedRoutesPager.addRouteNumber(routeNumber);
            detailedRoutesPager.addDateStamp(dateStamp);
        }
        return detailedRoutesPager;
    }

    private ArrayList getGuarantyDataV(TreeMap<Float, DetailedRoute> routesForIntervalsForExcelExport, TreeMap<Float, RouteData> routesDataFromDB) {
        ArrayList returnValue = new ArrayList();
        for (Map.Entry<Float, DetailedRoute> intervalRouteEntry : routesForIntervalsForExcelExport.entrySet()) {
            DetailedRoute route = intervalRouteEntry.getValue();
            route.calculateIntervalsData();
            TreeMap<Date, Day> days = route.getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                IntervalDay day = (IntervalDay) dayEntry.getValue();

                TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable = day.getAbTimetable();
                //--------
                IntervalTripPeriod scheduledGuarantyTripAB = abTimetable.pollLastEntry().getValue();

                LocalDateTime abGuarantyTripStartTimeScheduled = scheduledGuarantyTripAB.getStartTimeScheduled();

                short abGuarantyExodusScheduled = scheduledGuarantyTripAB.getExodusNumber();
                short baseNumber = scheduledGuarantyTripAB.getBaseNumber();

                TreeMap<LocalDateTime, DetailedTripPeriod> abGpsTimetable = day.getAbGpsTimetable();

                IntervalTripPeriod actualGuarantyTripAB = (IntervalTripPeriod) abGpsTimetable.pollLastEntry().getValue();

                LocalDateTime abGuarantyTripStartTimeActual = actualGuarantyTripAB.getStartTimeActual();

                short abGuarantyExodusActual = actualGuarantyTripAB.getExodusNumber();

                if (abGuarantyTripStartTimeActual.isBefore(abGuarantyTripStartTimeScheduled)) {
                    if (actualGuarantyTripAB.getArrivalTimeActual() != null) {

                        GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                        guarantyTripsData.setType("ab");
                        guarantyTripsData.setGuarantyType("საგარანტიო");
                        guarantyTripsData.setBaseNumber(baseNumber);
                        guarantyTripsData.setRouteNumber(route.getNumber());
                        guarantyTripsData.setDateStamp(day.getDateStamp());

                        guarantyTripsData.setExodusScheduled(abGuarantyExodusScheduled);
                        guarantyTripsData.setGuarantyStartTimeScheduled(abGuarantyTripStartTimeScheduled);
                        guarantyTripsData.setExodusActual(abGuarantyExodusActual);
                        guarantyTripsData.setGuarantyStartTimeActual(abGuarantyTripStartTimeActual);
                        returnValue.add(guarantyTripsData);
                    }
                }

                //--------
                TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable = day.getBaTimetable();

                if (baTimetable.size() > 0) {
                    IntervalTripPeriod scheduledGuarantyTripBA = baTimetable.pollLastEntry().getValue();

                    LocalDateTime baGuarantyTripStartTimeScheduled = scheduledGuarantyTripBA.getStartTimeScheduled();
                    short baGuarantyExodusScheduled = scheduledGuarantyTripBA.getExodusNumber();

                    TreeMap<LocalDateTime, DetailedTripPeriod> baGpsTimetable = day.getBaGpsTimetable();

                    IntervalTripPeriod actualGuarantyTripBA = (IntervalTripPeriod) baGpsTimetable.pollLastEntry().getValue();

                    LocalDateTime baGuarantyTripStartTimeActual = actualGuarantyTripBA.getStartTimeActual();

                    short baGuarantyExodusActual = actualGuarantyTripBA.getExodusNumber();

                    if (baGuarantyTripStartTimeActual.isBefore(baGuarantyTripStartTimeScheduled)) {
                        if (actualGuarantyTripAB.getArrivalTimeActual() != null) {
                            GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                            guarantyTripsData.setType("ba");
                            guarantyTripsData.setGuarantyType("საგარანტიო");
                            guarantyTripsData.setBaseNumber(baseNumber);
                            guarantyTripsData.setRouteNumber(route.getNumber());
                            guarantyTripsData.setDateStamp(day.getDateStamp());

                            guarantyTripsData.setExodusScheduled(baGuarantyExodusScheduled);
                            guarantyTripsData.setGuarantyStartTimeScheduled(baGuarantyTripStartTimeScheduled);
                            guarantyTripsData.setExodusActual(baGuarantyExodusActual);
                            guarantyTripsData.setGuarantyStartTimeActual(baGuarantyTripStartTimeActual);
                            returnValue.add(guarantyTripsData);
                        }
                    }
                }
                //-------NOW SUBGUARANTIES------
                IntervalTripPeriod scheduledSubGuarantyTripAB = abTimetable.pollLastEntry().getValue();

                LocalDateTime abSubGuarantyTripStartTimeScheduled = scheduledSubGuarantyTripAB.getStartTimeScheduled();

                short abSubGuarantyExodusScheduled = scheduledSubGuarantyTripAB.getExodusNumber();

                IntervalTripPeriod actualSubGuarantyTripAB = (IntervalTripPeriod) abGpsTimetable.pollLastEntry().getValue();

                LocalDateTime abSubGuarantyTripStartTimeActual = actualSubGuarantyTripAB.getStartTimeActual();

                short abSubGuarantyExodusActual = actualSubGuarantyTripAB.getExodusNumber();

                if (abSubGuarantyTripStartTimeActual.isBefore(abSubGuarantyTripStartTimeScheduled)) {
                    if (actualSubGuarantyTripAB.getArrivalTimeActual() != null) {

                        GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                        guarantyTripsData.setType("ab");
                        guarantyTripsData.setGuarantyType("ქვე-საგარანტიო");
                        guarantyTripsData.setBaseNumber(baseNumber);
                        guarantyTripsData.setRouteNumber(route.getNumber());
                        guarantyTripsData.setDateStamp(day.getDateStamp());

                        guarantyTripsData.setExodusScheduled(abSubGuarantyExodusScheduled);
                        guarantyTripsData.setGuarantyStartTimeScheduled(abSubGuarantyTripStartTimeScheduled);
                        guarantyTripsData.setExodusActual(abSubGuarantyExodusActual);
                        guarantyTripsData.setGuarantyStartTimeActual(abSubGuarantyTripStartTimeActual);
                        returnValue.add(guarantyTripsData);
                    }
                }

                //--------ba
                if (baTimetable.size() > 0) {
                    IntervalTripPeriod scheduledSubGuarantyTripBA = baTimetable.pollLastEntry().getValue();

                    LocalDateTime baSubGuarantyTripStartTimeScheduled = scheduledSubGuarantyTripBA.getStartTimeScheduled();
                    short baSubGuarantyExodusScheduled = scheduledSubGuarantyTripBA.getExodusNumber();

                    TreeMap<LocalDateTime, DetailedTripPeriod> baGpsTimetable = day.getBaGpsTimetable();

                    IntervalTripPeriod actualSubGuarantyTripBA = (IntervalTripPeriod) baGpsTimetable.pollLastEntry().getValue();

                    LocalDateTime baSubGuarantyTripStartTimeActual = actualSubGuarantyTripBA.getStartTimeActual();

                    short baSubGuarantyExodusActual = actualSubGuarantyTripBA.getExodusNumber();

                    if (baSubGuarantyTripStartTimeActual.isBefore(baSubGuarantyTripStartTimeScheduled)) {
                        if (actualSubGuarantyTripAB.getArrivalTimeActual() != null) {
                            GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                            guarantyTripsData.setType("ba");
                            guarantyTripsData.setGuarantyType("ქვე-საგარანტიო");
                            guarantyTripsData.setBaseNumber(baseNumber);
                            guarantyTripsData.setRouteNumber(route.getNumber());
                            guarantyTripsData.setDateStamp(day.getDateStamp());

                            guarantyTripsData.setExodusScheduled(baSubGuarantyExodusScheduled);
                            guarantyTripsData.setGuarantyStartTimeScheduled(baSubGuarantyTripStartTimeScheduled);
                            guarantyTripsData.setExodusActual(baSubGuarantyExodusActual);
                            guarantyTripsData.setGuarantyStartTimeActual(baSubGuarantyTripStartTimeActual);
                            returnValue.add(guarantyTripsData);
                        }
                    }
                }

            }
        }
        return returnValue;
    }

}
