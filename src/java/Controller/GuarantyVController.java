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
            route.calculateIntervalsDataVVersion();
            TreeMap<Date, Day> days = route.getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                ArrayList dayArray = new ArrayList();
                IntervalDay day = (IntervalDay) dayEntry.getValue();

                TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable = day.getAbTimetable();
                TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable = day.getBaTimetable();
                TreeMap<LocalDateTime, DetailedTripPeriod> abGpsTimetable = day.getAbGpsTimetable();
                TreeMap<LocalDateTime, DetailedTripPeriod> baGpsTimetable = day.getBaGpsTimetable();

                IntervalTripPeriod scheduledGuarantyTripAB = null;
                IntervalTripPeriod scheduledSubGuarantyTripAB = null;
                IntervalTripPeriod actualGuarantyTripAB = null;
                IntervalTripPeriod actualSubGuarantyTripAB = null;

                if (abTimetable.size() > 0) {
                    scheduledGuarantyTripAB = abTimetable.pollLastEntry().getValue();
                }
                if (abTimetable.size() > 0) {
                    scheduledSubGuarantyTripAB = abTimetable.pollLastEntry().getValue();
                }

                if (abGpsTimetable.size() > 0) {
                    actualGuarantyTripAB = (IntervalTripPeriod) abGpsTimetable.pollLastEntry().getValue();
                }
                if (abGpsTimetable.size() > 0) {
                    actualSubGuarantyTripAB = (IntervalTripPeriod) abGpsTimetable.pollLastEntry().getValue();
                }

                if (actualSubGuarantyTripAB != null && scheduledSubGuarantyTripAB != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ab");
                    guarantyTripsData.setGuarantyType("qve-საგარანტიო");
                    guarantyTripsData.setBaseNumber(actualSubGuarantyTripAB.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());

                    guarantyTripsData.setExodusScheduled(scheduledSubGuarantyTripAB.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledSubGuarantyTripAB.getStartTimeScheduled());

                    guarantyTripsData.setExodusActual(actualSubGuarantyTripAB.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeActual(actualSubGuarantyTripAB.getStartTimeActual());
                    guarantyTripsData.setExodusActualStartTimeScheduled(actualSubGuarantyTripAB.getStartTimeScheduled());
                    guarantyTripsData.setSpacialCase(actualSubGuarantyTripAB.isSpacialCase());
                    dayArray.add(guarantyTripsData);
                }

                if (actualGuarantyTripAB != null && scheduledGuarantyTripAB != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ab");
                    guarantyTripsData.setGuarantyType("საგარანტიო");
                    guarantyTripsData.setBaseNumber(actualGuarantyTripAB.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());

                    guarantyTripsData.setExodusScheduled(scheduledGuarantyTripAB.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledGuarantyTripAB.getStartTimeScheduled());
                    guarantyTripsData.setExodusActual(actualGuarantyTripAB.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeActual(actualGuarantyTripAB.getStartTimeActual());
                    guarantyTripsData.setExodusActualStartTimeScheduled(actualGuarantyTripAB.getStartTimeScheduled());
                    guarantyTripsData.setSpacialCase(actualGuarantyTripAB.isSpacialCase());
                    dayArray.add(guarantyTripsData);

                }

                //---------------BA-------------
                IntervalTripPeriod scheduledGuarantyTripBA = null;
                IntervalTripPeriod scheduledSubGuarantyTripBA = null;
                IntervalTripPeriod actualGuarantyTripBA = null;
                IntervalTripPeriod actualSubGuarantyTripBA = null;

                if (baTimetable.size() > 0) {
                    scheduledGuarantyTripBA = baTimetable.pollLastEntry().getValue();
                }
                if (baTimetable.size() > 0) {
                    scheduledSubGuarantyTripBA = baTimetable.pollLastEntry().getValue();
                }

                if (baGpsTimetable.size() > 0) {
                    actualGuarantyTripBA = (IntervalTripPeriod) baGpsTimetable.pollLastEntry().getValue();
                }
                if (baGpsTimetable.size() > 0) {
                    actualSubGuarantyTripBA = (IntervalTripPeriod) baGpsTimetable.pollLastEntry().getValue();
                }

                if (actualSubGuarantyTripBA != null && scheduledSubGuarantyTripBA != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ba");
                    guarantyTripsData.setGuarantyType("qve-საგარანტიო");
                    guarantyTripsData.setBaseNumber(actualSubGuarantyTripBA.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());

                    guarantyTripsData.setExodusScheduled(scheduledSubGuarantyTripBA.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledSubGuarantyTripBA.getStartTimeScheduled());
                    guarantyTripsData.setExodusActual(actualSubGuarantyTripBA.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeActual(actualSubGuarantyTripBA.getStartTimeActual());
                    guarantyTripsData.setExodusActualStartTimeScheduled(actualSubGuarantyTripBA.getStartTimeScheduled());
                    guarantyTripsData.setSpacialCase(actualSubGuarantyTripBA.isSpacialCase());
                    dayArray.add(guarantyTripsData);
                }

                if (actualGuarantyTripBA != null && scheduledGuarantyTripBA != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ba");
                    guarantyTripsData.setGuarantyType("საგარანტიო");
                    guarantyTripsData.setBaseNumber(actualGuarantyTripBA.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());

                    guarantyTripsData.setExodusScheduled(scheduledGuarantyTripBA.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledGuarantyTripBA.getStartTimeScheduled());
                    guarantyTripsData.setExodusActual(actualGuarantyTripBA.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeActual(actualGuarantyTripBA.getStartTimeActual());
                    guarantyTripsData.setExodusActualStartTimeScheduled(actualGuarantyTripBA.getStartTimeScheduled());
                    guarantyTripsData.setSpacialCase(actualGuarantyTripBA.isSpacialCase());
                    dayArray.add(guarantyTripsData);
                }
                returnValue.add(dayArray);
            }
        }
        return returnValue;
    }

}
