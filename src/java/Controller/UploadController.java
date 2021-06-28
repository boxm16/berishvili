package Controller;

import Model.Route;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.time.Instant;
import java.util.TreeMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class UploadController {

    private String basementDirectory;

    public UploadController() {
        BasementController basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();

    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String rerouteUploadRequest() {
        return "upload";
    }

    @RequestMapping(value = "/savefile", method = RequestMethod.POST)
    public String upload(@RequestParam CommonsMultipartFile file, ModelMap model) {
        System.out.println("Starting working on uploaded excel file (saving as file, and saving as last upload in database)");
        Instant start = Instant.now();
        String filename = "uploadedExcelFile.xlsx";
        if (file.isEmpty()) {
            model.addAttribute("uploadStatus", "Upload could not been completed");
            model.addAttribute("errorMessage", "არცერთი ფაილი არ იყო არჩეული");
            return "upload";
        }
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basementDirectory + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();
            RouteFactory routeFactory = new RouteFactory();
            TreeMap<Float, Route> routesNumbersAndDatesFromUploadedExcelFile = routeFactory.getRoutesNumbersAndDatesFromUploadedExcelFile();
            UploadInsertionThread uit = new UploadInsertionThread(routesNumbersAndDatesFromUploadedExcelFile);
            uit.start();
            Instant end = Instant.now();
            System.out.println("Time needed for uploading process:" + Duration.between(start, end));

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("uploadStatus", "Upload could not been completed:" + e);
            return "upload";
        }

        return "redirect:/index.htm";
    }

}
