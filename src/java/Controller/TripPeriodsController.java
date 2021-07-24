package Controller;

import DAO.RouteDao;
import Model.TripPeriod2X;
import Model.TripPeriodsFilter;
import java.util.ArrayList;
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
    public String tripPeriodInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {

        TripPeriodsFilter tripPeriodsFilter = convertSelectedRoutesToTripPeriodFilter(routeDates);
        tripPeriodsFilter.setInitial(true);
        ArrayList<TripPeriod2X> tripPeriods = routeDao.getFilteredTripPeriods(tripPeriodsFilter);
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

    @RequestMapping(value = "tripPeriods")
    public String tripPeriods(ModelMap model, HttpSession session, @RequestParam String blockIndex) {

        return "tripPeriods";
    }

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

    //----------
    @RequestMapping(value = "filterChoice")
    public String filterChoice() {
        return "filterChoice";
    }

    @RequestMapping(value = "filter")
    public String filter() {
        return "filterChoice";
    }

}
