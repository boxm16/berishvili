package Controller;

import java.io.File;
import java.util.ArrayList;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TechController {

    private String basementDirectory;
    private BasementController basementController;

    public TechController() {
        this.basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();
    }

    @RequestMapping(value = "/techMan")
    public String rerouteToTechManJsp(ModelMap model) {
        Boolean uploadsDirectoryExists = uploadsDirectoryExists();
        //  ArrayList uploadedFilesList = getUploadedFilesList();
        String hostName = this.basementController.getApplicationHostName();
        model.addAttribute("hostName", hostName);
        model.addAttribute("uploadsDirectoryExists", uploadsDirectoryExists);
        //model.addAttribute("uploadedFilesList", uploadedFilesList);
        return "techMan";
    }

    @RequestMapping(value = "createUploadDirecotry", method = RequestMethod.GET)
    public String createUploadDirectory(ModelMap model) {
        Boolean uploadsDirectoryExists = uploadsDirectoryExists();
        if (uploadsDirectoryExists) {
            model.addAttribute("uploadsDirectoryExists", uploadsDirectoryExists);
            model.addAttribute("directoryPath", this.basementDirectory + "/uploads");
            return "techMan";
        }
        //Creating a File object
        File file = new File(this.basementDirectory + "/uploads");
        //Creating the directory
        boolean bool = file.mkdir();
        System.out.println(bool);
        String status = "Uploads directory could not been created.";
        if (bool) {
            status = "Uploads directroy has just been created";
        }
        model.addAttribute("uploadsDirectoryExists", status);
        model.addAttribute("directoryPath", this.basementDirectory);
        return "techMan";

    }

    private Boolean uploadsDirectoryExists() {
        File dir = new File(this.basementDirectory + "/uploads");
        // Tests whether the directory denoted by this abstract pathname exists.
        return dir.exists();
    }

    private ArrayList getUploadedFilesList() {
        ArrayList files = new ArrayList();
        File directory = new File(this.basementDirectory + "/uploads");
        File[] fList = directory.listFiles();
        for (File file : fList) {
            files.add(file);
        }
        return files;
    }

}