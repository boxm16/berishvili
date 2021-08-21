/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BasicRoute;
import Model.Day;
import Model.GuarantyRoute;
import Model.RouteAverages;
import Model.TripPeriod2X;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Hyperlink;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelWriter {

    private String basementDirectory;
    private Converter converter;
    private MemoryUsage memoryUsage;

    public ExcelWriter() {
        BasementController basementController = new BasementController();
        this.basementDirectory = basementController.getBasementDirectory();
        this.converter = new Converter();
        this.memoryUsage = new MemoryUsage();

    }

    public void write(TreeMap<Float, BasicRoute> routes) {

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("მარშრუტები");

        XSSFDataFormat format = workbook.createDataFormat();
        XSSFCellStyle style = workbook.createCellStyle();
        style.setDataFormat(format.getFormat("Text"));

        int rowIndex = 0;

        for (Map.Entry<Float, BasicRoute> routeEntry : routes.entrySet()) {
            Row row = sheet.createRow(rowIndex);
            int columnIndex = 0;
            float routeNumber = routeEntry.getKey();
            Cell cell = row.createCell(columnIndex);

            cell.setCellValue(routeNumber);
            cell.setCellStyle(style);

            BasicRoute route = routeEntry.getValue();
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
        while (columnIndex < 21) {
            switch (columnIndex) {
                case 0:
                    columnWidth = 1900;
                    break;
                case 1:
                    columnWidth = 1200;
                    break;
                case 2:
                    columnWidth = 7000;
                    break;
                case 3:
                    columnWidth = 3500;
                    break;
                case 4:
                    columnWidth = 4500;
                    break;
                case 5:
                    columnWidth = 2100;
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
                    columnWidth = 2100;
                    break;

                case 14:
                    columnWidth = 7000;
                    break;
                case 15:
                    columnWidth = 2100;
                    break;
                case 16:
                    columnWidth = 2100;
                    break;
                case 17:
                    columnWidth = 7000;
                    break;
                case 18:
                    columnWidth = 2100;
                    break;
                case 19:
                    columnWidth = 2100;
                    break;
                case 20:
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

        XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 221, 217, 195, 90, false);
        XSSFCellStyle headerStyleHorizontal = getHeaderStyle(workbook, 221, 217, 195, 0, false);
        XSSFCellStyle headerStyleHorizontalYellow = getHeaderStyle(workbook, 255, 255, 0, 0, false);
        XSSFCellStyle headerStyleHorizontalBold = getHeaderStyle(workbook, 221, 217, 195, 0, true);

        columnIndex = 0;
        while (columnIndex < 21) {
            Cell cell = headerRow1.createCell(columnIndex);
            switch (columnIndex) {
                case 0:
                    cell.setCellValue("მარშრუტის. № ");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 1:
                    cell.setCellValue("ავტო ბაზა");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;

                case 2:
                    cell.setCellValue("ავტობუსების მარშრუტების მოძრაობის სქემა");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 3:
                    cell.setCellValue("მარშრუტის ბრუნის სიგრძე, კმ");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 4:
                    cell.setCellValue("ავტობუსის ტიპი");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));
                    break;
                case 5:
                    cell.setCellValue("formula");
                    cell.setCellStyle(headerStyleHorizontalYellow);
                    break;
                case 6:
                    cell.setCellValue("ინტერვალი, წთ");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 7:
                    cell.setCellValue("ბრუნის დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 8:
                    cell.setCellValue("შესვენების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;

                case 9:
                    cell.setCellValue("რეისების ჯამური რაოდენობა");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 10:
                    cell.setCellValue("ა/ბ გამოსვლის დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 11:
                    cell.setCellValue("დაწყების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
                case 12:
                    cell.setCellValue("ხაზზე დასრულების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;

                case 13:
                    cell.setCellValue("დასრულების დრო");
                    cell.setCellStyle(headerStyleVertical);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;

                case 14:
                    cell.setCellValue("საგარანტიო გასვლები");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, columnIndex, columnIndex + 5));
                    break;
                case 20:
                    cell.setCellValue("შენიშვნა ");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 2, columnIndex, columnIndex));

                    break;
            }

            columnIndex++;
        }

        //second header row
        Row headerRow2 = sheet.createRow(++rowIndex);
        headerRow2.setHeightInPoints(rowHeigth);
        columnIndex = 0;
        while (columnIndex < 21) {
            if (columnIndex == 5) {
                Cell cell = headerRow2.createCell(columnIndex);
                cell.setCellValue("მომუშავე ავტობუსების რაოდენობა");
                cell.setCellStyle(headerStyleVertical);
                sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 5, 5));
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
        while (columnIndex < 21) {
            Cell cell = headerRow3.createCell(columnIndex);
            switch (columnIndex) {
                case 14:
                    cell.setCellValue("პუნქტი \"A\"");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    break;
                case 15:
                    cell.setCellValue("გასვლები \"A\" პუნქტიდან");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 1));
                    break;
                case 17:
                    cell.setCellValue("პუნქტი \"B\"");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    break;
                case 18:
                    cell.setCellValue("გასვლები \"B\" პუნქტიდან");
                    cell.setCellStyle(headerStyleHorizontalBold);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 1));
                    break;
                default:
                    cell.setCellStyle(headerStyleHorizontal);

            }
            columnIndex++;
        }

        Row middleRow = sheet.createRow(++rowIndex);
        columnIndex = 0;
        XSSFCellStyle middleRowStyle = getRowStyle(workbook, 146, 208, 80, false, false, "");
        while (columnIndex < 21) {
            Cell cell = middleRow.createCell(columnIndex);
            cell.setCellValue(columnIndex + 1);
            cell.setCellStyle(middleRowStyle);
            columnIndex++;
        }

        //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
        XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
        XSSFCellStyle rowStyleWhiteBold = getRowStyle(workbook, 255, 255, 255, false, true, "");
        XSSFCellStyle rowStyleGrayNumber = getRowStyle(workbook, 238, 236, 225, false, false, "0"); //"0" makes cell numeric type
        XSSFCellStyle rowStyleGrayRegular = getRowStyle(workbook, 238, 236, 225, false, false, "");
        XSSFCellStyle rowStyleGrayTimeMMss = getRowStyle(workbook, 238, 236, 225, false, false, "[mm]:ss");
        XSSFCellStyle rowStyleGrayTimeHHmm = getRowStyle(workbook, 238, 236, 225, false, false, "[hh]:mm");

        rowHeigth = 30;
        for (Map.Entry<Float, GuarantyRoute> entry : guarantyRoutes.entrySet()) {
            GuarantyRoute guarantyRoute = entry.getValue();

            Row row = sheet.createRow(++rowIndex);
            row.setHeightInPoints(rowHeigth);

            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(entry.getKey());
            cell_0.setCellStyle(rowStyleWhiteBold);

            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue(guarantyRoute.getBaseNumber());
            cell_1.setCellStyle(rowStyleWhiteRegular);

            Cell cell_2 = row.createCell(2);
            cell_2.setCellValue(guarantyRoute.getScheme());
            cell_2.setCellStyle(rowStyleWhiteRegular);

            Cell cell_3 = row.createCell(3);
            cell_3.setCellValue("");
            cell_3.setCellStyle(rowStyleWhiteRegular);
//-------------//-------------//---------------//---------
            Cell cell_4 = row.createCell(4);
            cell_4.setCellValue(guarantyRoute.getBusType());
            XSSFCellStyle busTypeStyle = getRowStyle(workbook, 255, 0, 0, false, false, "");
            switch (guarantyRoute.getBusType()) {
                case "MAN A-47":
                    busTypeStyle = getRowStyle(workbook, 204, 255, 204, false, false, "");
                    break;
                case "BMC Procity":
                    busTypeStyle = getRowStyle(workbook, 0, 176, 80, false, false, "");
                    break;
                case "Isuzu Novociti Life":
                    busTypeStyle = getRowStyle(workbook, 146, 206, 80, false, false, "");
                    break;
                case "MAN A-21":
                    busTypeStyle = getRowStyle(workbook, 149, 179, 215, false, false, "");
                    break;
                case "ბოგდან А092, A093":
                    busTypeStyle = getRowStyle(workbook, 255, 255, 0, false, false, "");
                    break;
            }
            cell_4.setCellStyle(busTypeStyle);
//+++++++//+++++++++++++++//+++++++++++++++//++++++++++++//+++++++++
            Cell cell_5 = row.createCell(5);
            cell_5.setCellValue(guarantyRoute.getBusCount());
            cell_5.setCellStyle(rowStyleGrayNumber);

            //-----------//----------//-----------//---------
            Duration standardIntervalTime = guarantyRoute.getStandardIntervalTime();
            long intervalSeconds = standardIntervalTime.getSeconds();
            Cell cell_6 = row.createCell(6);
            cell_6.setCellValue(intervalSeconds * 0.1 / 8640);
            cell_6.setCellStyle(rowStyleGrayTimeMMss);
            //---+++--++--++--++--++--++--++
            //-----------//----------//-----------//---------
            Duration standardTripPeriodTime = guarantyRoute.getStandardTripPeriodTime();
            long tripPeriodSeconds = standardTripPeriodTime.getSeconds();
            Cell cell_7 = row.createCell(7);
            cell_7.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            cell_7.setCellStyle(rowStyleGrayTimeHHmm);
            //---+++--++--++--++--++--++--++
            //-----------//----------//-----------//---------
            Duration standardBreakTime = guarantyRoute.getStandardBreakTime();
            System.out.println("RouteNumber:" + guarantyRoute.getNumber() + ":-:" + standardBreakTime);
            if (standardBreakTime == null) {
                Cell cell_8 = row.createCell(8);

                cell_8.setCellStyle(rowStyleGrayTimeMMss);
            } else {
                long breakSeconds = standardBreakTime.getSeconds();
                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(breakSeconds * 0.1 / 8640);
                cell_8.setCellStyle(rowStyleGrayTimeMMss);
            }
            //---+++--++--++--++--++--++--++
            //-----------//----------//-----------//---------
            Cell cell_9 = row.createCell(9);
            cell_9.setCellValue(guarantyRoute.getTotalRaces());
            cell_9.setCellStyle(rowStyleGrayRegular);
            //---+++--++--++--++--++--++--++
            Cell cell_10 = row.createCell(10);
            cell_10.setCellValue(guarantyRoute.getFirstBaseLeavingTime());
            cell_10.setCellStyle(rowStyleGrayTimeHHmm);
            //---+++--++--++--++--++--++--++
            //-----------//----------//-----------//---------
            Cell cell_11 = row.createCell(11);
            cell_11.setCellValue(guarantyRoute.getRouteStartTimeExcelFormat());
            cell_11.setCellStyle(rowStyleGrayTimeHHmm);
            //---+++--++--++--++--++--++--++
            //-----------//----------//-----------//---------
            Cell cell_12 = row.createCell(12);
            cell_12.setCellValue(guarantyRoute.getRouteEndTimeExcelFormat());
            cell_12.setCellStyle(rowStyleGrayTimeHHmm);
            //---+++--++--++--++--++--++--++

            //-----------//----------//-----------//---------
            Cell cell_13 = row.createCell(13);
            cell_13.setCellValue(guarantyRoute.getLastBaseReturnTime());
            cell_13.setCellStyle(rowStyleGrayTimeHHmm);
            //---+++--++--++--++--++--++--++

            Cell cell_14 = row.createCell(14);
            cell_14.setCellValue(guarantyRoute.getaPoint());
            cell_14.setCellStyle(rowStyleGrayRegular);
            //---+++--++--++--++--++--++--++

            Cell cell_15 = row.createCell(15);
            cell_15.setCellValue(guarantyRoute.getAbSubguarantyTripPeriodStartTimeScheduledExcelFormat());
            cell_15.setCellStyle(rowStyleGrayTimeHHmm);

            Cell cell_16 = row.createCell(16);
            cell_16.setCellValue(guarantyRoute.getAbGuarantyTripPeriodStartTimeScheduledExcelFormat());
            cell_16.setCellStyle(rowStyleGrayTimeHHmm);
            //---+++--++--++--++--++--++--++
            Cell cell_17 = row.createCell(17);
            cell_17.setCellValue(guarantyRoute.getbPoint());
            cell_17.setCellStyle(rowStyleGrayRegular);
            //---+++--++--++--++--++--++--++

            Cell cell_18 = row.createCell(18);

            if (guarantyRoute.getBaSubguarantyTripPeriodStartTimeScheduledExcelFormat() == null) {
                cell_18.setCellValue("");
            } else {
                cell_18.setCellValue(guarantyRoute.getBaSubguarantyTripPeriodStartTimeScheduledExcelFormat());
            }
            cell_18.setCellStyle(rowStyleGrayTimeHHmm);

            Cell cell_19 = row.createCell(19);
            if (guarantyRoute.getBaGuarantyTripPeriodStartTimeScheduledExcelFormat() == null) {
                cell_19.setCellValue("");
            } else {
                cell_19.setCellValue(guarantyRoute.getBaGuarantyTripPeriodStartTimeScheduledExcelFormat());
            }
            cell_19.setCellStyle(rowStyleGrayTimeHHmm);

            //---+++--++--++--++--++--++--++
            Cell cell_20 = row.createCell(20);
            cell_20.setCellValue("");
            cell_20.setCellStyle(rowStyleGrayRegular);
            //---+++--++--++--++--++--++--++

        }

        //setting formula
        CellReference cellReference = new CellReference("F1");
        Row row = sheet.getRow(cellReference.getRow());
        Cell formulaCell = row.getCell(cellReference.getCol());
        String lastRowIndex = String.valueOf(rowIndex + 1);

        formulaCell.setCellFormula("SUBTOTAL(9,F5:F" + lastRowIndex + ")");

        try (FileOutputStream outputStream = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private XSSFCellStyle getHeaderStyle(XSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, int rotation, boolean bold) {
        XSSFCellStyle style = workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setRotation((short) rotation);

        style.setWrapText(true);

        style.setRotation((short) rotation);

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
        font.setBold(bold);
        style.setFont(font);

        return style;
    }

    private XSSFCellStyle getRowStyle(XSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, boolean italic, boolean bold, String format) {

        XSSFCellStyle style = workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

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
        font.setItalic(italic);
        font.setBold(bold);
        style.setFont(font);

        //time formats "[hh]:mm"   "[mm]:ss"
        XSSFDataFormat fmts = workbook.createDataFormat();
        style.setDataFormat(fmts.getFormat(format));
        return style;
    }

    void exportTripPeriods(ArrayList<TripPeriod2X> tripPeriods, String fileName) {

        Instant start = Instant.now();
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ბრუნების დროები");
//setting date 1904 system (to show negative duration in excel workbook)
        workbook.getCTWorkbook().getWorkbookPr().setDate1904(true);

        int rowIndex = 0;
        int columnIndex = 0;
        int rowHeigth = 0;
        int columnWidth = 0;

        //column width
        //  sheet.setDefaultColumnWidth(2);
        while (columnIndex < 21) {
            switch (columnIndex) {
                case 0:
                    columnWidth = 1000;
                    break;
                case 1:
                    columnWidth = 3000;
                    break;
                case 2:
                    columnWidth = 3300;
                    break;
                case 3:
                    columnWidth = 1000;
                    break;
                case 4:
                    columnWidth = 6500;
                    break;
                case 5:
                    columnWidth = 2800;
                    break;
                case 6:
                    columnWidth = 2800;
                    break;
                case 7:
                    columnWidth = 2800;
                    break;
                case 8:
                    columnWidth = 2800;
                    break;
                case 9:
                    columnWidth = 2800;
                    break;
                case 10:
                    columnWidth = 2800;
                    break;
                case 11:
                    columnWidth = 2800;
                    break;
                case 12:
                    columnWidth = 2800;
                    break;

            }
            sheet.setColumnWidth(columnIndex, columnWidth);
            columnIndex++;
        }
        //now headers
        //first header row
        Row headerRow1 = sheet.createRow(rowIndex);
        rowHeigth = 80;
        headerRow1.setHeightInPoints(rowHeigth);

        XSSFCellStyle headerStyle = getHeaderStyle(workbook, 209, 112, 247, 0, false);
        XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 209, 112, 247, 90, false);

        columnIndex = 0;
        while (columnIndex < 13) {
            Cell cell = headerRow1.createCell(columnIndex);
            switch (columnIndex) {
                case 0:
                    cell.setCellValue("მარშრუტის #");
                    cell.setCellStyle(headerStyleVertical);
                    break;
                case 1:
                    cell.setCellValue("თარიღი");
                    cell.setCellStyle(headerStyle);
                    break;

                case 2:
                    cell.setCellValue("ავტობუსის #");
                    cell.setCellStyle(headerStyle);
                    break;
                case 3:
                    cell.setCellValue("გასვლი #");
                    cell.setCellStyle(headerStyleVertical);
                    break;

                case 4:
                    cell.setCellValue("მძღოლი");
                    cell.setCellStyle(headerStyle);
                    break;
                case 5:
                    cell.setCellValue("მიმართულება");
                    cell.setCellStyle(headerStyle);
                    break;
                case 6:
                    cell.setCellValue("გასვლის  გეგმიური  დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 7:
                    cell.setCellValue("გასვლის ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 8:
                    cell.setCellValue("მისვლის გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 9:
                    cell.setCellValue("მისვლის ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 10:
                    cell.setCellValue("წირის გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 11:
                    cell.setCellValue("წირის ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;

                case 12:
                    cell.setCellValue("სხვაობა");
                    cell.setCellStyle(headerStyle);
                    break;

            }

            columnIndex++;
        }

        //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
        XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
        XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
        XSSFCellStyle rowStyleWhiteTimeHHmm = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm");
        XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
        XSSFCellStyle rowStyleYellowTimeHHmmss = getRowStyle(workbook, 255, 255, 0, false, false, "[hh]:mm:ss");
        XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

        rowHeigth = 30;
        for (TripPeriod2X tripPeriod : tripPeriods) {

            Row row = sheet.createRow(++rowIndex);
            row.setHeightInPoints(rowHeigth);

            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(this.converter.convertRouteNumber(tripPeriod.getRouteNumber()));
            cell_0.setCellStyle(rowStyleWhiteRegular);

            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue(tripPeriod.getDateStamp());
            cell_1.setCellStyle(rowStyleWhiteRegular);

            Cell cell_2 = row.createCell(2);
            cell_2.setCellValue(tripPeriod.getBusNumber());
            cell_2.setCellStyle(rowStyleWhiteRegular);

            Cell cell_3 = row.createCell(3);
            cell_3.setCellValue((short) tripPeriod.getExodusNumber());
            cell_3.setCellStyle(rowStyleWhiteNumber);

            Cell cell_4 = row.createCell(4);
            cell_4.setCellValue(tripPeriod.getDriverName());
            cell_4.setCellStyle(rowStyleWhiteRegular);

            Cell cell_5 = row.createCell(5);
            cell_5.setCellValue(tripPeriod.getTypeG());
            cell_5.setCellStyle(rowStyleWhiteRegular);

            Cell cell_6 = row.createCell(6);
            cell_6.setCellValue(tripPeriod.getStartTimeScheduledExcelFormat());
            cell_6.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_7 = row.createCell(7);
            if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                cell_7.setCellValue("");
            } else {
                cell_7.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
            }
            cell_7.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_8 = row.createCell(8);
            cell_8.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
            cell_8.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_9 = row.createCell(9);
            if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                cell_9.setCellValue("");
            } else {
                cell_9.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
            }
            cell_9.setCellStyle(rowStyleWhiteTimeHHmm);

            Duration tripPeriodTimeScheduled = tripPeriod.getTripPeriodTimeScheduled();
            Cell cell_10 = row.createCell(10);
            if (tripPeriodTimeScheduled == null) {
                cell_10.setCellValue("");
            } else {
                long tripPeriodSeconds = tripPeriodTimeScheduled.getSeconds();
                cell_10.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            }
            cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);

            Duration tripPeriodTimeActiual = tripPeriod.getTripPeriodTimeActual();
            Cell cell_11 = row.createCell(11);
            if (tripPeriodTimeActiual == null) {
                cell_11.setCellValue("");
            } else {
                long tripPeriodSeconds = tripPeriodTimeActiual.getSeconds();
                cell_11.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            }
            cell_11.setCellStyle(rowStyleWhiteTimeHHmmss);

            Duration tripPeriodTimeDifference = tripPeriod.getTripPeriodTimeDifference();
            Cell cell_12 = row.createCell(12);
            if (tripPeriodTimeDifference == null) {
                cell_12.setCellValue("");
            } else {
                long tripPeriodSeconds = tripPeriodTimeDifference.getSeconds();
                cell_12.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            }
            String tripPeriodTimeDifferenceColor = tripPeriod.getTripPeriodTimeDifferenceColor();
            cell_12.setCellStyle(rowStyleWhiteTimeHHmmss);
            if (tripPeriodTimeDifferenceColor.equals("yellow")) {
                cell_12.setCellStyle(rowStyleYellowTimeHHmmss);
            }
            if (tripPeriodTimeDifferenceColor.equals("red")) {
                cell_12.setCellStyle(rowStyleRedTimeHHmmss);
            }

        }

        try (FileOutputStream outputStream = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        Instant end = Instant.now();
        System.out.println("Trip Periods Excel Export completed. Time needed:" + Duration.between(start, end));

    }

    void exportTripPeriodsAndRoutesAverages(ArrayList<TripPeriod2X> tripPeriods, TreeMap<Float, RouteAverages> routesAverages, int percents, String fileName) {
        //first part is for Trip Periods

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("ბრუნების დროები");
//setting date 1904 system (to show negative duration in excel workbook)
        workbook.getCTWorkbook().getWorkbookPr().setDate1904(true);

        int rowIndex = 0;
        int columnIndex = 0;
        int rowHeigth = 0;
        int columnWidth = 0;

        //column width
        //  sheet.setDefaultColumnWidth(2);
        while (columnIndex < 21) {
            switch (columnIndex) {
                case 0:
                    columnWidth = 1000;
                    break;
                case 1:
                    columnWidth = 3000;
                    break;
                case 2:
                    columnWidth = 3300;
                    break;
                case 3:
                    columnWidth = 1000;
                    break;
                case 4:
                    columnWidth = 6500;
                    break;
                case 5:
                    columnWidth = 2800;
                    break;
                case 6:
                    columnWidth = 2800;
                    break;
                case 7:
                    columnWidth = 2800;
                    break;
                case 8:
                    columnWidth = 2800;
                    break;
                case 9:
                    columnWidth = 2800;
                    break;
                case 10:
                    columnWidth = 2800;
                    break;
                case 11:
                    columnWidth = 2800;
                    break;
                case 12:
                    columnWidth = 2800;
                    break;

            }
            sheet.setColumnWidth(columnIndex, columnWidth);
            columnIndex++;
        }
        //now headers
        //first header row
        Row headerRow1 = sheet.createRow(rowIndex);
        rowHeigth = 80;
        headerRow1.setHeightInPoints(rowHeigth);

        XSSFCellStyle headerStyle = getHeaderStyle(workbook, 209, 112, 247, 0, false);
        XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 209, 112, 247, 90, false);

        columnIndex = 0;
        while (columnIndex < 13) {
            Cell cell = headerRow1.createCell(columnIndex);
            switch (columnIndex) {
                case 0:
                    cell.setCellValue("მარშრუტის #");
                    cell.setCellStyle(headerStyleVertical);
                    break;
                case 1:
                    cell.setCellValue("თარიღი");
                    cell.setCellStyle(headerStyle);
                    break;

                case 2:
                    cell.setCellValue("ავტობუსის #");
                    cell.setCellStyle(headerStyle);
                    break;
                case 3:
                    cell.setCellValue("გასვლი #");
                    cell.setCellStyle(headerStyleVertical);
                    break;

                case 4:
                    cell.setCellValue("მძღოლი");
                    cell.setCellStyle(headerStyle);
                    break;
                case 5:
                    cell.setCellValue("მიმართულება");
                    cell.setCellStyle(headerStyle);
                    break;
                case 6:
                    cell.setCellValue("გასვლის  გეგმიური  დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 7:
                    cell.setCellValue("გასვლის ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 8:
                    cell.setCellValue("მისვლის გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 9:
                    cell.setCellValue("მისვლის ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 10:
                    cell.setCellValue("წირის გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 11:
                    cell.setCellValue("წირის ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;

                case 12:
                    cell.setCellValue("სხვაობა");
                    cell.setCellStyle(headerStyle);
                    break;

            }

            columnIndex++;
        }

        //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
        XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
        XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
        XSSFCellStyle rowStyleWhiteTimeHHmm = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm");
        XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
        XSSFCellStyle rowStyleYellowTimeHHmmss = getRowStyle(workbook, 255, 255, 0, false, false, "[hh]:mm:ss");
        XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

        rowHeigth = 30;
        for (TripPeriod2X tripPeriod : tripPeriods) {

            Row row = sheet.createRow(++rowIndex);
            row.setHeightInPoints(rowHeigth);

            Cell cell_0 = row.createCell(0);
            cell_0.setCellValue(this.converter.convertRouteNumber(tripPeriod.getRouteNumber()));
            cell_0.setCellStyle(rowStyleWhiteRegular);

            Cell cell_1 = row.createCell(1);
            cell_1.setCellValue(tripPeriod.getDateStamp());
            cell_1.setCellStyle(rowStyleWhiteRegular);

            Cell cell_2 = row.createCell(2);
            cell_2.setCellValue(tripPeriod.getBusNumber());
            cell_2.setCellStyle(rowStyleWhiteRegular);

            Cell cell_3 = row.createCell(3);
            cell_3.setCellValue((short) tripPeriod.getExodusNumber());
            cell_3.setCellStyle(rowStyleWhiteNumber);

            Cell cell_4 = row.createCell(4);
            cell_4.setCellValue(tripPeriod.getDriverName());
            cell_4.setCellStyle(rowStyleWhiteRegular);

            Cell cell_5 = row.createCell(5);
            cell_5.setCellValue(tripPeriod.getTypeG());
            cell_5.setCellStyle(rowStyleWhiteRegular);

            Cell cell_6 = row.createCell(6);
            cell_6.setCellValue(tripPeriod.getStartTimeScheduledExcelFormat());
            cell_6.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_7 = row.createCell(7);
            if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                cell_7.setCellValue("");
            } else {
                cell_7.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
            }
            cell_7.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_8 = row.createCell(8);
            cell_8.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
            cell_8.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_9 = row.createCell(9);
            if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                cell_9.setCellValue("");
            } else {
                cell_9.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
            }
            cell_9.setCellStyle(rowStyleWhiteTimeHHmm);

            Duration tripPeriodTimeScheduled = tripPeriod.getTripPeriodTimeScheduled();
            Cell cell_10 = row.createCell(10);
            if (tripPeriodTimeScheduled == null) {
                cell_10.setCellValue("");
            } else {
                long tripPeriodSeconds = tripPeriodTimeScheduled.getSeconds();
                cell_10.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            }
            cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);

            Duration tripPeriodTimeActiual = tripPeriod.getTripPeriodTimeActual();
            Cell cell_11 = row.createCell(11);
            if (tripPeriodTimeActiual == null) {
                cell_11.setCellValue("");
            } else {
                long tripPeriodSeconds = tripPeriodTimeActiual.getSeconds();
                cell_11.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            }
            cell_11.setCellStyle(rowStyleWhiteTimeHHmmss);

            Duration tripPeriodTimeDifference = tripPeriod.getTripPeriodTimeDifference();
            Cell cell_12 = row.createCell(12);
            if (tripPeriodTimeDifference == null) {
                cell_12.setCellValue("");
            } else {
                long tripPeriodSeconds = tripPeriodTimeDifference.getSeconds();
                cell_12.setCellValue(tripPeriodSeconds * 0.1 / 8640);
            }
            String tripPeriodTimeDifferenceColor = tripPeriod.getTripPeriodTimeDifferenceColor();
            cell_12.setCellStyle(rowStyleWhiteTimeHHmmss);
            if (tripPeriodTimeDifferenceColor.equals("yellow")) {
                cell_12.setCellStyle(rowStyleYellowTimeHHmmss);
            }
            if (tripPeriodTimeDifferenceColor.equals("red")) {
                cell_12.setCellStyle(rowStyleRedTimeHHmmss);
            }

            if (rowIndex % 1000 == 0) {

                System.out.println(rowIndex + " Rows Have Been Written Into Excel By Now");
                memoryUsage.printMemoryUsage();
            }

        }
        System.out.println("++++Trip Periods Excel Writing Completed+++++ ");
        //----------------------------------------------------------------------------------
        //          NOW AVERAGES
        //----------------------------------------------------------------------------------
        System.out.println("----Routes Averages Excel Writing Started------");
        rowIndex = 0;
        XSSFSheet averagesSheet = workbook.createSheet("ბრუნების საშუალო დროები");
        Row averagesHeaderRow1 = averagesSheet.createRow(rowIndex);
        rowHeigth = 80;
        averagesHeaderRow1.setHeightInPoints(rowHeigth);

        //column width
        //  sheet.setDefaultColumnWidth(2);
        columnIndex = 0;
        while (columnIndex < 13) {
            switch (columnIndex) {
                case 0:
                    columnWidth = 1700;
                    break;
                case 1:
                    columnWidth = 1700;
                    break;
                case 2:
                    columnWidth = 4000;
                    break;
                case 3:
                    columnWidth = 4000;
                    break;
                case 4:
                    columnWidth = 4000;
                    break;
                case 5:
                    columnWidth = 4000;
                    break;
                case 6:
                    columnWidth = 4000;
                    break;
                case 7:
                    columnWidth = 4000;
                    break;
                case 8:
                    columnWidth = 2500;
                    break;
                case 9:
                    columnWidth = 4000;
                    break;
                case 10:
                    columnWidth = 4000;
                    break;
                case 11:
                    columnWidth = 4000;
                    break;
                case 12:
                    columnWidth = 4000;
                    break;

            }
            averagesSheet.setColumnWidth(columnIndex, columnWidth);
            columnIndex++;
        }

        columnIndex = 0;
        while (columnIndex < 13) {
            Cell cell = averagesHeaderRow1.createCell(columnIndex);
            switch (columnIndex) {
                case 0:
                    cell.setCellValue("მარშრუტის #");
                    cell.setCellStyle(headerStyleVertical);
                    break;
                case 1:
                    cell.setCellValue("მიმართულება");
                    cell.setCellStyle(headerStyleVertical);
                    break;

                case 2:
                    cell.setCellValue("+" + percents + "% ჩათვლილი რეისების რაოდენობა");
                    cell.setCellStyle(headerStyle);
                    break;
                case 3:
                    cell.setCellValue("+" + percents + "% ჩათვლილი რეისების საშუალო ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;

                case 4:
                    cell.setCellValue("-" + percents + "% ჩათვლილი რეისების რაოდენობა");
                    cell.setCellStyle(headerStyle);
                    break;
                case 5:
                    cell.setCellValue("-" + percents + "% ჩათვლილი რეისების საშუალო ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 6:
                    cell.setCellValue("ყველა ჩათვლილი რეისების რაოდენობა");
                    cell.setCellStyle(headerStyle);
                    break;
                case 7:
                    cell.setCellValue("ყველა ჩათვლილი რეისების საშუალო ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 8:
                    cell.setCellValue("იძებნება მრავალი გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 9:
                    cell.setCellValue("წირის სტანდარტული გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 10:
                    cell.setCellValue("ბრუნების სტანდარტული გეგმიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;
                case 11:
                    cell.setCellValue("ყველა ჩათვლილი რეისების რაოდენობა");
                    cell.setCellStyle(headerStyle);
                    break;

                case 12:
                    cell.setCellValue("ორივე მიმართულების ბრუნების საშუალო ფაქტიური დრო");
                    cell.setCellStyle(headerStyle);
                    break;

            }

            columnIndex++;
        }

        rowHeigth = 30;
        for (Map.Entry<Float, RouteAverages> routesAveragesEntry : routesAverages.entrySet()) {
            RouteAverages routeAverages = routesAveragesEntry.getValue();
            Row rowAB = averagesSheet.createRow(++rowIndex);
            rowAB.setHeightInPoints(rowHeigth);

            Cell cell_AB_0 = rowAB.createCell(0);
            cell_AB_0.setCellValue(this.converter.convertRouteNumber(routeAverages.getRouteNumber()));
            cell_AB_0.setCellStyle(rowStyleWhiteRegular);

            averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 0, 0));

            Cell cell_AB_1 = rowAB.createCell(1);
            cell_AB_1.setCellValue("A_B");
            cell_AB_1.setCellStyle(rowStyleWhiteRegular);

            Cell cell_AB_2 = rowAB.createCell(2);
            cell_AB_2.setCellValue(routeAverages.getAbLowCount());
            cell_AB_2.setCellStyle(rowStyleWhiteNumber);

            Cell cell_AB_3 = rowAB.createCell(3);
            cell_AB_3.setCellValue(routeAverages.getAbLowAverageString());
            cell_AB_3.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_AB_4 = rowAB.createCell(4);
            cell_AB_4.setCellValue(routeAverages.getAbHighCount());
            cell_AB_4.setCellStyle(rowStyleWhiteNumber);

            Cell cell_AB_5 = rowAB.createCell(5);
            cell_AB_5.setCellValue(routeAverages.getAbHighAverageString());
            cell_AB_5.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_AB_6 = rowAB.createCell(6);
            cell_AB_6.setCellValue(routeAverages.getAbLowAndHighCount());
            cell_AB_6.setCellStyle(rowStyleWhiteNumber);

            Cell cell_AB_7 = rowAB.createCell(7);
            cell_AB_7.setCellValue(routeAverages.getAbLowAndHighAverage());
            cell_AB_7.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_AB_8 = rowAB.createCell(8);
            cell_AB_8.setCellValue(routeAverages.abTripPeriodTimeIsMultiple());
            cell_AB_8.setCellStyle(rowStyleWhiteRegular);

            Cell cell_AB_9 = rowAB.createCell(9);
            cell_AB_9.setCellValue(routeAverages.getABTripPeriodStandartTimeString());
            cell_AB_9.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_AB_10 = rowAB.createCell(10);
            cell_AB_10.setCellValue(routeAverages.getTripRoundStandartTimeString());
            cell_AB_10.setCellStyle(rowStyleWhiteTimeHHmm);
            averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 10, 10));

            Cell cell_AB_11 = rowAB.createCell(11);
            cell_AB_11.setCellValue(routeAverages.getAllCount());
            cell_AB_11.setCellStyle(rowStyleWhiteNumber);
            averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 11, 11));

            Cell cell_AB_12 = rowAB.createCell(12);
            cell_AB_12.setCellValue(routeAverages.getAllAverage());
            cell_AB_12.setCellStyle(rowStyleWhiteTimeHHmm);
            averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 12, 12));

            Row rowBA = averagesSheet.createRow(++rowIndex);
            rowBA.setHeightInPoints(rowHeigth);

            Cell cell_BA_0 = rowBA.createCell(0);
            cell_BA_0.setCellStyle(rowStyleWhiteRegular);

            Cell cell_BA_1 = rowBA.createCell(1);
            cell_BA_1.setCellValue("B_A");
            cell_BA_1.setCellStyle(rowStyleWhiteRegular);

            Cell cell_BA_2 = rowBA.createCell(2);
            cell_BA_2.setCellValue(routeAverages.getBaLowCount());
            cell_BA_2.setCellStyle(rowStyleWhiteNumber);

            Cell cell_BA_3 = rowBA.createCell(3);
            cell_BA_3.setCellValue(routeAverages.getBaLowAverageString());
            cell_BA_3.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_BA_4 = rowBA.createCell(4);
            cell_BA_4.setCellValue(routeAverages.getBaHighCount());
            cell_BA_4.setCellStyle(rowStyleWhiteNumber);

            Cell cell_BA_5 = rowBA.createCell(5);
            cell_BA_5.setCellValue(routeAverages.getBaHighAverageString());
            cell_BA_5.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_BA_6 = rowBA.createCell(6);
            cell_BA_6.setCellValue(routeAverages.getBaLowAndHighCount());
            cell_BA_6.setCellStyle(rowStyleWhiteNumber);

            Cell cell_BA_7 = rowBA.createCell(7);
            cell_BA_7.setCellValue(routeAverages.getBaLowAndHighAverage());
            cell_BA_7.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_BA_8 = rowBA.createCell(8);
            cell_BA_8.setCellValue(routeAverages.baTripPeriodTimeIsMultiple());
            cell_BA_8.setCellStyle(rowStyleWhiteRegular);

            Cell cell_BA_9 = rowBA.createCell(9);
            cell_BA_9.setCellValue(routeAverages.getBATripPeriodStandartTimeString());
            cell_BA_9.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_BA_10 = rowBA.createCell(10);
            cell_BA_10.setCellStyle(rowStyleWhiteTimeHHmm);

            Cell cell_BA_11 = rowBA.createCell(11);
            cell_BA_11.setCellStyle(rowStyleWhiteNumber);

            Cell cell_BA_12 = rowBA.createCell(12);
            cell_BA_12.setCellStyle(rowStyleWhiteTimeHHmm);
            System.out.println("Route N" + routeAverages.getRouteNumber() + " Completed");
        }
        System.out.println("++++Routes Averages Excel Writing Completed++++");
        try (FileOutputStream outputStream = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            workbook.write(outputStream);
        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    void SXSSF(ArrayList<TripPeriod2X> tripPeriods, TreeMap<Float, RouteAverages> routesAverages, int percents, String fileName, HttpServletRequest request) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            //setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet tripPeriodsSheet = workbook.createSheet("ბრუნების დროები");
            /*
            String val = "The first%s Xing di%s column";
            for (int rowNum = 0; rowNum < 100_0000; rowNum++) {
                Row row = tripPeriodsSheet.createRow(rowNum);
                int realRowNum = rowNum + 1;
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(realRowNum);
            }
             */
            int rowIndex = 0;
            int columnIndex = 0;
            int rowHeigth = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 21) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 1000;
                        break;
                    case 1:
                        columnWidth = 3000;
                        break;
                    case 2:
                        columnWidth = 3300;
                        break;
                    case 3:
                        columnWidth = 1000;
                        break;
                    case 4:
                        columnWidth = 6500;
                        break;
                    case 5:
                        columnWidth = 2800;
                        break;
                    case 6:
                        columnWidth = 2800;
                        break;
                    case 7:
                        columnWidth = 2800;
                        break;
                    case 8:
                        columnWidth = 2800;
                        break;
                    case 9:
                        columnWidth = 2800;
                        break;
                    case 10:
                        columnWidth = 2800;
                        break;
                    case 11:
                        columnWidth = 2800;
                        break;
                    case 12:
                        columnWidth = 2800;
                        break;

                }
                tripPeriodsSheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            //now headers
            //first header row
            Row headerRow1 = tripPeriodsSheet.createRow(rowIndex);
            rowHeigth = 80;
            headerRow1.setHeightInPoints(rowHeigth);

            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 209, 112, 247, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 209, 112, 247, 90, false);

            columnIndex = 0;
            while (columnIndex < 13) {
                Cell cell = headerRow1.createCell(columnIndex);
                switch (columnIndex) {
                    case 0:
                        cell.setCellValue("მარშრუტის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 1:
                        cell.setCellValue("თარიღი");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 2:
                        cell.setCellValue("ავტობუსის #");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 3:
                        cell.setCellValue("გასვლი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;

                    case 4:
                        cell.setCellValue("მძღოლი");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 5:
                        cell.setCellValue("მიმართულება");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 6:
                        cell.setCellValue("გასვლის  გეგმიური  დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 7:
                        cell.setCellValue("გასვლის ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 8:
                        cell.setCellValue("მისვლის გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 9:
                        cell.setCellValue("მისვლის ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 10:
                        cell.setCellValue("წირის გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 11:
                        cell.setCellValue("წირის ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 12:
                        cell.setCellValue("სხვაობა");
                        cell.setCellStyle(headerStyle);
                        break;

                }

                columnIndex++;
            }

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteTimeHHmm = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm");
            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleYellowTimeHHmmss = getRowStyle(workbook, 255, 255, 0, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

            rowHeigth = 30;
            for (TripPeriod2X tripPeriod : tripPeriods) {

                Row row = tripPeriodsSheet.createRow(++rowIndex);
                row.setHeightInPoints(rowHeigth);

                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(this.converter.convertRouteNumber(tripPeriod.getRouteNumber()));
                cell_0.setCellStyle(rowStyleWhiteRegular);

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(tripPeriod.getDateStamp());
                cell_1.setCellStyle(rowStyleWhiteRegular);

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(tripPeriod.getBusNumber());
                cell_2.setCellStyle(rowStyleWhiteRegular);

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue((short) tripPeriod.getExodusNumber());
                cell_3.setCellStyle(rowStyleWhiteNumber);

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(tripPeriod.getDriverName());
                cell_4.setCellStyle(rowStyleWhiteRegular);

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(tripPeriod.getTypeG());
                cell_5.setCellStyle(rowStyleWhiteRegular);

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(tripPeriod.getStartTimeScheduledExcelFormat());
                cell_6.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_7 = row.createCell(7);
                if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                    cell_7.setCellValue("");
                } else {
                    cell_7.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
                }
                cell_7.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
                cell_8.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_9 = row.createCell(9);
                if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                    cell_9.setCellValue("");
                } else {
                    cell_9.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
                }
                cell_9.setCellStyle(rowStyleWhiteTimeHHmm);

                Duration tripPeriodTimeScheduled = tripPeriod.getTripPeriodTimeScheduled();
                Cell cell_10 = row.createCell(10);
                if (tripPeriodTimeScheduled == null) {
                    cell_10.setCellValue("");
                } else {
                    long tripPeriodSeconds = tripPeriodTimeScheduled.getSeconds();
                    cell_10.setCellValue(tripPeriodSeconds * 0.1 / 8640);
                }
                cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);

                Duration tripPeriodTimeActiual = tripPeriod.getTripPeriodTimeActual();
                Cell cell_11 = row.createCell(11);
                if (tripPeriodTimeActiual == null) {
                    cell_11.setCellValue("");
                } else {
                    long tripPeriodSeconds = tripPeriodTimeActiual.getSeconds();
                    cell_11.setCellValue(tripPeriodSeconds * 0.1 / 8640);
                }
                cell_11.setCellStyle(rowStyleWhiteTimeHHmmss);

                Duration tripPeriodTimeDifference = tripPeriod.getTripPeriodTimeDifference();
                Cell cell_12 = row.createCell(12);
                if (tripPeriodTimeDifference == null) {
                    cell_12.setCellValue("");
                } else {
                    long tripPeriodSeconds = tripPeriodTimeDifference.getSeconds();
                    cell_12.setCellValue(tripPeriodSeconds * 0.1 / 8640);
                }
                String tripPeriodTimeDifferenceColor = tripPeriod.getTripPeriodTimeDifferenceColor();
                cell_12.setCellStyle(rowStyleWhiteTimeHHmmss);
                if (tripPeriodTimeDifferenceColor.equals("yellow")) {
                    cell_12.setCellStyle(rowStyleYellowTimeHHmmss);
                }
                if (tripPeriodTimeDifferenceColor.equals("red")) {
                    cell_12.setCellStyle(rowStyleRedTimeHHmmss);
                }

                if (rowIndex % 1000 == 0) {

                    System.out.println(rowIndex + " Rows Have Been Written Into Excel By Now");
                    memoryUsage.printMemoryUsage();
                }

            }
            System.out.println("++++Trip Periods Excel Writing Completed+++++ ");

            //----------------------------------------------------------------------------------
            //          NOW AVERAGES
            //----------------------------------------------------------------------------------
            System.out.println("----Routes Averages Excel Writing Started------");
            rowIndex = 0;
            Sheet averagesSheet = workbook.createSheet("ბრუნების საშუალო დროები");
            Row averagesHeaderRow1 = averagesSheet.createRow(rowIndex);
            rowHeigth = 80;
            averagesHeaderRow1.setHeightInPoints(rowHeigth);

            //column width
            //  sheet.setDefaultColumnWidth(2);
            columnIndex = 0;
            while (columnIndex < 13) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 1700;
                        break;
                    case 1:
                        columnWidth = 1700;
                        break;
                    case 2:
                        columnWidth = 4000;
                        break;
                    case 3:
                        columnWidth = 4000;
                        break;
                    case 4:
                        columnWidth = 4000;
                        break;
                    case 5:
                        columnWidth = 4000;
                        break;
                    case 6:
                        columnWidth = 4000;
                        break;
                    case 7:
                        columnWidth = 4000;
                        break;
                    case 8:
                        columnWidth = 2500;
                        break;
                    case 9:
                        columnWidth = 4000;
                        break;
                    case 10:
                        columnWidth = 4000;
                        break;
                    case 11:
                        columnWidth = 4000;
                        break;
                    case 12:
                        columnWidth = 4000;
                        break;

                }
                averagesSheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            String path;
            path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

            columnIndex = 0;
            while (columnIndex < 13) {
                Cell cell = averagesHeaderRow1.createCell(columnIndex);
                switch (columnIndex) {
                    case 0:
                        cell.setCellValue("მარშრუტის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 1:
                        cell.setCellValue("მიმართულება");
                        cell.setCellStyle(headerStyleVertical);
                        break;

                    case 2:
                        cell.setCellValue("+" + percents + "% ჩათვლილი რეისების რაოდენობა");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 3:
                        cell.setCellValue("+" + percents + "% ჩათვლილი რეისების საშუალო ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 4:
                        cell.setCellValue("-" + percents + "% ჩათვლილი რეისების რაოდენობა");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 5:
                        cell.setCellValue("-" + percents + "% ჩათვლილი რეისების საშუალო ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 6:
                        cell.setCellValue("ყველა ჩათვლილი რეისების რაოდენობა");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 7:
                        cell.setCellValue("ყველა ჩათვლილი რეისების საშუალო ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 8:
                        cell.setCellValue("იძებნება მრავალი გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 9:
                        cell.setCellValue("წირის სტანდარტული გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 10:
                        cell.setCellValue("ბრუნების სტანდარტული გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 11:
                        cell.setCellValue("ყველა ჩათვლილი რეისების რაოდენობა");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 12:
                        cell.setCellValue("ორივე მიმართულების ბრუნების საშუალო ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;

                }

                columnIndex++;
            }

            rowHeigth = 30;
            for (Map.Entry<Float, RouteAverages> routesAveragesEntry : routesAverages.entrySet()) {
                RouteAverages routeAverages = routesAveragesEntry.getValue();
                Row rowAB = averagesSheet.createRow(++rowIndex);
                rowAB.setHeightInPoints(rowHeigth);

                Cell cell_AB_0 = rowAB.createCell(0);
                cell_AB_0.setCellValue(this.converter.convertRouteNumber(routeAverages.getRouteNumber()));
                cell_AB_0.setCellStyle(rowStyleWhiteRegular);

                averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 0, 0));

                Cell cell_AB_1 = rowAB.createCell(1);
                cell_AB_1.setCellValue("A_B");
                cell_AB_1.setCellStyle(rowStyleWhiteRegular);

                Cell cell_AB_2 = rowAB.createCell(2);
                cell_AB_2.setCellValue(routeAverages.getAbLowCount());
                cell_AB_2.setCellStyle(rowStyleWhiteNumber);
                Hyperlink hyperlink_AB_2 = workbook.getCreationHelper()
                        .createHyperlink(HyperlinkType.URL);
                hyperlink_AB_2.setAddress(path + "countedTripPeriods.htm?routeNumber=" + routeAverages.getRouteNumber() + "&dateStamps=" + routeAverages.getDateStamps() + "&type=ab&percents=" + percents + "&height=low");
                cell_AB_2.setHyperlink((XSSFHyperlink) hyperlink_AB_2);

                Cell cell_AB_3 = rowAB.createCell(3);
                cell_AB_3.setCellValue(routeAverages.getAbLowAverageString());
                cell_AB_3.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_AB_4 = rowAB.createCell(4);
                cell_AB_4.setCellValue(routeAverages.getAbHighCount());
                cell_AB_4.setCellStyle(rowStyleWhiteNumber);
                Hyperlink hyperlink_AB_4 = workbook.getCreationHelper()
                        .createHyperlink(HyperlinkType.URL);
                hyperlink_AB_4.setAddress(path + "countedTripPeriods.htm?routeNumber=" + routeAverages.getRouteNumber() + "&dateStamps=" + routeAverages.getDateStamps() + "&type=ab&percents=" + percents + "&height=high");
                cell_AB_4.setHyperlink((XSSFHyperlink) hyperlink_AB_4);

                Cell cell_AB_5 = rowAB.createCell(5);
                cell_AB_5.setCellValue(routeAverages.getAbHighAverageString());
                cell_AB_5.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_AB_6 = rowAB.createCell(6);
                cell_AB_6.setCellValue(routeAverages.getAbLowAndHighCount());
                cell_AB_6.setCellStyle(rowStyleWhiteNumber);
                Hyperlink hyperlink_AB_6 = workbook.getCreationHelper()
                        .createHyperlink(HyperlinkType.URL);
                hyperlink_AB_6.setAddress(path + "countedTripPeriods.htm?routeNumber=" + routeAverages.getRouteNumber() + "&dateStamps=" + routeAverages.getDateStamps() + "&type=ab&percents=" + percents + "&height=both");
                cell_AB_6.setHyperlink((XSSFHyperlink) hyperlink_AB_6);

                Cell cell_AB_7 = rowAB.createCell(7);
                cell_AB_7.setCellValue(routeAverages.getAbLowAndHighAverage());
                cell_AB_7.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_AB_8 = rowAB.createCell(8);
                cell_AB_8.setCellValue(routeAverages.abTripPeriodTimeIsMultiple());
                cell_AB_8.setCellStyle(rowStyleWhiteRegular);

                Cell cell_AB_9 = rowAB.createCell(9);
                cell_AB_9.setCellValue(routeAverages.getABTripPeriodStandartTimeString());
                cell_AB_9.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_AB_10 = rowAB.createCell(10);
                cell_AB_10.setCellValue(routeAverages.getTripRoundStandartTimeString());
                cell_AB_10.setCellStyle(rowStyleWhiteTimeHHmm);
                averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 10, 10));

                Cell cell_AB_11 = rowAB.createCell(11);
                cell_AB_11.setCellValue(routeAverages.getAllCount());
                cell_AB_11.setCellStyle(rowStyleWhiteNumber);
                averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 11, 11));

                Cell cell_AB_12 = rowAB.createCell(12);
                cell_AB_12.setCellValue(routeAverages.getAllAverage());
                cell_AB_12.setCellStyle(rowStyleWhiteTimeHHmm);
                averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 12, 12));

                Row rowBA = averagesSheet.createRow(++rowIndex);
                rowBA.setHeightInPoints(rowHeigth);

                Cell cell_BA_0 = rowBA.createCell(0);
                cell_BA_0.setCellStyle(rowStyleWhiteRegular);

                Cell cell_BA_1 = rowBA.createCell(1);
                cell_BA_1.setCellValue("B_A");
                cell_BA_1.setCellStyle(rowStyleWhiteRegular);

                Cell cell_BA_2 = rowBA.createCell(2);
                cell_BA_2.setCellValue(routeAverages.getBaLowCount());
                cell_BA_2.setCellStyle(rowStyleWhiteNumber);
                Hyperlink hyperlink_BA_2 = workbook.getCreationHelper()
                        .createHyperlink(HyperlinkType.URL);
                hyperlink_BA_2.setAddress(path + "countedTripPeriods.htm?routeNumber=" + routeAverages.getRouteNumber() + "&dateStamps=" + routeAverages.getDateStamps() + "&type=ba&percents=" + percents + "&height=low");
                cell_BA_2.setHyperlink((XSSFHyperlink) hyperlink_BA_2);

                Cell cell_BA_3 = rowBA.createCell(3);
                cell_BA_3.setCellValue(routeAverages.getBaLowAverageString());
                cell_BA_3.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_BA_4 = rowBA.createCell(4);
                cell_BA_4.setCellValue(routeAverages.getBaHighCount());
                cell_BA_4.setCellStyle(rowStyleWhiteNumber);
                Hyperlink hyperlink_BA_4 = workbook.getCreationHelper()
                        .createHyperlink(HyperlinkType.URL);
                hyperlink_BA_4.setAddress(path + "countedTripPeriods.htm?routeNumber=" + routeAverages.getRouteNumber() + "&dateStamps=" + routeAverages.getDateStamps() + "&type=ba&percents=" + percents + "&height=high");
                cell_BA_4.setHyperlink((XSSFHyperlink) hyperlink_BA_4);

                Cell cell_BA_5 = rowBA.createCell(5);
                cell_BA_5.setCellValue(routeAverages.getBaHighAverageString());
                cell_BA_5.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_BA_6 = rowBA.createCell(6);
                cell_BA_6.setCellValue(routeAverages.getBaLowAndHighCount());
                cell_BA_6.setCellStyle(rowStyleWhiteNumber);
                Hyperlink hyperlink_BA_6 = workbook.getCreationHelper()
                        .createHyperlink(HyperlinkType.URL);
                hyperlink_BA_6.setAddress(path + "countedTripPeriods.htm?routeNumber=" + routeAverages.getRouteNumber() + "&dateStamps=" + routeAverages.getDateStamps() + "&type=ba&percents=" + percents + "&height=both");
                cell_BA_6.setHyperlink((XSSFHyperlink) hyperlink_BA_6);

                Cell cell_BA_7 = rowBA.createCell(7);
                cell_BA_7.setCellValue(routeAverages.getBaLowAndHighAverage());
                cell_BA_7.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_BA_8 = rowBA.createCell(8);
                cell_BA_8.setCellValue(routeAverages.baTripPeriodTimeIsMultiple());
                cell_BA_8.setCellStyle(rowStyleWhiteRegular);

                Cell cell_BA_9 = rowBA.createCell(9);
                cell_BA_9.setCellValue(routeAverages.getBATripPeriodStandartTimeString());
                cell_BA_9.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_BA_10 = rowBA.createCell(10);
                cell_BA_10.setCellStyle(rowStyleWhiteTimeHHmm);

                Cell cell_BA_11 = rowBA.createCell(11);
                cell_BA_11.setCellStyle(rowStyleWhiteNumber);

                Cell cell_BA_12 = rowBA.createCell(12);
                cell_BA_12.setCellStyle(rowStyleWhiteTimeHHmm);
                System.out.println("Route N" + routeAverages.getRouteNumber() + " Completed");
            }
            workbook.write(os);
            System.out.println("++++Routes Averages Excel Writing Completed++++");

            LOGGER.info("Export 100 W Row data time (seconds):" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private XSSFCellStyle getHeaderStyle(SXSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, int rotation, boolean bold) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        style.setRotation((short) rotation);

        style.setWrapText(true);

        style.setRotation((short) rotation);

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
        font.setBold(bold);
        style.setFont(font);

        return style;
    }

    private XSSFCellStyle getRowStyle(SXSSFWorkbook workbook, int rgbA, int rgbB, int rgbC, boolean italic, boolean bold, String format) {
        XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
        byte[] rgb = new byte[]{(byte) rgbA, (byte) rgbB, (byte) rgbC};
        XSSFColor color = new XSSFColor(rgb, null); //IndexedColorMap has no usage until now. So it can be set null.
        style.setFillForegroundColor(color);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

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
        font.setItalic(italic);
        font.setBold(bold);
        style.setFont(font);

        //time formats "[hh]:mm"   "[mm]:ss"
        XSSFDataFormat fmts = (XSSFDataFormat) workbook.createDataFormat();
        style.setDataFormat(fmts.getFormat(format));
        return style;
    }

}
