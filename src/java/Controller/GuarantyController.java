package Controller;

import Model.GuarantyExodus;
import Model.GuarantyRoute;
import Model.GuarantyTripPeriod;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
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

        //here we calculate given data to get some results inside guarantyRoutes
        calculateData(guarantyRoutes);

        //now write the results
        ExcelWriter excelWriter = new ExcelWriter();
        excelWriter.exportGuarantyRoutes(guarantyRoutes, fileName);

        model.addAttribute("fileName", fileName);
        return "guarantyTripsDashboard";

    }

    private void calculateData(TreeMap<Float, GuarantyRoute> guarantyRoutes) {
        ArrayList<LocalDateTime> abTimeTable = new ArrayList();
        ArrayList<LocalDateTime> baTimeTable = new ArrayList();
        for (Map.Entry<Float, GuarantyRoute> routeEntry : guarantyRoutes.entrySet()) {

            GuarantyRoute guarantyRoute = routeEntry.getValue();
            TreeMap<Short, GuarantyExodus> exoduses = guarantyRoute.getExoduses();
            for (Map.Entry<Short, GuarantyExodus> exodusEntry : exoduses.entrySet()) {
                GuarantyExodus exodus = exodusEntry.getValue();
                ArrayList<GuarantyTripPeriod> guarantyTripPeriods = exodus.getGuarantyTripPeriods();
                for (GuarantyTripPeriod tripPeriod : guarantyTripPeriods) {
                    String tripPeriodType = tripPeriod.getType();
                    if (tripPeriodType.equals("ab")) {
                        abTimeTable.add(tripPeriod.getStartTimeScheduled());
                    }
                    if (tripPeriodType.equals("ba")) {
                        baTimeTable.add(tripPeriod.getStartTimeScheduled());
                    }
                }
            }
            Collections.sort(abTimeTable);
            Collections.sort(baTimeTable);
            if (abTimeTable.size() > 2) {
                guarantyRoute.setAbGuarantyTripPeriodStartTimeScheduled(abTimeTable.get(abTimeTable.size() - 1));
                guarantyRoute.setAbSubguarantyTripPeriodStartTimeScheduled(abTimeTable.get(abTimeTable.size() - 2));
            }
            if (baTimeTable.size() > 2) {
                guarantyRoute.setBaGuarantyTripPeriodStartTimeScheduled(baTimeTable.get(baTimeTable.size() - 1));
                guarantyRoute.setBaSubguarantyTripPeriodStartTimeScheduled(baTimeTable.get(baTimeTable.size() - 2));
            }
            abTimeTable.clear();
            baTimeTable.clear();

        }
    }
}
