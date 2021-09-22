package Controller;

import DAO.DetailedRouteDao;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import Model.Exodus;
import java.time.Duration;
import java.time.Instant;
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
public class DetailedRoutesController {

    @Autowired
    private DetailedRouteDao detailedRouteDao;
    private MemoryUsage memoryUsage;
    private Converter converter;

    public DetailedRoutesController() {
        memoryUsage = new MemoryUsage();
        converter = new Converter();
    }

    @RequestMapping(value = "detailedRoutesInitialRequest")
    public String detailedRoutesInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {

        DetailedRoutesPager detailedRoutesPager = createDetailedRoutesPager(routeDates);
        detailedRoutesPager.setCurrentRoute("initial");
        DetailedRoute detailedRoute = detailedRouteDao.getDetailedRoute(detailedRoutesPager);
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);

        if (session.getAttribute("percents") == null) {
            session.setAttribute("percents", 20);
        }//i have no idea why i have this here ??

        detailedRoute.calculateData();
        model.addAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "detailedRoutes";
    }

    @RequestMapping(value = "detailedRoutesRequest")
    public String detailedRoutesRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        DetailedRoutesPager detailedRoutesPager = (DetailedRoutesPager) session.getAttribute("detailedRoutesPager");
        if (detailedRoutesPager == null) {
            return "errorPage";
        }
        detailedRoutesPager.setCurrentRoute(requestedRoute);
        DetailedRoute detailedRoute = detailedRouteDao.getDetailedRoute(detailedRoutesPager);
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);

        detailedRoute.calculateData();
        model.addAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("detailedRoute", detailedRoute);
        return "detailedRoutes";
    }

    private DetailedRoutesPager createDetailedRoutesPager(String routeDates) {

        DetailedRoutesPager detailedRoutesPager = new DetailedRoutesPager("detailedRoutes");

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

    @RequestMapping(value = "exodus")
    public String exodus(@RequestParam("routeNumber") String routeNumber, @RequestParam("dateStamp") String dateStamp, @RequestParam("exodusNumber") String exodusNumber, @RequestParam("startTimeScheduled") String startTimeScheduled, ModelMap model, HttpSession session) {

        DetailedRoute detailedRoute = detailedRouteDao.getDetailedRouteExodus(routeNumber, dateStamp, exodusNumber);
        detailedRoute.calculateData();
        Date requestedDate = converter.convertDateStampDatabaseFormatToDate(dateStamp);
        Exodus requestedExodus = detailedRoute.getDays().get(requestedDate).getExoduses().get(Short.valueOf(exodusNumber));
        TreeMap<Short, Exodus> requestedExodusMap = new TreeMap();
        requestedExodusMap.put(Short.valueOf(exodusNumber), requestedExodus);
        detailedRoute.getDays().get(requestedDate).setExoduses(requestedExodusMap);
        model.addAttribute("detailedRoute", detailedRoute);
        model.addAttribute("anchor", startTimeScheduled);
        String exodusHeader = "მარშრუტი # " + routeNumber + ", " + dateStamp + ", გასვლა #" + exodusNumber;
        model.addAttribute("exodusHeader", exodusHeader);

        return "exodus";
    }

    @RequestMapping(value = "detailedRoutesExcelExportDashboardInitialRequest")
    public String detailedRoutesExcelExportDashboardInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager detailedRoutesPager = createDetailedRoutesPager(routeDates);
        detailedRoutesPager.setCurrentRoute("initial");
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("excelExportLink", "exportDetailedRoutes.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "detailedRoutesExcelExportDashboard")
    public String detailedRoutesExcelExportDashboard(ModelMap model, HttpSession session) {

        DetailedRoutesPager detailedRoutesPager = (DetailedRoutesPager) session.getAttribute("detailedRoutesPager");
        if (detailedRoutesPager == null) {
            return "errorPage";
        }
        model.addAttribute("excelExportLink", "exportDetailedRoutes.htm");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportDetailedRoutes", method = RequestMethod.POST)
    public String exportDetailedRoutes(String fileName, ModelMap model, HttpSession session, HttpServletRequest request) {
        System.out.println("---------------Detailed Routes Excel Export Starting ------------------------------");
        Instant start = Instant.now();
//first get data

        DetailedRoutesPager detailedRoutesPager = (DetailedRoutesPager) session.getAttribute("detailedRoutesPager");

        TreeMap<Float, DetailedRoute> detailedRoutes = detailedRouteDao.getDetailedRoutes(detailedRoutesPager);
        System.out.println("---Detailed Routes Excel Export Data Created------");
        for (Map.Entry<Float, DetailedRoute> detailedRouteEntry : detailedRoutes.entrySet()) {
            detailedRouteEntry.getValue().calculateData();
        }
        System.out.println("---Detailed Routes Calculations Completed------");
        memoryUsage.printMemoryUsage();

        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();

        System.out.println("---Writing Excel File Started---");
        memoryUsage.printMemoryUsage();
        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF_DetailedRoutes(detailedRoutes, fileName);

        model.addAttribute("excelExportLink", "exportDetailedRoutes.htm");
        model.addAttribute("fileName", fileName);
        Instant end = Instant.now();
        System.out.println("++++++++Detailed Routes Excel Export completed. Time needed:" + Duration.between(start, end) + "+++++++++++");
        model.addAttribute("message", "");
        return "excelExportDashboard";
    }
}
