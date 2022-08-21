package Controller;

import graphical.RouteData;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IntervalChangeController {

    @RequestMapping(value = "intervalChangeInitialRequest", method = RequestMethod.GET)
    public String intervalChangeInitialRequest(ModelMap model) {

        RouteData routeData = new RouteData();
        model.addAttribute("routeData", routeData);

        return "intervalChangeDashboard";
    }

    @RequestMapping(value = "showTimeTable", method = RequestMethod.POST)
    public String showTimeTable(ModelMap model) {

     

        return "timeTable";
    }

    @RequestMapping(value = "newInterval", method = RequestMethod.POST)
    public String newIterval(ModelMap model, HttpSession session,
            @RequestParam("routeVersionNumber") String routeVersionNumber,
            @RequestParam("newIntervalMinutes") String newIntervalMinutes,
            @RequestParam("newIntervalStartTime") String newIntervalStartTime) {
        /*
        ArrayList<Route> routeBreakVersions = new ArrayList();

        ArrayList<Route> routeWithoutBreakVersion = (ArrayList<Route>) session.getAttribute("routes");

        Route choosedRoute = routeWithoutBreakVersion.get(Integer.valueOf(routeVersionNumber));

        IgnitionSequence ignitionSequence = new IgnitionSequence();
        for (Exodus exodus : choosedRoute.getExoduses()) {
            TripPeriod firstTripPeriodOfExodus = exodus.getTripPeriods().get(0);
            ExodusIgnitionCode exodusIgnitionCode = new ExodusIgnitionCode();
            exodusIgnitionCode.setStartTime(firstTripPeriodOfExodus.getStartTime());
            exodusIgnitionCode.setType(firstTripPeriodOfExodus.getType());
            ignitionSequence.addExodusIgnitionCode(exodusIgnitionCode);
        }

        RouteData routeData = (RouteData) session.getAttribute("routeData");
        routeData.setFirstBreakStartTime(firstBreakStartTime);
        routeData.setLastBreakEndTime(lastBreakEndTime);
        routeData.setBreakTimeMinutes(Integer.valueOf(breakTimeMinutes));
        routeData.setBreakStayPoint(breakStayPoint);

        ArrayList<ArrayList<Integer>> findBreaksPossiblePoints = findBreaksPossiblePoints(choosedRoute, routeData);

        TwoDimArrayCombinations twoDimArrayCombinator = new TwoDimArrayCombinations();
        List<ArrayList> breakSequences = twoDimArrayCombinator.getIntegerCombinations(findBreaksPossiblePoints);
        MemoryUsage mu = new MemoryUsage();
        for (ArrayList breakSequence : breakSequences) {
            Route routeWithBreakVersion = createRouteWithBreaks(ignitionSequence, routeData, breakSequence);
            if (routeWithBreakVersion != null) {
                routeBreakVersions.add(routeWithBreakVersion);
            }
        }

        System.out.println("All Possible Breaks Versions Count:" + breakSequences.size());
        System.out.println("All Clear Breaks Versions Count:" + routeBreakVersions.size());

        BreaksPager breaksPager = new BreaksPager(routeBreakVersions, 50);
        breaksPager.setCurrentPage(1);
        model.addAttribute("breaksPager", breaksPager);
        session.setAttribute("breaksPager", breaksPager);
         */
        return "newIntervalVersions";
    }
}
