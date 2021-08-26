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
        DetailedRoutesPager detailedRoutesPager = createDetailedRoutesPager(routeDates);
        detailedRoutesPager.setCurrentRoute("initial");
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(detailedRoutesPager);
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);

        detailedRoute.calculateIntervalsData();
        model.addAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "intervals";
    }

    @RequestMapping(value = "intervalsRequest")
    public String intervalsRequest(@RequestParam("requestedRoute") String requestedRoute, ModelMap model, HttpSession session) {
        DetailedRoutesPager detailedRoutesPager = (DetailedRoutesPager) session.getAttribute("detailedRoutesPager");

        detailedRoutesPager.setCurrentRoute(requestedRoute);
        DetailedRoute detailedRoute = intervalsDao.getRoutesForIntervals(detailedRoutesPager);
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);

        detailedRoute.calculateIntervalsData();
        model.addAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "intervals";
    }

    private DetailedRoutesPager createDetailedRoutesPager(String routeDates) {

        DetailedRoutesPager detailedRoutesPager = new DetailedRoutesPager();

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
