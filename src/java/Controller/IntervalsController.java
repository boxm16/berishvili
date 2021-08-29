/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.IntervalsDao;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IntervalsController {

    @Autowired
    private IntervalsDao intervalsDao;
    private MemoryUsage memoryUsage;

    public IntervalsController() {
        memoryUsage = new MemoryUsage();
    }

    @RequestMapping(value = "intervalsInitialRequest")
    public String intervalsInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        DetailedRoutesPager intervalsPager = createDetailedRoutesPager(routeDates);
        intervalsPager.setCurrentRoute("initial");
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(intervalsPager);
        session.setAttribute("intervalsPager", intervalsPager);

        detailedRoute.calculateIntervalsData();
        model.addAttribute("intervalsPager", intervalsPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "intervals";
    }

    @RequestMapping(value = "intervalsRequest")
    public String intervalsRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        DetailedRoutesPager intervalsPager = (DetailedRoutesPager) session.getAttribute("intervalsPager");

        intervalsPager.setCurrentRoute(requestedRoute);
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(intervalsPager);
        session.setAttribute("intervalsPager", intervalsPager);

        detailedRoute.calculateIntervalsData();
        model.addAttribute("intervalsPager", intervalsPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "intervals";
    }

    private DetailedRoutesPager createDetailedRoutesPager(String routeDates) {

        DetailedRoutesPager detailedRoutesPager = new DetailedRoutesPager("intervals");

        //trimming and cleaning input
        routeDates = routeDates.trim();
        if (routeDates.length() == 0) {
            return detailedRoutesPager;
        }
        if (routeDates.substring(routeDates.length() - 1, routeDates.length()).equals(",")) {
            routeDates = routeDates.substring(0, routeDates.length() - 1).trim();
        }
        String[] routeDatesArray = routeDates.split(",");
        for (String routeDate : routeDatesArray) {

            String[] routeDateArray = routeDate.split(":");
            String routeNumber = routeDateArray[0];
            String dateStamp = routeDateArray[1];
            detailedRoutesPager.addRouteNumber(routeNumber);
            detailedRoutesPager.addDateStamp(dateStamp);
        }
        return detailedRoutesPager;
    }

}
