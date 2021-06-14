/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Day;
import Model.GuarantyRoute;
import Model.Route;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

    private String basementDirectory;

    public ExcelWriter() {
        BasementController basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();

    }

    public void write(TreeMap<Float, Route> routes) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("მარშრუტები");

        XSSFDataFormat format = workbook.createDataFormat();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat("Text"));

        int rowIndex = 0;

        for (Map.Entry<Float, Route> routeEntry : routes.entrySet()) {
            Row row = sheet.createRow(rowIndex);
            int columnIndex = 0;
            float routeNumber = routeEntry.getKey();
            Cell cell = row.createCell(columnIndex);

            cell.setCellValue(routeNumber);
            cell.setCellStyle(style);

            Route route = routeEntry.getValue();
            TreeMap<Date, Day> days = route.getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                columnIndex++;
                Date date = dayEntry.getKey();
                cell = row.createCell(columnIndex);
                cell.setCellValue(date.toString());

            }
            rowIndex++;
        }
        /*
        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet("format sheet");
        CellStyle style;
        DataFormat format = wb.createDataFormat();
        Row row;
        Cell cell;
        short rowNum = 0;
        short colNum = 0;

        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(-337499.939437217); // general format

        style = wb.createCellStyle();
        style.setDataFormat(format.getFormat("#.###############")); // custom number format
        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(-337499.939437217);
        cell.setCellStyle(style);
        
        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(123.456789012345);
        cell.setCellStyle(style);
        
        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(123456789.012345);
        cell.setCellStyle(style);

        style = wb.createCellStyle();
        style.setDataFormat((short) 0x7); // builtin currency format
        row = sheet.createRow(rowNum++);
        cell = row.createCell(colNum);
        cell.setCellValue(-1234.5678);
        cell.setCellStyle(style);

        sheet.autoSizeColumn(0);
         */
        Date date = new Date();
        try (FileOutputStream outputStream = new FileOutputStream(this.basementDirectory + "/downloads/" + date.getTime() + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void exportGuarantyRoutes(TreeMap<Float, GuarantyRoute> guarantyRoutes, String fileName) {
        System.out.println(fileName);
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("საგარანტიო გასვლების ანალიზი");

        int rowIndex = 0;
        int columnIndex = 0;
        sheet.addMergedRegion(new CellRangeAddress(0, 2, 0, 0));
        Row row = sheet.createRow(rowIndex);
        row.setHeightInPoints(50);
        Cell cell = row.createCell(columnIndex);
        cell.setCellValue("რიგითი №");

        XSSFCellStyle style = workbook.createCellStyle();
        style.setRotation((short) 90);
        byte[] rgb = new byte[]{(byte) 222, (byte) 222, (byte) 222};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.

        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        cell.setCellStyle(style);

        try (FileOutputStream outputStream = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
