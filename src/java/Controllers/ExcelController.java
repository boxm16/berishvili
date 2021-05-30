package Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.CellType;
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
        HashMap<String, String> data = howto.getData();
        System.out.println(data.size());
        data.forEach((k, v) -> {
            System.out.println(k + ":" + v);
        });
        ArrayList<String> dataList=howto.getDataList();
        for (String s:dataList){
            System.out.println(s);}


// howto.processAllSheets(filePath);
    }

    private void tryRead2(String filePath) {
        System.out.println("Reading a large excel file..");

        ExcelReader excelReader = new ExcelReader(filePath);

        try {
            List<ExcelReader.Row> data = excelReader.readExcel();

            data.forEach(row -> {

                row.getCells().forEach(cell -> {

                    if (cell.getType() == CellType.NUMERIC) {
                        System.out.print(cell.getNumericCellValue());
                    } else if (cell.getType() == CellType.BOOLEAN) {
                        System.out.print(cell.getBooleanCellValue());
                    } else if (cell.getType() == CellType.STRING) {
                        System.out.print(cell.getStringCellValue());
                    }

                    System.out.print(",");
                });

                System.out.println();

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
