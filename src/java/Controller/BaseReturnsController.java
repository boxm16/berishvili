/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.BaseReturnsDao;
import Model.BaseReturn;
import Model.Day;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.Exodus;
import Model.TripPeriod;
import Model.TripVoucher;
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
public class BaseReturnsController {

    @Autowired
    private BaseReturnsDao baseReturnsDao;

    private MemoryUsage memoryUsage;

    public BaseReturnsController() {
        memoryUsage = new MemoryUsage();
    }

    @RequestMapping(value = "baseReturnsInitialRequest")
    public String baseReturnsInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager baseRerturnsPager = createDetailedRoutesPager(routeDates);
        baseRerturnsPager.setCurrentRoute("initial");

        TreeMap<Float, DetailedRoute> baseReturnsData = baseReturnsDao.getBaseReturnRoute(baseRerturnsPager);
        TreeMap<Float, DetailedRoute> routeWithCalculatedbaseReturnsData = calculateBaseReturns(baseReturnsData);
        //acctually you dont treeMap<FLoat, DetailedRoute>, because now there is only one route
        session.setAttribute("baseReturnsPager", baseRerturnsPager);
        model.addAttribute("baseReturnsPager", baseRerturnsPager);
        model.addAttribute("routeWithCalculatedbaseReturnsData", routeWithCalculatedbaseReturnsData);
        return "baseReturns";
    }

    @RequestMapping(value = "baseReturnsRequest")
    public String baseReturnsRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        DetailedRoutesPager baseReturnsPager = (DetailedRoutesPager) session.getAttribute("baseReturnsPager");
        if (baseReturnsPager == null) {
            return "errorPage";
        }
        baseReturnsPager.setCurrentRoute(requestedRoute);
        TreeMap<Float, DetailedRoute> baseReturnsData = baseReturnsDao.getBaseReturnRoute(baseReturnsPager);
        TreeMap<Float, DetailedRoute> routeWithCalculatedbaseReturnsData = calculateBaseReturns(baseReturnsData);
        model.addAttribute("baseReturnsPager", baseReturnsPager);
        model.addAttribute("routeWithCalculatedbaseReturnsData", routeWithCalculatedbaseReturnsData);
        return "baseReturns";
    }

    private DetailedRoutesPager createDetailedRoutesPager(String routeDates) {

        DetailedRoutesPager detailedRoutesPager = new DetailedRoutesPager("baseReturns");

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

    private TreeMap<Float, DetailedRoute> calculateBaseReturns(TreeMap<Float, DetailedRoute> baseReturnsData) {

        for (Map.Entry<Float, DetailedRoute> routeEntry : baseReturnsData.entrySet()) {

            TreeMap<Date, Day> days = routeEntry.getValue().getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                TreeMap<Short, Exodus> exoduses = dayEntry.getValue().getExoduses();
                for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                    TreeMap<String, TripVoucher> tripVouchers = exodusEntry.getValue().getTripVouchers();
                    for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                        BaseReturn baseReturn = (BaseReturn) tripVoucherEntry.getValue();
                        TreeMap<LocalDateTime, TripPeriod> tripPeriods = baseReturn.getTripPeriodsV3();

                        ArrayList<TripPeriod> tripPeriodsArray = new ArrayList<TripPeriod>(tripPeriods.values());
                        if (tripPeriodsArray.size() == 2) {
                            LocalDateTime arrivalTimeScheduled = tripPeriodsArray.get(0).getArrivalTimeScheduled();
                            LocalDateTime startTimeScheduled = tripPeriodsArray.get(1).getStartTimeScheduled();

                            LocalDateTime arrivalTimeActual = tripPeriodsArray.get(0).getArrivalTimeActual();
                            LocalDateTime startTimeActual = tripPeriodsArray.get(1).getStartTimeActual();
                            baseReturn.setLastHaltTimeScheduled(Duration.between(arrivalTimeScheduled, startTimeScheduled));

                            baseReturn.setBaseReturnTripStartTimeScheduled(tripPeriodsArray.get(1).getStartTimeScheduled());
                            baseReturn.setBaseReturnTripArrivalTimeScheduled(tripPeriodsArray.get(1).getArrivalTimeScheduled());

                            baseReturn.setBaseReturnTripStartTimeGPS(tripPeriodsArray.get(1).getStartTimeActual());
                            baseReturn.setBaseReturnTripArrivalTimeGPS(tripPeriodsArray.get(1).getArrivalTimeActual());

                            if (arrivalTimeActual != null && startTimeActual != null) {
                                baseReturn.setLastHaltTimeActual(Duration.between(arrivalTimeActual, startTimeActual));
                            }
                        } else {
                            System.out.println("Size=" + tripPeriodsArray.size()
                            );
                        }

                    }
                }
            }
        }

        return baseReturnsData;
    }

    @RequestMapping(value = "baseReturnsExcelExportDashboard")
    public String baseReturnsExcelExportDashboard(ModelMap model, HttpSession session) {

        DetailedRoutesPager baseReturnsPager = (DetailedRoutesPager) session.getAttribute("baseReturnsPager");
        if (baseReturnsPager == null) {
            return "errorPage";
        }
        model.addAttribute("excelExportLink", "exportBaseReturns.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "baseReturnsExcelExportDashboardInitialRequest")
    public String baseReturnsExcelExportDashboardInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager baseReturnsPager = createDetailedRoutesPager(routeDates);
        baseReturnsPager.setCurrentRoute("initial");
        session.setAttribute("baseReturns", baseReturnsPager);
        model.addAttribute("excelExportLink", "exportBaseReturns.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportBaseReturns", method = RequestMethod.POST)
    public String exportBaseReturns(String fileName, ModelMap model, HttpSession session, HttpServletRequest request) {
        System.out.println("---------------Base Returns Excel Export Starting ------------------------------");
        Instant start = Instant.now();
//first get data
        DetailedRoutesPager baseReturnsPager = (DetailedRoutesPager) session.getAttribute("baseReturnsPager");
        TreeMap<Float, DetailedRoute> baseReturnsData = baseReturnsDao.getBaseReturnData(baseReturnsPager);
        TreeMap<Float, DetailedRoute> routeWithCalculatedbaseReturnsData = calculateBaseReturns(baseReturnsData);
        System.out.println("---Base Returns Excel Export Data Created And Calculated------");
        memoryUsage.printMemoryUsage();

        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();

        System.out.println("---Writing Excel File Started---");
        memoryUsage.printMemoryUsage();
        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF_BaseReturns(routeWithCalculatedbaseReturnsData, fileName);

        model.addAttribute("excelExportLink", "exportBaseReturns.htm");
        model.addAttribute("fileName", fileName);
        Instant end = Instant.now();
        System.out.println("++++++++Base Returns Excel Export completed. Time needed:" + Duration.between(start, end) + "+++++++++++");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

}
