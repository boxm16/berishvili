/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Day;
import Model.Exodus;
import Model.GuarantyExodus;
import Model.GuarantyRoute;
import Model.GuarantyTripPeriod;
import Model.Route;
import Model.RouteData;
import Model.TripPeriod;
import Model.TripVoucher;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RouteFactory {

    private Converter converter;
    private MemoryUsage mu;
    private BasementController basementController;

    public RouteFactory() {
        this.converter = new Converter();
        this.mu = new MemoryUsage();
        this.basementController = new BasementController();
    }

    public TreeMap<Float, Route> createRoutesFromUploadedFile() {
        TreeMap<Float, Route> routes = new TreeMap<>();
        try {

            String filePath = this.basementController.getBasementDirectory() + "/uploads/uploadedExcelFile.xlsx";
            ExcelReader excelReader = new ExcelReader();
            HashMap<String, String> data = excelReader.getCellsFromExcelFile(filePath);
            routes = convertExcelDataToRoutes(data);
        } catch (Exception ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return routes;
    }

    private TreeMap<Float, Route> convertExcelDataToRoutes(HashMap<String, String> data) {
        TreeMap<Float, Route> routes = new TreeMap<>();
        int rowIndex = 8;
        while (!data.isEmpty()) {
            String routeNumberLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
            String routeNumberString = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumberString == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            Float routeNumberFloat = this.converter.convertRouteNumber(routeNumberString);
            Route route;
            if (routes.containsKey(routeNumberFloat)) {
                route = routes.get(routeNumberFloat);
            } else {
                route = new Route();
            }
            route.setNumber(routeNumberString);
            route = addRowElementsToRoute(route, data, rowIndex);
            routes.put(routeNumberFloat, route);
            if (rowIndex % 1000 == 0) {
                System.out.print("RowIndex:" + rowIndex + " #");
                mu.printMemoryUsage();
            }
            rowIndex++;
        }

        /*
        for (Map.Entry<Float, Route> routeEntry : routes.entrySet()) {
            Route route = routeEntry.getValue();
            System.out.println("RouteNumber:" + route);

            TreeMap<Date, Day> days = route.getDays();
            for (Map.Entry<Date, Day> dayEntry : days.entrySet()) {
                Day day = dayEntry.getValue();
                System.out.println("Date Stamp:" + day.getDateStamp());

                TreeMap<Short, Exodus> exoduses = day.getExoduses();
                for (Map.Entry<Short, Exodus> exodusEntry : exoduses.entrySet()) {
                    Exodus exodus = exodusEntry.getValue();
                    System.out.println("Exodus Number:" + exodus.getNumber());

                    TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
                    for (Map.Entry<String, TripVoucher> tripVoucherEntry : tripVouchers.entrySet()) {
                        TripVoucher tripVoucher = tripVoucherEntry.getValue();
                        System.out.println("TripVoucher Number:" + tripVoucher.getNumber());
                    }
                }

            }

        }
         */
        return routes;
    }

    private Route addRowElementsToRoute(Route route, HashMap<String, String> data, int rowIndex) {
        //----first baseNumber
        String baseNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
        short baseNumber = Short.valueOf(data.remove(baseNumberLocationInTheRow));
        route.setBaseNumber(baseNumber);
        //now see the date
        String dateStampLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
        String dateStampExcelFormat = data.remove(dateStampLocationInTheRow);
        Date date = this.converter.convertDateStampExcelFormatToDate(dateStampExcelFormat);
        String dateStamp = this.converter.convertDateStampExcelFormatToDatabaseFormat(dateStampExcelFormat);

        TreeMap<Date, Day> days = route.getDays();
        Day day;
        if (days.containsKey(date)) {
            day = days.get(date);
        } else {
            day = new Day();
        }
        day.setDateStamp(dateStamp);
        day = addRowElementsToDay(day, data, rowIndex);
        days.put(date, day);

        route.setDays(days);
        return route;
    }

    private Day addRowElementsToDay(Day day, HashMap<String, String> data, int rowIndex) {
        String exodusNumberLocationInTheRow = new StringBuilder("I").append(String.valueOf(rowIndex)).toString();
        short exodusNumber = Float.valueOf(data.remove(exodusNumberLocationInTheRow)).shortValue();
        TreeMap<Short, Exodus> exoduses = day.getExoduses();
        Exodus exodus;
        if (exoduses.containsKey(exodusNumber)) {
            exodus = exoduses.get(exodusNumber);
        } else {
            exodus = new Exodus();
        }
        exodus.setNumber(exodusNumber);
        exodus = addRowElementsToExodus(exodus, data, rowIndex);
        exoduses.put(exodusNumber, exodus);
        day.setExoduses(exoduses);
        return day;
    }

    private Exodus addRowElementsToExodus(Exodus exodus, HashMap<String, String> data, int rowIndex) {
        String tripVoucherNumberLocationInTheRow = new StringBuilder("G").append(String.valueOf(rowIndex)).toString();
        String tripVoucherNumber = data.remove(tripVoucherNumberLocationInTheRow);
        TreeMap<String, TripVoucher> tripVouchers = exodus.getTripVouchers();
        TripVoucher tripVoucher;
        if (tripVouchers.containsKey(tripVoucherNumber)) {
            tripVoucher = tripVouchers.get(tripVoucherNumber);
        } else {
            tripVoucher = new TripVoucher();
            String busNumberLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String busrNumber = data.remove(busNumberLocationInTheRow);

            String busTypeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String busType = data.remove(busTypeLocationInTheRow);

            String driverNumberLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String driverNumber = data.remove(driverNumberLocationInTheRow);

            String driverNameLocationInTheRow = new StringBuilder("E").append(String.valueOf(rowIndex)).toString();
            String driverName = data.remove(driverNameLocationInTheRow);

            String notesLocationInTheRow = new StringBuilder("AC").append(String.valueOf(rowIndex)).toString();
            String notes = data.remove(notesLocationInTheRow);
            tripVoucher.setBusNumber(busrNumber);
            tripVoucher.setBusType(busType);
            tripVoucher.setDriverNumber(driverNumber);
            tripVoucher.setDriverName(driverName);
            tripVoucher.setNotes(notes);

        }
        tripVoucher.setNumber(tripVoucherNumber);
        //add another elements
        tripVoucher = addRowElementsToTripVoucher(tripVoucher, data, rowIndex);
        tripVouchers.put(tripVoucherNumber, tripVoucher);
        exodus.setTripVouchers(tripVouchers);
        return exodus;
    }

    private TripVoucher addRowElementsToTripVoucher(TripVoucher tripVoucher, HashMap<String, String> data, int rowIndex) {
        String tripPeriodDescriptionLocationInTheRow = new StringBuilder("P").append(String.valueOf(rowIndex)).toString();
        String tripPeriodDescription = data.remove(tripPeriodDescriptionLocationInTheRow);
        String tripPeriodType = getTripPeriodTypeFromTripDescription(tripPeriodDescription);
        ArrayList<TripPeriod> tripPeriods = tripVoucher.getTripPeriods();
        TripPeriod tripPeriod;
        switch (tripPeriodType) {
            case "baseLeaving":
                tripPeriod = createBaseLeavingPeriod(data, rowIndex);
                tripPeriods.add(tripPeriod);
                break;
            case "baseReturn":
                tripPeriod = createBaseReturnPeriod(data, rowIndex);
                tripPeriods.add(tripPeriod);
                break;
            case "break":
                tripPeriod = createBreakPeriod(data, rowIndex);
                tripPeriods.add(tripPeriod);
                break;
            case "round":
                ArrayList<TripPeriod> tripPeriodsOfRound = createTripPeridsOfRound(data, rowIndex);
                for (TripPeriod tp : tripPeriodsOfRound) {
                    tripPeriods.add(tp);
                }
                break;
        }
        tripVoucher.setTripPeriods(tripPeriods);
        return tripVoucher;
    }

    private String getTripPeriodTypeFromTripDescription(String tripPeriodDescription) {
        if (tripPeriodDescription.contains("გასვლა")) {
            return "baseLeaving";
        }
        if (tripPeriodDescription.contains("შესვენება")) {
            return "break";
        }
        if (tripPeriodDescription.contains("დაბრუნება")) {
            return "baseReturn";
        }
        if (tripPeriodDescription.contains("ბრუნი")) {
            return "round";
        }
        return tripPeriodDescription;
    }

    private TripPeriod createBaseLeavingPeriod(HashMap<String, String> data, int rowIndex) {
        String leftSideAnchor = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        TripPeriod tripPeriod;
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "baseLeaving_A";
            tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
        } else {
            String tripPeriodType = "baseLeaving_B";
            tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
        }
        return tripPeriod;
    }

    private TripPeriod createBaseReturnPeriod(HashMap<String, String> data, int rowIndex) {
        String leftSideAnchor = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        TripPeriod tripPeriod;
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "A_baseReturn";
            tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
        } else {
            String tripPeriodType = "B_baseReturn";
            tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
        }
        return tripPeriod;
    }

    private TripPeriod createBreakPeriod(HashMap<String, String> data, int rowIndex) {
        String leftSideAnchor = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        TripPeriod tripPeriod;
        String tripPeriodType = "break";
        if (data.containsKey(leftSideAnchor)) {
            tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
        } else {
            tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
        }
        return tripPeriod;
    }

    private ArrayList<TripPeriod> createTripPeridsOfRound(HashMap<String, String> data, int rowIndex) {
        ArrayList<TripPeriod> tripPeriodsOfRound = new ArrayList();
        String leftSideAnchor = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        String rightSideAnchor = new StringBuilder("W").append(String.valueOf(rowIndex)).toString();
        if (data.containsKey(leftSideAnchor) && data.containsKey(rightSideAnchor)) {
            LocalDateTime leftSideTime = this.converter.convertStringTimeToDate(data.get(leftSideAnchor));
            LocalDateTime rightSideTime = this.converter.convertStringTimeToDate(data.get(rightSideAnchor));
            if (leftSideTime.isBefore(rightSideTime)) {
                String tripPeriodType = "ab";
                TripPeriod tripPeriodAB = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodAB);
                tripPeriodType = "ba";
                TripPeriod tripPeriodBA = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodBA);
                return tripPeriodsOfRound;
            } else {
                String tripPeriodType = "ba";
                TripPeriod tripPeriodBA = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodBA);
                tripPeriodType = "ab";
                TripPeriod tripPeriodAB = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
                tripPeriodsOfRound.add(tripPeriodAB);
                return tripPeriodsOfRound;
            }
        }
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "ab";
            TripPeriod tripPeriod = createTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
            tripPeriodsOfRound.add(tripPeriod);
            return tripPeriodsOfRound;
        }
        if (data.containsKey(rightSideAnchor)) {
            String tripPeriodType = "ba";
            TripPeriod tripPeriod = createTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
            tripPeriodsOfRound.add(tripPeriod);
            return tripPeriodsOfRound;
        }
        return tripPeriodsOfRound;
    }

    private TripPeriod createTripPeriodFromLeftSide(HashMap<String, String> data, int rowIndex, String tripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        String startTimeActualLocationInTheRow = new StringBuilder("R").append(String.valueOf(rowIndex)).toString();
        String startTimeDifferenceLocationInTheRow = new StringBuilder("S").append(String.valueOf(rowIndex)).toString();

        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("T").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeActualLocationInTheRow = new StringBuilder("U").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeDifferenceLocationInTheRow = new StringBuilder("V").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        LocalDateTime startTimeActual = this.converter.convertStringTimeToDate(data.remove(startTimeActualLocationInTheRow));
        String startTimeDifference = data.remove(startTimeDifferenceLocationInTheRow);

        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));
        LocalDateTime arrivalTimeActual = this.converter.convertStringTimeToDate(data.remove(arrivalTimeActualLocationInTheRow));
        String arrivalTimeDifference = data.remove(arrivalTimeDifferenceLocationInTheRow);

        return new TripPeriod(tripPeriodType, startTimeScheduled, startTimeActual, startTimeDifference, arrivalTimeScheduled, arrivalTimeActual, arrivalTimeDifference);
    }

    private TripPeriod createTripPeriodFromRightSide(HashMap<String, String> data, int rowIndex, String tripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("W").append(String.valueOf(rowIndex)).toString();
        String startTimeActualLocationInTheRow = new StringBuilder("X").append(String.valueOf(rowIndex)).toString();
        String startTimeDifferenceLocationInTheRow = new StringBuilder("Y").append(String.valueOf(rowIndex)).toString();

        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("Z").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeActualLocationInTheRow = new StringBuilder("AA").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeDifferenceLocationInTheRow = new StringBuilder("AB").append(String.valueOf(rowIndex)).toString();
        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        LocalDateTime startTimeActual = this.converter.convertStringTimeToDate(data.remove(startTimeActualLocationInTheRow));
        String startTimeDifference = data.remove(startTimeDifferenceLocationInTheRow);

        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));
        LocalDateTime arrivalTimeActual = this.converter.convertStringTimeToDate(data.remove(arrivalTimeActualLocationInTheRow));
        String arrivalTimeDifference = data.remove(arrivalTimeDifferenceLocationInTheRow);
        return new TripPeriod(tripPeriodType, startTimeScheduled, startTimeActual, startTimeDifference, arrivalTimeScheduled, arrivalTimeActual, arrivalTimeDifference);
    }
