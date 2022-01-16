/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.BasicRoute;
import Model.Day;
import Model.DetailedRoute;
import Model.DetailedTripPeriod;
import Model.Exodus;
import Model.FirstTripPeriod;
import Model.GuarantyRoute;
import Model.GuarantyTripsData;
import Model.IntervalDay;
import Model.IntervalTripPeriod;
import Model.MisconductTripPeriod;
import Model.RouteAverages;
import Model.TripPeriod;
import Model.TripPeriod2X;
import Model.TripVoucher;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.invoke.MethodHandles;
import java.lang.management.ManagementFactory;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
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
            cell_6.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_7 = row.createCell(7);
            if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                cell_7.setCellValue("");
            } else {
                cell_7.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
            }
            cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_8 = row.createCell(8);
            cell_8.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
            cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_9 = row.createCell(9);
            if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                cell_9.setCellValue("");
            } else {
                cell_9.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
            }
            cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);

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
            cell_6.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_7 = row.createCell(7);
            if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                cell_7.setCellValue("");
            } else {
                cell_7.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
            }
            cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_8 = row.createCell(8);
            cell_8.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
            cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_9 = row.createCell(9);
            if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                cell_9.setCellValue("");
            } else {
                cell_9.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
            }
            cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);

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
            cell_AB_7.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_AB_8 = rowAB.createCell(8);
            cell_AB_8.setCellValue(routeAverages.abTripPeriodTimeIsMultiple());
            cell_AB_8.setCellStyle(rowStyleWhiteRegular);

            Cell cell_AB_9 = rowAB.createCell(9);
            cell_AB_9.setCellValue(routeAverages.getABTripPeriodStandartTimeString());
            cell_AB_9.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_AB_10 = rowAB.createCell(10);
            cell_AB_10.setCellValue(routeAverages.getTripRoundStandartTimeString());
            cell_AB_10.setCellStyle(rowStyleWhiteTimeHHmmss);
            averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 10, 10));

            Cell cell_AB_11 = rowAB.createCell(11);
            cell_AB_11.setCellValue(routeAverages.getAllCount());
            cell_AB_11.setCellStyle(rowStyleWhiteNumber);
            averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 11, 11));

            Cell cell_AB_12 = rowAB.createCell(12);
            cell_AB_12.setCellValue(routeAverages.getAllAverage());
            cell_AB_12.setCellStyle(rowStyleWhiteTimeHHmmss);
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
            cell_BA_7.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_BA_8 = rowBA.createCell(8);
            cell_BA_8.setCellValue(routeAverages.baTripPeriodTimeIsMultiple());
            cell_BA_8.setCellStyle(rowStyleWhiteRegular);

            Cell cell_BA_9 = rowBA.createCell(9);
            cell_BA_9.setCellValue(routeAverages.getBATripPeriodStandartTimeString());
            cell_BA_9.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_BA_10 = rowBA.createCell(10);
            cell_BA_10.setCellStyle(rowStyleWhiteTimeHHmmss);

            Cell cell_BA_11 = rowBA.createCell(11);
            cell_BA_11.setCellStyle(rowStyleWhiteNumber);

            Cell cell_BA_12 = rowBA.createCell(12);
            cell_BA_12.setCellStyle(rowStyleWhiteTimeHHmmss);
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
                cell_6.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_7 = row.createCell(7);
                if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                    cell_7.setCellValue("");
                } else {
                    cell_7.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
                }
                cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
                cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_9 = row.createCell(9);
                if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                    cell_9.setCellValue("");
                } else {
                    cell_9.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
                }
                cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);

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
                cell_AB_7.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_AB_8 = rowAB.createCell(8);
                cell_AB_8.setCellValue(routeAverages.abTripPeriodTimeIsMultiple());
                cell_AB_8.setCellStyle(rowStyleWhiteRegular);

                Cell cell_AB_9 = rowAB.createCell(9);
                cell_AB_9.setCellValue(routeAverages.getABTripPeriodStandartTimeString());
                cell_AB_9.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_AB_10 = rowAB.createCell(10);
                cell_AB_10.setCellValue(routeAverages.getTripRoundStandartTimeString());
                cell_AB_10.setCellStyle(rowStyleWhiteTimeHHmmss);
                averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 10, 10));

                Cell cell_AB_11 = rowAB.createCell(11);
                cell_AB_11.setCellValue(routeAverages.getAllCount());
                cell_AB_11.setCellStyle(rowStyleWhiteNumber);
                averagesSheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex + 1, 11, 11));

                Cell cell_AB_12 = rowAB.createCell(12);
                cell_AB_12.setCellValue(routeAverages.getAllAverage());
                cell_AB_12.setCellStyle(rowStyleWhiteTimeHHmmss);
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
                cell_BA_7.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_BA_8 = rowBA.createCell(8);
                cell_BA_8.setCellValue(routeAverages.baTripPeriodTimeIsMultiple());
                cell_BA_8.setCellStyle(rowStyleWhiteRegular);

                Cell cell_BA_9 = rowBA.createCell(9);
                cell_BA_9.setCellValue(routeAverages.getBATripPeriodStandartTimeString());
                cell_BA_9.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_BA_10 = rowBA.createCell(10);
                cell_BA_10.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_BA_11 = rowBA.createCell(11);
                cell_BA_11.setCellStyle(rowStyleWhiteNumber);

                Cell cell_BA_12 = rowBA.createCell(12);
                cell_BA_12.setCellStyle(rowStyleWhiteTimeHHmmss);
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

    void SXSSF_DetailedRoutes(TreeMap<Float, DetailedRoute> detailedRoutes, String fileName) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {
            //setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("ბრუნები დეტალურად");
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
                        columnWidth = 1500;
                        break;
                    case 1:
                        columnWidth = 3000;
                        break;
                    case 2:
                        columnWidth = 1500;
                        break;
                    case 3:
                        columnWidth = 4000;
                        break;
                    case 4:
                        columnWidth = 2800;
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
                    case 13:
                        columnWidth = 2800;
                        break;
                    case 14:
                        columnWidth = 2800;
                        break;
                    case 15:
                        columnWidth = 2800;
                        break;
                    case 16:
                        columnWidth = 2800;
                        break;
                    case 17:
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

            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 0, 0, 255, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 0, 0, 255, 90, false);

            columnIndex = 0;
            while (columnIndex < 19) {
                Cell cell = headerRow1.createCell(columnIndex);
                switch (columnIndex) {
                    case 0:
                        cell.setCellValue("მარშრუტი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 1:
                        cell.setCellValue("თარიღი");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 2:
                        cell.setCellValue("გასვლის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 3:
                        cell.setCellValue("საგზურის #");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 4:
                        cell.setCellValue("გეგმიუირი გასვლის დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 5:
                        cell.setCellValue("ფაქტიური გასვლის დრო");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 6:
                        cell.setCellValue("სხვაობა");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 7:
                        cell.setCellValue("მიმართულება");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 8:
                        cell.setCellValue("გეგმიუირი მისვლის დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 9:
                        cell.setCellValue("ფაქტიური მისვლის დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 10:
                        cell.setCellValue("სხვაობა");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 11:
                        cell.setCellValue("წირის გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 12:
                        cell.setCellValue("წირის ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 13:
                        cell.setCellValue("სხვაობა");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 14:
                        cell.setCellValue("დგომის გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 15:
                        cell.setCellValue("დგომის ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 16:
                        cell.setCellValue("'დაკარგული დრო'");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 17:
                        cell.setCellValue("GPS ინტერვალი");
                        cell.setCellStyle(headerStyle);
                        break;

                }

                columnIndex++;
            }

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteRegularLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "");

            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "0"); //"0" makes cell numeric type

            XSSFCellStyle rowStyleWhiteTimeHHmm = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm");
            XSSFCellStyle rowStyleWhiteTimeHHmmLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "[hh]:mm");

            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleWhiteTimeHHmmssLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleYellowTimeHHmmss = getRowStyle(workbook, 255, 255, 0, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

            rowHeigth = 30;
            //--------------------------------starting writing cells---------------  
            boolean routeLightOn = true;

            for (Map.Entry<Float, DetailedRoute> detailedRouteEntry : detailedRoutes.entrySet()) {
                if (routeLightOn) {
                    routeLightOn = false;
                } else {
                    routeLightOn = true;
                }
                DetailedRoute detailedRoute = detailedRouteEntry.getValue();
                TreeMap<Date, Day> days = detailedRoute.getDays();
                for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                    //    newDay = true;
                    Day day = dayEntry.getValue();
                    TreeMap<Short, Exodus> exoduses = day.getExoduses();

                    for (Map.Entry<Short, Exodus> exodusesEntry : exoduses.entrySet()) {

                        Exodus exodus = exodusesEntry.getValue();
                        TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
                        for (Map.Entry<String, TripVoucher> tripVouchersEntry : tripVouchers.entrySet()) {

                            TripVoucher tripVoucher = tripVouchersEntry.getValue();
                            ArrayList<TripPeriod> tripPeriods = tripVouchersEntry.getValue().getTripPeriods();
                            for (TripPeriod tripPeriodA : tripPeriods) {

                                DetailedTripPeriod tripPeriod = (DetailedTripPeriod) tripPeriodA;
                                Row row = sheet.createRow(++rowIndex);
                                row.setHeightInPoints(rowHeigth);
                                //-------------------------------
                                Cell routeNumberCell = row.createCell(0);
                                routeNumberCell.setCellValue(detailedRoute.getNumber());
                                if (routeLightOn) {
                                    routeNumberCell.setCellStyle(rowStyleWhiteRegularLightOn);
                                } else {
                                    routeNumberCell.setCellStyle(rowStyleWhiteRegular);
                                }
                                //-------------------------------
                                Cell dateStampCell = row.createCell(1);
                                dateStampCell.setCellValue(day.getDateStampWeekFormat());
                                if (routeLightOn) {
                                    dateStampCell.setCellStyle(rowStyleWhiteRegularLightOn);
                                } else {
                                    dateStampCell.setCellStyle(rowStyleWhiteRegular);
                                }
                                //-------------------------------
                                Cell exodusCell = row.createCell(2);
                                exodusCell.setCellValue(exodus.getNumber());
                                if (routeLightOn) {
                                    exodusCell.setCellStyle(rowStyleWhiteRegularLightOn);
                                } else {
                                    exodusCell.setCellStyle(rowStyleWhiteRegular);
                                }
                                //-------------------------------
                                Cell tripVoucherCell = row.createCell(3);
                                tripVoucherCell.setCellValue(tripVoucher.getNumber());
                                if (routeLightOn) {
                                    tripVoucherCell.setCellStyle(rowStyleWhiteRegularLightOn);
                                } else {
                                    tripVoucherCell.setCellStyle(rowStyleWhiteRegular);
                                }
                                //-------------------------------
                                Cell startTimeScheduledCell = row.createCell(4);
                                startTimeScheduledCell.setCellValue(tripPeriod.getStartTimeScheduledExcelFormat());
                                if (routeLightOn) {
                                    startTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    startTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }
                                //-------------------------------
                                Cell startTimeActualCell = row.createCell(5);
                                if (tripPeriod.getStartTimeActualExcelFormat() == null) {
                                    startTimeActualCell.setCellValue("");
                                } else {
                                    startTimeActualCell.setCellValue(tripPeriod.getStartTimeActualExcelFormat());
                                }
                                if (routeLightOn) {
                                    startTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    startTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }
                                //-------------------------------
                                Cell startTimeDifferenceCell = row.createCell(6);
                                startTimeDifferenceCell.setCellValue(tripPeriod.getStartTimeDifference());
                                if (routeLightOn) {
                                    startTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    startTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }
                                if (tripPeriod.getStartTimeDifferenceColor().equals("red")) {
                                    startTimeDifferenceCell.setCellStyle(rowStyleRedTimeHHmmss);
                                }
                                if (tripPeriod.getStartTimeDifferenceColor().equals("yellow")) {
                                    startTimeDifferenceCell.setCellStyle(rowStyleYellowTimeHHmmss);
                                }
                                //-------------------------------
                                Cell typeCell = row.createCell(7);
                                typeCell.setCellValue(tripPeriod.getTypeG());
                                if (routeLightOn) {
                                    typeCell.setCellStyle(rowStyleWhiteRegularLightOn);
                                } else {
                                    typeCell.setCellStyle(rowStyleWhiteRegular);
                                }

                                //-------------------------------
                                Cell arrivalTimeScheduledCell = row.createCell(8);
                                arrivalTimeScheduledCell.setCellValue(tripPeriod.getArrivaltTimeScheduledExcelFormat());
                                if (routeLightOn) {
                                    arrivalTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    arrivalTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                                //-------------------------------
                                Cell arrivalTimeActualCell = row.createCell(9);
                                if (tripPeriod.getArrivalTimeActualExcelFormat() == null) {
                                    arrivalTimeActualCell.setCellValue("");
                                } else {
                                    arrivalTimeActualCell.setCellValue(tripPeriod.getArrivalTimeActualExcelFormat());
                                }
                                if (routeLightOn) {
                                    arrivalTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    arrivalTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                                //-------------------------------
                                Cell arrivalTimeDifferenceCell = row.createCell(10);
                                arrivalTimeDifferenceCell.setCellValue(tripPeriod.getArrivalTimeDifference());
                                if (routeLightOn) {
                                    arrivalTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    arrivalTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }
                                if (tripPeriod.getArrivalTimeDifferenceColor().equals("red")) {
                                    arrivalTimeDifferenceCell.setCellStyle(rowStyleRedTimeHHmmss);
                                }
                                if (tripPeriod.getArrivalTimeDifferenceColor().equals("yellow")) {
                                    arrivalTimeDifferenceCell.setCellStyle(rowStyleYellowTimeHHmmss);
                                }

                                //-------------------------------
                                Cell tripPeriodTimeScheduledCell = row.createCell(11);
                                tripPeriodTimeScheduledCell.setCellValue(tripPeriod.getTripPeriodTimeScheduledExcelFormat());
                                if (routeLightOn) {
                                    tripPeriodTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    tripPeriodTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                                //-------------------------------
                                Cell tripPeriodTimeActualCell = row.createCell(12);
                                if (tripPeriod.getTripPeriodTimeActualExcelFormat() == null) {
                                    tripPeriodTimeActualCell.setCellValue("");
                                } else {
                                    tripPeriodTimeActualCell.setCellValue(tripPeriod.getTripPeriodTimeActualExcelFormat());
                                }
                                if (routeLightOn) {
                                    tripPeriodTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    tripPeriodTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                                //-------------------------------
                                Cell tripPeriodTimeDifferenceCell = row.createCell(13);
                                if (tripPeriod.getTripPeriodTimeDifferenceExcelFormat() == null) {
                                    tripPeriodTimeDifferenceCell.setCellValue("");
                                } else {
                                    tripPeriodTimeDifferenceCell.setCellValue(tripPeriod.getTripPeriodTimeDifferenceExcelFormat());
                                }
                                if (routeLightOn) {
                                    tripPeriodTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    tripPeriodTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }
                                if (tripPeriod.getTripPeriodTimeDifferenceColor().equals("red")) {
                                    tripPeriodTimeDifferenceCell.setCellStyle(rowStyleRedTimeHHmmss);
                                }
                                if (tripPeriod.getTripPeriodTimeDifferenceColor().equals("yellow")) {
                                    tripPeriodTimeDifferenceCell.setCellStyle(rowStyleYellowTimeHHmmss);
                                }

                                //-------------------------------
                                Cell haltTimeScheduledCell = row.createCell(14);
                                if (tripPeriod.getHaltTimeScheduledExcelFormat() == null) {
                                    haltTimeScheduledCell.setCellValue("");
                                } else {
                                    haltTimeScheduledCell.setCellValue(tripPeriod.getHaltTimeScheduledExcelFormat());
                                }
                                if (routeLightOn) {
                                    haltTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    haltTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                                //-------------------------------
                                Cell haltTimeActualCell = row.createCell(15);
                                if (tripPeriod.getHaltTimeActualExcelFormat() == null) {
                                    haltTimeActualCell.setCellValue("");
                                } else {
                                    haltTimeActualCell.setCellValue(tripPeriod.getHaltTimeActualExcelFormat());
                                }
                                if (routeLightOn) {
                                    haltTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    haltTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                                //-------------------------------
                                Cell lostTimeCell = row.createCell(16);
                                if (tripPeriod.getLostTimeExcelFormat() == null) {
                                    lostTimeCell.setCellValue("");
                                } else {
                                    lostTimeCell.setCellValue(tripPeriod.getLostTimeExcelFormat());
                                }
                                if (routeLightOn) {
                                    lostTimeCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    lostTimeCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }
                                if (tripPeriod.getLostTimeColor().equals("red")) {
                                    lostTimeCell.setCellStyle(rowStyleRedTimeHHmmss);
                                }
                                if (tripPeriod.getLostTimeColor().equals("yellow")) {
                                    lostTimeCell.setCellStyle(rowStyleYellowTimeHHmmss);
                                }

                                //-------------------------------
                                Cell gpsIntervalCell = row.createCell(17);
                                if (tripPeriod.getGpsIntervalExcelFormat() == null) {
                                    gpsIntervalCell.setCellValue("");
                                } else {
                                    gpsIntervalCell.setCellValue(tripPeriod.getGpsIntervalExcelFormat());
                                }
                                if (routeLightOn) {
                                    gpsIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                                } else {
                                    gpsIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                                }

                            }


                            /*  if (newTripVoucherStartIndex != rowIndex) {
                                sheet.addMergedRegion(new CellRangeAddress(newTripVoucherStartIndex, rowIndex, 3, 3));
                            }*/
                        }
                        //sheet.addMergedRegion(new CellRangeAddress(newExodusStartIndex, rowIndex, 2, 2));
                    }
                    //  sheet.addMergedRegion(new CellRangeAddress(newDayStartIndex, rowIndex, 1, 1));
                }

                // sheet.addMergedRegion(new CellRangeAddress(newRouteStartIndex, rowIndex, 0, 0));
            }

            workbook.write(os);
            System.out.println("++++Routes Averages Excel Writing Completed++++");

            LOGGER.info("Export 100 W Row data time (seconds):" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void SXSSF_Intervals(TreeMap<Float, DetailedRoute> detailedRoutes, String fileName, HttpServletRequest request) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {

//setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("ინტერვალები");

            int rowIndex = 0;
            int columnIndex = 0;
            int rowHeigth = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 39) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 1100;
                        break;
                    case 1:
                        columnWidth = 3000;
                        break;
                    case 2:
                        columnWidth = 3500;
                        break;
                    case 3:
                        columnWidth = 7000;
                        break;
                    case 4:
                        columnWidth = 800;
                        break;
                    case 5:
                        columnWidth = 2800;
                        break;
                    case 6:
                        columnWidth = 2800;
                        break;
                    case 7:
                        columnWidth = 800;
                        break;
                    case 8:
                        columnWidth = 800;
                        break;
                    case 9:
                        columnWidth = 800;
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
                    case 13:
                        columnWidth = 2800;
                        break;
                    case 14:
                        columnWidth = 2800;
                        break;
                    case 15:
                        columnWidth = 2800;
                        break;
                    case 16:
                        columnWidth = 2800;
                        break;
                    case 17:
                        columnWidth = 800;
                        break;
                    case 18:
                        columnWidth = 800;
                        break;
                    //-----------
                    case 19:
                        columnWidth = 800;
                        break;
                    //--------
                    case 20:
                        columnWidth = 3500;
                        break;
                    case 21:
                        columnWidth = 7000;
                        break;
                    case 22:
                        columnWidth = 800;
                        break;
                    case 23:
                        columnWidth = 2800;
                        break;
                    case 24:
                        columnWidth = 2800;
                        break;
                    case 25:
                        columnWidth = 800;
                        break;
                    case 26:
                        columnWidth = 800;
                        break;
                    case 27:
                        columnWidth = 800;
                        break;
                    case 28:
                        columnWidth = 2800;
                        break;
                    case 29:
                        columnWidth = 2800;
                        break;
                    case 30:
                        columnWidth = 2800;
                        break;
                    case 31:
                        columnWidth = 2800;
                        break;
                    case 32:
                        columnWidth = 2800;
                        break;
                    case 33:
                        columnWidth = 2800;
                        break;
                    case 34:
                        columnWidth = 2800;
                        break;
                    case 35:
                        columnWidth = 800;
                        break;
                    case 36:
                        columnWidth = 800;
                        break;

                }
                sheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            //now headers
            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 102, 102, 102, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 102, 102, 102, 90, false);
            XSSFCellStyle headerStyleWhite = getHeaderStyle(workbook, 255, 255, 255, 0, false);
            //first header row
            Row headerRow1 = sheet.createRow(rowIndex);
            columnIndex = 0;
            while (columnIndex < 37) {
                if (columnIndex == 0) {
                    Cell cell = headerRow1.createCell(columnIndex);
                    cell.setCellValue("");
                    cell.setCellStyle(headerStyleWhite);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 1));

                } else if (columnIndex == 2) {
                    Cell cell = headerRow1.createCell(columnIndex);
                    cell.setCellValue("A-B განრიგი");
                    cell.setCellStyle(headerStyleWhite);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 5));

                } else if (columnIndex == 8) {
                    Cell cell = headerRow1.createCell(columnIndex);
                    cell.setCellValue("A-B GPS გამოთვლები");
                    cell.setCellStyle(headerStyleWhite);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 10));

                } else if (columnIndex == 22) {
                    Cell cell = headerRow1.createCell(columnIndex);
                    cell.setCellValue("B-A განრიგი");
                    cell.setCellStyle(headerStyleWhite);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 5));

                } else if (columnIndex == 28) {
                    Cell cell = headerRow1.createCell(columnIndex);
                    cell.setCellValue("B-A GPS გამოთვლები");
                    cell.setCellStyle(headerStyleWhite);
                    sheet.addMergedRegion(new CellRangeAddress(rowIndex, rowIndex, columnIndex, columnIndex + 10));

                }
                columnIndex++;
            }

            rowIndex++;

            //second header row
            Row headerRow2 = sheet.createRow(rowIndex);
            rowHeigth = 80;
            headerRow2.setHeightInPoints(rowHeigth);

            columnIndex = 0;
            while (columnIndex < 37) {
                Cell cell = headerRow2.createCell(columnIndex);
                switch (columnIndex) {
                    case 0:
                        cell.setCellValue("მარშრუტი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 1:
                        cell.setCellValue("თარიღი");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 2:
                        cell.setCellValue("ავტობუსის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 3:
                        cell.setCellValue("მძღოლი");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 4:
                        cell.setCellValue("რიგითი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;

                    case 5:
                        cell.setCellValue("გასვლის გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 6:
                        cell.setCellValue("გასვილი ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 7:
                        cell.setCellValue("გასვლის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    //-----gps here ---
                    case 8:
                        cell.setCellValue("გასვლის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 9:
                        cell.setCellValue("რიგითი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 10:
                        cell.setCellValue("გასვლის გეგმიური დრო");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 11:
                        cell.setCellValue("გასვლის ფაქტიური დრო");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 12:
                        cell.setCellValue("სხვაობა");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 13:
                        cell.setCellValue("დგომის ფაქტიური დრო");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 14:
                        cell.setCellValue("დაკარგული დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 15:
                        cell.setCellValue("გეგმიური ინტერვალი");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 16:
                        cell.setCellValue("GPS ინტერვალი");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 17:
                        cell.setCellValue("ხარცეზი");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 18:
                        cell.setCellValue("გადასწრება");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 19:
                        cell.setCellValue("");
                        cell.setCellStyle(headerStyleWhite);
                        break;
                    case 20:
                        cell.setCellValue("ავტობუსის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 21:
                        cell.setCellValue("მძღოლი");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 22:
                        cell.setCellValue("რიგითი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;

                    case 23:
                        cell.setCellValue("გასვლის გეგმიური დრო");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 24:
                        cell.setCellValue("გასვილი ფაქტიური დრო");
                        cell.setCellStyle(headerStyleVertical);
                        break;

                    case 25:
                        cell.setCellValue("გასვლის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    //-----gps here ---
                    case 26:
                        cell.setCellValue("გასვლის #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 27:
                        cell.setCellValue("რიგითი #");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 28:
                        cell.setCellValue("გასვლის გეგმიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 29:
                        cell.setCellValue("გასვლის ფაქტიური დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 30:
                        cell.setCellValue("სხვაობა");
                        cell.setCellStyle(headerStyle);
                        break;

                    case 31:
                        cell.setCellValue("დგომის ფაქტიური დრო");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 32:
                        cell.setCellValue("დაკარგული დრო");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 33:
                        cell.setCellValue("გეგმიური ინტერვალი");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 34:
                        cell.setCellValue("GPS ინტერვალი");
                        cell.setCellStyle(headerStyle);
                        break;
                    case 35:
                        cell.setCellValue("ხარცეზი");
                        cell.setCellStyle(headerStyleVertical);
                        break;
                    case 36:
                        cell.setCellValue("გადასწრება");
                        cell.setCellStyle(headerStyleVertical);
                        break;

                }

                columnIndex++;
            }

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteRegularLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "");

            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightBlue = getRowStyle(workbook, 63, 219, 203, false, false, "0"); //"0" makes cell numeric type

            XSSFCellStyle rowStyleWhiteTimeHHmm = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm");
            XSSFCellStyle rowStyleWhiteTimeHHmmLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "[hh]:mm");

            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleWhiteTimeHHmmssLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleYellowTimeHHmmss = getRowStyle(workbook, 255, 255, 0, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleLightGreenRegular = getRowStyle(workbook, 144, 238, 144, false, false, "");
            XSSFCellStyle rowStyleBlueRegular = getRowStyle(workbook, 0, 0, 255, false, false, "");

            rowHeigth = 30;
            //--------------------------------starting writing cells---------------  

            boolean routeLightOn = true;

            String path;
            path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

            rowIndex++;

            for (Map.Entry<Float, DetailedRoute> detailedRouteEntry : detailedRoutes.entrySet()) {
                if (routeLightOn) {
                    routeLightOn = false;
                } else {
                    routeLightOn = true;
                }

                TreeMap<Date, Day> days = detailedRouteEntry.getValue().getDays();
                for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                    IntervalDay day = (IntervalDay) dayEntry.getValue();
                    TreeMap<LocalDateTime, IntervalTripPeriod> abTimetableTreeMap = day.getAbTimetable();
                    TreeMap<LocalDateTime, IntervalTripPeriod> baTimetableTreeMap = day.getBaTimetable();
                    TreeMap<LocalDateTime, DetailedTripPeriod> abGpsTimetableTreeMap = day.getAbGpsTimetable();
                    TreeMap<LocalDateTime, DetailedTripPeriod> baGpsTimetableTreeMap = day.getBaGpsTimetable();

                    Collection<IntervalTripPeriod> abTimetableValues = abTimetableTreeMap.values();
                    ArrayList<IntervalTripPeriod> abTimetable = new ArrayList<>(abTimetableValues);

                    Collection<IntervalTripPeriod> baTimetableValues = baTimetableTreeMap.values();
                    ArrayList<IntervalTripPeriod> baTimetable = new ArrayList<>(baTimetableValues);

                    Collection<DetailedTripPeriod> abGpsTimetableValues = abGpsTimetableTreeMap.values();
                    ArrayList<DetailedTripPeriod> abGpsTimetable = new ArrayList<>(abGpsTimetableValues);

                    Collection<DetailedTripPeriod> baGpsTimetableValues = baGpsTimetableTreeMap.values();
                    ArrayList<DetailedTripPeriod> baGpsTimetable = new ArrayList<>(baGpsTimetableValues);

                    int maxCount = abTimetable.size() >= baTimetable.size() ? abTimetable.size() : baTimetable.size();

                    for (int count = 0; count < maxCount; count++) {
                        Row row = sheet.createRow(rowIndex);
                        //------------------------------------------
                        Cell routeNumberCell = row.createCell(0);
                        routeNumberCell.setCellValue(detailedRouteEntry.getValue().getNumber());
                        if (routeLightOn) {
                            routeNumberCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            routeNumberCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }

                        //------------------------------------------
                        Cell dateStampCell = row.createCell(1);
                        dateStampCell.setCellValue(day.getDateStamp());
                        if (routeLightOn) {
                            dateStampCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            dateStampCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }

                        //------------------------------------------
                        Cell busNumberCell = row.createCell(2);
                        if (count < abTimetable.size()) {
                            busNumberCell.setCellValue(abTimetable.get(count).getBusNumber());
                        } else {
                            busNumberCell.setCellValue("");
                        }

                        if (routeLightOn) {
                            busNumberCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            busNumberCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }
                        //------------------------------------------
                        Cell busDriver = row.createCell(3);

                        if (count < abTimetable.size()) {
                            busDriver.setCellValue(abTimetable.get(count).getDriverName());
                        } else {
                            busDriver.setCellValue("");
                        }
                        if (routeLightOn) {
                            busDriver.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            busDriver.setCellStyle(rowStyleWhiteNumberLightOn);
                        }
                        //------------------------------------------

                        Cell abTimetableScheduledTimetableSequenceNumberCell = row.createCell(4);

                        if (count < abTimetable.size()) {
                            abTimetableScheduledTimetableSequenceNumberCell.setCellValue(abTimetable.get(count).getScheduledTimetableSequenceNumber());
                        } else {
                            abTimetableScheduledTimetableSequenceNumberCell.setCellValue("");
                        }
                        abTimetableScheduledTimetableSequenceNumberCell.setCellStyle(rowStyleWhiteNumberLightBlue);
                        //------------------------------------------
                        Cell abTimetableStartTimeScheduledCell = row.createCell(5);
                        if (count < abTimetable.size()) {
                            abTimetableStartTimeScheduledCell.setCellValue(abTimetable.get(count).getStartTimeScheduledExcelFormat());
                        } else {
                            abTimetableStartTimeScheduledCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        //------------------------------------------
                        Cell abTimetableStartTimeActualCell = row.createCell(6);
                        if (count < abTimetable.size()) {
                            Double startTimeActualExcelFormat = abTimetable.get(count).getStartTimeActualExcelFormat();
                            if (startTimeActualExcelFormat == null) {
                                abTimetableStartTimeActualCell.setCellValue("");
                            } else {
                                abTimetableStartTimeActualCell.setCellValue(startTimeActualExcelFormat);
                            }
                        } else {
                            abTimetableStartTimeActualCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        //------------------------------------------
                        Cell abTimetableExodusNumberCell = row.createCell(7);
                        if (count < abTimetable.size()) {
                            abTimetableExodusNumberCell.setCellValue(abTimetable.get(count).getExodusNumber());

                        } else {
                            abTimetableExodusNumberCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            abTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }

                        //------------------------------------------
                        Cell abGpsTimetableExodusNumberCell = row.createCell(8);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableExodusNumberCell.setCellValue(intervalTripPeriod.getExodusNumber());

                        } else {
                            abGpsTimetableExodusNumberCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            abGpsTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }
                        //------------------------------------------
                        Cell abGpsTimetableScheduledTimetableSequenceNumberCell = row.createCell(9);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableScheduledTimetableSequenceNumberCell.setCellValue(intervalTripPeriod.getScheduledTimetableSequenceNumber());
                        } else {
                            abGpsTimetableScheduledTimetableSequenceNumberCell.setCellValue("");
                        }
                        abGpsTimetableScheduledTimetableSequenceNumberCell.setCellStyle(rowStyleWhiteNumberLightBlue);

                        //------------------------------------------
                        Cell abGpsTimetableStartTimeScheduledCell = row.createCell(10);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableStartTimeScheduledCell.setCellValue(intervalTripPeriod.getStartTimeScheduledExcelFormat());
                        } else {
                            abGpsTimetableStartTimeScheduledCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        //------------------------------------------
                        Cell abGpsTimetableStartTimeActualCell = row.createCell(11);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableStartTimeActualCell.setCellValue(intervalTripPeriod.getStartTimeActualExcelFormat());
                        } else {
                            abGpsTimetableStartTimeActualCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        Cell abGpsTimetableStartTimeDifferenceCell = row.createCell(12);
                        if (count < abGpsTimetable.size()) {
                            abGpsTimetableStartTimeDifferenceCell.setCellValue(abGpsTimetable.get(count).getStartTimeDifference());
                        } else {
                            abGpsTimetableStartTimeDifferenceCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        if (count < abGpsTimetable.size()) {
                            if (abGpsTimetable.get(count).getStartTimeDifferenceColor().equals("yellow")) {
                                abGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleYellowTimeHHmmss);
                            }
                            if (abGpsTimetable.get(count).getStartTimeDifferenceColor().equals("red")) {
                                abGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleRedTimeHHmmss);
                            }
                        }

                        //------------------------------------------
                        Cell abGpsTimetableHaltTimeScheduledCell = row.createCell(13);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);

                            Double haltTimeActualExcelFormat = intervalTripPeriod.getHaltTimeActualExcelFormat();
                            if (haltTimeActualExcelFormat == null) {
                                abGpsTimetableHaltTimeScheduledCell.setCellValue("");
                            } else {
                                abGpsTimetableHaltTimeScheduledCell.setCellValue(intervalTripPeriod.getHaltTimeActualExcelFormat());
                            }
                        } else {
                            abGpsTimetableHaltTimeScheduledCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableHaltTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableHaltTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        //------------------------------------------
                        Cell abGpsTimetableLostTimeCell = row.createCell(14);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            if (intervalTripPeriod.getLostTimeExcelFormat() == null) {
                                abGpsTimetableLostTimeCell.setCellValue("");
                            } else {
                                abGpsTimetableLostTimeCell.setCellValue(intervalTripPeriod.getLostTimeExcelFormat());
                            }
                        } else {
                            abGpsTimetableLostTimeCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableLostTimeCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableLostTimeCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        if (count < abGpsTimetable.size()) {
                            if (abGpsTimetable.get(count).getLostTimeColor().equals("yellow")) {
                                abGpsTimetableLostTimeCell.setCellStyle(rowStyleYellowTimeHHmmss);
                            }
                            if (abGpsTimetable.get(count).getLostTimeColor().equals("red")) {
                                abGpsTimetableLostTimeCell.setCellStyle(rowStyleRedTimeHHmmss);
                            }
                        }

                        //------------------------------------------
                        Cell abGpsTimetableScheduledIntervalCell = row.createCell(15);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableScheduledIntervalCell.setCellValue(intervalTripPeriod.getScheduledIntervalString());
                        } else {
                            abGpsTimetableScheduledIntervalCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableScheduledIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableScheduledIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        //------------------------------------------
                        Cell abGpsTimetableGpsIntervalCell = row.createCell(16);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            if (intervalTripPeriod.getGpsIntervalExcelFormat() == null) {
                                abGpsTimetableGpsIntervalCell.setCellValue("");
                            } else {
                                abGpsTimetableGpsIntervalCell.setCellValue(intervalTripPeriod.getGpsIntervalExcelFormat());
                            }
                        } else {
                            abGpsTimetableGpsIntervalCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableGpsIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            abGpsTimetableGpsIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            if (intervalTripPeriod.getGpsIntervalColor().equals("yellow")) {
                                abGpsTimetableGpsIntervalCell.setCellStyle(rowStyleYellowTimeHHmmss);
                            }
                            if (intervalTripPeriod.getGpsIntervalColor().equals("red")) {
                                abGpsTimetableGpsIntervalCell.setCellStyle(rowStyleRedTimeHHmmss);
                            }
                        }
                        //------------------------------------------
                        Cell abGpsTimetableMisconductCell = row.createCell(17);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableMisconductCell.setCellValue(intervalTripPeriod.getMisconduct());

                            //  baGpsTimetableExodusNumberCell.setCellValue("----");
                            Hyperlink hyperlink_BB = workbook.getCreationHelper()
                                    .createHyperlink(HyperlinkType.URL);
                            hyperlink_BB.setAddress(path + "exodus.htm?routeNumber=" + detailedRouteEntry.getValue().getNumber() + "&dateStamp=" + day.getDateStamp() + "&exodusNumber=" + intervalTripPeriod.getExodusNumber() + "&startTimeScheduled=" + intervalTripPeriod.getStartTimeScheduled());
                            abGpsTimetableMisconductCell.setHyperlink((XSSFHyperlink) hyperlink_BB);

                        } else {
                            abGpsTimetableMisconductCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableMisconductCell.setCellStyle(rowStyleWhiteRegular);
                        } else {
                            abGpsTimetableMisconductCell.setCellStyle(rowStyleWhiteRegularLightOn);
                        }
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            if (intervalTripPeriod.getMisconductColor().equals("lightgreen")) {
                                abGpsTimetableMisconductCell.setCellStyle(rowStyleLightGreenRegular);
                            }
                        }

                        //------------------------------------------
                        Cell abGpsTimetableRunOverCell = row.createCell(18);
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            abGpsTimetableRunOverCell.setCellValue(intervalTripPeriod.getRunOver());
                        } else {
                            abGpsTimetableRunOverCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            abGpsTimetableRunOverCell.setCellStyle(rowStyleWhiteRegular);
                        } else {
                            abGpsTimetableRunOverCell.setCellStyle(rowStyleWhiteRegularLightOn);
                        }
                        if (count < abGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                            if (intervalTripPeriod.getRunOverColor().equals("lightgreen")) {
                                abGpsTimetableRunOverCell.setCellStyle(rowStyleLightGreenRegular);
                            }
                        }
                        //-----------------------------------

                        Cell middleOutCell = row.createCell(19);
                        middleOutCell.setCellValue("");
                        IntervalTripPeriod abIntervalTripPeriod = null;
                        IntervalTripPeriod baIntervalTripPeriod = null;
                        if (count < abGpsTimetable.size()) {
                            abIntervalTripPeriod = (IntervalTripPeriod) abGpsTimetable.get(count);
                        }
                        if (count < baGpsTimetable.size()) {
                            baIntervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                        }
                        if ((abIntervalTripPeriod != null && (abIntervalTripPeriod.getMisconductColor().equals("lightgreen") || abIntervalTripPeriod.getRunOverColor().equals("lightgreen")))
                                || (baIntervalTripPeriod != null && (baIntervalTripPeriod.getMisconductColor().equals("lightgreen") || baIntervalTripPeriod.getRunOverColor().equals("lightgreen")))) {
                            middleOutCell.setCellStyle(rowStyleBlueRegular);
                        } else {
                            if (routeLightOn) {
                                middleOutCell.setCellStyle(rowStyleWhiteRegular);
                            } else {
                                middleOutCell.setCellStyle(rowStyleWhiteRegularLightOn);
                            }
                        }

                        // ----------------------------------
                        //+++++++++++++++++++++++++++++++++++
                        //==========================================
                        //------------------------------------------
                        Cell baBusNumberCell = row.createCell(20);
                        if (count < baTimetable.size()) {
                            baBusNumberCell.setCellValue(baTimetable.get(count).getBusNumber());
                        } else {
                            baBusNumberCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baBusNumberCell.setCellStyle(rowStyleWhiteRegular);
                        } else {
                            baBusNumberCell.setCellStyle(rowStyleWhiteRegularLightOn);
                        }

                        Cell baDriverName = row.createCell(21);
                        if (count < baTimetable.size()) {
                            baDriverName.setCellValue(baTimetable.get(count).getDriverName());
                        } else {
                            baBusNumberCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baDriverName.setCellStyle(rowStyleWhiteRegular);
                        } else {
                            baDriverName.setCellStyle(rowStyleWhiteRegularLightOn);
                        }

                        Cell baTimetableScheduledTimetableSequenceNumberCell = row.createCell(22);

                        if (count < baTimetable.size()) {
                            baTimetableScheduledTimetableSequenceNumberCell.setCellValue(baTimetable.get(count).getScheduledTimetableSequenceNumber());
                        } else {
                            baTimetableScheduledTimetableSequenceNumberCell.setCellValue("");
                        }
                        baTimetableScheduledTimetableSequenceNumberCell.setCellStyle(rowStyleWhiteNumberLightBlue);
                        //------------------------------------------
                        Cell baTimetableStartTimeScheduledCell = row.createCell(23);
                        if (count < baTimetable.size()) {
                            baTimetableStartTimeScheduledCell.setCellValue(baTimetable.get(count).getStartTimeScheduledExcelFormat());
                        } else {
                            baTimetableStartTimeScheduledCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        //------------------------------------------
                        Cell baTimetableStartTimeActualCell = row.createCell(24);
                        if (count < baTimetable.size()) {
                            Double startTimeActualExcelFormat = baTimetable.get(count).getStartTimeActualExcelFormat();
                            if (startTimeActualExcelFormat == null) {
                                baTimetableStartTimeActualCell.setCellValue("");
                            } else {
                                baTimetableStartTimeActualCell.setCellValue(startTimeActualExcelFormat);
                            }
                        } else {
                            baTimetableStartTimeActualCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        //------------------------------------------
                        Cell baTimetableExodusNumberCell = row.createCell(25);
                        if (count < baTimetable.size()) {
                            baTimetableExodusNumberCell.setCellValue(baTimetable.get(count).getExodusNumber());

                        } else {
                            baTimetableExodusNumberCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            baTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }
                        //------------------------------------------
                        Cell baGpsTimetableExodusNumberCell = row.createCell(26);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableExodusNumberCell.setCellValue(intervalTripPeriod.getExodusNumber());

                        } else {
                            baGpsTimetableExodusNumberCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            baGpsTimetableExodusNumberCell.setCellStyle(rowStyleWhiteNumberLightOn);
                        }
                        //------------------------------------------
                        Cell baGpsTimetableScheduledTimetableSequenceNumberCell = row.createCell(27);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableScheduledTimetableSequenceNumberCell.setCellValue(intervalTripPeriod.getScheduledTimetableSequenceNumber());
                        } else {
                            baGpsTimetableScheduledTimetableSequenceNumberCell.setCellValue("");
                        }
                        baGpsTimetableScheduledTimetableSequenceNumberCell.setCellStyle(rowStyleWhiteNumberLightBlue);

                        //------------------------------------------
                        Cell baGpsTimetableStartTimeScheduledCell = row.createCell(28);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableStartTimeScheduledCell.setCellValue(intervalTripPeriod.getStartTimeScheduledExcelFormat());
                        } else {
                            baGpsTimetableStartTimeScheduledCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableStartTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        //------------------------------------------
                        Cell baGpsTimetableStartTimeActualCell = row.createCell(29);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableStartTimeActualCell.setCellValue(intervalTripPeriod.getStartTimeActualExcelFormat());
                        } else {
                            baGpsTimetableStartTimeActualCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableStartTimeActualCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        Cell baGpsTimetableStartTimeDifferenceCell = row.createCell(30);
                        if (count < baGpsTimetable.size()) {
                            baGpsTimetableStartTimeDifferenceCell.setCellValue(baGpsTimetable.get(count).getStartTimeDifference());
                        } else {
                            baGpsTimetableStartTimeDifferenceCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        if (count < baGpsTimetable.size()) {
                            if (baGpsTimetable.get(count).getStartTimeDifferenceColor().equals("yellow")) {
                                baGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleYellowTimeHHmmss);
                            }
                            if (baGpsTimetable.get(count).getStartTimeDifferenceColor().equals("red")) {
                                baGpsTimetableStartTimeDifferenceCell.setCellStyle(rowStyleRedTimeHHmmss);
                            }
                        }

                        //------------------------------------------
                        Cell baGpsTimetableHaltTimeScheduledCell = row.createCell(31);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            Double haltTimeActualExcelFormat = intervalTripPeriod.getHaltTimeActualExcelFormat();
                            if (haltTimeActualExcelFormat == null) {
                                baGpsTimetableHaltTimeScheduledCell.setCellValue("");
                            } else {
                                baGpsTimetableHaltTimeScheduledCell.setCellValue(intervalTripPeriod.getHaltTimeActualExcelFormat());
                            }
                        } else {
                            baGpsTimetableHaltTimeScheduledCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableHaltTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableHaltTimeScheduledCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        //------------------------------------------
                        Cell baGpsTimetableLostTimeCell = row.createCell(32);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            if (intervalTripPeriod.getLostTimeExcelFormat() == null) {
                                baGpsTimetableLostTimeCell.setCellValue("");
                            } else {
                                baGpsTimetableLostTimeCell.setCellValue(intervalTripPeriod.getLostTimeExcelFormat());
                            }
                        } else {
                            baGpsTimetableLostTimeCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableLostTimeCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableLostTimeCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        if (count < baGpsTimetable.size()) {
                            if (baGpsTimetable.get(count).getLostTimeColor().equals("yellow")) {
                                baGpsTimetableLostTimeCell.setCellStyle(rowStyleYellowTimeHHmmss);
                            }
                            if (baGpsTimetable.get(count).getLostTimeColor().equals("red")) {
                                baGpsTimetableLostTimeCell.setCellStyle(rowStyleRedTimeHHmmss);
                            }
                        }

                        //------------------------------------------
                        Cell baGpsTimetableScheduledIntervalCell = row.createCell(33);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableScheduledIntervalCell.setCellValue(intervalTripPeriod.getScheduledIntervalString());
                        } else {
                            baGpsTimetableScheduledIntervalCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableScheduledIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableScheduledIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }

                        //------------------------------------------
                        Cell baGpsTimetableGpsIntervalCell = row.createCell(34);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            if (intervalTripPeriod.getGpsIntervalExcelFormat() == null) {
                                baGpsTimetableGpsIntervalCell.setCellValue("");
                            } else {
                                baGpsTimetableGpsIntervalCell.setCellValue(intervalTripPeriod.getGpsIntervalExcelFormat());
                            }
                        } else {
                            baGpsTimetableGpsIntervalCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableGpsIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            baGpsTimetableGpsIntervalCell.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            if (intervalTripPeriod.getGpsIntervalColor().equals("yellow")) {
                                baGpsTimetableGpsIntervalCell.setCellStyle(rowStyleYellowTimeHHmmss);
                            }
                            if (intervalTripPeriod.getGpsIntervalColor().equals("red")) {
                                baGpsTimetableGpsIntervalCell.setCellStyle(rowStyleRedTimeHHmmss);
                            }
                        }
                        //------------------------------------------
                        Cell baGpsTimetableMisconductCell = row.createCell(35);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableMisconductCell.setCellValue(intervalTripPeriod.getMisconduct());

                            //  baGpsTimetableExodusNumberCell.setCellValue("----");
                            Hyperlink hyperlink_BB = workbook.getCreationHelper()
                                    .createHyperlink(HyperlinkType.URL);
                            hyperlink_BB.setAddress(path + "exodus.htm?routeNumber=" + detailedRouteEntry.getValue().getNumber() + "&dateStamp=" + day.getDateStamp() + "&exodusNumber=" + intervalTripPeriod.getExodusNumber() + "&startTimeScheduled=" + intervalTripPeriod.getStartTimeScheduled());
                            baGpsTimetableMisconductCell.setHyperlink((XSSFHyperlink) hyperlink_BB);

                        } else {
                            baGpsTimetableMisconductCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableMisconductCell.setCellStyle(rowStyleWhiteRegular);
                        } else {
                            baGpsTimetableMisconductCell.setCellStyle(rowStyleWhiteRegularLightOn);
                        }
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            if (intervalTripPeriod.getMisconductColor().equals("lightgreen")) {
                                baGpsTimetableMisconductCell.setCellStyle(rowStyleLightGreenRegular);
                            }
                        }

                        //------------------------------------------
                        Cell baGpsTimetableRunOverCell = row.createCell(36);
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            baGpsTimetableRunOverCell.setCellValue(intervalTripPeriod.getRunOver());

                        } else {
                            baGpsTimetableRunOverCell.setCellValue("");
                        }
                        if (routeLightOn) {
                            baGpsTimetableRunOverCell.setCellStyle(rowStyleWhiteRegular);
                        } else {
                            baGpsTimetableRunOverCell.setCellStyle(rowStyleWhiteRegularLightOn);
                        }
                        if (count < baGpsTimetable.size()) {
                            IntervalTripPeriod intervalTripPeriod = (IntervalTripPeriod) baGpsTimetable.get(count);
                            if (intervalTripPeriod.getRunOverColor().equals("lightgreen")) {
                                baGpsTimetableRunOverCell.setCellStyle(rowStyleLightGreenRegular);
                            }
                        }
                        rowIndex++;
                        if (rowIndex % 1000 == 0) {
                            System.out.println(rowIndex + " rows has been written.");
                            memoryUsage.printMemoryUsage();
                            double averLoad = ManagementFactory.getPlatformMXBean(
                                    com.sun.management.OperatingSystemMXBean.class).getSystemLoadAverage();
                            System.out.println("Average Load:" + averLoad);
                            double cpuLoad = ManagementFactory.getPlatformMXBean(
                                    com.sun.management.OperatingSystemMXBean.class).getSystemCpuLoad();
                            System.out.println("CPU Load:" + cpuLoad);

                        }
                    }
                    //-----------------------
                    //++++++++++++++++++++++++++
                    Row dayChangerRow = sheet.createRow(rowIndex++);
                    for (int a = 0; a < 20; a++) {
                        Cell cell = dayChangerRow.createCell(a);
                        cell.setCellValue("====");
                    }
                }
            }

            workbook.write(os);
            System.out.println("++++Intervals Excel Writing Completed++++");

            LOGGER.info("Time needed:" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void SXSSF_Misconducts(ArrayList<MisconductTripPeriod> misconductTripPeriods, ArrayList<FirstTripPeriod> misconductedFirstTripPeriods, ArrayList<FirstTripPeriod> misconductedFirstTripPeriodsMinusVersion, String fileName, HttpServletRequest request) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {

//setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("ხაზზე დარღვევები");
            String path;
            path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

            //  int rowIndex = 0;
            //------------------------------------------------------------------
            int rowIndex = 0;
            int columnIndex = 0;
            int rowHeigth = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 14) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 1000;
                        break;
                    case 2:
                        columnWidth = 1500;
                        break;
                    case 3:
                        columnWidth = 1000;
                        break;
                    case 4:
                        columnWidth = 3000;
                        break;
                    case 5:
                        columnWidth = 2000;
                        break;
                    case 6:
                        columnWidth = 7000;
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
                        columnWidth = 1500;
                        break;
                    case 12:
                        columnWidth = 2500;
                        break;
                    case 13:
                        columnWidth = 1500;
                        break;

                }
                sheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            //now headers
            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 255, 255, 0, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 255, 255, 0, 90, false);
            XSSFCellStyle headerStyleWhite = getHeaderStyle(workbook, 255, 255, 255, 0, false);

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteRegularLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "");
            XSSFCellStyle rowStylePurpleRegular = getRowStyle(workbook, 191, 0, 255, false, false, "");

            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightBlue = getRowStyle(workbook, 63, 219, 203, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStylePurpleNumber = getRowStyle(workbook, 191, 0, 255, false, false, "0"); //"0" makes cell numeric type

            XSSFCellStyle rowStyleWhiteTimeHHmm = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm");
            XSSFCellStyle rowStyleWhiteTimeHHmmLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "[hh]:mm");
            XSSFCellStyle rowStylePurpleTimeHHmm = getRowStyle(workbook, 191, 0, 255, false, false, "[hh]:mm");

            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleWhiteTimeHHmmssLightOn = getRowStyle(workbook, 240, 240, 240, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleYellowTimeHHmmss = getRowStyle(workbook, 255, 255, 0, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStylePurpleTimeHHmmss = getRowStyle(workbook, 191, 0, 255, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleLightGreenRegular = getRowStyle(workbook, 144, 238, 144, false, false, "");
            XSSFCellStyle rowStyleBlueRegular = getRowStyle(workbook, 0, 0, 255, false, false, "");

//first header row
            Row headerRow = sheet.createRow(rowIndex);
            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow.createCell(columnIndex);
                        cell_0.setCellValue("თარიღი");
                        cell_0.setCellStyle(headerStyle);
                        break;
                    case 1:
                        Cell cell_1 = headerRow.createCell(columnIndex);
                        cell_1.setCellValue("ავტობაზა");
                        cell_1.setCellStyle(headerStyleVertical);
                        break;
                    case 2:
                        Cell cell_2 = headerRow.createCell(columnIndex);
                        cell_2.setCellValue("მარშრუტის #");
                        cell_2.setCellStyle(headerStyleVertical);
                        break;
                    case 3:
                        Cell cell_3 = headerRow.createCell(columnIndex);
                        cell_3.setCellValue("გასვლის #");
                        cell_3.setCellStyle(headerStyleVertical);
                        break;
                    case 4:
                        Cell cell_4 = headerRow.createCell(columnIndex);
                        cell_4.setCellValue("ავტობუსის #");
                        cell_4.setCellStyle(headerStyle);
                        break;
                    case 5:
                        Cell cell_5 = headerRow.createCell(columnIndex);
                        cell_5.setCellValue("მძღოლის ტაბელი");
                        cell_5.setCellStyle(headerStyleVertical);
                        break;
                    case 6:
                        Cell cell_6 = headerRow.createCell(columnIndex);
                        cell_6.setCellValue("მძღოლი");
                        cell_6.setCellStyle(headerStyle);
                        break;
                    case 7:
                        Cell cell_7 = headerRow.createCell(columnIndex);
                        cell_7.setCellValue("პუნქტში მისვლის დრო");
                        cell_7.setCellStyle(headerStyleVertical);
                        break;
                    case 8:
                        Cell cell_8 = headerRow.createCell(columnIndex);
                        cell_8.setCellValue("ხაზზე გასვლის დრო გეგმიური");
                        cell_8.setCellStyle(headerStyleVertical);
                        break;
                    case 9:
                        Cell cell_9 = headerRow.createCell(columnIndex);
                        cell_9.setCellValue("ხაზზე გასვლის დრო ფაქტიური");
                        cell_9.setCellStyle(headerStyleVertical);
                        break;
                    case 10:
                        Cell cell_10 = headerRow.createCell(columnIndex);
                        cell_10.setCellValue("წინა მანქანის ხაზზე გასვლის დრო");
                        cell_10.setCellStyle(headerStyleVertical);
                        break;

                    case 11:
                        Cell cell_11 = headerRow.createCell(columnIndex);
                        cell_11.setCellValue("დარღვევა");
                        cell_11.setCellStyle(headerStyleVertical);
                        break;
                    case 12:
                        Cell cell_12 = headerRow.createCell(columnIndex);
                        cell_12.setCellValue("შესადავებელი დრო");
                        cell_12.setCellStyle(headerStyleVertical);
                        break;
                    case 13:
                        Cell cell_13 = headerRow.createCell(columnIndex);
                        cell_13.setCellValue("გადასწრება");
                        cell_13.setCellStyle(headerStyleVertical);
                        break;
                }
                columnIndex++;
            }

            //now rows
            rowIndex++;
            for (MisconductTripPeriod misconductTripPeriod : misconductTripPeriods) {
                Row row = sheet.createRow(rowIndex);

                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(misconductTripPeriod.getDateStamp());
                cell_0.setCellStyle(rowStyleWhiteRegular);

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(misconductTripPeriod.getBaseNumber());
                cell_1.setCellStyle(rowStyleWhiteNumber);

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(misconductTripPeriod.getRouteNumber());
                cell_2.setCellStyle(rowStyleWhiteNumber);

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue(misconductTripPeriod.getExodusNumber());
                cell_3.setCellStyle(rowStyleWhiteNumber);

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(misconductTripPeriod.getBusNumber());
                cell_4.setCellStyle(rowStyleWhiteNumber);

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(misconductTripPeriod.getDriverNumber());
                cell_5.setCellStyle(rowStyleWhiteNumber);

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(misconductTripPeriod.getDriverName());
                cell_6.setCellStyle(rowStyleWhiteNumber);

                Cell cell_7 = row.createCell(7);
                cell_7.setCellValue(misconductTripPeriod.getPreviousTripPeriodArrvialTimeActualString());
                cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(misconductTripPeriod.getStartTimeScheduledString());
                cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_9 = row.createCell(9);
                cell_9.setCellValue(misconductTripPeriod.getStartTimeActualString());
                cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_10 = row.createCell(10);
                cell_10.setCellValue(misconductTripPeriod.getPreviousBusStartTimeActualString());
                cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_11 = row.createCell(11);
                cell_11.setCellValue(misconductTripPeriod.getMisconduct());
                cell_11.setCellStyle(rowStyleWhiteRegular);

                Cell cell_12 = row.createCell(12);
                if (misconductTripPeriod.getMisconductDurationExcelFormat() == null) {
                    cell_12.setCellValue("");
                } else {
                    cell_12.setCellValue(misconductTripPeriod.getMisconductDurationExcelFormat());
                }
                cell_12.setCellStyle(rowStyleWhiteTimeHHmmss);

                Cell cell_13 = row.createCell(13);
                cell_13.setCellValue(misconductTripPeriod.getRunOver());
                cell_13.setCellStyle(rowStyleWhiteRegular);

                rowIndex++;
            }

            //now second sheet
            rowIndex = 0;
            Sheet sheet_2 = workbook.createSheet("ავტობაზის დარღვევები");

            columnIndex = 0;
            while (columnIndex < 14) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 1000;
                        break;
                    case 2:
                        columnWidth = 1500;
                        break;
                    case 3:
                        columnWidth = 1000;
                        break;
                    case 4:
                        columnWidth = 3000;
                        break;
                    case 5:
                        columnWidth = 1500;
                        break;
                    case 6:
                        columnWidth = 1500;
                        break;
                    case 7:
                        columnWidth = 2800;
                        break;
                    case 8:
                        columnWidth = 1500;
                        break;
                    case 9:
                        columnWidth = 1500;
                        break;
                    case 10:
                        columnWidth = 2800;
                        break;

                }
                sheet_2.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            Row headerRow_2 = sheet_2.createRow(rowIndex);

            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow_2.createCell(columnIndex);
                        cell_0.setCellValue("თარიღი");
                        cell_0.setCellStyle(headerStyle);
                        break;
                    case 1:
                        Cell cell_1 = headerRow_2.createCell(columnIndex);
                        cell_1.setCellValue("ავტობაზა");
                        cell_1.setCellStyle(headerStyleVertical);
                        break;
                    case 2:
                        Cell cell_2 = headerRow_2.createCell(columnIndex);
                        cell_2.setCellValue("მარშრუტის #");
                        cell_2.setCellStyle(headerStyleVertical);
                        break;
                    case 3:
                        Cell cell_3 = headerRow_2.createCell(columnIndex);
                        cell_3.setCellValue("გასვლის #");
                        cell_3.setCellStyle(headerStyleVertical);
                        break;
                    case 4:
                        Cell cell_4 = headerRow_2.createCell(columnIndex);
                        cell_4.setCellValue("ავტობუსის #");
                        cell_4.setCellStyle(headerStyle);
                        break;

                    case 5:
                        Cell cell_5 = headerRow_2.createCell(columnIndex);
                        cell_5.setCellValue("გეგმიუირი გასვლის დრო");
                        cell_5.setCellStyle(headerStyleVertical);
                        break;
                    case 6:
                        Cell cell_6 = headerRow_2.createCell(columnIndex);
                        cell_6.setCellValue("ფაქტიური გასვლის დრო");
                        cell_6.setCellStyle(headerStyleVertical);
                        break;
                    case 7:
                        Cell cell_7 = headerRow_2.createCell(columnIndex);
                        cell_7.setCellValue("სხვაობა");
                        cell_7.setCellStyle(headerStyleVertical);
                        break;

                    case 8:
                        Cell cell_8 = headerRow_2.createCell(columnIndex);
                        cell_8.setCellValue("ბაზიდან გეგმიუირი გასვლის დრო");
                        cell_8.setCellStyle(headerStyleVertical);
                        break;
                    case 9:
                        Cell cell_9 = headerRow_2.createCell(columnIndex);
                        cell_9.setCellValue("ბაზიდან ფაქტიური გასვლის დრო");
                        cell_9.setCellStyle(headerStyleVertical);
                        break;
                    case 10:
                        Cell cell_10 = headerRow_2.createCell(columnIndex);
                        cell_10.setCellValue("სხვაობა");
                        cell_10.setCellStyle(headerStyleVertical);
                        break;
                }
                columnIndex++;
            }

            //now rows
            rowIndex++;
            for (FirstTripPeriod misconductTripPeriod : misconductedFirstTripPeriods) {
                Row row = sheet_2.createRow(rowIndex);

                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(misconductTripPeriod.getDateStamp());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_0.setCellStyle(rowStylePurpleRegular);
                } else {
                    cell_0.setCellStyle(rowStyleWhiteRegular);
                }

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(misconductTripPeriod.getBaseNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_1.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_1.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(misconductTripPeriod.getRouteNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_2.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_2.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue(misconductTripPeriod.getExodusNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_3.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_3.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(misconductTripPeriod.getBusNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_4.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_4.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(misconductTripPeriod.getStartTimeScheduledString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_5.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_5.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(misconductTripPeriod.getStartTimeActualString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_6.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_6.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_7 = row.createCell(7);
                cell_7.setCellValue(misconductTripPeriod.getStartTimeDifferenceExcelFormat());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_7.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(misconductTripPeriod.getBaseTripStartTimeScheduledString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_8.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_9 = row.createCell(9);
                cell_9.setCellValue(misconductTripPeriod.getBaseTripStartTimeActualString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_9.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_10 = row.createCell(10);
                cell_10.setCellValue(misconductTripPeriod.getBaseTripStartTimeDifferenceExcelFormat());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_10.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                rowIndex++;
            }

            ///now third sheet
            //now second sheet
            rowIndex = 0;
            Sheet sheet_3 = workbook.createSheet("ავტობაზის დარღვევები(ნაადრევად გასვლების ჩათვლით)");

            columnIndex = 0;
            while (columnIndex < 14) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 1000;
                        break;
                    case 2:
                        columnWidth = 1500;
                        break;
                    case 3:
                        columnWidth = 1000;
                        break;
                    case 4:
                        columnWidth = 3000;
                        break;
                    case 5:
                        columnWidth = 1500;
                        break;
                    case 6:
                        columnWidth = 1500;
                        break;
                    case 7:
                        columnWidth = 2800;
                        break;
                    case 8:
                        columnWidth = 1500;
                        break;
                    case 9:
                        columnWidth = 1500;
                        break;
                    case 10:
                        columnWidth = 2800;
                        break;

                }
                sheet_3.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }

            Row headerRow_3 = sheet_3.createRow(rowIndex);

            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow_3.createCell(columnIndex);
                        cell_0.setCellValue("თარიღი");
                        cell_0.setCellStyle(headerStyle);
                        break;
                    case 1:
                        Cell cell_1 = headerRow_3.createCell(columnIndex);
                        cell_1.setCellValue("ავტობაზა");
                        cell_1.setCellStyle(headerStyleVertical);
                        break;
                    case 2:
                        Cell cell_2 = headerRow_3.createCell(columnIndex);
                        cell_2.setCellValue("მარშრუტის #");
                        cell_2.setCellStyle(headerStyleVertical);
                        break;
                    case 3:
                        Cell cell_3 = headerRow_3.createCell(columnIndex);
                        cell_3.setCellValue("გასვლის #");
                        cell_3.setCellStyle(headerStyleVertical);
                        break;
                    case 4:
                        Cell cell_4 = headerRow_3.createCell(columnIndex);
                        cell_4.setCellValue("ავტობუსის #");
                        cell_4.setCellStyle(headerStyle);
                        break;

                    case 5:
                        Cell cell_5 = headerRow_3.createCell(columnIndex);
                        cell_5.setCellValue("გეგმიუირი გასვლის დრო");
                        cell_5.setCellStyle(headerStyleVertical);
                        break;
                    case 6:
                        Cell cell_6 = headerRow_3.createCell(columnIndex);
                        cell_6.setCellValue("ფაქტიური გასვლის დრო");
                        cell_6.setCellStyle(headerStyleVertical);
                        break;
                    case 7:
                        Cell cell_7 = headerRow_3.createCell(columnIndex);
                        cell_7.setCellValue("სხვაობა");
                        cell_7.setCellStyle(headerStyleVertical);
                        break;

                    case 8:
                        Cell cell_8 = headerRow_3.createCell(columnIndex);
                        cell_8.setCellValue("ბაზიდან გეგმიუირი გასვლის დრო");
                        cell_8.setCellStyle(headerStyleVertical);
                        break;
                    case 9:
                        Cell cell_9 = headerRow_3.createCell(columnIndex);
                        cell_9.setCellValue("ბაზიდან ფაქტიური გასვლის დრო");
                        cell_9.setCellStyle(headerStyleVertical);
                        break;
                    case 10:
                        Cell cell_10 = headerRow_3.createCell(columnIndex);
                        cell_10.setCellValue("სხვაობა");
                        cell_10.setCellStyle(headerStyleVertical);
                        break;
                }
                columnIndex++;
            }

            //now rows
            rowIndex++;
            for (FirstTripPeriod misconductTripPeriod : misconductedFirstTripPeriodsMinusVersion) {
                Row row = sheet_3.createRow(rowIndex);

                Cell cell_0 = row.createCell(0);
                cell_0.setCellValue(misconductTripPeriod.getDateStamp());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_0.setCellStyle(rowStylePurpleRegular);
                } else {
                    cell_0.setCellStyle(rowStyleWhiteRegular);
                }

                Cell cell_1 = row.createCell(1);
                cell_1.setCellValue(misconductTripPeriod.getBaseNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_1.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_1.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_2 = row.createCell(2);
                cell_2.setCellValue(misconductTripPeriod.getRouteNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_2.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_2.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_3 = row.createCell(3);
                cell_3.setCellValue(misconductTripPeriod.getExodusNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_3.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_3.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_4 = row.createCell(4);
                cell_4.setCellValue(misconductTripPeriod.getBusNumber());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_4.setCellStyle(rowStylePurpleNumber);
                } else {
                    cell_4.setCellStyle(rowStyleWhiteNumber);
                }

                Cell cell_5 = row.createCell(5);
                cell_5.setCellValue(misconductTripPeriod.getStartTimeScheduledString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_5.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_5.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_6 = row.createCell(6);
                cell_6.setCellValue(misconductTripPeriod.getStartTimeActualString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_6.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_6.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_7 = row.createCell(7);
                cell_7.setCellValue(misconductTripPeriod.getStartTimeDifferenceExcelFormat());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_7.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_7.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_8 = row.createCell(8);
                cell_8.setCellValue(misconductTripPeriod.getBaseTripStartTimeScheduledString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_8.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_9 = row.createCell(9);
                cell_9.setCellValue(misconductTripPeriod.getBaseTripStartTimeActualString());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_9.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                Cell cell_10 = row.createCell(10);
                cell_10.setCellValue(misconductTripPeriod.getBaseTripStartTimeDifferenceExcelFormat());
                if (misconductTripPeriod.isBrokenExodus()) {
                    cell_10.setCellStyle(rowStylePurpleTimeHHmmss);
                } else {
                    cell_10.setCellStyle(rowStyleWhiteTimeHHmmss);
                }

                rowIndex++;
            }

            workbook.write(os);
            System.out.println("++++Intervals Excel Writing Completed++++");

            LOGGER.info("Time needed:" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void SXSSF_GuarantyTrips(ArrayList<ArrayList<GuarantyTripsData>> guarantyData, String fileName, HttpServletRequest request) {
        long begin = System.currentTimeMillis();
        Logger LOGGER = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());

        // keep 100 rows in memory, exceeding rows will be flushed to disk
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100);
                OutputStream os = new FileOutputStream(this.basementDirectory + "/downloads/" + fileName + ".xlsx")) {

//setting date 1904 system (to show negative duration in excel workbook)
            workbook.getXSSFWorkbook().getCTWorkbook().getWorkbookPr().setDate1904(true);

            Sheet sheet = workbook.createSheet("საგარანტიო გასვლები");
            String path;
            path = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

            //  int rowIndex = 0;
            //------------------------------------------------------------------
            int rowIndex = 0;
            int columnIndex = 0;
            int rowHeigth = 0;
            int columnWidth = 0;

            //column width
            while (columnIndex < 14) {
                switch (columnIndex) {
                    case 0:
                        columnWidth = 3000;
                        break;
                    case 1:
                        columnWidth = 1000;
                        break;
                    case 2:
                        columnWidth = 1500;
                        break;
                    case 3:
                        columnWidth = 7000;
                        break;
                    case 4:
                        columnWidth = 7000;
                        break;
                    case 5:
                        columnWidth = 2000;
                        break;
                    case 6:
                        columnWidth = 5000;
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
                        columnWidth = 1500;
                        break;
                    case 11:
                        columnWidth = 1500;
                        break;
                    case 12:
                        columnWidth = 7000;
                        break;
                    case 13:
                        columnWidth = 3500;
                        break;

                }
                sheet.setColumnWidth(columnIndex, columnWidth);
                columnIndex++;
            }
            //now headers
            XSSFCellStyle headerStyle = getHeaderStyle(workbook, 74, 229, 55, 0, false);
            XSSFCellStyle headerStyleVertical = getHeaderStyle(workbook, 74, 229, 55, 90, false);

            //  XSSFCellStyle rowStyleWhiteItalic = getRowStyle(workbook, 255, 255, 255, true, false, "");
            XSSFCellStyle rowStyleWhiteRegular = getRowStyle(workbook, 255, 255, 255, false, false, "");
            XSSFCellStyle rowStyleWhiteRegularLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "");

            XSSFCellStyle rowStyleWhiteNumber = getRowStyle(workbook, 255, 255, 255, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleWhiteNumberLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "0"); //"0" makes cell numeric type
            XSSFCellStyle rowStyleRedNumber = getRowStyle(workbook, 255, 0, 0, false, false, "0");

            XSSFCellStyle rowStyleWhiteTimeHHmmss = getRowStyle(workbook, 255, 255, 255, false, false, "[hh]:mm:ss");
            XSSFCellStyle rowStyleWhiteTimeHHmmssLightOn = getRowStyle(workbook, 220, 220, 220, false, false, "[hh]:mm:ss");

            XSSFCellStyle rowStyleRedTimeHHmmss = getRowStyle(workbook, 255, 0, 0, false, false, "[hh]:mm:ss");

//first header row
            Row headerRow = sheet.createRow(rowIndex);
            columnIndex = 0;
            while (columnIndex < 37) {

                switch (columnIndex) {
                    case 0:
                        Cell cell_0 = headerRow.createCell(columnIndex);
                        cell_0.setCellValue("თარიღი");
                        cell_0.setCellStyle(headerStyle);
                        break;
                    case 1:
                        Cell cell_1 = headerRow.createCell(columnIndex);
                        cell_1.setCellValue("ავტობაზა");
                        cell_1.setCellStyle(headerStyleVertical);
                        break;
                    case 2:
                        Cell cell_2 = headerRow.createCell(columnIndex);
                        cell_2.setCellValue("მარშრუტის #");
                        cell_2.setCellStyle(headerStyleVertical);
                        break;

                    case 3:
                        Cell cell_3 = headerRow.createCell(columnIndex);
                        cell_3.setCellValue("A პუნქტი");
                        cell_3.setCellStyle(headerStyle);
                        break;
                    case 4:
                        Cell cell_4 = headerRow.createCell(columnIndex);
                        cell_4.setCellValue("B პუნქტი");
                        cell_4.setCellStyle(headerStyle);
                        break;
                    case 5:
                        Cell cell_5 = headerRow.createCell(columnIndex);
                        cell_5.setCellValue("მიმართულება");
                        cell_5.setCellStyle(headerStyleVertical);
                        break;
                    case 6:
                        Cell cell_6 = headerRow.createCell(columnIndex);
                        cell_6.setCellValue("საგარანტიო გასვლის ტიპი");
                        cell_6.setCellStyle(headerStyle);
                        break;
                    case 7:
                        Cell cell_7 = headerRow.createCell(columnIndex);
                        cell_7.setCellValue("გეგმიური გასვლის დრო");
                        cell_7.setCellStyle(headerStyleVertical);
                        break;
                    case 8:
                        Cell cell_8 = headerRow.createCell(columnIndex);
                        cell_8.setCellValue("ფაქტიური გასვლის დრო");
                        cell_8.setCellStyle(headerStyleVertical);
                        break;
                    case 9:
                        Cell cell_9 = headerRow.createCell(columnIndex);
                        cell_9.setCellValue("სხვაობა");
                        cell_9.setCellStyle(headerStyleVertical);
                        break;
                    case 10:
                        Cell cell_10 = headerRow.createCell(columnIndex);
                        cell_10.setCellValue("გეგმიური გასვლის ნომერი");
                        cell_10.setCellStyle(headerStyleVertical);
                        break;

                    case 11:
                        Cell cell_11 = headerRow.createCell(columnIndex);
                        cell_11.setCellValue("ფაქტიური გასვლის ნომერი");
                        cell_11.setCellStyle(headerStyleVertical);
                        break;
                    case 12:
                        Cell cell_12 = headerRow.createCell(columnIndex);
                        cell_12.setCellValue("ფაქტიური გასვლის მძღოლი");
                        cell_12.setCellStyle(headerStyle);
                        break;
                    case 13:
                        Cell cell_13 = headerRow.createCell(columnIndex);
                        cell_13.setCellValue("ფაქტიური გასვლის სახ. ნომერი");
                        cell_13.setCellStyle(headerStyleVertical);
                        break;
                }
                columnIndex++;
            }
            //--------------------
            //now rows

            rowIndex++;
            int dayIndex = 0;
            for (ArrayList<GuarantyTripsData> guarantyTripsDataDayArray : guarantyData) {
                for (GuarantyTripsData guarantyTripsData : guarantyTripsDataDayArray) {
                    Row row = sheet.createRow(rowIndex);
                    Cell cell_0 = row.createCell(0);
                    cell_0.setCellValue(guarantyTripsData.getDateStamp());
                    if (dayIndex % 2 == 0) {
                        cell_0.setCellStyle(rowStyleWhiteRegular);
                    } else {
                        cell_0.setCellStyle(rowStyleWhiteRegularLightOn);
                    }

                    Cell cell_1 = row.createCell(1);
                    cell_1.setCellValue(guarantyTripsData.getBaseNumber());
                    if (dayIndex % 2 == 0) {
                        cell_1.setCellStyle(rowStyleWhiteRegular);
                    } else {
                        cell_1.setCellStyle(rowStyleWhiteRegularLightOn);
                    }

                    Cell cell_2 = row.createCell(2);
                    cell_2.setCellValue(this.converter.convertRouteNumber(guarantyTripsData.getRouteNumber()));
                    if (dayIndex % 2 == 0) {
                        cell_2.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_2.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_3 = row.createCell(3);
                    cell_3.setCellValue(guarantyTripsData.getaPoint());
                    if (dayIndex % 2 == 0) {
                        cell_3.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_3.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_4 = row.createCell(4);
                    cell_4.setCellValue(guarantyTripsData.getbPoint());
                    if (dayIndex % 2 == 0) {
                        cell_4.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_4.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_5 = row.createCell(5);
                    cell_5.setCellValue(guarantyTripsData.getTypeG());
                    if (dayIndex % 2 == 0) {
                        cell_5.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_5.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_6 = row.createCell(6);
                    cell_6.setCellValue(guarantyTripsData.getGuarantyType());
                    if (dayIndex % 2 == 0) {
                        cell_6.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_6.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_7 = row.createCell(7);
                    cell_7.setCellValue(guarantyTripsData.getGuarantyStartTimeScheduledString());
                    if (dayIndex % 2 == 0) {
                        cell_7.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_7.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_8 = row.createCell(8);
                    cell_8.setCellValue(guarantyTripsData.getGuarantyStartTimeActualString());
                    if (guarantyTripsData.isSpacialCase()) {
                        cell_8.setCellStyle(rowStyleRedTimeHHmmss);
                    } else {
                        if (dayIndex % 2 == 0) {
                            cell_8.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            cell_8.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                    }

                    Cell cell_9 = row.createCell(9);
                    cell_9.setCellValue(guarantyTripsData.getGuarantyStartTimeDifferenceString());
                    if (guarantyTripsData.getGuarantyDifferenceColor().equals("red")) {
                        cell_9.setCellStyle(rowStyleRedTimeHHmmss);
                    } else {
                        if (dayIndex % 2 == 0) {
                            cell_9.setCellStyle(rowStyleWhiteTimeHHmmss);
                        } else {
                            cell_9.setCellStyle(rowStyleWhiteTimeHHmmssLightOn);
                        }
                    }

                    Cell cell_10 = row.createCell(10);
                    cell_10.setCellValue(guarantyTripsData.getExodusScheduled());
                    if (dayIndex % 2 == 0) {
                        cell_10.setCellStyle(rowStyleWhiteNumber);
                    } else {
                        cell_10.setCellStyle(rowStyleWhiteNumberLightOn);
                    }

                    Cell cell_11 = row.createCell(11);
                    if (guarantyTripsData.getExodusActual() == 0) {
                        cell_11.setCellValue("");
                    } else {
                        cell_11.setCellValue(guarantyTripsData.getExodusActual());
                    }
                    if (guarantyTripsData.getExodusScheduled() != guarantyTripsData.getExodusActual()) {
                        cell_11.setCellStyle(rowStyleRedNumber);
                    } else {
                        if (dayIndex % 2 == 0) {
                            cell_11.setCellStyle(rowStyleWhiteNumber);
                        } else {
                            cell_11.setCellStyle(rowStyleWhiteNumberLightOn);
                        }
                    }

                    Cell cell_12 = row.createCell(12);
                    cell_12.setCellValue(guarantyTripsData.getDriverName());
                    if (dayIndex % 2 == 0) {
                        cell_12.setCellStyle(rowStyleWhiteRegular);
                    } else {
                        cell_12.setCellStyle(rowStyleWhiteRegularLightOn);
                    }

                    Cell cell_13 = row.createCell(13);
                    cell_13.setCellValue(guarantyTripsData.getBusNumber());
                    if (dayIndex % 2 == 0) {
                        cell_13.setCellStyle(rowStyleWhiteRegular);
                    } else {
                        cell_13.setCellStyle(rowStyleWhiteRegularLightOn);
                    }
                    rowIndex++;
                }
                dayIndex++;
            }
//--------------------------
            workbook.write(os);
            System.out.println("++++Guaranty Trips Excel Writing Completed++++");

            LOGGER.info("Time needed:" + (System.currentTimeMillis() - begin) / 1000);

        } catch (IOException ex) {
            Logger.getLogger(ExcelWriter.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
