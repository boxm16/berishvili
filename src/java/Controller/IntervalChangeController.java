package Controller;

import Model.IntervalTripPeriod;
import graphical.Exodus;
import graphical.Route;
import graphical.RouteData;
import graphical.TripPeriod;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
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
    public String showTimeTable(ModelMap model, HttpSession session,
            @RequestParam("routeVersionNumber") String routeVersionNumber,
            @RequestParam("newIntervalMinutes") String newIntervalMinutes,
            @RequestParam("newIntervalStartTime") String newIntervalStartTime) {

        ArrayList<Route> routeWithoutBreakVersion = (ArrayList<Route>) session.getAttribute("routes");

        Route choosedRoute = routeWithoutBreakVersion.get(Integer.valueOf(routeVersionNumber));
        TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable = new TreeMap<>();
        TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable = new TreeMap<>();
        ArrayList<Exodus> exoduses = choosedRoute.getExoduses();
        short exodusIndex = 1;
        for (Exodus exodus : exoduses) {
            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            for (TripPeriod tripPeriod : tripPeriods) {
                if (tripPeriod.getType().equals("ab")) {
                    IntervalTripPeriod intervalTripPeriod = new IntervalTripPeriod();
                    intervalTripPeriod.setExodusNumber(exodusIndex);
                    intervalTripPeriod.setStartTimeScheduled(tripPeriod.getStartTime());
                    intervalTripPeriod.setArrivalTimeScheduled(tripPeriod.getStartTime().plus(tripPeriod.getDuration()));

                    abTimetable.put(tripPeriod.getStartTime(), intervalTripPeriod);
                }
                if (tripPeriod.getType().equals("ba")) {
                    IntervalTripPeriod intervalTripPeriod = new IntervalTripPeriod();
                    intervalTripPeriod.setExodusNumber(exodusIndex);
                    intervalTripPeriod.setStartTimeScheduled(tripPeriod.getStartTime());
                    intervalTripPeriod.setArrivalTimeScheduled(tripPeriod.getStartTime().plus(tripPeriod.getDuration()));

                    baTimetable.put(tripPeriod.getStartTime(), intervalTripPeriod);
                }
            }
            exodusIndex++;
        }

        abTimetable = calculateIntervals(abTimetable);
        baTimetable = calculateIntervals(baTimetable);

        model.addAttribute("abTimetable", abTimetable);
        model.addAttribute("baTimetable", baTimetable);
        return "timeTable";
    }

    @RequestMapping(value = "newInterval", method = RequestMethod.POST)
    public String newIterval(ModelMap model, HttpSession session,
            @RequestParam("routeVersionNumber") String routeVersionNumber,
            @RequestParam("newIntervalMinutes") String newIntervalMinutes,
            @RequestParam("newIntervalStartTime") String newIntervalStartTimeString) {

        ArrayList<Route> routeWithoutBreakVersion = (ArrayList<Route>) session.getAttribute("routes");
        Route choosedRoute = routeWithoutBreakVersion.get(Integer.valueOf(routeVersionNumber));
        Converter converter = new Converter();
        LocalDateTime newIntervalStartTime = converter.convertStringTimeToDate(newIntervalStartTimeString);
        ArrayList<Exodus> exoduses = choosedRoute.getExoduses();
        for (Exodus exodus : exoduses) {
            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            for (int i = tripPeriods.size() - 1; i >= 0; --i) {
                if (tripPeriods.get(i).getStartTime().isBefore(newIntervalStartTime)) {
                    //do nothing    
                } else {
                    tripPeriods.remove(i);
                }
            }
        }

        model.addAttribute("route", choosedRoute);
        return "newIntervalVersions";
    }

    private TreeMap<LocalDateTime, IntervalTripPeriod> calculateIntervals(TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable) {
        LocalDateTime previousTripPeriodStartTime = null;
        for (Map.Entry<LocalDateTime, IntervalTripPeriod> entry : abTimetable.entrySet()) {
            if (previousTripPeriodStartTime == null) {
                previousTripPeriodStartTime = entry.getKey();
            } else {
                IntervalTripPeriod intervalTripPeriod = entry.getValue();
                LocalDateTime tripPeriodStartTimeScheduled = intervalTripPeriod.getStartTimeScheduled();
                Duration interval = Duration.between(previousTripPeriodStartTime, tripPeriodStartTimeScheduled);
                intervalTripPeriod.setScheduledInterval(interval);

                previousTripPeriodStartTime = entry.getKey();
            }

        }

        return abTimetable;

    }
}
