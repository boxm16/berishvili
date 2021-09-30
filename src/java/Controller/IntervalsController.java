/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.IntervalsDao;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import java.time.Duration;
import java.time.Instant;
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
public class IntervalsController {

    @Autowired
    private IntervalsDao intervalsDao;
    private MemoryUsage memoryUsage;

    public IntervalsController() {
        memoryUsage = new MemoryUsage();
    }

    @RequestMapping(value = "intervalsInitialRequest")
    public String intervalsInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager intervalsPager = createDetailedRoutesPager(routeDates);
        intervalsPager.setCurrentRoute("initial");
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(intervalsPager);
        session.setAttribute("intervalsPager", intervalsPager);

        detailedRoute.calculateIntervalsData();
        model.addAttribute("intervalsPager", intervalsPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "intervals";
    }

    @RequestMapping(value = "intervalsRequest")
    public String intervalsRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        DetailedRoutesPager intervalsPager = (DetailedRoutesPager) session.getAttribute("intervalsPager");
        if (intervalsPager == null) {
            return "errorPage";
        }
        intervalsPager.setCurrentRoute(requestedRoute);
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(intervalsPager);
        session.setAttribute("intervalsPager", intervalsPager);

        detailedRoute.calculateIntervalsData();
        model.addAttribute("intervalsPager", intervalsPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "intervals";
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

    @RequestMapping(value = "interval")
    public String interval(@RequestParam("routeNumber") String routeNumber, @RequestParam("startTimeScheduled") String startTimeScheduled, @RequestParam("tripPeriodType") String tripPeriodType, @RequestParam("dateStamp") String dateStamp, ModelMap model) {
        DetailedRoutesPager intervalsPager = new DetailedRoutesPager("intervals");
        intervalsPager.addRouteNumber(routeNumber);
        intervalsPager.addDateStamp(dateStamp);
        intervalsPager.setCurrentRoute("initial");
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(intervalsPager);

        detailedRoute.calculateIntervalsData();

        model.addAttribute("typeAnchor", tripPeriodType);
        model.addAttribute("startTimeAnchor", startTimeScheduled);

        model.addAttribute("intervalsPager", intervalsPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "interval";
    }

    //-------------------------excel export==-----------------------------
    @RequestMapping(value = "intervalsExcelExportDashboardInitialRequest")
    public String intervalsExcelExportDashboardInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager intervalsPager = createDetailedRoutesPager(routeDates);
        intervalsPager.setCurrentRoute("initial");
        session.setAttribute("intervalsPager", intervalsPager);
        model.addAttribute("excelExportLink", "exportIntervals.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "intervalsExcelExportDashboard")
    public String intervalsExcelExportDashboard(ModelMap model, HttpSession session) {

        DetailedRoutesPager intervalsPager = (DetailedRoutesPager) session.getAttribute("intervalsPager");
        if (intervalsPager == null) {
            return "errorPage";
        }
        model.addAttribute("excelExportLink", "exportIntervals.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportIntervals", method = RequestMethod.POST)
    public String exportIntervals(String fileName, ModelMap model, HttpSession session, HttpServletRequest request) {
        System.out.println("---------------Intervals Excel Export Starting ------------------------------");
        Instant start = Instant.now();
//first get data
        DetailedRoutesPager intervalsPager = (DetailedRoutesPager) session.getAttribute("intervalsPager");

        TreeMap<Float, DetailedRoute> detailedRoutes = intervalsDao.getRoutesForIntervalsForExcelExport(intervalsPager);
        System.out.println("---Intervals Excel Export Data Created------");
        for (Map.Entry<Float, DetailedRoute> detailedRouteEntry : detailedRoutes.entrySet()) {
            detailedRouteEntry.getValue().calculateIntervalsData();
        }
        System.out.println("---Intervals Data Calculations Completed------");
        memoryUsage.printMemoryUsage();

        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();

        System.out.println("---Writing Excel File Started---");
        memoryUsage.printMemoryUsage();
        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF_Intervals(detailedRoutes, fileName, request);

        model.addAttribute("excelExportLink", "exportIntervals.htm");
        model.addAttribute("fileName", fileName);
        Instant end = Instant.now();
        System.out.println("++++++++Intervals Excel Export completed. Time needed:" + Duration.between(start, end) + "+++++++++++");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

}
