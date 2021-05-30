package Controllers;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

    @RequestMapping(value = "readUploadedExcelFile")
    public String readUploadedExcelFile() {
        BasementController basementController = new BasementController();
        String filePath = basementController.getBasementDirectory() + "/uploads/uploadedExcelFile.xlsx";
        try {
            tryRead(filePath);
        } catch (Exception ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "readSuccess";
    }

    private void tryRead(String filePath) throws Exception {
        FromHowTo howto = new FromHowTo();
        howto.processFirstSheet(filePath);
       // howto.processAllSheets(filePath);
    }

}
