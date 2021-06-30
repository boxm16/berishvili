/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import DAO.IndexDao;
import Model.Route;
import java.time.Duration;
import java.time.Instant;
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
        TreeMap<Float, Route> routes = rf.getRoutesNumbersAndDatesFromUploadedExcelFile();
        Instant end = Instant.now();
        System.out.println("Time needed to get route numbers and dates from uploaded excel file:" + Duration.between(start, end));
        model.addAttribute("routes", routes);
        return "index";
    }

    @RequestMapping(value = "index")
    public String goToIndexDataBaseEdition(ModelMap model) {
        Instant start = Instant.now();

        TreeMap<Float, Route> routes = indexDao.getRoutes();
        Instant end = Instant.now();
        System.out.println("Time needed to get route numbers and dates from database:" + Duration.between(start, end));
        model.addAttribute("routes", routes);
        return "index";
    }

}
