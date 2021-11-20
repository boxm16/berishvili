package Controller;

import graphical.Exodus;
import graphical.ExodusIgnitionCode;
import graphical.IgnitionSequence;
import graphical.Route;
import graphical.RouteData;
import graphical.TripPeriod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    public String graphicalRouteVersions(ModelMap model,
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

        return "graphicalRoute";
    }

    @RequestMapping(value = "breaks", method = RequestMethod.GET)
    public String breaks(ModelMap model) {
        ArrayList<Route> routes = new ArrayList();
        for (int x = 0; x < 10; x++) {
            Route route = new Route();
            for (int y = 0; y < 5; y++) {
                Exodus exodus = new Exodus();
                for (int z = 0; z < 10; z++) {
                    TripPeriod tripPeriod = new TripPeriod();
                    try {
                        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm:ss");
                        String stringTime = "21:00:00";
                        Date dateTime = dateTimeFormatter.parse(stringTime);
                        LocalDateTime startTime = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                        tripPeriod.setStartTime(startTime);
                        exodus.getTripPeriods().add(tripPeriod);
                    } catch (ParseException ex) {
                        Logger.getLogger(GraphicalController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                route.getExoduses().add(exodus);
            }
            routes.add(route);
        }

        model.addAttribute("routes", routes);

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
        int returnTripPeriodsCount = routeData.getStarterTrip().equals("ab") ? routeData.getBaBusCount() : routeData.getAbBusCount();
        IgnitionSequence currentSequence = changingPart;

        int shuttleIndex = currentSequence.getSequence().size() - returnTripPeriodsCount;
        while (!reachedEnd(currentSequence, returnTripPeriodsCount, routeData)) {
            currentSequence = crollUp(currentSequence, shuttleIndex, routeData);
            allVariations.add(currentSequence);

            if (shuttleIndex == 0) {
                currentSequence = pullUp(currentSequence, routeData);
                shuttleIndex = getShuttleIndexAfterPullUp(currentSequence);
            }
            shuttleIndex--;
        }
        return allVariations;
    }

    private boolean reachedEnd(IgnitionSequence ignitionSequence, int returnTripPeriodsCount, RouteData routeData) {
        String typeZero = routeData.getStarterTrip().equals("ab") ? "ba" : "ab";

        while (returnTripPeriodsCount > 0) {
            String currentType = ignitionSequence.getSequence().get(returnTripPeriodsCount - 1).getType();
            if (currentType.equals(typeZero)) {
                returnTripPeriodsCount--;
            } else {
                return false;
            }
        }
        return true;
    }

    private IgnitionSequence crollUp(IgnitionSequence currentSequence, int shuttleIndex, RouteData routeData) {
        IgnitionSequence newIgnitionSequence = new IgnitionSequence();
        ArrayList<ExodusIgnitionCode> clonedArrayList = (ArrayList<ExodusIgnitionCode>) currentSequence.getSequence().clone();
        Duration returnTripTime;
        if (clonedArrayList.get(shuttleIndex).getType().equals("ab")) {
            ExodusIgnitionCode exodusIgnitionCode = new ExodusIgnitionCode();
            exodusIgnitionCode.setType("ba");
            returnTripTime = Duration.ofSeconds(((routeData.getBaTripTimeMinutes() + routeData.getHaltTimeMinutes()) * 60) + (routeData.getBaTripTimeSeconds() + routeData.getHaltTimeSeconds()));
            exodusIgnitionCode.setStartTime(clonedArrayList.get(shuttleIndex).getStartTime().minus(returnTripTime));
            clonedArrayList.set(shuttleIndex, exodusIgnitionCode);

            ExodusIgnitionCode exodusIgnitionCodeZ = new ExodusIgnitionCode();
            exodusIgnitionCodeZ.setType("ab");
            exodusIgnitionCodeZ.setStartTime(clonedArrayList.get(shuttleIndex + 1).getStartTime().plus(returnTripTime));
            clonedArrayList.set(shuttleIndex + 1, exodusIgnitionCodeZ);
        } else {
        }
        newIgnitionSequence.setSequence(clonedArrayList);
        return newIgnitionSequence;
    }

    private IgnitionSequence pullUp(IgnitionSequence currentSequence, RouteData routeData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private int getShuttleIndexAfterPullUp(IgnitionSequence currentSequence) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
