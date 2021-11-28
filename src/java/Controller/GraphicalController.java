package Controller;

import graphical.Exodus;
import graphical.ExodusIgnitionCode;
import graphical.IgnitionSequence;
import graphical.Permutations;
import graphical.Route;
import graphical.RouteData;
import graphical.TripPeriod;
import graphical.TwoDimArrayCombinations;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GraphicalController {

    Converter converter;

    public GraphicalController() {
        converter = new Converter();
    }

    @RequestMapping(value = "graphicalRouteInitialRequest", method = RequestMethod.GET)
    public String graphicalRouteInitialRequest(ModelMap model) {

        RouteData routeData = new RouteData();
        model.addAttribute("routeData", routeData);

        return "graphicalRoute";
    }

    @RequestMapping(value = "graphicalRouteVersions", method = RequestMethod.POST)
    public String graphicalRouteVersions(ModelMap model, HttpSession session,
            @RequestParam(value = "roundCheckBox", required = false) String roundCheckBoxChecked,
            @RequestParam(value = "roundInputHour", required = false) String roundInputHour,
            @RequestParam(value = "roundInputMinute", required = false) String roundInputMinute,
            @RequestParam(value = "roundInputSecond", required = false) String roundInputSecond,
            @RequestParam(value = "roundInputMinutes", required = false) String roundInputMinutes,
            @RequestParam(value = "roundInputSeconds", required = false) String roundInputSeconds,
            @RequestParam(value = "busCheckBox", required = false) String busCheckBoxChecked,
            @RequestParam(value = "busInput", required = false) String busInput,
            @RequestParam(value = "intervalCheckBox", required = false) String intervalCheckBoxChecked,
            @RequestParam(value = "intervalInputHour", required = false) String intervalInputHour,
            @RequestParam(value = "intervalInputMinute", required = false) String intervalInputMinute,
            @RequestParam(value = "intervalInputSecond", required = false) String intervalInputSecond,
            @RequestParam("plusMinusInput") String plusMinusInput,
            @RequestParam("firstTripStartTimeInFormInput") String firstTripStartTimeInFormInput,
            @RequestParam("lastTripStartTimeInFormInput") String lastTripStartTimeInFormInput,
            @RequestParam("circularRoute") String circularRoute,
            @RequestParam(value = "starterTripInFormInput", required = false) String starterTripInFormInput,
            @RequestParam("abTripTimeMinutesInFormInput") String abTripTimeMinutesInFormInput,
            @RequestParam("abTripTimeSecondsInFormInput") String abTripTimeSecondsInFormInput,
            @RequestParam("baTripTimeMinutesInFormInput") String baTripTimeMinutesInFormInput,
            @RequestParam("baTripTimeSecondsInFormInput") String baTripTimeSecondsInFormInput,
            @RequestParam("haltTimeMinutes") String haltTimeMinutes,
            @RequestParam("haltTimeSeconds") String haltTimeSeconds,
            @RequestParam("abBusCountInFormInput") String abBusCountInFormInput,
            @RequestParam("baBusCountInFormInput") String baBusCountInFormInput,
            @RequestParam("intervalTimeInFormInput") String intervalTimeInFormInput) {
        RouteData routeData = new RouteData();
        if (roundCheckBoxChecked != null) {
            routeData.setRoundCheckBoxChecked("checked");
        }
        if (roundInputHour == null) {
            routeData.setRoundInputHourValue("00");
        } else {
            routeData.setRoundInputHourValue(roundInputHour);
        }

        if (roundInputMinute == null) {
            routeData.setRoundInputMinuteValue("00");
        } else {
            routeData.setRoundInputMinuteValue(roundInputMinute);
        }

        if (roundInputMinute == null) {
            routeData.setRoundInputMinuteValue("00");
        } else {
            routeData.setRoundInputMinuteValue(roundInputMinute);
        }

        if (roundInputSecond == null) {
            routeData.setRoundInputSecondValue("00");
        } else {
            routeData.setRoundInputSecondValue(roundInputSecond);
        }

        if (roundInputMinutes == null) {
            routeData.setRoundInputMinutesValue("00");
        } else {
            routeData.setRoundInputMinutesValue(roundInputMinutes);
        }

        if (roundInputSeconds == null) {
            routeData.setRoundInputSecondsValue("00");
        } else {
            routeData.setRoundInputSecondsValue(roundInputSeconds);
        }

        if (busCheckBoxChecked != null) {
            routeData.setBusCheckBoxChecked("checked");
        }

        if (busInput == null) {
            routeData.setBusInputValue("");
        } else {
            routeData.setBusInputValue(busInput);
        }

        if (intervalCheckBoxChecked != null) {
            routeData.setIntervalCheckBoxChecked("checked");
        }

        if (intervalInputHour == null) {
            routeData.setIntervalInputHourValue("00:00");
        } else {
            routeData.setIntervalInputHourValue(intervalInputHour);
        }

        if (intervalInputMinute == null) {
            routeData.setIntervalInputMinuteValue("00:00");
        } else {
            routeData.setIntervalInputMinuteValue(intervalInputMinute);
        }

        if (intervalInputSecond == null) {
            routeData.setIntervalInputSecondValue("00:00");
        } else {
            routeData.setIntervalInputSecondValue(intervalInputSecond);
        }

        routeData.setPlusMinusInputValue(plusMinusInput);

        routeData.setFirstTripStartTime(firstTripStartTimeInFormInput);

        routeData.setLastTripStartTime(lastTripStartTimeInFormInput);

        if (circularRoute.equals("yes")) {
            routeData.setCircularRoute(true);
        }

        routeData.setStarterTrip(starterTripInFormInput);

        routeData.setHaltTimeMinutes(Integer.valueOf(haltTimeMinutes));
        routeData.setHaltTimeSeconds(Integer.valueOf(haltTimeSeconds));

        routeData.setAbTripTimeMinutes(Integer.valueOf(abTripTimeMinutesInFormInput));

        routeData.setAbTripTimeSeconds(Integer.valueOf(abTripTimeSecondsInFormInput));

        routeData.setBaTripTimeMinutes(Integer.valueOf(baTripTimeMinutesInFormInput));

        routeData.setBaTripTimeSeconds(Integer.valueOf(baTripTimeSecondsInFormInput));

        routeData.setAbBusCount(Integer.valueOf(abBusCountInFormInput));
        routeData.setBaBusCount(Integer.valueOf(baBusCountInFormInput));
        routeData.setBusCount(Integer.valueOf(abBusCountInFormInput) + Integer.valueOf(baBusCountInFormInput));

        routeData.setIntervalTime(intervalTimeInFormInput);
        IgnitionSequence initialIgnitionSequences = getInitialIgnitionSequence(routeData);

        ArrayList<IgnitionSequence> allPossibleIgnitionSequences = getAllPossibleIgnitionSequences(initialIgnitionSequences, routeData);
        ArrayList<Route> routes = new ArrayList<>();
        for (IgnitionSequence ignitionSequence : allPossibleIgnitionSequences) {
            Route route = createtRouteWithoutBreaks(ignitionSequence, routeData);
            routes.add(route);
        }

        model.addAttribute("routeData", routeData);
        model.addAttribute("routes", routes);

        session.setAttribute("routeData", routeData);
        session.setAttribute("routes", routes);
        return "graphicalRoute";
    }

    @RequestMapping(value = "breaks", method = RequestMethod.POST)
    public String breaks(ModelMap model, HttpSession session,
            @RequestParam("routeVersionNumber") String routeVersionNumber,
            @RequestParam("breakTimeMinutes") String breakTimeMinutes,
            @RequestParam("firstBreakStartTime") String firstBreakStartTime,
            @RequestParam("lastBreakEndTime") String lastBreakEndTime,
            @RequestParam("breakStayPoint") String breakStayPoint) {

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
        if (routeBreakVersions.size() > 200) {
            List l = routeBreakVersions.subList(0, 200);
            model.addAttribute("routes", l);
        } else {
            model.addAttribute("routes", routeBreakVersions);
        }

        System.out.println("All Possible Breaks Versions Count:" + breakSequences.size());
        System.out.println("All Clear Breaks Versions Count:" + routeBreakVersions.size());

        return "breaks";
    }

    private IgnitionSequence getInitialIgnitionSequence(RouteData routeData) {
        IgnitionSequence ignitionSequence = new IgnitionSequence();

        String goTripType = routeData.getStarterTrip();
        String returnTripType;

        int goBusesCount;
        int returnBusesCount;

        Duration goTripPeriodTime;
        Duration returnTripPeriodTime;
        LocalDateTime startTime = converter.convertStringTimeToDate(routeData.getFirstTripStartTime());

        if (routeData.isCircularRoute()) {
            goBusesCount = routeData.getAbBusCount() + routeData.getBaBusCount();
            while (goBusesCount > 0) {
                ExodusIgnitionCode exodusIgnitionCode = new ExodusIgnitionCode();
                exodusIgnitionCode.setType("ab");
                exodusIgnitionCode.setStartTime(startTime);
                ignitionSequence.addExodusIgnitionCode(exodusIgnitionCode);

                Duration intervalTime = converter.convertStringToDuration(routeData.getIntervalTime());
                startTime = startTime.plus(intervalTime);

                goBusesCount--;
            }
        } else {

            if (goTripType.equals("ab")) {
                goBusesCount = routeData.getAbBusCount();
                returnBusesCount = routeData.getBaBusCount();
                returnTripType = "ba";

                // goTripPeriodTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());
                returnTripPeriodTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());

            } else {
                goBusesCount = routeData.getBaBusCount();
                returnBusesCount = routeData.getAbBusCount();
                returnTripType = "ab";

                // goTripPeriodTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());
                returnTripPeriodTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());

            }

            while (goBusesCount > 0) {
                ExodusIgnitionCode exodusIgnitionCode = new ExodusIgnitionCode();
                exodusIgnitionCode.setType(goTripType);
                exodusIgnitionCode.setStartTime(startTime);
                ignitionSequence.addExodusIgnitionCode(exodusIgnitionCode);

                Duration intervalTime = converter.convertStringToDuration(routeData.getIntervalTime());
                startTime = startTime.plus(intervalTime);

                goBusesCount--;
            }
            Duration haltTime = Duration.ofSeconds(routeData.getHaltTimeMinutes() * 60 + routeData.getHaltTimeSeconds());
            startTime = startTime.minus(returnTripPeriodTime).minus(haltTime);
            //   startTime=startTime.minus(amountToSubtract);
            while (returnBusesCount > 0) {
                ExodusIgnitionCode exodusIgnitionCode = new ExodusIgnitionCode();
                exodusIgnitionCode.setType(returnTripType);
                exodusIgnitionCode.setStartTime(startTime);
                ignitionSequence.addExodusIgnitionCode(exodusIgnitionCode);

                Duration intervalTime = converter.convertStringToDuration(routeData.getIntervalTime());
                startTime = startTime.plus(intervalTime);

                returnBusesCount--;
            }
        }
        return ignitionSequence;
    }

    private ArrayList<IgnitionSequence> getAllPossibleIgnitionSequences(IgnitionSequence initialIgnitionSequence, RouteData routeData) {

        ArrayList<IgnitionSequence> allPossibleIgnitionSequences = new ArrayList<>();
        if (routeData.isCircularRoute()) {
            allPossibleIgnitionSequences.add(initialIgnitionSequence);
            return allPossibleIgnitionSequences;
        }
        if ((routeData.getStarterTrip().equals("ab") && (routeData.getAbBusCount() <= routeData.getBaBusCount()))
                || (routeData.getStarterTrip().equals("ba") && (routeData.getBaBusCount() <= routeData.getAbBusCount()))) {
            allPossibleIgnitionSequences.add(initialIgnitionSequence);
            return allPossibleIgnitionSequences;
        }
        IgnitionSequence nonChangingPart = new IgnitionSequence();
        IgnitionSequence changingPart = new IgnitionSequence();
        ExodusIgnitionCode firstIgnitionCode = initialIgnitionSequence.getSequence().get(0);
        LocalDateTime firstTripStartTime = firstIgnitionCode.getStartTime();
        int haltTimeInSeconds = (routeData.getHaltTimeMinutes() * 60) + routeData.getHaltTimeSeconds();
        Duration haltTimeDuration = Duration.ofSeconds(haltTimeInSeconds);
        Duration returnTripTime;
        if (firstIgnitionCode.getType().equals("ab")) {
            returnTripTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());
        } else {
            returnTripTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());
        }
        boolean roubikonPassed = false;
        for (ExodusIgnitionCode exodusIgnitionCode : initialIgnitionSequence.getSequence()) {
            // System.out.println(firstTripStartTime + ":" + exodusIgnitionCode.getStartTime().minus(haltTimeDuration).minus(returnTripTime));

            if (exodusIgnitionCode.getStartTime().minus(haltTimeDuration).minus(returnTripTime).isAfter(firstTripStartTime)
                    || exodusIgnitionCode.getStartTime().minus(haltTimeDuration).minus(returnTripTime).isEqual(firstTripStartTime)) {
                roubikonPassed = true;
            }
            if (roubikonPassed) {
                changingPart.addExodusIgnitionCode(exodusIgnitionCode);
            } else {
                nonChangingPart.addExodusIgnitionCode(exodusIgnitionCode);
            }

        }
        ArrayList<IgnitionSequence> allVariationsOfChangablePart = getAllVariationsOfChangablePart(changingPart, routeData);

        for (IgnitionSequence ignitionSequence : allVariationsOfChangablePart) {
            ArrayList<ExodusIgnitionCode> newVersion = (ArrayList<ExodusIgnitionCode>) nonChangingPart.getSequence().clone();
            newVersion.addAll(ignitionSequence.getSequence());
            IgnitionSequence newIgnitionSequence = new IgnitionSequence();
            newIgnitionSequence.setSequence(newVersion);
            allPossibleIgnitionSequences.add(newIgnitionSequence);
        }

        return allPossibleIgnitionSequences;
    }

    private Route createtRouteWithoutBreaks(IgnitionSequence ignitionSequence, RouteData routeData) {
        ArrayList<ExodusIgnitionCode> routeIgnitionSequence = ignitionSequence.getSequence();
        Route route = new Route();
        ArrayList<Exodus> exoduses = new ArrayList<>();
        LocalDateTime lastTripPeriodStartTime = converter.convertStringTimeToDate(routeData.getLastTripStartTime());
        Duration haltTime = Duration.ofSeconds(routeData.getHaltTimeMinutes() * 60 + routeData.getHaltTimeSeconds());
        for (ExodusIgnitionCode ignitionCode : routeIgnitionSequence) {
            LocalDateTime tripPeriodStartTime = ignitionCode.getStartTime();
            String goTripPeriodType = ignitionCode.getType();
            String returnTripPeriodType;

            Duration goTripPeriodTime;
            Duration returnTripPeriodTime;
            if (routeData.isCircularRoute()) {
                int tripPeriodTimeInSeconds
                        = (routeData.getAbTripTimeMinutes() + routeData.getBaTripTimeMinutes()) * 60
                        + routeData.getAbTripTimeSeconds()
                        + routeData.getBaTripTimeSeconds();
                goTripPeriodTime = Duration.ofSeconds(tripPeriodTimeInSeconds);
                goTripPeriodTime = goTripPeriodTime.plus(Duration.ofMinutes(10));//of not existing halt time
                Exodus exodus = new Exodus();

                while (tripPeriodStartTime.isBefore(lastTripPeriodStartTime)
                        || tripPeriodStartTime.isEqual(lastTripPeriodStartTime)) {

                    TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, goTripPeriodTime, goTripPeriodType);
                    exodus.getTripPeriods().add(tripPeriod);
                    tripPeriodStartTime = tripPeriodStartTime.plus(goTripPeriodTime);

                    TripPeriod haltTripPeriod = new TripPeriod(tripPeriodStartTime, haltTime, "halt");
                    tripPeriodStartTime = tripPeriodStartTime.plus(haltTime);
                    exodus.getTripPeriods().add(haltTripPeriod);

                }
                exoduses.add(exodus);
            } else {

                if (goTripPeriodType.equals("ab")) {
                    returnTripPeriodType = "ba";

                    goTripPeriodTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());
                    returnTripPeriodTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());
                } else {

                    returnTripPeriodType = "ab";

                    goTripPeriodTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());
                    returnTripPeriodTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());
                }

                Exodus exodus = new Exodus();
                int counter = 0;
                while (tripPeriodStartTime.isBefore(lastTripPeriodStartTime)
                        || tripPeriodStartTime.isEqual(lastTripPeriodStartTime)) {

                    if (counter % 2 == 0) {
                        TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, goTripPeriodTime, goTripPeriodType);
                        exodus.getTripPeriods().add(tripPeriod);
                        tripPeriodStartTime = tripPeriodStartTime.plus(goTripPeriodTime);
                    } else {
                        TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, returnTripPeriodTime, returnTripPeriodType);
                        exodus.getTripPeriods().add(tripPeriod);
                        tripPeriodStartTime = tripPeriodStartTime.plus(returnTripPeriodTime);
                    }
                    TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, haltTime, "halt");
                    tripPeriodStartTime = tripPeriodStartTime.plus(haltTime);
                    exodus.getTripPeriods().add(tripPeriod);

                    counter++;
                }
                exoduses.add(exodus);
            }
        }
        route.setExoduses(exoduses);
        return route;
    }

    private ArrayList<IgnitionSequence> getAllVariationsOfChangablePart(IgnitionSequence changingPart, RouteData routeData) {
        ArrayList<IgnitionSequence> allVariations = new ArrayList<>();
        if (changingPart.getSequence().size() == 0) {
            allVariations.add(changingPart);
            return allVariations;
        }

        ArrayList<String[]> allPermutationsMap = getAllPermutationsMap(changingPart);
        LocalDateTime standartStartTime = changingPart.getSequence().get(0).getStartTime();
        String goTripType = changingPart.getSequence().get(0).getType();
        Duration goTripTime;
        Duration returnTripTime;
        if (goTripType.equals("ab")) {
            goTripTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() + routeData.getHaltTimeMinutes()) * 60 + (routeData.getAbTripTimeSeconds() + routeData.getHaltTimeSeconds()));
            returnTripTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() + routeData.getHaltTimeMinutes()) * 60 + (routeData.getBaTripTimeSeconds() + routeData.getHaltTimeSeconds()));
        } else {
            goTripTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() + routeData.getHaltTimeMinutes()) * 60 + (routeData.getBaTripTimeSeconds() + routeData.getHaltTimeSeconds()));
            returnTripTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() + routeData.getHaltTimeMinutes()) * 60 + (routeData.getAbTripTimeSeconds() + routeData.getHaltTimeSeconds()));
        }
        for (String[] array : allPermutationsMap) {
            IgnitionSequence newIgnitionSequence = new IgnitionSequence();
            for (int x = 0; x < array.length; x++) {
                if (array[x].equals(goTripType)) {
                    if (changingPart.getSequence().get(x).getType().equals(goTripType)) {
                        newIgnitionSequence.addExodusIgnitionCode(changingPart.getSequence().get(x));

                    } else {
                        ExodusIgnitionCode exodusIgnitonCode = new ExodusIgnitionCode();
                        exodusIgnitonCode.setType(array[x]);
                        LocalDateTime startTime = changingPart.getSequence().get(x).getStartTime();
                        exodusIgnitonCode.setStartTime(startTime.plus(returnTripTime));
                        newIgnitionSequence.addExodusIgnitionCode(exodusIgnitonCode);
                    }
                } else {
                    if (changingPart.getSequence().get(x).getType().equals(goTripType)) {
                        ExodusIgnitionCode exodusIgnitonCode = new ExodusIgnitionCode();
                        exodusIgnitonCode.setType(array[x]);
                        LocalDateTime startTime = changingPart.getSequence().get(x).getStartTime();
                        exodusIgnitonCode.setStartTime(startTime.minus(returnTripTime));
                        newIgnitionSequence.addExodusIgnitionCode(exodusIgnitonCode);
                    } else {
                        newIgnitionSequence.addExodusIgnitionCode(changingPart.getSequence().get(x));
                    }
                }
            }
            allVariations.add(newIgnitionSequence);
        }
        return allVariations;
    }

    private ArrayList<String[]> getAllPermutationsMap(IgnitionSequence changingPart) {
        ArrayList<ExodusIgnitionCode> sequence = changingPart.getSequence();
        int size = sequence.size();
        Object[] initialExodusCodeMap = new Object[size];
        int index = 0;
        for (ExodusIgnitionCode ignitionCode : sequence) {
            initialExodusCodeMap[index] = ignitionCode.getType();
            index++;
        }

        Permutations permutator = new Permutations(initialExodusCodeMap);
        ArrayList allPermutationsMap = new ArrayList();
        while (permutator.hasNext()) {
            Object[] next = permutator.next();
            String[] array = new String[size];
            int ind = 0;
            for (Object ob : next) {
                array[ind] = (String) ob;
                ind++;
            }
            allPermutationsMap.add(array);
        }

        return allPermutationsMap;
    }

    private ArrayList<ArrayList<Integer>> findBreaksPossiblePoints(Route choosedRoute, RouteData routeData) {
        ArrayList<ArrayList<Integer>> breakPointsMap = new ArrayList();

        LocalDateTime firstBreakStartTime = converter.convertStringTimeToDate(routeData.getFirstBreakStartTime());
        LocalDateTime lastBreakEndTime = converter.convertStringTimeToDate(routeData.getLastBreakEndTime());
        String breakStayPoint = routeData.getBreakStayPoint();
        ArrayList<Exodus> exoduses = choosedRoute.getExoduses();

        for (Exodus exodus : exoduses) {
            ArrayList breakPossiblePointsArray = new ArrayList();
            ArrayList<TripPeriod> tripPeriods = exodus.getTripPeriods();
            int breakPoint = 0;
            for (TripPeriod tripPeriod : tripPeriods) {
                if (tripPeriod.getType().equals("halt")) {
                    //do nothing
                } else {
                    if (breakPoint > 0
                            && (tripPeriod.getStartTime().isAfter(firstBreakStartTime) || tripPeriod.getStartTime().isEqual(firstBreakStartTime))
                            && (tripPeriod.getStartTime().isBefore(lastBreakEndTime) || tripPeriod.getStartTime().isEqual(lastBreakEndTime))
                            && (tripPeriod.getType().equals(breakStayPoint) || breakStayPoint.equals("AB"))) {
                        breakPossiblePointsArray.add(breakPoint);

                    }
                    breakPoint++;
                }
            }
            breakPointsMap.add(breakPossiblePointsArray);
        }

        return breakPointsMap;

    }

    private Route createRouteWithBreaks(IgnitionSequence ignitionSequence, RouteData routeData, ArrayList breakSequence) {

        ArrayList<ExodusIgnitionCode> routeIgnitionSequence = ignitionSequence.getSequence();
        Route route = new Route();
        ArrayList<Exodus> exoduses = new ArrayList<>();
        LocalDateTime lastTripPeriodStartTime = converter.convertStringTimeToDate(routeData.getLastTripStartTime());
        Duration haltTime = Duration.ofSeconds(routeData.getHaltTimeMinutes() * 60 + routeData.getHaltTimeSeconds());

        int exodusCounter = 0;
        HashSet<TripPeriod> breaksPool = new HashSet();
        for (ExodusIgnitionCode ignitionCode : routeIgnitionSequence) {
            int breakPointInExodus = (int) breakSequence.get(exodusCounter);
            LocalDateTime tripPeriodStartTime = ignitionCode.getStartTime();
            String goTripPeriodType = ignitionCode.getType();
            String returnTripPeriodType;

            Duration goTripPeriodTime;
            Duration returnTripPeriodTime;

            if (routeData.isCircularRoute()) {
                int tripPeriodTimeInSeconds
                        = (routeData.getAbTripTimeMinutes() + routeData.getBaTripTimeMinutes()) * 60
                        + routeData.getAbTripTimeSeconds()
                        + routeData.getBaTripTimeSeconds();
                goTripPeriodTime = Duration.ofSeconds(tripPeriodTimeInSeconds);
                goTripPeriodTime = goTripPeriodTime.plus(Duration.ofMinutes(10));//of not existing halt time
                Exodus exodus = new Exodus();
                int counter = 0;
                while (tripPeriodStartTime.isBefore(lastTripPeriodStartTime)
                        || tripPeriodStartTime.isEqual(lastTripPeriodStartTime)) {

                    TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, goTripPeriodTime, goTripPeriodType);
                    exodus.getTripPeriods().add(tripPeriod);
                    tripPeriodStartTime = tripPeriodStartTime.plus(goTripPeriodTime);
                    counter++;

                    if (counter == breakPointInExodus) {
                        Duration breakDuration = Duration.ofMinutes(routeData.getBreakTimeMinutes());
                        TripPeriod breakTripPeriod = new TripPeriod(tripPeriodStartTime, breakDuration, "break");

                        if (breakCrossesOtherBreak(breakTripPeriod, breaksPool)) {
                            return null;
                        }
                        breaksPool.add(breakTripPeriod);

                        exodus.getTripPeriods().add(breakTripPeriod);
                        tripPeriodStartTime = tripPeriodStartTime.plus(breakDuration);

                        TripPeriod haltTripPeriod = new TripPeriod(tripPeriodStartTime, haltTime, "halt");
                        tripPeriodStartTime = tripPeriodStartTime.plus(haltTime);
                        exodus.getTripPeriods().add(haltTripPeriod);

                    } else {
                        TripPeriod haltTripPeriod = new TripPeriod(tripPeriodStartTime, haltTime, "halt");
                        tripPeriodStartTime = tripPeriodStartTime.plus(haltTime);
                        exodus.getTripPeriods().add(haltTripPeriod);
                    }

                }
                exoduses.add(exodus);
            } else {

                if (goTripPeriodType.equals("ab")) {
                    returnTripPeriodType = "ba";

                    goTripPeriodTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());
                    returnTripPeriodTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());
                } else {

                    returnTripPeriodType = "ab";

                    goTripPeriodTime = Duration.ofSeconds((routeData.getBaTripTimeMinutes() * 60) + routeData.getBaTripTimeSeconds());
                    returnTripPeriodTime = Duration.ofSeconds((routeData.getAbTripTimeMinutes() * 60) + routeData.getAbTripTimeSeconds());
                }

                Exodus exodus = new Exodus();
                int counter = 0;
                while (tripPeriodStartTime.isBefore(lastTripPeriodStartTime)
                        || tripPeriodStartTime.isEqual(lastTripPeriodStartTime)) {

                    if (counter % 2 == 0) {
                        TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, goTripPeriodTime, goTripPeriodType);
                        exodus.getTripPeriods().add(tripPeriod);
                        tripPeriodStartTime = tripPeriodStartTime.plus(goTripPeriodTime);
                    } else {
                        TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, returnTripPeriodTime, returnTripPeriodType);
                        exodus.getTripPeriods().add(tripPeriod);
                        tripPeriodStartTime = tripPeriodStartTime.plus(returnTripPeriodTime);
                    }
                    counter++;

                    if (counter == breakPointInExodus) {
                        Duration breakDuration = Duration.ofMinutes(routeData.getBreakTimeMinutes());
                        TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, breakDuration, "break");

                        if (breakCrossesOtherBreak(tripPeriod, breaksPool)) {
                            return null;
                        }
                        breaksPool.add(tripPeriod);

                        exodus.getTripPeriods().add(tripPeriod);
                        tripPeriodStartTime = tripPeriodStartTime.plus(breakDuration);

                        TripPeriod haltTripPeriod = new TripPeriod(tripPeriodStartTime, haltTime, "halt");
                        tripPeriodStartTime = tripPeriodStartTime.plus(haltTime);
                        exodus.getTripPeriods().add(haltTripPeriod);

                    } else {
                        TripPeriod tripPeriod = new TripPeriod(tripPeriodStartTime, haltTime, "halt");
                        tripPeriodStartTime = tripPeriodStartTime.plus(haltTime);
                        exodus.getTripPeriods().add(tripPeriod);
                    }
                }
                exoduses.add(exodus);
            }
            exodusCounter++;
        }
        route.setExoduses(exoduses);
        return route;
    }

    private boolean breakCrossesOtherBreak(TripPeriod breakTripPeriod, HashSet<TripPeriod> breaksPool) {

        LocalDateTime breakEndTime = breakTripPeriod.getStartTime().plus(breakTripPeriod.getDuration());
        for (TripPeriod breakInPoolTripPeriod : breaksPool) {
            LocalDateTime poolBreakEndTime = breakInPoolTripPeriod.getStartTime().plus(breakInPoolTripPeriod.getDuration());
            if (breakTripPeriod.getStartTime().isEqual(breakInPoolTripPeriod.getStartTime())) {
                return true;
            }
            if (breakTripPeriod.getStartTime().isAfter(breakInPoolTripPeriod.getStartTime())
                    && breakTripPeriod.getStartTime().isBefore(poolBreakEndTime)) {
                return true;
            }
            if (breakEndTime.isAfter(breakInPoolTripPeriod.getStartTime())
                    && breakEndTime.isBefore(poolBreakEndTime)) {
                return true;
            }
        }
        return false;
    }
}
