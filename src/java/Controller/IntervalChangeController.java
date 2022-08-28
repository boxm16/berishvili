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
            @RequestParam("newIntervalHours") String newIntervalHours,
            @RequestParam("newIntervalMinutes") String newIntervalMinutes,
            @RequestParam("newIntervalSeconds") String newIntervalSeconds,
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
        Duration newInterval = converter.convertStringToDuration(newIntervalHours + ":" + newIntervalMinutes + ":" + newIntervalSeconds);

        choosedRoute = realculateTripPeriodsBasedOnNewInterval(choosedRoute, newIntervalStartTime, newInterval);
        model.addAttribute("route", choosedRoute);
        session.setAttribute("routeWithNewInterval", choosedRoute);
        return "newIntervalVersions";
    }

    private TreeMap<LocalDateTime, IntervalTripPeriod> calculateIntervals(TreeMap<LocalDateTime, IntervalTripPeriod> timetable) {
        LocalDateTime previousTripPeriodStartTime = null;
        for (Map.Entry<LocalDateTime, IntervalTripPeriod> entry : timetable.entrySet()) {
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

        return timetable;

    }

    private Route realculateTripPeriodsBasedOnNewInterval(Route choosedRoute, LocalDateTime newIntervalStartTime, Duration newInterval) {
        Converter converter = new Converter();
        LocalDateTime endTime = converter.convertStringTimeToDate("23:00:00");

        while (newIntervalStartTime.isBefore(endTime)) {
            getAvailableBus(choosedRoute, newIntervalStartTime, newInterval);
            newIntervalStartTime = newIntervalStartTime.plusSeconds(1);

        }

        return choosedRoute;

    }

    private void getAvailableBus(Route choosedRoute, LocalDateTime currentTime, Duration newInterval) {
        ArrayList<Exodus> exoduses = choosedRoute.getExoduses();

        //first find ab and ba durations
        Duration abTripDuration = Duration.ZERO;
        Duration baTripDuration = Duration.ZERO;
        ArrayList<TripPeriod> tps = choosedRoute.getExoduses().get(0).getTripPeriods();
        int indx = 0;
        while (abTripDuration == Duration.ZERO || baTripDuration == Duration.ZERO) {

            TripPeriod tp = tps.get(indx);
            if (tp.getType().equals("ab")) {
                abTripDuration = tp.getDuration();
            }
            if (tp.getType().equals("ba")) {
                baTripDuration = tp.getDuration();
            }
            if (indx > tps.size() - 1) {
                break;
            }
            indx++;
        }
        //------------------------------
        //
        TreeMap<LocalDateTime, IntervalTripPeriod> abDirectionTimeTable = getDirectionTimeTable(choosedRoute, "ab");
        LocalDateTime abLastBusStartTimeFromDIrectionTimeTable = abDirectionTimeTable.lastKey();
        LocalDateTime abNextBusStartTime = abLastBusStartTimeFromDIrectionTimeTable.plus(newInterval);

        TreeMap<LocalDateTime, IntervalTripPeriod> baDirectionTimeTable = getDirectionTimeTable(choosedRoute, "ba");
        LocalDateTime baLastBusStartTimeFromDIrectionTimeTable = baDirectionTimeTable.lastKey();
        LocalDateTime baNextBusStartTime = baLastBusStartTimeFromDIrectionTimeTable.plus(newInterval);

        // now we are estimating which bus as ended its last trip at current time 
        //and sending him in corresponding waiting room
        TreeMap<Short, Exodus> abWaitingRoom = new TreeMap<>();
        TreeMap<Short, Exodus> baWaitingRoom = new TreeMap<>();

        short exodusIndex = 1;
        // System.out.println("CurrentTime:" + currentTime);

        for (Exodus exodus : exoduses) {
            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            TripPeriod lastTripPeriod = tripPeriods.get(tripPeriods.size() - 1);
            //skip "halt" timePeriod
            if (lastTripPeriod.getType().equals("halt")) {
                lastTripPeriod = tripPeriods.get(tripPeriods.size() - 2);
            }
            LocalDateTime lastTripPeriodEndTime = lastTripPeriod.getStartTime().plus(lastTripPeriod.getDuration()).plusMinutes(5);
            if (lastTripPeriodEndTime.isAfter(currentTime)) {
            } else {
                String type = lastTripPeriod.getType();

                if (type.equals("ba")) {
                    abWaitingRoom.put(exodusIndex, exodus);
                }
                if (type.equals("ab")) {
                    baWaitingRoom.put(exodusIndex, exodus);
                }

                // System.out.println("exodus number:" + exodusIndex + "LastTripEndTime" + lastTripPeriodEndTime + "TYPE:" + type);
            }
            exodusIndex++;
        }
        if (abNextBusStartTime.isEqual(currentTime)) {

            // System.out.println("TYPE: " + type + "LAST TRIP PERIOD START TIME IN TIMETABLE" + lastBusStartTimeFromDIrectionTimeTable);
            //  System.out.println("should go ");
            Exodus exodus = getClosestBus(abWaitingRoom, currentTime);
            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            TripPeriod lastTripPeriodInExodus = tripPeriods.get(tripPeriods.size() - 1);
            LocalDateTime startTime = lastTripPeriodInExodus.getStartTime();
            LocalDateTime endTime = startTime.plus(lastTripPeriodInExodus.getDuration());
            Duration haltTimeDuration = Duration.between(endTime, currentTime);
            TripPeriod halt = new TripPeriod();
            halt.setType("halt");
            halt.setStartTime(endTime);
            halt.setDuration(haltTimeDuration);
            exodus.getTripPeriods().add(halt);

            TripPeriod tripPeriod = new TripPeriod();
            tripPeriod.setType("ab");
            tripPeriod.setStartTime(currentTime);
            tripPeriod.setDuration(abTripDuration);

            exodus.getTripPeriods().add(tripPeriod);
        }
        if (baNextBusStartTime.isEqual(currentTime)) {

            // System.out.println("TYPE: " + type + "LAST TRIP PERIOD START TIME IN TIMETABLE" + lastBusStartTimeFromDIrectionTimeTable);
            //  System.out.println("should go ");
            Exodus exodus = getClosestBus(baWaitingRoom, currentTime);

            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            TripPeriod lastTripPeriodInExodus = tripPeriods.get(tripPeriods.size() - 1);
            LocalDateTime startTime = lastTripPeriodInExodus.getStartTime();
            LocalDateTime endTime = startTime.plus(lastTripPeriodInExodus.getDuration());
            Duration haltTimeDuration = Duration.between(endTime, currentTime);
            TripPeriod halt = new TripPeriod();
            halt.setType("halt");
            halt.setStartTime(endTime);
            halt.setDuration(haltTimeDuration);
            exodus.getTripPeriods().add(halt);

            TripPeriod tripPeriod = new TripPeriod();
            tripPeriod.setType("ba");
            tripPeriod.setStartTime(currentTime);
            tripPeriod.setDuration(baTripDuration);

            exodus.getTripPeriods().add(tripPeriod);
        }

    }

    private Exodus getClosestBus(TreeMap<Short, Exodus> waitingRoom, LocalDateTime currentTime) {
        System.out.println("waitingRoom SIZE:" + waitingRoom.size());
        if (waitingRoom.size() == 1) {
            Map.Entry<Short, Exodus> lastEntry = waitingRoom.pollLastEntry();
            ArrayList<TripPeriod> tripPeriods = lastEntry.getValue().getTripPeriods();
            TripPeriod lastTripPeriod = tripPeriods.get(tripPeriods.size() - 1);
            if (lastTripPeriod.getType().equals("halt")) {
                tripPeriods.remove(tripPeriods.size() - 1);
            }
            return lastEntry.getValue();
        } else {
            Exodus katalliloExodus = null;
            Duration differenceTime = Duration.ofDays(15);
            for (Map.Entry<Short, Exodus> entry : waitingRoom.entrySet()) {
                Exodus exodus = entry.getValue();
                ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
                TripPeriod lastTripPeriod = tripPeriods.get(tripPeriods.size() - 1);
                if (lastTripPeriod.getType().equals("halt")) {
                    lastTripPeriod = tripPeriods.get(tripPeriods.size() - 2);
                }
                LocalDateTime lastTripPeriodEndTimeWithHaltTime = lastTripPeriod.getStartTime().plus(lastTripPeriod.getDuration()).plusMinutes(5);
                Duration differenceBetweenCurrentTimeAndLastPeriodEndTimeWithHalt = Duration.between(lastTripPeriodEndTimeWithHaltTime, currentTime);
                System.out.println("DIFFEREN:" + differenceBetweenCurrentTimeAndLastPeriodEndTimeWithHalt);
                int val = differenceBetweenCurrentTimeAndLastPeriodEndTimeWithHalt.compareTo(differenceTime);
                if (val > 0) {

                } else {
                    differenceTime = differenceBetweenCurrentTimeAndLastPeriodEndTimeWithHalt;
                    katalliloExodus = exodus;
                }
            }

            ArrayList<TripPeriod> tripPeriods = katalliloExodus.getTripPeriods();
            TripPeriod lastTripPeriod = tripPeriods.get(tripPeriods.size() - 1);
            if (lastTripPeriod.getType().equals("halt")) {
                tripPeriods.remove(tripPeriods.size() - 1);
            }
            return katalliloExodus;
        }
    }

    private TreeMap<LocalDateTime, IntervalTripPeriod> getDirectionTimeTable(Route choosedRoute, String direction) {
        TreeMap<LocalDateTime, IntervalTripPeriod> directionTimetable = new TreeMap<>();

        ArrayList<Exodus> exoduses = choosedRoute.getExoduses();
        short exodusIndex = 1;
        for (Exodus exodus : exoduses) {
            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            for (TripPeriod tripPeriod : tripPeriods) {
                if (tripPeriod.getType().equals(direction)) {
                    IntervalTripPeriod intervalTripPeriod = new IntervalTripPeriod();
                    intervalTripPeriod.setExodusNumber(exodusIndex);
                    intervalTripPeriod.setStartTimeScheduled(tripPeriod.getStartTime());
                    intervalTripPeriod.setArrivalTimeScheduled(tripPeriod.getStartTime().plus(tripPeriod.getDuration()));

                    directionTimetable.put(tripPeriod.getStartTime(), intervalTripPeriod);
                }
            }
            exodusIndex++;
        }
        return directionTimetable;
    }

    @RequestMapping(value = "timeTable2", method = RequestMethod.GET)
    public String newIterval(ModelMap model, HttpSession session) {

        Route routeWithNewInterval = (Route) session.getAttribute("routeWithNewInterval");
        TreeMap<LocalDateTime, IntervalTripPeriod> abTimetable = new TreeMap<>();
        TreeMap<LocalDateTime, IntervalTripPeriod> baTimetable = new TreeMap<>();
        ArrayList<Exodus> exoduses = routeWithNewInterval.getExoduses();
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
        return "timeTableWithNewIntervals";
    }

}
