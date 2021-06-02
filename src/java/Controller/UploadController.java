package Controller;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView upload(@RequestParam CommonsMultipartFile file, HttpSession session) {
        String filename = "uploadedExcelFile.xlsx";
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basementDirectory + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();

        } catch (Exception e) {
            System.out.println(e);
            return new ModelAndView("upload-success", "uploadStatus", "Upload could not been completed:" + e);
        }
        return new ModelAndView("upload-success", "uploadStatus", "Upload completed successfully");
    }

}
