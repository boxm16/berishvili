package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.util.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DownloadController {

    BasementController basementController;
    private String basementDirectory;

    public DownloadController() {
        this.basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();
    }

    @RequestMapping(value = "goDownloadsDirectory", method = RequestMethod.GET)
    public String goDownloadsDirectory(ModelMap model) {
        ArrayList<File> downloadURLList = getDownloadsFilesURLList();
        ArrayList downloadFilesList = new ArrayList();

        for (File file : downloadURLList) {
            String path = file.getPath();
            String fileName = path.substring(path.lastIndexOf('/') + 1);
            if (this.basementController.getApplicationHostName().equals("LAPTOP")) {
                fileName = path.substring(path.lastIndexOf('\\') + 1);
            }
            downloadFilesList.add(fileName);
        }
        model.addAttribute("downloadFilesList", downloadFilesList);
        return "downloads";
    }

    @RequestMapping(value = "downloadFile", method = RequestMethod.GET)
    public void downloadRequestedFile(@RequestParam String fileName, HttpServletResponse response) {

        File file = new File(this.basementDirectory + "/downloads/" + fileName);
        downloadFile(file, fileName, response);
    }

    private ArrayList getDownloadsFilesURLList() {
        ArrayList files = new ArrayList();
        File directory = new File(this.basementDirectory + "/downloads");
        File[] fList = directory.listFiles();
        for (File file : fList) {
            files.add(file);
        }
        return files;
    }

    private void downloadFile(File file, String fileName, HttpServletResponse response) {
        try {
            InputStream inputStream = new FileInputStream(file);
            //response.setContentType("application/force-download");
            // response.setHeader("Content-Disposition", "attachment; filename=" + fileName);

            response.setContentType("application/ms-excel; charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

            IOUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
