package Controller;

import Model.Day;
import Model.Exodus;
import Model.Route;
import Model.TripPeriod;
import Model.TripVoucher;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ExcelController {

    private Converter converter;
    private MemoryUsage mu;

    public ExcelController() {
        this.converter = new Converter();
        this.mu = new MemoryUsage();
    }

    @RequestMapping(value = "readUploadedExcelFile")//request name will be changed
    public String getRoutesFromUploadedFile(ModelMap model) {
        Instant inst1 = Instant.now();

        TreeMap<Float, Route> routes = new TreeMap();

        System.out.println();
        System.out.println("----- MEMORY USAGE ---BEFORE----  FILE READING---------");
        mu.showMemoryUsage();
        System.out.println("----END OF MEMORY USAGE ---BEFORE----  FILE READING---------");
        routes = createRoutesFromUploadedFile();
        //tring to deallocate memmory
        /* for (Iterator<Map.Entry<String, String>> it = data.entrySet().iterator(); it.hasNext();) {
            Map.Entry<String, String> entry = it.next();
            entry = null;
        }
        System.out.println("DATA size:" + data.size());
        data = null;*/
        //end of triaing
        Instant inst2 = Instant.now();
        System.out.println();
        System.out.println("Time needed for reading excel file and making routes for it: " + Duration.between(inst1, inst2).toString());
        System.out.println();
        model.addAttribute("routes", routes);
        System.out.println();
        System.out.println("++++++++++++++++ MEMORY USAGE AFTER FILE READING+++++++++++");
        mu.showMemoryUsage();
        System.out.println("++++++++++++++++END OF MEMORY USAGE AFTER FILE READING+++++++++++");

        return "readSuccess";
    }

    private TreeMap<Float, Route> createRoutesFromUploadedFile() {
        TreeMap<Float, Route> routes = new TreeMap<>();
        try {
            HashMap<String, String> data = new HashMap();
            BasementController basementController = new BasementController();
            String filePath = basementController.getBasementDirectory() + "/uploads/uploadedExcelFile.xlsx";
            ExcelReader excelReader = new ExcelReader();
            data = excelReader.getCellsFromExcelFile(filePath);
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
            Route route = new Route();
            if (routes.containsKey(routeNumberFloat)) {
                route = routes.get(routeNumberFloat);
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
        Day day = new Day();
        if (days.containsKey(date)) {
            day = days.get(date);
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
        Exodus exodus = new Exodus();
        if (exoduses.containsKey(exodusNumber)) {
            exodus = exoduses.get(exodusNumber);
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
        TripVoucher tripVoucher = new TripVoucher();
        if (tripVouchers.containsKey(tripVoucherNumber)) {
            tripVoucher = tripVouchers.get(tripVoucherNumber);
        } else {
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

    //------------writing--------
    @RequestMapping(value = "writeExcelFile")
    public String writeExcelFile() {
        ExcelWriter excelWriter = new ExcelWriter();
        TreeMap<Float, Route> routes = createRoutesFromUploadedFile();
        excelWriter.write(routes);
        return "index";
    }

}
