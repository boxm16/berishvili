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
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GuarantyVController {

    @Autowired
    private GuarantyVDao guarantyDao;
    @Autowired
    private RouteDao routeDao;
    private MemoryUsage memoryUsage;

    public GuarantyVController() {
        memoryUsage = new MemoryUsage();
    }

    //----------
    @RequestMapping(value = "guarantyTripsInitialRequest")
    public String guarantyTripsInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager guarantyTrisPager = createDetailedRoutesPager(routeDates);
        TreeMap<Float, DetailedRoute> routesForIntervalsForExcelExport = guarantyDao.getRoutesForIntervalsForExcelExport(guarantyTrisPager);
        TreeMap<Float, RouteData> routesDataFromDB = routeDao.getRoutesDataFromDB();
        ArrayList guarantyData = getGuarantyDataV(routesForIntervalsForExcelExport, routesDataFromDB, routesDataFromDB);

        session.setAttribute("guarantyTrisPager", guarantyTrisPager);
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

    private ArrayList getGuarantyDataV(TreeMap<Float, DetailedRoute> routesForIntervalsForExcelExport, TreeMap<Float, RouteData> routesDataFromDB, TreeMap<Float, RouteData> routesDataFromDB2) {
        ArrayList returnValue = new ArrayList();
        for (Map.Entry<Float, DetailedRoute> intervalRouteEntry : routesForIntervalsForExcelExport.entrySet()) {
            DetailedRoute route = intervalRouteEntry.getValue();
            route.calculateIntervalsDataVVersion();

            RouteData routeData = routesDataFromDB2.get(intervalRouteEntry.getKey());
            String aPoint = routeData.getaPoint();
            String bPoint = routeData.getbPoint();

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
                //-------
                //-------
                /*
                if (actualGuarantyTripAB == scheduledSubGuarantyTripAB) {
                    actualSubGuarantyTripAB = actualGuarantyTripAB;
                    actualGuarantyTripAB = new IntervalTripPeriod();
                }
                 */
                if (actualGuarantyTripAB == scheduledSubGuarantyTripAB) {
                    if (actualGuarantyTripAB == null
                            || scheduledGuarantyTripAB == null
                            || actualGuarantyTripAB.getStartTimeActual() == null
                            || scheduledGuarantyTripAB.getStartTimeScheduled() == null) {
                        //do nothing
                    } else {
                        if (actualGuarantyTripAB.getStartTimeActual().isAfter(scheduledGuarantyTripAB.getStartTimeScheduled())) {
                            //again, do nothing, keep things untouched
                        } else {
                            Duration durationTillGuarantyTrip = Duration.between(actualGuarantyTripAB.getStartTimeActual(), scheduledGuarantyTripAB.getStartTimeScheduled());
                            Duration durationTillSubGuarantyTrip = Duration.between(actualGuarantyTripAB.getStartTimeActual(), scheduledSubGuarantyTripAB.getStartTimeScheduled());
                            if (durationTillGuarantyTrip.getSeconds() <= durationTillSubGuarantyTrip.getSeconds()) {
                                // and again, do nothing
                            } else {
                                actualSubGuarantyTripAB = actualGuarantyTripAB;
                                actualGuarantyTripAB = new IntervalTripPeriod();
                            }
                        }
                    }
                }

                //---------
                if (scheduledSubGuarantyTripAB != null) {

                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ab");
                    guarantyTripsData.setGuarantyType("ქვე-საგარანტიო");
                    guarantyTripsData.setBaseNumber(scheduledSubGuarantyTripAB.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setaPoint(aPoint);
                    guarantyTripsData.setbPoint(bPoint);

                    guarantyTripsData.setDateStamp(day.getDateStamp());

                    guarantyTripsData.setExodusScheduled(scheduledSubGuarantyTripAB.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledSubGuarantyTripAB.getStartTimeScheduled());
                    if (actualSubGuarantyTripAB == null) {
                        //do nothing
                    } else {
                        guarantyTripsData.setExodusActual(actualSubGuarantyTripAB.getExodusNumber());
                        guarantyTripsData.setDriverName(actualSubGuarantyTripAB.getDriverName());
                        guarantyTripsData.setBusNumber(actualSubGuarantyTripAB.getBusNumber());
                        guarantyTripsData.setGuarantyStartTimeActual(actualSubGuarantyTripAB.getStartTimeActual());
                        guarantyTripsData.setExodusActualStartTimeScheduled(actualSubGuarantyTripAB.getStartTimeScheduled());
                        guarantyTripsData.setSpacialCase(actualSubGuarantyTripAB.isSpacialCase());

                    }
                    dayArray.add(guarantyTripsData);
                }

                if (scheduledGuarantyTripAB != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ab");
                    guarantyTripsData.setGuarantyType("საგარანტიო");
                    guarantyTripsData.setBaseNumber(scheduledGuarantyTripAB.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());
                    guarantyTripsData.setaPoint(aPoint);
                    guarantyTripsData.setbPoint(bPoint);

                    guarantyTripsData.setExodusScheduled(scheduledGuarantyTripAB.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledGuarantyTripAB.getStartTimeScheduled());
                    if (actualGuarantyTripAB == null) {
                        //do nothing
                    } else {
                        guarantyTripsData.setExodusActual(actualGuarantyTripAB.getExodusNumber());
                        guarantyTripsData.setDriverName(actualGuarantyTripAB.getDriverName());
                        guarantyTripsData.setBusNumber(actualGuarantyTripAB.getBusNumber());
                        guarantyTripsData.setGuarantyStartTimeActual(actualGuarantyTripAB.getStartTimeActual());
                        guarantyTripsData.setExodusActualStartTimeScheduled(actualGuarantyTripAB.getStartTimeScheduled());
                        guarantyTripsData.setSpacialCase(actualGuarantyTripAB.isSpacialCase());
                    }
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

                //-------
                /*if (actualGuarantyTripBA == scheduledSubGuarantyTripBA) {
                    actualSubGuarantyTripBA = actualGuarantyTripBA;
                    actualGuarantyTripBA = new IntervalTripPeriod();
                }
                 */
                if (actualGuarantyTripBA == scheduledSubGuarantyTripBA) {
                    if (actualGuarantyTripBA == null
                            || scheduledGuarantyTripBA == null
                            || actualGuarantyTripBA.getStartTimeActual() == null
                            || scheduledGuarantyTripBA.getStartTimeScheduled() == null) {
                        //do nothing
                    } else {
                        if (actualGuarantyTripBA.getStartTimeActual().isAfter(scheduledGuarantyTripBA.getStartTimeScheduled())) {
                            //again, do nothing, keep things untouched
                        } else {
                            Duration durationTillGuarantyTrip = Duration.between(actualGuarantyTripBA.getStartTimeActual(), scheduledGuarantyTripBA.getStartTimeScheduled());
                            Duration durationTillSubGuarantyTrip = Duration.between(actualGuarantyTripBA.getStartTimeActual(), scheduledSubGuarantyTripBA.getStartTimeScheduled());
                            if (durationTillGuarantyTrip.getSeconds() <= durationTillSubGuarantyTrip.getSeconds()) {
                                // and again, do nothing
                            } else {
                                actualSubGuarantyTripBA = actualGuarantyTripBA;
                                actualGuarantyTripBA = new IntervalTripPeriod();
                            }
                        }
                    }
                }

                //---------
                if (scheduledSubGuarantyTripBA != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ba");
                    guarantyTripsData.setGuarantyType("ქვე-საგარანტიო");
                    guarantyTripsData.setBaseNumber(scheduledSubGuarantyTripBA.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());
                    guarantyTripsData.setaPoint(aPoint);
                    guarantyTripsData.setbPoint(bPoint);

                    guarantyTripsData.setExodusScheduled(scheduledSubGuarantyTripBA.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledSubGuarantyTripBA.getStartTimeScheduled());
                    if (actualSubGuarantyTripBA == null) {
                        //do nothing
                    } else {
                        guarantyTripsData.setExodusActual(actualSubGuarantyTripBA.getExodusNumber());
                        guarantyTripsData.setDriverName(actualSubGuarantyTripBA.getDriverName());
                        guarantyTripsData.setBusNumber(actualSubGuarantyTripBA.getBusNumber());
                        guarantyTripsData.setGuarantyStartTimeActual(actualSubGuarantyTripBA.getStartTimeActual());
                        guarantyTripsData.setExodusActualStartTimeScheduled(actualSubGuarantyTripBA.getStartTimeScheduled());
                        guarantyTripsData.setSpacialCase(actualSubGuarantyTripBA.isSpacialCase());
                    }
                    dayArray.add(guarantyTripsData);
                }
                if (scheduledGuarantyTripBA != null) {
                    GuarantyTripsData guarantyTripsData = new GuarantyTripsData();
                    guarantyTripsData.setType("ba");
                    guarantyTripsData.setGuarantyType("საგარანტიო");
                    guarantyTripsData.setBaseNumber(scheduledGuarantyTripBA.getBaseNumber());
                    guarantyTripsData.setRouteNumber(route.getNumber());
                    guarantyTripsData.setDateStamp(day.getDateStamp());
                    guarantyTripsData.setaPoint(aPoint);
                    guarantyTripsData.setbPoint(bPoint);

                    guarantyTripsData.setExodusScheduled(scheduledGuarantyTripBA.getExodusNumber());
                    guarantyTripsData.setGuarantyStartTimeScheduled(scheduledGuarantyTripBA.getStartTimeScheduled());
                    if (actualGuarantyTripBA == null) {
                        //do nothing
                    } else {
                        guarantyTripsData.setExodusActual(actualGuarantyTripBA.getExodusNumber());
                        guarantyTripsData.setDriverName(actualGuarantyTripBA.getDriverName());
                        guarantyTripsData.setBusNumber(actualGuarantyTripBA.getBusNumber());
                        guarantyTripsData.setGuarantyStartTimeActual(actualGuarantyTripBA.getStartTimeActual());
                        guarantyTripsData.setExodusActualStartTimeScheduled(actualGuarantyTripBA.getStartTimeScheduled());
                        guarantyTripsData.setSpacialCase(actualGuarantyTripBA.isSpacialCase());
                    }
                    dayArray.add(guarantyTripsData);
                }
                returnValue.add(dayArray);
            }
        }
        return returnValue;
    }

    @RequestMapping(value = "guarantyExplanation")
    public String explanation(ModelMap model) {
        String explanation = " <center>    <h1>საგარანტიოს და ქვე-საგარანტიოს გამოთვლები<h1></center>\n"
                + "\n"
                + "                <h3>\n"
                + "\n"
                + "                    იმისთვის რომ რეისი (საგარანტიო თუ ქვესაგარანტიო) ჩაითვალოს შესრულებულად უნდა ფიქსირდებოდეს მისვლის ფაქტიური დრო. \n"
                + "                    სხვა შემთხვევაში რეისი ითვლება არშესრულებულად(გაუქმებულად).<br>\n"
                + "\n"
                + "                    თუ საგარანტიოს და ქვესაგარანტიო რეისებს არ უფიქსირდებათ ფაქტიური გასვლი დრო \n"
                + "                    (ფაქტიური მისვლის დრო როგორც ვთქვით უფიქსირდება ყოველთვის) რეისი მაინც  ითვლება შესრულებულად. \n"
                + "                    გასვლის ფაქტიურ დრო კი რჩება ცარიელი (null) მაგრამ შემოდის ეხალი მცნება, \n"
                + "                    fake(ვითომ) ფაქტიური გასვლას დრო, და ეს დრო გამოითვლება ამ რეისის და სხვა მომიჯნავე რეისების\n"
                + "                    მისვლის ფაქტიური დროის მიხედვით, და ამ fake ფაქტიური გასვლის მიხედვით ხდება ამ რეისისთვის \n"
                + "                    სათანადო ადგილის მიჩინება გასვლების ცხრილში.\n"
                + "\n"
                + "                    <br>\n"
                + "                    თუ ბოლოს რეისი (საგარანტიო) სინამდვილეში არის გასვლა (ავტობუსი) რომელსაც უნდა შეესრულებინა \n"
                + "                    ქვესაგარანტიო რეისი, იმის დადგენა თუ ეს რეისი უნდა ჩაითვალოს საგარანტიო თუ ქვესაგარანთიო ხდება \n"
                + "                    შემდეგნაირად. თუ ამ რეისის ფაქტიური გასვლის დრო არის უფრო გვიან ვიდრე გეგმიური საგარანტიო რეისის \n"
                + "                    გეგმიუირი გასვლის დრო, მაშინ ეს რეისი ჩაითვლება ბოლო/საგარანტიო რეისად, და შესაბამისად მის წინ რეისი\n"
                + "                    ჩაითვლება ქვესაგარანტიოდ. \n"
                + "                    <br>\n"
                + "                    თუკი ეს დრო(ანუ ამ რეისის ფაქტიური გასვლის დრო) არის უფრო ადრე ვიდრე გეგმიური საგარანტიო რეისის\n"
                + "                    გეგმიუირ გასვლის დრო მაშინ ხდება ამ დროის შედარება გეგმიური საგარანტიო და გეგმიური ქვესაგარანტიო \n"
                + "                    რეისების გეგმიური გასვლის დროსთანაც. თუ ის არის უფრო ახლოს  გეგმიური საგარანტიო რეისის გეგმიუირ \n"
                + "                    გასვლის დროსთან ეს რეისი ისევე ჩაითვლება  ბოლო/საგარანტიო რეისად, და შესაბამისად მის წინ რეისი \n"
                + "                    ჩაითვლება ქვესაგარანტიოდ. <br>\n"
                + "                    თუკი ის უფრო ახლოს არის გეგმიურ ქვესაგარანტიო რეისის გეგმიურ გასვლის დროსთან მაშინ ეს რეისი ჩაითვლება\n"
                + "                    ქვესაგარანტიოდ და ფაქტიური საგარანტიო რეისი ჩაივლება რომ საერთოდ არ შესრულებულა\n"
                + "                </h3>";
        model.addAttribute("explanation", explanation);
        return "explanations";
    }

    //-------------------------excel export==-----------------------------
    @RequestMapping(value = "guarantyTripsExcelExportDashboardInitialRequest")
    public String guarantyTripsExcelExportDashboardInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager guarantyTrisPager = createDetailedRoutesPager(routeDates);
        guarantyTrisPager.setCurrentRoute("initial");
        session.setAttribute("guarantyTrisPager", guarantyTrisPager);
        model.addAttribute("excelExportLink", "exportGuarantyTrips.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "guarantyTripsExcelExportDashboard")
    public String guarantyTripsExcelExportDashboard(ModelMap model, HttpSession session) {

        DetailedRoutesPager guarantyTrisPager = (DetailedRoutesPager) session.getAttribute("guarantyTrisPager");
        if (guarantyTrisPager == null) {
            return "errorPage";
        }
        model.addAttribute("excelExportLink", "exportGuarantyTrips.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportGuarantyTrips", method = RequestMethod.POST)
    public String exportGuarantyTrips(String fileName, ModelMap model, HttpSession session, HttpServletRequest request) {
        System.out.println("---------------Guaranty Trips Excel Export Starting ------------------------------");
        Instant start = Instant.now();
//first get data
        DetailedRoutesPager guarantyTrisPager = (DetailedRoutesPager) session.getAttribute("guarantyTrisPager");

        TreeMap<Float, DetailedRoute> routesForIntervalsForExcelExport = guarantyDao.getRoutesForIntervalsForExcelExport(guarantyTrisPager);
        TreeMap<Float, RouteData> routesDataFromDB = routeDao.getRoutesDataFromDB();
        ArrayList guarantyData = getGuarantyDataV(routesForIntervalsForExcelExport, routesDataFromDB, routesDataFromDB);
        System.out.println("---Guaranty Trips Excel Export Data Created------");

        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();

        System.out.println("---Writing Excel File Started---");
        memoryUsage.printMemoryUsage();
        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF_GuarantyTrips(guarantyData, fileName, request);

        model.addAttribute("excelExportLink", "exportGuarantyTrips.htm");
        model.addAttribute("fileName", fileName);
        Instant end = Instant.now();
        System.out.println("++++++++Guaranty Trips Excel Export completed. Time needed:" + Duration.between(start, end) + "+++++++++++");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }
}
