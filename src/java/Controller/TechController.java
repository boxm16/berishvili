package Controller;

import DAO.RouteDao;
import DAO.TechDao;
import Model.RouteData;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

@Controller
public class TechController {

    @Autowired
    private TechDao techDao;
    
    @Autowired
    private RouteDao routeDao;

    private RouteFactory routeFactory;

    private String basementDirectory;
    private BasementController basementController;

    public TechController() {
        this.basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();
        this.routeFactory = new RouteFactory();
    }

    @RequestMapping(value = "/techMan")
    public String rerouteToTechManJsp(ModelMap model) {
        Boolean uploadsDirectoryExists = uploadsDirectoryExists();
        Boolean downloadsDirectoryExists = downloadsDirectoryExists();
        //  ArrayList uploadedFilesList = getUploadedFilesList();
        String hostName = this.basementController.getApplicationHostName();
        model.addAttribute("hostName", hostName);
        model.addAttribute("uploadsDirectoryExists", uploadsDirectoryExists);
        model.addAttribute("downloadsDirectoryExists", downloadsDirectoryExists);

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

    @RequestMapping(value = "createDownloadDirecotry", method = RequestMethod.GET)
    public String createDownloadDirectory(ModelMap model) {
        Boolean downloadsDirectoryExists = downloadsDirectoryExists();
        if (downloadsDirectoryExists) {
            model.addAttribute("downloadsDirectoryExists", downloadsDirectoryExists);
            model.addAttribute("directoryPath", this.basementDirectory + "/downloads");
            return "techMan";
        }
        //Creating a File object
        File file = new File(this.basementDirectory + "/downloads");
        //Creating the directory
        boolean bool = file.mkdir();
        System.out.println(bool);
        String status = "Downloads directory could not been created.";
        if (bool) {
            status = "Downloads directroy has just been created";
        }
        model.addAttribute("downloadsDirectoryExists", status);
        model.addAttribute("directoryPath", this.basementDirectory);
        return "techMan";
    }

    @RequestMapping(value = "runTest", method = RequestMethod.POST)
    public String runTest(ModelMap model, int routesQuantity) {
        MemoryUsage mu = new MemoryUsage();
        mu.runTest(routesQuantity);
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

    private Boolean downloadsDirectoryExists() {
        File dir = new File(this.basementDirectory + "/downloads");
        // Tests whether the directory denoted by this abstract pathname exists.
        return dir.exists();
    }

    @RequestMapping(value = "createSchema", method = RequestMethod.GET)
    public String createSchema(ModelMap model) throws SQLException {
        String schemaCreationStatus = techDao.createSchema();
        model.addAttribute("schemaCreationStatus", schemaCreationStatus);
        return "techMan";
    }

    @RequestMapping(value = "createTables", method = RequestMethod.GET)
    public String createTables(ModelMap model) throws SQLException {
        String routeTableCreationStatus = techDao.createRouteTable();
        String lastUploadTableCreationStatus = techDao.createLastUploadTable();
        String tripVoucherTableCreationStatus = techDao.createTripVoucherTable();
        String tripPeriodTableCreationStatus = techDao.createTripPeriodTable();
        model.addAttribute("routeTableCreationStatus", routeTableCreationStatus
                + "<br>" + lastUploadTableCreationStatus
                + "<br>" + tripVoucherTableCreationStatus
                + "<br>" + tripPeriodTableCreationStatus);

        return "techMan";
    }

    @RequestMapping(value = "/uploadRoutesDataFile", method = RequestMethod.POST)
    public String uploadRoutesDataFile(@RequestParam CommonsMultipartFile file, ModelMap model) {
        String filename = "uploadedRoutesDataExcelFile.xlsx";
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basementDirectory + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
            model.addAttribute("uploadStatus", "Upload could not been completed:" + e);
            return "techMan";
        }

        TreeMap<Float, RouteData> routesNamesData = routeFactory.getRoutesNamesDataFromUploadedFile();
        String dataBaseInsertionStatus = techDao.uploadRoutesNamesData(routesNamesData);
        model.addAttribute("uploadStatus", "Upload completed successfully");
        model.addAttribute("dataBaseInsertionStatus", dataBaseInsertionStatus);
        return "techMan";
    }

    @RequestMapping(value = "startNewThread", method = RequestMethod.POST)
    public String createNewThread(ModelMap model, @RequestParam String sleepTime, String count) throws SQLException {

        NewThread newThread = new NewThread(sleepTime, count);
        newThread.start();
        return "techMan";
    }

  
}
