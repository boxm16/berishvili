/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Route;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexXController {

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

    @RequestMapping(value = "index")
    public String goToIndex(ModelMap model) {
        Instant start = Instant.now();
        RouteFactory rf = new RouteFactory();
        TreeMap<Float, Route> routes = rf.getRoutesNumbersAndDatesFromUploadedExcelFile();
        Instant end = Instant.now();
        System.out.println("Time needed for reading excel file:" + Duration.between(start, end));
        model.addAttribute("routes", routes);
        return "index";
    }

}
