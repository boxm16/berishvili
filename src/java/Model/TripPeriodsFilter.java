package Model;

import Controller.Converter;
import java.util.TreeMap;

public class TripPeriodsFilter {

    private TreeMap<String, Boolean> routeNumbers;
    private TreeMap<String, Boolean> dateStamps;
    private TreeMap<String, Boolean> busNumbers;
    private TreeMap<Short, Boolean> exodusNumbers;
    private TreeMap<String, Boolean> driverNames;
    private TreeMap<String, Boolean> tripPeriodTypes;
    private TreeMap<String, Boolean> startTimesScheduled;
    private TreeMap<String, Boolean> startTimesActual;
    private TreeMap<String, Boolean> tripPeriodTimeScheduled;
    private TreeMap<String, Boolean> arrivalTimesScheduled;
    private TreeMap<String, Boolean> tripPeriodTimeActual;
    private TreeMap<String, Boolean> arrivalTimesActual;
    private TreeMap<String, Boolean> tripPeriodTimeDifference;

    private Converter converter;

    public TripPeriodsFilter() {
        this.routeNumbers = new TreeMap();
        this.dateStamps = new TreeMap();
        this.busNumbers = new TreeMap();
        this.exodusNumbers = new TreeMap();
        this.driverNames = new TreeMap();
        this.tripPeriodTypes = new TreeMap();
        this.startTimesScheduled = new TreeMap();
        this.startTimesActual = new TreeMap();
        this.tripPeriodTimeScheduled = new TreeMap();
        this.arrivalTimesScheduled = new TreeMap();
        this.tripPeriodTimeActual = new TreeMap();
        this.arrivalTimesActual = new TreeMap();
        this.tripPeriodTimeDifference = new TreeMap();
        converter = new Converter();
    }

    public void addRouteNumber(String routeNumber) {
        this.routeNumbers.put(routeNumber, Boolean.TRUE);
    }

    public void addDateStamp(String dateStamp) {
        this.dateStamps.put(converter.convertDateStampDatabaseFormatToExcelFormat(dateStamp), Boolean.TRUE);
    }

    public void addBusNumber(String busNumber) {
        this.busNumbers.put(busNumber, Boolean.TRUE);
    }

    public void addExodusNumber(short exodusNumber) {
        this.exodusNumbers.put(exodusNumber, Boolean.TRUE);
    }

    public void addDriverName(String driverName) {
        this.driverNames.put(driverName, Boolean.TRUE);
    }

    public void addTripPeriodType(String tripPeriodType) {

        switch (tripPeriodType) {
            case "baseLeaving_A":
                this.tripPeriodTypes.put("ბაზა_A", Boolean.TRUE);
            case "baseLeaving_B":
                this.tripPeriodTypes.put("ბაზა_B", Boolean.TRUE);
            case "break":
                this.tripPeriodTypes.put("შესვენება", Boolean.TRUE);
            case "ab":
                this.tripPeriodTypes.put("A_B", Boolean.TRUE);
            case "ba":
                this.tripPeriodTypes.put("B_A", Boolean.TRUE);
            case "A_baseReturn":
                this.tripPeriodTypes.put("A_ბაზა", Boolean.TRUE);
            case "B_baseReturn":
                this.tripPeriodTypes.put("B_ბაზა", Boolean.TRUE);

        }
    }

    public void addStartTimeScheduled(String startTimeScheduled) {
        this.startTimesScheduled.put(startTimeScheduled, Boolean.TRUE);
    }

    public void addStartTimeActual(String startTimeActual) {
        if (startTimeActual != null) {
            this.startTimesActual.put(startTimeActual, Boolean.TRUE);
        }
    }

    public void addArrivalTimeScheduled(String arrivalTimeScheduled) {
        this.arrivalTimesScheduled.put(arrivalTimeScheduled, Boolean.TRUE);
    }

    public void addArrivalTimeActual(String arrivalTimesActual) {
        if (arrivalTimesActual != null) {
            this.arrivalTimesActual.put(arrivalTimesActual, Boolean.TRUE);
        }
    }

    public TreeMap<String, Boolean> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(TreeMap<String, Boolean> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public TreeMap<String, Boolean> getDateStamps() {
        return dateStamps;
    }

    public void setDateStamps(TreeMap<String, Boolean> dateStamps) {
        this.dateStamps = dateStamps;
    }

    public TreeMap<String, Boolean> getBusNumbers() {
        return busNumbers;
    }

    public void setBusNumbers(TreeMap<String, Boolean> busNumbers) {
        this.busNumbers = busNumbers;
    }

    public TreeMap<Short, Boolean> getExodusNumbers() {
        return exodusNumbers;
    }

    public void setExodusNumbers(TreeMap<Short, Boolean> exodusNumbers) {
        this.exodusNumbers = exodusNumbers;
    }

    public TreeMap<String, Boolean> getDriverNames() {
        return driverNames;
    }

    public void setDriverNames(TreeMap<String, Boolean> driverNames) {
        this.driverNames = driverNames;
    }

    public TreeMap<String, Boolean> getTripPeriodTypes() {
        return tripPeriodTypes;
    }

    public void setTripPeriodTypes(TreeMap<String, Boolean> tripPeriodTypes) {
        this.tripPeriodTypes = tripPeriodTypes;
    }

    public TreeMap<String, Boolean> getStartTimesScheduled() {
        return startTimesScheduled;
    }

    public void setStartTimesScheduled(TreeMap<String, Boolean> startTimesScheduled) {
        this.startTimesScheduled = startTimesScheduled;
    }

    public TreeMap<String, Boolean> getStartTimesActual() {
        return startTimesActual;
    }

    public void setStartTimesActual(TreeMap<String, Boolean> startTimesActual) {
        this.startTimesActual = startTimesActual;
    }

    public TreeMap<String, Boolean> getTripPeriodTimeScheduled() {
        return tripPeriodTimeScheduled;
    }

    public void setTripPeriodTimeScheduled(TreeMap<String, Boolean> tripPeriodTimeScheduled) {
        this.tripPeriodTimeScheduled = tripPeriodTimeScheduled;
    }

    public TreeMap<String, Boolean> getArrivalTimesScheduled() {
        return arrivalTimesScheduled;
    }

    public void setArrivalTimesScheduled(TreeMap<String, Boolean> arrivalTimesScheduled) {
        this.arrivalTimesScheduled = arrivalTimesScheduled;
    }

    public TreeMap<String, Boolean> getTripPeriodTimeActual() {
        return tripPeriodTimeActual;
    }

    public void setTripPeriodTimeActual(TreeMap<String, Boolean> tripPeriodTimeActual) {
        this.tripPeriodTimeActual = tripPeriodTimeActual;
    }

    public TreeMap<String, Boolean> getArrivalTimesActual() {
        return arrivalTimesActual;
    }

    public void setArrivalTimesActual(TreeMap<String, Boolean> arrivalTimesActual) {
        this.arrivalTimesActual = arrivalTimesActual;
    }

    public TreeMap<String, Boolean> getTripPeriodTimeDifference() {
        return tripPeriodTimeDifference;
    }

    public void setTripPeriodTimeDifference(TreeMap<String, Boolean> tripPeriodTimeDifference) {
        this.tripPeriodTimeDifference = tripPeriodTimeDifference;
    }

    public Converter getConverter() {
        return converter;
    }

    public void setConverter(Converter converter) {
        this.converter = converter;
    }
    
    

}