//this method are almost identical to methods above, with some little changes

    public TreeMap<Float, GuarantyRoute> createGuarantyRoutesFromUploadedFile() {
        TreeMap<Float, GuarantyRoute> guarantyRoutes = new TreeMap();

        String filePath = this.basementController.getBasementDirectory() + "/uploads/uploadedGuarantyExcelFile.xlsx";
        ExcelReader excelReader = new ExcelReader();
        HashMap<String, String> data = excelReader.getCellsFromExcelFile(filePath);
        guarantyRoutes = convertExcelDataToGuarantyRoutes(data);

        return guarantyRoutes;
    }

    private TreeMap<Float, GuarantyRoute> convertExcelDataToGuarantyRoutes(HashMap<String, String> data) {
        TreeMap<Float, GuarantyRoute> guarantyRoutes = new TreeMap<>();
        int rowIndex = 8;
        while (!data.isEmpty()) {

            String startTimeActualLocationInTheRow = new StringBuilder("K").append(String.valueOf(rowIndex)).toString();
            if (data.containsKey(startTimeActualLocationInTheRow)) {
                guarantyRoutes.clear();
                guarantyRoutes.put(0.001f, new GuarantyRoute());//0.001f is code for error when actualStartTimes exist in file,(it cant be, because this file have to be from futer dates
                return guarantyRoutes;
            }

            String routeNumberLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
            String routeNumberString = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumberString == null) {//in theory this means that you reached the end of rows with data
                break;
            }

            Float routeNumberFloat = this.converter.convertRouteNumber(routeNumberString);
            GuarantyRoute guarantyRoute;
            if (guarantyRoutes.containsKey(routeNumberFloat)) {
                guarantyRoute = guarantyRoutes.get(routeNumberFloat);
                //this here is security check, if there is two different dates, then the uploaded file is not good
                String dateStampLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
                String dateStampExcelFormat = data.remove(dateStampLocationInTheRow);
                Date date = this.converter.convertDateStampExcelFormatToDate(dateStampExcelFormat);
                String dateStamp = this.converter.convertDateStampExcelFormatToDatabaseFormat(dateStampExcelFormat);
                if (guarantyRoute.getDateStamp() != null) {
                    if (!guarantyRoute.getDateStamp().equals(dateStamp)) {
                        guarantyRoutes.clear();
                        guarantyRoutes.put(0.002f, new GuarantyRoute());//0.002f is code for error when two or more stamp dates are existing in uploaded file
                        return guarantyRoutes;
                    }
                }
                //now checking busType
                if (guarantyRoute.getBusType().equals("")) {
                    String busTypeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
                    if (data.containsKey(busTypeLocationInTheRow)) {
                        String busType = data.remove(busTypeLocationInTheRow);
                        guarantyRoute.setBusType(busType);
                    }
                }
                //exoduses here
                String guarantyExodusNumberLocationInTheRow = new StringBuilder("I").append(String.valueOf(rowIndex)).toString();
                short guarantyExodusNumber = Float.valueOf(data.remove(guarantyExodusNumberLocationInTheRow)).shortValue();
                GuarantyExodus guarantyExodus;
                if (guarantyRoute.getExoduses().containsKey(guarantyExodusNumber)) {
                    guarantyExodus = guarantyRoute.getExoduses().get(guarantyExodusNumber);
                    guarantyExodus = addElementsToGuarantyExodus(guarantyExodus, data, rowIndex);
                    guarantyRoute.getExoduses().put(guarantyExodusNumber, guarantyExodus);
                } else {
                    guarantyExodus = new GuarantyExodus();
                    guarantyExodus.setNumber(guarantyExodusNumber);
                    guarantyExodus = addElementsToGuarantyExodus(guarantyExodus, data, rowIndex);
                    guarantyRoute.getExoduses().put(guarantyExodusNumber, guarantyExodus);
                }

            } else {
                guarantyRoute = new GuarantyRoute();
//first setting route number
                guarantyRoute.setNumber(routeNumberString);
//now going for datestamp
                String dateStampLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
                String dateStampExcelFormat = data.remove(dateStampLocationInTheRow);
                Date date = this.converter.convertDateStampExcelFormatToDate(dateStampExcelFormat);
                String dateStamp = this.converter.convertDateStampExcelFormatToDatabaseFormat(dateStampExcelFormat);
                guarantyRoute.setDateStamp(dateStamp);
//this time -baseNumber
                String baseNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
                short baseNumber = Short.valueOf(data.remove(baseNumberLocationInTheRow));
                guarantyRoute.setBaseNumber(baseNumber);
//here - busType
                String busTypeLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
                if (data.containsKey(busTypeLocationInTheRow)) {
                    String busType = data.remove(busTypeLocationInTheRow);
                    guarantyRoute.setBusType(busType);
                } else {
                    guarantyRoute.setBusType("");
                }

                //we arrived to exoduses
                String guarantyExodusNumberLocationInTheRow = new StringBuilder("I").append(String.valueOf(rowIndex)).toString();
                short guarantyExodusNumber = Float.valueOf(data.remove(guarantyExodusNumberLocationInTheRow)).shortValue();
                GuarantyExodus guarantyExodus = new GuarantyExodus();
                guarantyExodus.setNumber(guarantyExodusNumber);
                guarantyExodus = addElementsToGuarantyExodus(guarantyExodus, data, rowIndex);
                guarantyRoute.getExoduses().put(guarantyExodusNumber, guarantyExodus);

                guarantyRoutes.put(routeNumberFloat, guarantyRoute);
            }

            if (rowIndex % 1000 == 0) {
                System.out.print("RowIndex:" + rowIndex + " #");
                mu.printMemoryUsage();
            }
            rowIndex++;
        }
        return guarantyRoutes;
    }

    private GuarantyExodus addElementsToGuarantyExodus(GuarantyExodus guarantyExodus, HashMap<String, String> data, int rowIndex) {

        String tripPeriodDescriptionLocationInTheRow = new StringBuilder("P").append(String.valueOf(rowIndex)).toString();
        String tripPeriodDescription = data.remove(tripPeriodDescriptionLocationInTheRow);
        String tripPeriodType = getTripPeriodTypeFromTripDescription(tripPeriodDescription);
        ArrayList<GuarantyTripPeriod> guarantyTripPeriods = guarantyExodus.getGuarantyTripPeriods();
        GuarantyTripPeriod guarantyTripPeriod;
        switch (tripPeriodType) {
            case "baseLeaving":
                guarantyTripPeriod = createGuarantyBaseLeavingPeriod(data, rowIndex);
                guarantyTripPeriods.add(guarantyTripPeriod);
                break;
            case "baseReturn":
                guarantyTripPeriod = createGuarantyBaseReturnPeriod(data, rowIndex);
                guarantyTripPeriods.add(guarantyTripPeriod);
                break;
            case "break":
                guarantyTripPeriod = createGuarantyBreakPeriod(data, rowIndex);
                guarantyTripPeriods.add(guarantyTripPeriod);

            case "round":
                ArrayList<GuarantyTripPeriod> guarantyTripPeriodsOfRound = createGuarantyTripPeridsOfRound(data, rowIndex);
                for (GuarantyTripPeriod gtp : guarantyTripPeriodsOfRound) {
                    guarantyTripPeriods.add(gtp);
                }
                break;
        }
        guarantyExodus.setGuarantyTripPeriods(guarantyTripPeriods);
        return guarantyExodus;
    }

    private GuarantyTripPeriod createGuarantyBaseLeavingPeriod(HashMap<String, String> data, int rowIndex) {
        GuarantyTripPeriod baseLeavingGuarantyTripPeriod;
        String guarantyTripPeriodType;
        String baseLeaving_A_LocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        if (data.containsKey(baseLeaving_A_LocationInTheRow)) {
            guarantyTripPeriodType = "baseLeaving_A";
            baseLeavingGuarantyTripPeriod = createGuarantyTripPeriodFromLeftSide(data, rowIndex, guarantyTripPeriodType);
        } else {
            guarantyTripPeriodType = "baseLeaving_B";
            baseLeavingGuarantyTripPeriod = createGuarantyTripPeriodFromRightSide(data, rowIndex, guarantyTripPeriodType);

        }
        return baseLeavingGuarantyTripPeriod;
    }

    private GuarantyTripPeriod createGuarantyBaseReturnPeriod(HashMap<String, String> data, int rowIndex) {
        GuarantyTripPeriod baseReturnGuarantyTripPeriod;
        String guarantyTripPeriodType;
        String baseReturn_A_LocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        if (data.containsKey(baseReturn_A_LocationInTheRow)) {
            guarantyTripPeriodType = "A_baseReturn";
            baseReturnGuarantyTripPeriod = createGuarantyTripPeriodFromLeftSide(data, rowIndex, guarantyTripPeriodType);
        } else {
            guarantyTripPeriodType = "B_baseReturn";
            baseReturnGuarantyTripPeriod = createGuarantyTripPeriodFromRightSide(data, rowIndex, guarantyTripPeriodType);

        }
        return baseReturnGuarantyTripPeriod;
    }

    private GuarantyTripPeriod createGuarantyBreakPeriod(HashMap<String, String> data, int rowIndex) {

        GuarantyTripPeriod breakTripPeriod;
        String guarantyTripPeriodType;
        String break_A_LocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        if (data.containsKey(break_A_LocationInTheRow)) {
            guarantyTripPeriodType = "break";
            breakTripPeriod = createGuarantyTripPeriodFromLeftSide(data, rowIndex, guarantyTripPeriodType);
        } else {
            guarantyTripPeriodType = "break";
            breakTripPeriod = createGuarantyTripPeriodFromRightSide(data, rowIndex, guarantyTripPeriodType);

        }
        return breakTripPeriod;

    }

    private ArrayList<GuarantyTripPeriod> createGuarantyTripPeridsOfRound(HashMap<String, String> data, int rowIndex) {

        ArrayList<GuarantyTripPeriod> guarantyTripPeriodsOfRound = new ArrayList();
        String leftSideAnchor = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        String rightSideAnchor = new StringBuilder("W").append(String.valueOf(rowIndex)).toString();
        if (data.containsKey(leftSideAnchor) && data.containsKey(rightSideAnchor)) {
            LocalDateTime leftSideTime = this.converter.convertStringTimeToDate(data.get(leftSideAnchor));
            LocalDateTime rightSideTime = this.converter.convertStringTimeToDate(data.get(rightSideAnchor));
            if (leftSideTime.isBefore(rightSideTime)) {
                String tripPeriodType = "ab";
                GuarantyTripPeriod tripPeriodAB = createGuarantyTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
                guarantyTripPeriodsOfRound.add(tripPeriodAB);
                tripPeriodType = "ba";
                GuarantyTripPeriod tripPeriodBA = createGuarantyTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
                guarantyTripPeriodsOfRound.add(tripPeriodBA);
                return guarantyTripPeriodsOfRound;
            } else {
                String tripPeriodType = "ba";
                GuarantyTripPeriod tripPeriodBA = createGuarantyTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
                guarantyTripPeriodsOfRound.add(tripPeriodBA);
                tripPeriodType = "ab";
                GuarantyTripPeriod tripPeriodAB = createGuarantyTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
                guarantyTripPeriodsOfRound.add(tripPeriodAB);
                return guarantyTripPeriodsOfRound;
            }
        }
        if (data.containsKey(leftSideAnchor)) {
            String tripPeriodType = "ab";
            GuarantyTripPeriod tripPeriod = createGuarantyTripPeriodFromLeftSide(data, rowIndex, tripPeriodType);
            guarantyTripPeriodsOfRound.add(tripPeriod);
            return guarantyTripPeriodsOfRound;
        }
        if (data.containsKey(rightSideAnchor)) {
            String tripPeriodType = "ba";
            GuarantyTripPeriod tripPeriod = createGuarantyTripPeriodFromRightSide(data, rowIndex, tripPeriodType);
            guarantyTripPeriodsOfRound.add(tripPeriod);
            return guarantyTripPeriodsOfRound;
        }
        return guarantyTripPeriodsOfRound;

    }

    private GuarantyTripPeriod createGuarantyTripPeriodFromLeftSide(HashMap<String, String> data, int rowIndex, String guarantyTripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("Q").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("T").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));

        return new GuarantyTripPeriod(guarantyTripPeriodType, startTimeScheduled, arrivalTimeScheduled);

    }

    private GuarantyTripPeriod createGuarantyTripPeriodFromRightSide(HashMap<String, String> data, int rowIndex, String guarantyTripPeriodType) {
        String startTimeScheduledLocationInTheRow = new StringBuilder("W").append(String.valueOf(rowIndex)).toString();
        String arrivalTimeScheduledLocationInTheRow = new StringBuilder("Z").append(String.valueOf(rowIndex)).toString();

        LocalDateTime startTimeScheduled = this.converter.convertStringTimeToDate(data.remove(startTimeScheduledLocationInTheRow));
        LocalDateTime arrivalTimeScheduled = this.converter.convertStringTimeToDate(data.remove(arrivalTimeScheduledLocationInTheRow));

        return new GuarantyTripPeriod(guarantyTripPeriodType, startTimeScheduled, arrivalTimeScheduled);

    }

    TreeMap<Float, RouteData> getRoutesNamesDataFromUploadedFile() {
        TreeMap<Float, RouteData> routes = new TreeMap<>();
        try {
            String filePath = this.basementController.getBasementDirectory() + "/uploads/uploadedRoutesDataExcelFile.xlsx";
            ExcelReader excelReader = new ExcelReader();
            HashMap<String, String> data = excelReader.getCellsFromExcelFile(filePath);
            routes = convertExcelDataToRoutesNameData(data);
        } catch (Exception ex) {
            Logger.getLogger(ExcelController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return routes;
    }

    private TreeMap<Float, RouteData> convertExcelDataToRoutesNameData(HashMap<String, String> data) {
        TreeMap<Float, RouteData> routes = new TreeMap<>();
        int rowIndex = 2;
        while (!data.isEmpty()) {
            String routeNumberLocationInTheRow = new StringBuilder("A").append(String.valueOf(rowIndex)).toString();
            String routeNumberString = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumberString == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            Float routeNumberFloat = this.converter.convertRouteNumber(routeNumberString);
            RouteData routeData = new RouteData();
            routeData.setNumber(routeNumberString);

            String aPointLocationInTheRow = new StringBuilder("C").append(String.valueOf(rowIndex)).toString();
            String aPoint = data.remove(aPointLocationInTheRow);//at the same time reading and removing the cell from hash Map
            routeData.setaPoint(aPoint);

            String bPointLocationInTheRow = new StringBuilder("D").append(String.valueOf(rowIndex)).toString();
            String bPoint = data.remove(bPointLocationInTheRow);//at the same time reading and removing the cell from hash Map
            routeData.setbPoint(bPoint);

            String schemaLocationInTheRow = new StringBuilder("B").append(String.valueOf(rowIndex)).toString();
            String scheme = data.remove(schemaLocationInTheRow);//at the same time reading and removing the cell from hash Map
            routeData.setScheme(scheme);

            routes.put(routeNumberFloat, routeData);

            rowIndex++;
        }
        return routes;
    }

    public TreeMap<Float, Route> getRoutesNumbersAndDatesFromUploadedExcelFile() {
        String filePath = this.basementController.getBasementDirectory() + "/uploads/uploadedExcelFile.xlsx";
        ExcelReader er = new ExcelReader();
        HashMap<String, String> data = er.getCellsFromExcelFile(filePath);

        TreeMap<Float, Route> routes = new TreeMap<>();
        int rowIndex = 8;
        while (!data.isEmpty()) {
            String routeNumberLocationInTheRow = new StringBuilder("H").append(String.valueOf(rowIndex)).toString();
            String routeNumberString = data.remove(routeNumberLocationInTheRow);//at the same time reading and removing the cell from hash Map
            if (routeNumberString == null) {//in theory this means that you reached the end of rows with data
                break;
            }
            Float routeNumberFloat = this.converter.convertRouteNumber(routeNumberString);

            String dateStampLocationInTheRow = new StringBuilder("F").append(String.valueOf(rowIndex)).toString();
            String dateStampExcelFormat = data.remove(dateStampLocationInTheRow);
            Date date = this.converter.convertDateStampExcelFormatToDate(dateStampExcelFormat);
            String dateStamp = this.converter.convertDateStampExcelFormatToDatabaseFormat(dateStampExcelFormat);

            if (routes.containsKey(routeNumberFloat)) {
                Route route = routes.get(routeNumberFloat);

                TreeMap<Date, Day> days = route.getDays();
                if (days.containsKey(date)) {
                    //do nothing
                } else {
                    Day day = new Day();
                    day.setDateStamp(dateStamp);
                    days.put(date, day);
                }
                route.setDays(days);
            } else {
                Route route = new Route();
                route.setNumber(routeNumberString);

                TreeMap<Date, Day> days = route.getDays();
                Day day = new Day();
                day.setDateStamp(dateStamp);
                days.put(date, day);
                routes.put(routeNumberFloat, route);
            }
            rowIndex++;
        }
        data = null;
        return routes;

    }

}
