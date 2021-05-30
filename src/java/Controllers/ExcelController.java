package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

    @RequestMapping(value = "readUploadedExcelFile")
    public String readUploadedExcelFile(ModelMap model) {
        BasementController basementController = new BasementController();
        String filePath = basementController.getBasementDirectory() + "/uploads/uploadedExcelFile.xlsx";
        HashMap<String, String> data = new HashMap();
        try {
            ExcelReader excelReader = new ExcelReader();
            data = excelReader.getCellsFromExcelFile(filePath);
        } catch (Exception ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(data.size());
        System.out.println(data.get("E35"));
        int rowIndex = 10;
        ArrayList<String> list = new ArrayList();
        while (rowIndex < 60000) {
            String key = "E" + String.valueOf(rowIndex);
          //  System.out.println(data.get(key));
            list.add(data.get(key));
            rowIndex++;
        }
        model.addAttribute("list", list);
        return "readSuccess";
    }
}
