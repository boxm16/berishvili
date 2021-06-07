package Controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
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
        System.out.println("--------");
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

}
