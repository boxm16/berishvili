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
            @RequestParam(value = "roundCheckBox", required = false) String roundCheckBox,
            @RequestParam(value = "roundInputHour", required = false) String roundInputHour,
            @RequestParam(value = "roundInputMinute", required = false) String roundInputMinute,
            @RequestParam(value = "roundInputSeconds", required = false) String roundInputSecond,
            @RequestParam(value = "roundInputSeconds", required = false) String roundInputMinutes,
            @RequestParam(value = "roundInputSeconds", required = false) String roundInputSeconds,
            @RequestParam(value = "busCheckBox", required = false) String busCheckBox,
            @RequestParam(value = "busInput", required = false) String busInput,
            @RequestParam(value = "intervalCheckBox", required = false) String intervalCheckBox,
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
        System.out.println(roundCheckBox);

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
