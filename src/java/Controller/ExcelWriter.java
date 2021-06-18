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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.VerticalAlignment;
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
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("საგარანტიო გასვლების ანალიზი");

        int rowIndex = 0;
        int columnIndex = 0;
        int rowHeigth = 0;
        int columnWidth = 0;

        //column width
        //  sheet.setDefaultColumnWidth(2);
        while (columnIndex < 20) {
            switch (columnIndex) {
                case 0:
                    columnWidth = 1200;
                    break;
                case 1:
                    columnWidth = 1200;
                    break;
                case 2:
                    columnWidth = 1900;
                    break;
                case 3:
                    columnWidth = 7000;
                    break;
                case 4:
                    columnWidth = 3500;
                    break;
                case 5:
                    columnWidth = 4500;
                    break;
                case 6:
                    columnWidth = 2100;
                    break;
                case 7:
                    columnWidth = 2100;
                    break;
                case 8:
                    columnWidth = 2100;
                    break;
                case 9:
                    columnWidth = 2100;
                    break;
                case 10:
                    columnWidth = 2100;
                    break;
                case 11:
                    columnWidth = 2100;
                    break;
                case 12:
                    columnWidth = 2100;
                    break;
                case 13:
                    columnWidth = 7000;
                    break;
                case 14:
                    columnWidth = 2100;
                    break;
                case 15:
                    columnWidth = 2100;
                    break;
                case 16:
                    columnWidth = 7000;
                    break;
                case 17:
                    columnWidth = 2100;
                    break;
                case 18:
                    columnWidth = 2100;
                    break;
                case 19:
                    columnWidth = 5000;
                    break;
            }
            sheet.setColumnWidth(columnIndex, columnWidth);
            columnIndex++;
        }
        //now headers
        //first header row
        Row headerRow1 = sheet.createRow(rowIndex);
        rowHeigth = 20;
        headerRow1.setHeightInPoints(rowHeigth);

        XSSFCellStyle headerStyleVertical = getStyle(workbook, 221, 217, 195, 90);
        XSSFCellStyle headerStyleHorizontal = getStyle(workbook, 221, 217, 195, 0);
        XSSFCellStyle headerStyleHorizontalYellow = getStyle(workbook, 255, 255, 0, 0);

        columnIndex = 0;
        while (columnIndex < 20) {
            Cell cell = headerRow1.createCell(columnIndex);
            switch (columnIndex) {
                case 0:
                    cell.setCellValue("რიგითი №");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 1:
                    cell.setCellValue("ავტო ბაზა");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 2:
                    cell.setCellValue("მარშრუტის. № ");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 3:
                    cell.setCellValue("ავტობუსების მარშრუტების მოძრაობის სქემა");
                    cell.setCellStyle(headerStyleHorizontal);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 4:
                    cell.setCellValue("მარშრუტის ბრუნის სიგრძე, კმ");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 5:
                    cell.setCellValue("ავტობუსის ტიპი");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 6:
                    cell.setCellValue("formula");
                    cell.setCellStyle(headerStyleHorizontalYellow);
                    break;
                case 7:
                    cell.setCellValue("ინტერვალი, წთ");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 8:
                    cell.setCellValue("ბრუნის დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 9:
                    cell.setCellValue("ბრუნების ჯამური რაოდენობა");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 10:
                    cell.setCellValue("დაწყების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 11:
                    cell.setCellValue("დასრულების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 12:
                    cell.setCellValue("ხაზზე დასრულების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 13:
                    cell.setCellValue("საგარანტიო გასვლები");
                    cell.setCellStyle(headerStyleHorizontal);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, columnIndex, columnIndex + 5));
                    break;
                case 19:
                    cell.setCellValue("შენიშვნა ");
                    cell.setCellStyle(headerStyleHorizontal);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
            }

            columnIndex++;
        }

        //second header row
        Row headerRow2 = sheet.createRow(++rowIndex);
        headerRow2.setHeightInPoints(rowHeigth);
        columnIndex = 0;
        while (columnIndex < 20) {
            if (columnIndex == 6) {
                Cell cell = headerRow2.createCell(columnIndex);
                cell.setCellValue("მომუშავე ავტობუსების რაოდენობა");
                cell.setCellStyle(headerStyleVertical);
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 6, 6));
            } else {
                Cell cell = headerRow2.createCell(columnIndex);
                cell.setCellStyle(headerStyleVertical);
            }
            columnIndex++;
        }

        // third header row
        Row headerRow3 = sheet.createRow(++rowIndex);
        rowHeigth = 80;
        headerRow3.setHeightInPoints(rowHeigth);
        columnIndex = 0;
        while (columnIndex < 20) {
            Cell cell = headerRow3.createCell(columnIndex);
            switch (columnIndex) {
                case 13:
                    cell.setCellValue("პუნქტი \"A\"");
                    cell.setCellStyle(headerStyleHorizontal);
                    break;
                case 14:
                    cell.setCellValue("გასვლები \"A\" პუნქტიდან");
                    cell.setCellStyle(headerStyleHorizontal);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 1));
                    break;
                case 16:
                    cell.setCellValue("პუნქტი \"B\"");
                    cell.setCellStyle(headerStyleHorizontal);
                    break;
                case 17:
                    cell.setCellValue("გასვლები \"B\" პუნქტიდან");
                    cell.setCellStyle(headerStyleHorizontal);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 1));
                    break;
                default:
                    cell.setCellStyle(headerStyleHorizontal);

            }
            columnIndex++;
        }
        short AA = 0;

        XSSFCellStyle rowStyleB = getStyle(workbook, 238, 236, 225, 0);
        XSSFCellStyle datetimeStyleHHmm = workbook.createCellStyle();
        XSSFDataFormat fmt = workbook.createDataFormat();
        datetimeStyleHHmm.setDataFormat(fmt.getFormat("[hh]:mm"));
        for (Map.Entry<Float, GuarantyRoute> entry : guarantyRoutes.entrySet()) {
            GuarantyRoute guarantyRoute = entry.getValue();
            Row row = sheet.createRow(++rowIndex);
            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(AA++);

            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue(guarantyRoute.getBaseNumber());

            Cell cell_2 = row.createCell(2);
            cell_2.setCellValue(entry.getKey());

            Cell cell_5 = row.createCell(5);
            cell_5.setCellValue(guarantyRoute.getBusType());

            Cell cell_6 = row.createCell(6);
            cell_6.setCellValue(guarantyRoute.getBusCount());
            cell_6.setCellStyle(rowStyleB);

            Cell cell_14 = row.createCell(14);
            cell_14.setCellValue(guarantyRoute.getAbSubguarantyTripPeriodStartTimeScheduledExcelFormat());
            cell_14.setCellStyle(datetimeStyleHHmm);

            Cell cell_15 = row.createCell(15);
            cell_15.setCellValue(guarantyRoute.getAbGuarantyTripPeriodStartTimeScheduledExcelFormat());
            cell_15.setCellStyle(datetimeStyleHHmm);

            Cell cell_17 = row.createCell(17);

            if (guarantyRoute.getBaSubguarantyTripPeriodStartTimeScheduledExcelFormat() == null) {
                cell_17.setCellValue("");
            } else {
                cell_17.setCellValue(guarantyRoute.getBaSubguarantyTripPeriodStartTimeScheduledExcelFormat());
            }
            cell_17.setCellStyle(datetimeStyleHHmm);

            Cell cell_18 = row.createCell(18);
            if (guarantyRoute.getBaGuarantyTripPeriodStartTimeScheduledExcelFormat() == null) {
                cell_18.setCellValue("");
            } else {
                cell_18.setCellValue(guarantyRoute.getBaGuarantyTripPeriodStartTimeScheduledExcelFormat());
            }
            cell_18.setCellStyle(datetimeStyleHHmm);

        }

        try (FileOutputStream outputStream = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private XSSFCellStyle getStyle(XSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, int rotation) {
        XSSFCellStyle style = workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setRotation((short) rotation);

        style.setWrapText(true);

        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        // font
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 11);
        font.setFontName("Sylfaen");
        //  font.setItalic(true);
        //font.setStrikeout(true);
        // Applying font to the style  
        style.setFont(font);

        return style;
    }

}
