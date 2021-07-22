package Controller;

import DAO.RouteDao;
import Model.BasicRoute;
import Model.RoutesBlock;
import Model.TripPeriodsFilter;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.TreeMap;
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

    @RequestMapping(value = "tripPeriodsInitialRequest")
    public String tripPeriodInitialRequest(@RequestParam("routes:dates") String routeDates, @RequestParam("blockNumber") String routeNumber, ModelMap model, HttpSession session) {
        RoutesBlocksBuilder rbb = new RoutesBlocksBuilder();
        ArrayList<RoutesBlock> selectedRoutesBlocks = rbb.createRoutesBlocks(routeDates);
        session.setAttribute("selectedRoutesBlocks", selectedRoutesBlocks);
        TreeMap<Float, BasicRoute> routes = getSelectedBlockRoutes(session, "0");
        model.addAttribute("routes", routes);
        model.addAttribute("selectedRoutesBlocks", selectedRoutesBlocks);

        String previousBlockHtml = "";
        String currentBlockHtml = "<button type=\"button\" class=\"btn  btn-outline-primary\" disabled>"
                + " <span>" + selectedRoutesBlocks.get(0).getName() + "</span>"
                + " </button>";

        String nextBlockHtml = "";

        if (selectedRoutesBlocks.size() > 1) {
            nextBlockHtml = " <button type=\"button\" class=\"btn btn-outline-success\">"
                    + " <span> <a  href=\"tripPeriods.htm?blockIndex=1\">" + selectedRoutesBlocks.get(1).getName() + "</a></span>"
                    + " </button>";
        }
        model.addAttribute("currentBlockIndex", 0);
        model.addAttribute("previousBlock", previousBlockHtml);
        model.addAttribute("currentBlock", currentBlockHtml);
        model.addAttribute("nextBlock", nextBlockHtml);

        session.setAttribute("tripPeriodsFilter", null);//
        return "tripPeriods";
    }

    @RequestMapping(value = "tripPeriods")
    public String dispalySelectedBlock(ModelMap model, HttpSession session, @RequestParam String blockIndex) {
        if (session.getAttribute("selectedRoutesBlocks") == null) {
            return "errorPage";
        }
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");

        TreeMap<Float, BasicRoute> routes = getSelectedBlockRoutes(session, blockIndex);
        model.addAttribute("routes", routes);

        addTripPeriodsModelAttributes(model, selectedRoutesBlocks, blockIndex);

        session.setAttribute("tripPeriodsFilter", null);//
        return "tripPeriods";
    }

    private TreeMap<Float, BasicRoute> getSelectedBlockRoutes(HttpSession session, String blockIndex) {
        Instant start = Instant.now();
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");
        RoutesBlock block = selectedRoutesBlocks.get(Integer.valueOf(blockIndex));

        //RouteFactory routeFactory = new RouteFactory();
        //TreeMap<Float, BasicRoute> routes = routeFactory.createSelectedRoutesFromUploadedFile(block);
        TreeMap<Float, BasicRoute> routes = routeDao.getSelectedRoutes(block);

        Instant end = Instant.now();
        System.out.println("TripPeriods routes created. Creation time:" + Duration.between(start, end));
        return routes;
    }

    @RequestMapping(value = "tripPeriodsFilterRequest")
    public String tripPeriodsFilter(ModelMap model, HttpSession session, @RequestParam String blockIndex) {
        if (session.getAttribute("selectedRoutesBlocks") == null) {
            return "errorPage";
        }
        Instant filteringStart = Instant.now();
        TripPeriodsFilter baseTripPeriodsFilter;
        TripPeriodsFilter tripPeriodsFilter;
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");
        RoutesBlock block = selectedRoutesBlocks.get(Integer.valueOf(blockIndex));
        if (session.getAttribute("baseTripPeriodsFilter") == null) {
            baseTripPeriodsFilter = routeDao.getSelectedRoutesTripPeriodsFilter(block);
            tripPeriodsFilter = baseTripPeriodsFilter;
        } else {
            baseTripPeriodsFilter = (TripPeriodsFilter) session.getAttribute("baseTripPeriodsFilter");
        }
        tripPeriodsFilter = routeDao.getSelectedRoutesTripPeriodsFilter(block);
        Instant filteringEnd = Instant.now();
        System.out.println("TripPeriods routes filtered. Time needed:" + Duration.between(filteringStart, filteringEnd));
        model.addAttribute("blockIndex", blockIndex);
        model.addAttribute("tripPeriodsFilter", tripPeriodsFilter);
        session.setAttribute("baseTripPeriodsFilter", baseTripPeriodsFilter);
        return "tripPeriodsFilter";
    }

    private void addTripPeriodsModelAttributes(ModelMap model, ArrayList<RoutesBlock> selectedRoutesBlocks, String blockIndex) {
        String previousBlockHtml = "";
        String currentBlockHtml = "<button type=\"button\" class=\"btn btn-outline-primary\" disabled>"
                + " <span>" + selectedRoutesBlocks.get(Integer.valueOf(blockIndex)).getName() + "</span>"
                + " </button>";
        String nextBlockHtml = "";

        if (Integer.valueOf(blockIndex) > 0) {
            previousBlockHtml = " <button type=\"button\" class=\"btn btn-outline-success\">"
                    + " <span> <a href=\"tripPeriods.htm?blockIndex=" + (Integer.valueOf(blockIndex) - 1) + "\">" + selectedRoutesBlocks.get(Integer.valueOf(blockIndex) - 1).getName() + "</a> </span>"
                    + " </button>";
        }

        if (Integer.valueOf(blockIndex) < selectedRoutesBlocks.size() - 1) {
            nextBlockHtml = " <button type=\"button\" class=\"btn btn-outline-success\">"
                    + "<span><a  href=\"tripPeriods.htm?blockIndex=" + (Integer.valueOf(blockIndex) + 1) + "\">" + selectedRoutesBlocks.get(Integer.valueOf(blockIndex) + 1).getName() + "</a></span>"
                    + " </button>";
        }

        model.addAttribute("previousBlock", previousBlockHtml);
        model.addAttribute("currentBlock", currentBlockHtml);
        model.addAttribute("nextBlock", nextBlockHtml);
        model.addAttribute("currentBlockIndex", blockIndex);
    }

    @RequestMapping(value = "tripPeriodsFilter", method = RequestMethod.POST)
    public String filterTripPeriods(HttpSession session, ModelMap model,
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
        if (session.getAttribute("selectedRoutesBlocks") == null) {
            return "errorPage";
        }

        TripPeriodsFilter tripPeriodsFilter = (TripPeriodsFilter) session.getAttribute("baseTripPeriodsFilter");
        tripPeriodsFilter = tripPeriodsFilter.refactorFilter(triggerFilter, routeNumbers, dateStamps, busNumbers, exodusNumbers,
                driverNames, tripPeriodTypes, startTimesScheduled, startTimesActual,
                arrivalTimesScheduled, arrivalTimesActual, tripPeriodTimesScheduled, tripPeriodTimesActual,
                tripPeriodTimeScheduled, tripPeriodTimesDifference);

        TreeMap<Float, BasicRoute> routes = routeDao.getFilteredRoutes(tripPeriodsFilter);
        model.addAttribute("routes", routes);
        ArrayList<RoutesBlock> selectedRoutesBlocks = (ArrayList<RoutesBlock>) session.getAttribute("selectedRoutesBlocks");
        addTripPeriodsModelAttributes(model, selectedRoutesBlocks, blockIndex);
        model.addAttribute("baseTripPeriodsFilter", tripPeriodsFilter);
        return "tripPeriods";
    }

}
