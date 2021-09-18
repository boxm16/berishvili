package Controller;

import DAO.RouteDao;
import Model.RouteAverages;
import Model.TripPeriod2X;
import Model.TripPeriodsFilter;
import Model.TripPeriodsPager;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
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
public class TripPeriodsController {

    @Autowired
    private RouteDao routeDao;

    private MemoryUsage memoryUsage;

    public TripPeriodsController() {
        memoryUsage = new MemoryUsage();
    }

    @RequestMapping(value = "tripPeriodsInitialRequest")
    public String tripPeriodInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {

        TripPeriodsFilter tripPeriodsInitialFilter = convertSelectedRoutesToTripPeriodFilter(routeDates);
        session.setAttribute("tripPeriodsInitialFilter", tripPeriodsInitialFilter);

        ArrayList<TripPeriod2X> initialTripPeriods = routeDao.getInitialTripPeriods(tripPeriodsInitialFilter);

        int rowLimit = 200;
        TripPeriodsPager tripPeriodsPager = routeDao.getTripPeriodsPager(tripPeriodsInitialFilter, rowLimit);
        tripPeriodsPager.setCurrentPage(1);
        model.addAttribute("tripPeriods", initialTripPeriods);
        model.addAttribute("tripPeriodsPager", tripPeriodsPager);
        session.setAttribute("tripPeriodsPager", tripPeriodsPager);
        session.setAttribute("tripPeriodsInitialFilter", tripPeriodsInitialFilter);
        session.setAttribute("rowLimit", rowLimit);
        if (session.getAttribute("percents") == null) {
            session.setAttribute("percents", 20);
        }
        /*
        TripPeriodsFilter tripPeriodsFullInitialFilter = routeDao.getTripPeriodsFullInitialFilter(tripPeriodsInitialFilter);
        session.setAttribute("tripPeriodsFullInitialFilter", tripPeriodsFullInitialFilter);
        TripPeriodsFilter tripPeriodsCurrentFilter = tripPeriodsFullInitialFilter.getDeepCopy();
        session.setAttribute("tripPeriodsCurrentFilter", tripPeriodsCurrentFilter);
         */
        return "tripPeriods";
    }

    @RequestMapping(value = "tripPeriodsRequest")
    private String tripPeriodsRequest(@RequestParam(value = "requestedPage") String requestedPage, HttpSession session, ModelMap model) {
        TripPeriodsPager tripPeriodsPager = (TripPeriodsPager) session.getAttribute("tripPeriodsPager");
        int requestedPageNumber;

        int rowLimit = (int) session.getAttribute("rowLimit");

        try {
            requestedPageNumber = Integer.valueOf(requestedPage);

        } catch (NumberFormatException nfe) {
            return "errorPage";
        }
        tripPeriodsPager.setCurrentPage(requestedPageNumber);

        model.addAttribute("tripPeriodsPager", tripPeriodsPager);
        session.setAttribute("tripPeriodsPager", tripPeriodsPager);
        int startIndex = (requestedPageNumber - 1) * rowLimit;

        TripPeriodsFilter tripPeriodsFilter = (TripPeriodsFilter) session.getAttribute("tripPeriodsInitialFilter");
        ArrayList<TripPeriod2X> tripPeriods = routeDao.getTripPeriods(tripPeriodsFilter, startIndex, rowLimit);
        model.addAttribute("tripPeriods", tripPeriods);
        return "tripPeriods";
    }

    private TripPeriodsFilter convertSelectedRoutesToTripPeriodFilter(String routeDates) {
        TripPeriodsFilter tripPeriodsFilter = new TripPeriodsFilter();

        //trimming and cleaning input
        routeDates = routeDates.trim();
        if (routeDates.length() == 0) {
            return tripPeriodsFilter;
        }
        if (routeDates.substring(routeDates.length() - 1, routeDates.length()).equals(",")) {
            routeDates = routeDates.substring(0, routeDates.length() - 1).trim();
        }
        String[] routeDatesArray = routeDates.split(",");
        for (String routeDate : routeDatesArray) {

            String[] routeDateArray = routeDate.split(":");
            String routeNumber = routeDateArray[0];
            String dateStamp = routeDateArray[1];
            tripPeriodsFilter.addRouteNumber(routeNumber);
            tripPeriodsFilter.addDateStamp(dateStamp);
        }
        return tripPeriodsFilter;
    }

