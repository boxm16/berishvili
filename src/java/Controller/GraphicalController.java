package Controller;

import graphical.Exodus;
import graphical.Route;
import graphical.RouteData;
import graphical.TripPeriod;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    @RequestMapping(value = "graphicalRouteInitialRequest", method = RequestMethod.GET)
    public String graphicalRouteInitialRequest(ModelMap model) {

        RouteData routeData = new RouteData();
        model.addAttribute("route", routeData);

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
            @RequestParam(value = "starterTripInFormInput", required = false) String starterTripInFormInput,
            @RequestParam("abTripTimeMinutesInFormInput") String abTripTimeMinutesInFormInput,
            @RequestParam("abTripTimeSecondsInFormInput") String abTripTimeSecondsInFormInput,
            @RequestParam("baTripTimeMinutesInFormInput") String baTripTimeMinutesInFormInput,
            @RequestParam("baTripTimeSecondsInFormInput") String baTripTimeSecondsInFormInput,
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

        routeData.setStarterTrip(starterTripInFormInput);

        routeData.setAbTripTimeMinutes(Integer.valueOf(abTripTimeMinutesInFormInput));

        routeData.setAbTripTimeSeconds(Integer.valueOf(abTripTimeSecondsInFormInput));

        routeData.setBaTripTimeMinutes(Integer.valueOf(baTripTimeMinutesInFormInput));

        routeData.setBaTripTimeSeconds(Integer.valueOf(baTripTimeSecondsInFormInput));

        routeData.setAbBusCount(Integer.valueOf(abBusCountInFormInput));
        routeData.setBaBusCount(Integer.valueOf(baBusCountInFormInput));

        routeData.setIntervalTime(intervalTimeInFormInput);
        //   ArrayList<ExodusIgnitionCode

        model.addAttribute("route", routeData);

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

}
