package Controller;

import DAO.MisconductsDao;
import Model.DetailedRoutesPager;
import Model.MisconductTripPeriod;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MisconductsController {

    @Autowired
    private MisconductsDao misconductsDao;
    private MemoryUsage memoryUsage;

    public MisconductsController() {
        memoryUsage = new MemoryUsage();
    }

    @RequestMapping(value = "misconducts")
    public String misconducts(@RequestParam("routes:dates") String routeDates, ModelMap model, HttpSession session) {
        System.out.println("---------------------------Starting Collecting Misconducts----------------");
        Instant start = Instant.now();
        DetailedRoutesPager misconductsPager = createDetailedRoutesPager(routeDates);

        ArrayList<MisconductTripPeriod> misconductTripPeriods = misconductsDao.getMisconductTripPeriods(misconductsPager);

        Instant end = Instant.now();

        System.out.println("End of collecting misconducts. Time neede:" + Duration.between(start, end));
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        model.addAttribute("misconductTripPeriods", misconductTripPeriods);
        return "misconducts";
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