    //----------
    @RequestMapping(value = "tripPeriodsFilterDashboard")
    public String filterChoice(HttpSession session) {

        return "tripPeriodsFilterDashboard";
    }

    @RequestMapping(value = "tripPeriodsFilterChoice")
    public String filter(@RequestParam(value = "target") String targetFilter, HttpSession session, ModelMap model) {
        TripPeriodsFilter tripPeriodsFilter = (TripPeriodsFilter) session.getAttribute("tripPeriodsCurrentFilter");
        if (targetFilter.equals("routeNumbers")) {
            model.addAttribute("targetFilterTrigger", "routeNumbers");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getRouteNumbers());
        }
        if (targetFilter.equals("dateStamps")) {
            model.addAttribute("targetFilterTrigger", "dateStamps");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getDateStamps());
        }
        if (targetFilter.equals("busNumbers")) {
            model.addAttribute("targetFilterTrigger", "busNumbers");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getBusNumbers());
        }
        if (targetFilter.equals("exodusNumbers")) {
            model.addAttribute("targetFilterTrigger", "exodusNumbers");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getExodusNumbers());
        }
        if (targetFilter.equals("driverNames")) {
            model.addAttribute("targetFilterTrigger", "driverNames");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getDriverNames());
        }
        if (targetFilter.equals("tripPeriodTypes")) {
            model.addAttribute("targetFilterTrigger", "tripPeriodTypes");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getTripPeriodTypes());
        }
        if (targetFilter.equals("startTimesScheduled")) {
            model.addAttribute("targetFilterTrigger", "startTimesScheduled");
            model.addAttribute("targetFilterBody", tripPeriodsFilter.getStartTimesScheduled());
        }
        return "tripPeriodsFilterDashboard";
    }

    @RequestMapping(value = "filterTripPeriods", method = RequestMethod.POST)
    public String filterTripPeriods(HttpSession session, @RequestParam String trigger, @RequestParam String inputString, ModelMap model) {
        TripPeriodsFilter tripPeriodsCurrentFilter = (TripPeriodsFilter) session.getAttribute("tripPeriodsCurrentFilter");
        TreeMap<String, String> triggeredFilter = getTriggerFilter(inputString);
        TripPeriodsFilter tripPeriodsFullInitialFilter = (TripPeriodsFilter) session.getAttribute("tripPeriodsFullInitialFilter");
        if (trigger.equals("routeNumbers")) {
            tripPeriodsCurrentFilter.setRouteNumbers(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setRouteNumbers(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "routeNumbers");

        }
        if (trigger.equals("dateStamps")) {
            tripPeriodsCurrentFilter.setDateStamps(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setDateStamps(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "dateStamps");

        }
        if (trigger.equals("busNumbers")) {
            tripPeriodsCurrentFilter.setBusNumbers(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setBusNumbers(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "busNumbers");

        }
        if (trigger.equals("exodusNumbers")) {
            tripPeriodsCurrentFilter.setExodusNumbers(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setExodusNumbers(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "exodusNumbers");

        }
        if (trigger.equals("driverNames")) {
            tripPeriodsCurrentFilter.setDriverNames(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setDriverNames(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "driverNames");

        }
        if (trigger.equals("tripPeriodTypes")) {
            tripPeriodsCurrentFilter.setTripPeriodTypes(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setTripPeriodTypes(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "tripPeriodTypes");

        }
        if (trigger.equals("startTimesScheduled")) {
            tripPeriodsCurrentFilter.setStartTimesScheduled(triggeredFilter);
            tripPeriodsCurrentFilter = routeDao.getFilteredTripPeriodsFilter(tripPeriodsCurrentFilter, trigger);
            tripPeriodsCurrentFilter.setStartTimesScheduled(triggeredFilter);
            tripPeriodsFullInitialFilter = resetInitialFilter(tripPeriodsFullInitialFilter, triggeredFilter, "startTimesScheduled");

        }

        session.setAttribute("tripPeriodsFullInitialFilter", tripPeriodsFullInitialFilter);
        tripPeriodsCurrentFilter = refactorFiler(tripPeriodsFullInitialFilter, tripPeriodsCurrentFilter);
        session.setAttribute("tripPeriodsCurrentFilter", tripPeriodsCurrentFilter);
        return "tripPeriodsFilterDashboard";
    }

    private TreeMap<String, String> getTriggerFilter(String inputString) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        //trimming and cleaning input
        inputString = inputString.trim();
        if (inputString.length() == 0) {
            return treeMap;
        }
        if (inputString.substring(inputString.length() - 1, inputString.length()).equals(",")) {
            inputString = inputString.substring(0, inputString.length() - 1).trim();
        }
        String[] inputStringArray = inputString.split(",");
        for (String entryValue : inputStringArray) {

            String[] entryValueArray = entryValue.split(":");
            String entry = entryValueArray[0];
            String value = entryValueArray[1];
            treeMap.put(entry, value);
        }
        return treeMap;
    }

//-----------------//---------------------//---------------
    @RequestMapping(value = "tripPeriodsFilterRequest")
    public String tripPeriodsFilterRequest(ModelMap model, HttpSession session) {
        System.out.println("tripPeriodsFilterRequest");
        return "tripPeriodsFilter";
    }

    @RequestMapping(value = "tripPeriodsFilter", method = RequestMethod.POST)
    public String tripPeriodsFilter(HttpSession session, ModelMap model,
            @RequestParam(value = "blockIndex") String blockIndex,
            @RequestParam(value = "triggerFilter") String triggerFilter,
            @RequestParam(value = "routeNumbers", required = false) ArrayList<String> routeNumbers,
            @RequestParam(value = "dateStamps", required = false) ArrayList<String> dateStamps,
            @RequestParam(value = "busNumbers", required = false) ArrayList<String> busNumbers,
            @RequestParam(value = "exodusNumbers", required = false) ArrayList<String> exodusNumbers,
            @RequestParam(value = "driverNames", required = false) ArrayList<String> driverNames,
            @RequestParam(value = "tripPeriodTypes", required = false) ArrayList<String> tripPeriodTypes,
            @RequestParam(value = "startTimesScheduled", required = false) ArrayList<String> startTimesScheduled,
            @RequestParam(value = "startTimesActual", required = false) ArrayList<String> startTimesActual,
            @RequestParam(value = "arrivalTimesScheduled", required = false) ArrayList<String> arrivalTimesScheduled,
            @RequestParam(value = "arrivalTimesActual", required = false) ArrayList<String> arrivalTimesActual,
            @RequestParam(value = "tripPeriodTimesScheduled", required = false) ArrayList<String> tripPeriodTimesScheduled,
            @RequestParam(value = "tripPeriodTimesActual", required = false) ArrayList<String> tripPeriodTimesActual,
            @RequestParam(value = "tripPeriodTimeScheduled", required = false) ArrayList<String> tripPeriodTimeScheduled,
            @RequestParam(value = "tripPeriodTimesDifference", required = false) ArrayList<String> tripPeriodTimesDifference) {

        System.out.println("tripPeriodsFilter");
        return "tripPeriods";
    }

    private TripPeriodsFilter resetInitialFilter(TripPeriodsFilter tripPeriodsFullInitialFilter, TreeMap<String, String> triggeredFilter, String trigger) {
        if (trigger.equals("routeNumbers")) {
            TreeMap<String, String> routeNumbers = tripPeriodsFullInitialFilter.getRouteNumbers();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                routeNumbers.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setRouteNumbers(routeNumbers);
        }
        if (trigger.equals("dateStamps")) {
            TreeMap<String, String> dateStamps = tripPeriodsFullInitialFilter.getDateStamps();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                dateStamps.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setDateStamps(dateStamps);
        }
        if (trigger.equals("busNumbers")) {
            TreeMap<String, String> busNumbers = tripPeriodsFullInitialFilter.getBusNumbers();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                busNumbers.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setBusNumbers(busNumbers);
        }
        if (trigger.equals("exodusNumbers")) {
            TreeMap<String, String> exodusNumbers = tripPeriodsFullInitialFilter.getExodusNumbers();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                exodusNumbers.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setExodusNumbers(exodusNumbers);
        }
        if (trigger.equals("driverNames")) {
            TreeMap<String, String> driverNames = tripPeriodsFullInitialFilter.getDriverNames();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                driverNames.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setDriverNames(driverNames);
        }
        if (trigger.equals("tripPeriodTypes")) {
            TreeMap<String, String> tripPeriodTypes = tripPeriodsFullInitialFilter.getTripPeriodTypes();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                tripPeriodTypes.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setTripPeriodTypes(tripPeriodTypes);
        }
        if (trigger.equals("startTimesScheduled")) {
            TreeMap<String, String> startTimesScheduled = tripPeriodsFullInitialFilter.getStartTimesScheduled();
            for (Map.Entry<String, String> entry : triggeredFilter.entrySet()) {
                startTimesScheduled.put(entry.getKey(), entry.getValue());
            }
            tripPeriodsFullInitialFilter.setStartTimesScheduled(startTimesScheduled);
        }

        return tripPeriodsFullInitialFilter;
    }

    private TripPeriodsFilter refactorFiler(TripPeriodsFilter tripPeriodsFullInitialFilter, TripPeriodsFilter tripPeriodsCurrentFilter) {

        TreeMap<String, String> routeNumbers = tripPeriodsCurrentFilter.getRouteNumbers();
        for (Map.Entry<String, String> entry : routeNumbers.entrySet()) {
            String kk = entry.getKey();
            String savedValue = tripPeriodsFullInitialFilter.getRouteNumbers().get(kk);
            routeNumbers.put(kk, savedValue);
        }
        tripPeriodsCurrentFilter.setRouteNumbers(routeNumbers);
        //------
        TreeMap<String, String> dateStamps = tripPeriodsCurrentFilter.getDateStamps();
        for (Map.Entry<String, String> entry : dateStamps.entrySet()) {
            String kk = entry.getKey();
            String savedValue = tripPeriodsFullInitialFilter.getDateStamps().get(kk);
            dateStamps.put(kk, savedValue);
        }
        tripPeriodsCurrentFilter.setDateStamps(dateStamps);
        //------------------
        //------
        TreeMap<String, String> busNumbers = tripPeriodsCurrentFilter.getBusNumbers();
        for (Map.Entry<String, String> entry : busNumbers.entrySet()) {
            String kk = entry.getKey();
            String savedValue = tripPeriodsFullInitialFilter.getBusNumbers().get(kk);
            busNumbers.put(kk, savedValue);
        }
        tripPeriodsCurrentFilter.setBusNumbers(busNumbers);
        //------------------
        //------
        TreeMap<String, String> exodusNumbers = tripPeriodsCurrentFilter.getExodusNumbers();
        for (Map.Entry<String, String> entry : exodusNumbers.entrySet()) {
            String kk = entry.getKey();
            String savedValue = tripPeriodsFullInitialFilter.getExodusNumbers().get(kk);
            exodusNumbers.put(kk, savedValue);
        }
        tripPeriodsCurrentFilter.setExodusNumbers(exodusNumbers);
        //------------------
        //------------------
        //------
        TreeMap<String, String> tripPeriodTypes = tripPeriodsCurrentFilter.getTripPeriodTypes();
        for (Map.Entry<String, String> entry : tripPeriodTypes.entrySet()) {
            String kk = entry.getKey();
            String savedValue = tripPeriodsFullInitialFilter.getTripPeriodTypes().get(kk);
            tripPeriodTypes.put(kk, savedValue);
        }
        tripPeriodsCurrentFilter.setTripPeriodTypes(tripPeriodTypes);
        //------------------
        return tripPeriodsCurrentFilter;
    }

    //--------excel export-------------------
    //---------------------------------------
    @RequestMapping(value = "tripPeriodsExcelExportDashboard", method = RequestMethod.GET)
    public String tripPeriodsExcelExportDashboard(ModelMap model, HttpSession session) {
        model.addAttribute("excelExportLink", "exportTripPeriods.htm");
        session.setAttribute("message", "გამოთვლებისთვის დაფიქსირებულია " + session.getAttribute("percents") + " პროცენტიანი ზღვარი. <a href=\"tripPeriodsCalculations.htm\">შეცვალე პროცენტი</a>  ");
        return "excelExportDashboard";
    }

    @RequestMapping(value = "exportTripPeriods", method = RequestMethod.POST)
    public String exportTripPeriods(String fileName, ModelMap model, HttpSession session, HttpServletRequest request) {

        System.out.println("---------------Trip Periods And Routes Averages Excel Export Starting ------------------------------");
        Instant start = Instant.now();
//first get data
        if (session.getAttribute("percents") == null) {
            return "errorPage";
        }
        int percents = (Integer) session.getAttribute("percents");
        TripPeriodsFilter tripPeriodsInitialFilter = (TripPeriodsFilter) session.getAttribute("tripPeriodsInitialFilter");
        System.out.println("---Selected Routes Filter Created -----");
        memoryUsage.printMemoryUsage();
        HashMap excelExportData = routeDao.getExcelExportData(tripPeriodsInitialFilter, percents);
        System.out.println("---Excel Export Data Created------");
        memoryUsage.printMemoryUsage();
        ArrayList<TripPeriod2X> tripPeriods = (ArrayList) excelExportData.get("tripPeriods");
        TreeMap<Float, RouteAverages> routesAveragesTreeMap = (TreeMap) excelExportData.get("routesAverages");
        //ArrayList<TripPeriod2X> initialTripPeriods = routeDao.getTripPeriods(tripPeriodsInitialFilter);
        //now write the results

        ExcelWriter excelWriter = new ExcelWriter();
        //  excelWriter.exportTripPeriods(initialTripPeriods, fileName);
        System.out.println("---Writing Excel File Started---");
        memoryUsage.printMemoryUsage();
        //excelWriter.exportTripPeriodsAndRoutesAverages(tripPeriods, routesAveragesTreeMap, percents, fileName);
        excelWriter.SXSSF(tripPeriods, routesAveragesTreeMap, percents, fileName, request);

        model.addAttribute("excelExportLink", "exportTripPeriods.htm");
        model.addAttribute("fileName", fileName);
        Instant end = Instant.now();
        System.out.println("++++++++Trip Periods And Routes Averages Excel Export completed. Time needed:" + Duration.between(start, end) + "+++++++++++");

        return "excelExportDashboard";
    }
    //-----------------Calculations -----------------

    @RequestMapping(value = "tripPeriodsCalculationsInitialRequest", method = RequestMethod.POST)
    public String tripPeriodsCalculationsInitialRequest(@RequestParam("routes:dates") String routeDates, HttpSession session, ModelMap model) {
        TripPeriodsFilter tripPeriodsInitialFilter = convertSelectedRoutesToTripPeriodFilter(routeDates);
        session.setAttribute("tripPeriodsInitialFilter", tripPeriodsInitialFilter);
        int rowLimit = 200;
        TripPeriodsPager tripPeriodsPager = routeDao.getTripPeriodsPager(tripPeriodsInitialFilter, rowLimit);
        session.setAttribute("tripPeriodsPager", tripPeriodsPager);
        session.setAttribute("rowLimit", rowLimit);
        if (session.getAttribute("percents") == null) {
            session.setAttribute("percents", 20);
        }
        return "tripPeriodsCalculations";
    }

    @RequestMapping(value = "tripPeriodsCalculations")
    public String tripPeriodsCalculations(HttpSession session) {
        int percents = (Integer) session.getAttribute("percents");
        return "tripPeriodsCalculations";
    }

    @RequestMapping(value = "tripPeriodsCalculatePercentage", method = RequestMethod.POST)
    public String tripPeriodsCalculatePercentage(@RequestParam("percents") String percents, HttpSession session, ModelMap model) {
        TripPeriodsFilter tripPeriodsInitialFilter = (TripPeriodsFilter) session.getAttribute("tripPeriodsInitialFilter");
        int percentsInteger = Integer.valueOf(percents);
        TreeMap<Float, RouteAverages> routesAveragesTreeMap = routeDao.getRoutesAverages(tripPeriodsInitialFilter, percentsInteger);

        model.addAttribute("routesAverages", routesAveragesTreeMap);
        session.setAttribute("percents", percentsInteger);
        return "tripPeriodsCalculations";
    }

    @RequestMapping(value = "tripPeriodsExcelExportDashboardInitialRequest", method = RequestMethod.POST)
    public String tripPeriodsExcelExportDashboardInitialRequest(@RequestParam("routes:dates") String routeDates, HttpSession session) {
        TripPeriodsFilter tripPeriodsInitialFilter = convertSelectedRoutesToTripPeriodFilter(routeDates);
        session.setAttribute("tripPeriodsInitialFilter", tripPeriodsInitialFilter);
        if (session.getAttribute("percents") == null) {
            session.setAttribute("percents", 20);
        }
        return "redirect:/tripPeriodsExcelExportDashboard.htm";
    }

    @RequestMapping(value = "countedTripPeriods", method = RequestMethod.GET)
    public String countedTripPeriods(@RequestParam("routeNumber") String routeNumber,
            @RequestParam("dateStamps") String dateStamps,
            @RequestParam("type") String type,
            @RequestParam("percents") String percents,
            @RequestParam("height") String height,
            ModelMap model) {

        ArrayList<TripPeriod2X> countedTripPeriods = routeDao.getCountedTripPeriods(routeNumber, dateStamps, type, percents, height);
        model.addAttribute("countedTripPeriods", countedTripPeriods);
        return "countedTripPeriods";
    }

}
