package Controller;

import Model.Route;
import java.util.HashMap;
import java.util.TreeMap;
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
        TreeMap<Double, Route> routes = getRoutesFromExcelData();
        try {
            ExcelReader excelReader = new ExcelReader();
            data = excelReader.getCellsFromExcelFile(filePath);

        } catch (Exception ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        int rowIndex = 10;

        while (rowIndex < 60000) {
            String key = "H" + String.valueOf(rowIndex);

            rowIndex++;
        }
        model.addAttribute("data", data);
        return "readSuccess";
    }

    private TreeMap<Double, Route> getRoutesFromExcelData() {

        TreeMap<Double, Route> routes = new TreeMap();

        return routes;
    }
}
