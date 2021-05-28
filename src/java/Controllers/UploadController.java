package Controllers;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
        // String rootDirectory = new File("").getAbsolutePath();
        if (getApplicationHostName().equals("LAPTOP")) {
            this.basementDirectory = "C:\\Users\\Michail Sitmalidis\\berishvili";
        } else {
              this.basementDirectory = "/home/admin/basement";
        }
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String rerouteUploadReques() {
        return "upload";
    }

    @RequestMapping(value = "/savefile", method = RequestMethod.POST)
    public ModelAndView upload(@RequestParam CommonsMultipartFile file, HttpSession session) {
        String filename = file.getOriginalFilename();
        System.out.println(this.basementDirectory + "/uploads" + filename);
        try {
            byte barr[] = file.getBytes();

            BufferedOutputStream bout = new BufferedOutputStream(
                    new FileOutputStream(this.basementDirectory + "/uploads/" + filename));
            bout.write(barr);
            bout.flush();
            bout.close();
            /*
            File folder = new File(userDirectory);
            File[] listOfFiles = folder.listFiles();

            for (File fileItem : listOfFiles) {
                if (fileItem.isFile()) {
                    System.out.println(fileItem.getName());
                }
            }
             */
        } catch (Exception e) {
            System.out.println(e);
            return new ModelAndView("upload-success", "uploadStatus", "Upload could not been completed:" + e);
        }
        return new ModelAndView("upload-success", "uploadStatus", "Upload completed");
    }

    private String getApplicationHostName() {
        InetAddress ip;
        String hostname = "Ν/Α";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);

        } catch (UnknownHostException e) {

            e.printStackTrace();
        }
        return hostname;
    }

}
