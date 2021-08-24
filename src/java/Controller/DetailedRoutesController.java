package Controller;

import DAO.DetailedRouteDao;
import Model.Day;
import Model.DetailedRoute;
import Model.DetailedRoutesPager;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DetailedRoutesController {

    @Autowired
    private DetailedRouteDao detailedRouteDao;
    private MemoryUsage memoryUsage;

    public DetailedRoutesController() {
        memoryUsage = new MemoryUsage();
    }

    @RequestMapping(value = "detailedRoutesInitialRequest")
    public String detailedRoutesInitialRequest(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {

        DetailedRoutesPager detailedRoutesPager = createDetailedRoutesPager(routeDates);
        detailedRoutesPager.setCurrentPage(1);
        DetailedRoute detailedRoute = detailedRouteDao.getDetailedRoute(detailedRoutesPager);
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);
        if (session.getAttribute("percents") == null) {
            session.setAttribute("percents", 20);
        }

        TreeMap<Date, Day> days = detailedRoute.getDays();
        for (Map.Entry<Date, Day> entry : days.entrySet()) {
            //System.out.println(entry.getValue().);
        }

        detailedRoute.calculateData();
        model.addAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("detailedRoute", detailedRoute);

        return "detailedRoutes";
    }

    @RequestMapping(value = "detailedRoutesRequest")
    public String detailedRoutesRequest(@RequestParam("requestedPage") String requestedPage, ModelMap model, HttpSession session) {
        DetailedRoutesPager detailedRoutesPager = (DetailedRoutesPager) session.getAttribute("detailedRoutesPager");

        detailedRoutesPager.setCurrentPage(Integer.valueOf(requestedPage));
        DetailedRoute detailedRoute = detailedRouteDao.getDetailedRoute(detailedRoutesPager);
        session.setAttribute("detailedRoutesPager", detailedRoutesPager);
        if (session.getAttribute("percents") == null) {
            session.setAttribute("percents", 20);
        }
        detailedRoute.calculateData();
        model.addAttribute("detailedRoutesPager", detailedRoutesPager);
        model.addAttribute("detailedRoute", detailedRoute);
        return "detailedRoutes";
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
