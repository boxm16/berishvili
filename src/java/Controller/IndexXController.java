/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.IndexDao;
import Model.BasicRoute;
import Model.Day;
import Model.IndexDates;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexXController {

    @Autowired
    private IndexDao indexDao;

    private String basementDirectory;

    public IndexXController() {
        BasementController basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();
    }

    @RequestMapping(value = "indexX")
    public String goToIndexX() {
        System.out.println("oeee");
        return "indexX";
    }

    @RequestMapping(value = "indexFromExcelFile")
    public String goToIndex(ModelMap model) {
        Instant start = Instant.now();
        RouteFactory rf = new RouteFactory();
        TreeMap<Float, BasicRoute> routes = rf.getRoutesNumbersAndDatesFromUploadedExcelFile();
        Instant end = Instant.now();
        System.out.println("Time needed to get route numbers and dates from uploaded excel file:" + Duration.between(start, end));
        model.addAttribute("routes", routes);
        return "index";
    }

    @RequestMapping(value = "index")
    public String goToIndexDataBaseEdition(ModelMap model) {
        Instant start = Instant.now();

        TreeMap<Float, BasicRoute> routes = indexDao.getRoutes();
        IndexDates indexDates = getIndexDates(routes);
        Instant end = Instant.now();
        System.out.println("Time needed to get route numbers and dates from database:" + Duration.between(start, end));
        model.addAttribute("routes", routes);
        model.addAttribute("dates", indexDates);
        return "index";
    }

    private IndexDates getIndexDates(TreeMap<Float, BasicRoute> routes) {
        IndexDates indexDates = new IndexDates();
        for (Map.Entry<Float, BasicRoute> routeEntry : routes.entrySet()) {
            TreeMap<Date, Day> days = routeEntry.getValue().getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                indexDates.addDate(dayEntry.getKey());
            }
        }
        return indexDates;
    }

}
