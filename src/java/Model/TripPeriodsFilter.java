package Model;

import java.util.ArrayList;
import java.util.Collections;

public class TripPeriodsFilter {

    private ArrayList<String> routeNumbers;
    private ArrayList<String> dateStamps;
    private ArrayList<String> busNumbers;
    private ArrayList<Short> exodusNumbers;
    private ArrayList<String> driverNames;
    private ArrayList<String> tripPeriodTypes;
    private ArrayList<String> startTimesScheduled;
    private ArrayList<String> startTimesActual;
    private ArrayList<String> arrivalTimesScheduled;
    private ArrayList<String> arrivalTimesActual;

    public TripPeriodsFilter() {
        routeNumbers = new ArrayList<>();
        dateStamps = new ArrayList<>();
        busNumbers = new ArrayList<>();
        exodusNumbers = new ArrayList<>();
        driverNames = new ArrayList<>();
        tripPeriodTypes = new ArrayList<>();
        startTimesScheduled = new ArrayList<>();
        startTimesActual = new ArrayList<>();
        arrivalTimesScheduled = new ArrayList<>();
        arrivalTimesActual = new ArrayList<>();
    }

    public void addArrivalTimeActual(String arrivalTimeActual) {
        if (arrivalTimeActual != null && !arrivalTimesActual.contains(arrivalTimeActual)) {
            arrivalTimesActual.add(arrivalTimeActual);
        }
    }

    public void addArrivalTimeScheduled(String arrivalTimeScheduled) {
        if (!arrivalTimesScheduled.contains(arrivalTimeScheduled) && arrivalTimeScheduled != null) {
            arrivalTimesScheduled.add(arrivalTimeScheduled);
        }
    }

    public void addStartTimeActual(String startTimeActual) {
        if (startTimeActual != null && !startTimesActual.contains(startTimeActual)) {
            startTimesActual.add(startTimeActual);
        }
    }

    public void addStartTimeScheduled(String startTimeScheduled) {
        if (!startTimesScheduled.contains(startTimeScheduled) && startTimeScheduled != null) {
            startTimesScheduled.add(startTimeScheduled);
        }
    }

    public void addTripPeriodType(String tripPeriodType) {
        if (!tripPeriodTypes.contains(tripPeriodType)) {
            tripPeriodTypes.add(tripPeriodType);
        }
    }

    public void addDriverName(String driverName) {
        if (!driverNames.contains(driverName)) {
            driverNames.add(driverName);
        }
    }

    public void addBusNumber(String busNumber) {
        if (!busNumbers.contains(busNumber)) {
            busNumbers.add(busNumber);
        }
    }

    public void addExodusNumber(short exodusNumber) {
        if (!exodusNumbers.contains(exodusNumber)) {
            exodusNumbers.add(exodusNumber);
        }
    }

    public void addDateStamp(String dateStamp) {
        if (!dateStamps.contains(dateStamp)) {
            dateStamps.add(dateStamp);
        }
    }

    public void addRouteNumber(String number) {
        if (!routeNumbers.contains(number)) {
            routeNumbers.add(number);
        }
    }

    public ArrayList<String> getRouteNumbers() {
        Collections.sort(routeNumbers);
        return routeNumbers;
    }

    public void setRouteNumbers(ArrayList<String> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public ArrayList<String> getDateStamps() {
        Collections.sort(dateStamps);
        return dateStamps;
    }

    public void setDateStamps(ArrayList<String> dateStamps) {
        this.dateStamps = dateStamps;
    }

    public ArrayList<String> getBusNumbers() {
        Collections.sort(busNumbers);
        return busNumbers;
    }

    public void setBusNumbers(ArrayList<String> busNumbers) {
        this.busNumbers = busNumbers;
    }

    public ArrayList<Short> getExodusNumbers() {
        Collections.sort(exodusNumbers);
        return exodusNumbers;
    }

    public void setExodusNumbers(ArrayList<Short> exodusNumbers) {
        this.exodusNumbers = exodusNumbers;
    }

    public ArrayList<String> getDriverNames() {
        Collections.sort(driverNames);
        return driverNames;
    }

    public void setDriverNames(ArrayList<String> driverNames) {

        this.driverNames = driverNames;
    }

    public ArrayList<String> getTripPeriodTypes() {
        Collections.sort(tripPeriodTypes);
        return tripPeriodTypes;
    }

    public void setTripPeriodTypes(ArrayList<String> tripPeriodTypes) {
        this.tripPeriodTypes = tripPeriodTypes;
    }

    public ArrayList<String> getStartTimesScheduled() {
        Collections.sort(startTimesScheduled);
        return startTimesScheduled;
    }

    public void setStartTimesScheduled(ArrayList<String> startTimesScheduled) {
        this.startTimesScheduled = startTimesScheduled;
    }

    public ArrayList<String> getStartTimesActual() {
        Collections.sort(startTimesActual);
        return startTimesActual;
    }

    public void setStartTimesActual(ArrayList<String> startTimesActual) {
        this.startTimesActual = startTimesActual;
    }

    public ArrayList<String> getArrivalTimesScheduled() {
        Collections.sort(arrivalTimesScheduled);
        return arrivalTimesScheduled;
    }

    public void setArrivalTimesScheduled(ArrayList<String> arrivalTimesScheduled) {
        this.arrivalTimesScheduled = arrivalTimesScheduled;
    }

    public ArrayList<String> getArrivalTimesActual() {
        Collections.sort(arrivalTimesActual);
        return arrivalTimesActual;
    }

    public void setArrivalTimesActual(ArrayList<String> arrivalTimesActual) {
        this.arrivalTimesActual = arrivalTimesActual;
    }

}
