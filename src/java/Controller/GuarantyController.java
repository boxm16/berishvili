package Controller;

import Model.GuarantyRoute;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class GuarantyController {

    private String basementDirectory;

    public GuarantyController() {
        BasementController basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();

    }

    @RequestMapping(value = "guarantyTripsUploadPage")
    public String goToGuarantyTripsUploadPage(ModelMap model) {

        model.addAttribute("uploadedFileExists", true);
        return "guarantyTripsUploadPage";
    }

    @RequestMapping(value = "/saveGuarantyExcelFile", method = RequestMethod.POST)
    public String upload(@RequestParam CommonsMultipartFile file, ModelMap model) {
        if (file.isEmpty()) {
            model.addAttribute("status", "Upload could not been completed");
            model.addAttribute("errorMessage", "emptyFile");
            return "guarantyTripsUploadPage";
        }
        String filename = "uploadedGuarantyExcelFile.xlsx";
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basementDirectory + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("status", "Upload could not been completed");
            model.addAttribute("errorMessage", e);
            return "guarantyTripsUploadPage";
        }
        return "guarantyTripsDashboard";
    }

    @RequestMapping(value = "/gotGuarantyDashboard", method = RequestMethod.GET)
    public String gotGuarantyDashboard() {

        return "guarantyTripsDashboard";
    }

    @RequestMapping(value = "guarantyExport", method = RequestMethod.POST)
    public String garantyExport(String fileName, ModelMap model) {

        RouteFactory routeFactory = new RouteFactory();
        TreeMap<Float, GuarantyRoute> guarantyRoutes = new TreeMap();
        guarantyRoutes = routeFactory.createGuarantyRoutesFromUploadedFile();
        if (guarantyRoutes.containsKey(0.001f)) {//0.001f is code for error when actualStartTimes exist in file,(it cant be, because this file have to be from futer dates

            model.addAttribute("error", "ატვირთული ფაილი არ არის საგარანტიო გასცლების გამოსათვლელად გამოსადეგი (ფაილში იძებნება ფაქტიური გასვლის დრო, რაც აქ დაუშვებელია)"
                    + "<a  href=\"guarantyTripsUploadPage.htm\">დაბრუნდი და ატვირთე ახალი ფაილი</a>");

        }
        if (guarantyRoutes.containsKey(0.002f)) {//0.002 is a error code for error when uploaded file contains data with more than one datestamp

            model.addAttribute("dateStampError", "ატვირთული ფაილი არ არის საგარანტიო გასვლების გამოსათვლელად გამოსადეგი (ფაილში იძებნება სხვადასხვა რიცხვი, რაც აქ დაუშვებელია)&nbsp&nbsp"
                    + "<a  href=\"guarantyTripsUploadPage.htm\">დაბრუნდი და ატვირთე ახალი ფაილი</a>");

        }
        return "guarantyTripsDashboard";

    }
}
