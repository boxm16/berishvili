package Model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TripPeriodsFilter {

    private TreeMap<String, Boolean> routeNumbers;
    private TreeMap<String, Boolean> dateStamps;
    private TreeMap<String, Boolean> busNumbers;
    private TreeMap<String, Boolean> exodusNumbers;
    private TreeMap<String, Boolean> driverNames;
    private TreeMap<String, Boolean> tripPeriodTypes;
    private TreeMap<String, Boolean> startTimesScheduled;
    private TreeMap<String, Boolean> startTimesActual;
    private TreeMap<String, Boolean> tripPeriodTimesScheduled;
    private TreeMap<String, Boolean> arrivalTimesScheduled;
    private TreeMap<String, Boolean> tripPeriodTimesActual;
    private TreeMap<String, Boolean> arrivalTimesActual;
    private TreeMap<String, Boolean> tripPeriodTimeDifferences;

    public TripPeriodsFilter() {
        this.routeNumbers = new TreeMap();
        this.dateStamps = new TreeMap();
        this.busNumbers = new TreeMap();
        this.exodusNumbers = new TreeMap();
        this.driverNames = new TreeMap();
        this.tripPeriodTypes = new TreeMap();
        this.startTimesScheduled = new TreeMap();
        this.startTimesActual = new TreeMap();
        this.tripPeriodTimesScheduled = new TreeMap();
        this.arrivalTimesScheduled = new TreeMap();
        this.tripPeriodTimesActual = new TreeMap();
        this.arrivalTimesActual = new TreeMap();
        this.tripPeriodTimeDifferences = new TreeMap();

    }

    public void addRouteNumber(String routeNumber) {
        this.routeNumbers.put(routeNumber, Boolean.TRUE);
    }

    public void addDateStamp(String dateStamp) {
        this.dateStamps.put(dateStamp, Boolean.TRUE);
    }

    public void addBusNumber(String busNumber) {
        this.busNumbers.put(busNumber, Boolean.TRUE);
    }

    public void addExodusNumber(String exodusNumber) {
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

    //-------------------
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

    public TreeMap<String, Boolean> getExodusNumbers() {
        return exodusNumbers;
    }

    public void setExodusNumbers(TreeMap<String, Boolean> exodusNumbers) {
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

    public TreeMap<String, Boolean> getTripPeriodTimesScheduled() {
        return tripPeriodTimesScheduled;
    }

    public void setTripPeriodTimesScheduled(TreeMap<String, Boolean> tripPeriodTimesScheduled) {
        this.tripPeriodTimesScheduled = tripPeriodTimesScheduled;
    }

    public TreeMap<String, Boolean> getArrivalTimesScheduled() {
        return arrivalTimesScheduled;
    }

    public void setArrivalTimesScheduled(TreeMap<String, Boolean> arrivalTimesScheduled) {
        this.arrivalTimesScheduled = arrivalTimesScheduled;
    }

    public TreeMap<String, Boolean> getTripPeriodTimesActual() {
        return tripPeriodTimesActual;
    }

    public void setTripPeriodTimesActual(TreeMap<String, Boolean> tripPeriodTimesActual) {
        this.tripPeriodTimesActual = tripPeriodTimesActual;
    }

    public TreeMap<String, Boolean> getArrivalTimesActual() {
        return arrivalTimesActual;
    }

    public void setArrivalTimesActual(TreeMap<String, Boolean> arrivalTimesActual) {
        this.arrivalTimesActual = arrivalTimesActual;
    }

    public TreeMap<String, Boolean> getTripPeriodTimeDifferences() {
        return tripPeriodTimeDifferences;
    }

    public void setTripPeriodTimeDifferences(TreeMap<String, Boolean> tripPeriodTimeDifferences) {
        this.tripPeriodTimeDifferences = tripPeriodTimeDifferences;
    }

    public TripPeriodsFilter refactorFilter(String triggerFilter, ArrayList<String> routeNumbers, ArrayList<String> dateStamps, ArrayList<String> busNumbers, ArrayList<String> exodusNumbers, ArrayList<String> driverNames, ArrayList<String> tripPeriodTypes, ArrayList<String> startTimesScheduled, ArrayList<String> startTimesActual, ArrayList<String> arrivalTimesScheduled, ArrayList<String> arrivalTimesActual, ArrayList<String> tripPeriodTimesScheduled, ArrayList<String> tripPeriodTimesActual, ArrayList<String> tripPeriodTimeScheduled, ArrayList<String> tripPeriodTimeDifferences) {

        if (triggerFilter.equals("routeNumbers")) {
            refactorTriggerFilterItems(this.routeNumbers, routeNumbers);
        } else {
            refactorFilterItems(this.routeNumbers, routeNumbers);
        }
        //
        if (triggerFilter.equals("dateStamps")) {
            refactorTriggerFilterItems(this.dateStamps, dateStamps);
        } else {
            refactorFilterItems(this.dateStamps, dateStamps);
        }
        //
        if (triggerFilter.equals("busNumbers")) {
            refactorTriggerFilterItems(this.busNumbers, busNumbers);
        } else {
            refactorFilterItems(this.busNumbers, busNumbers);
        }
        //
        if (triggerFilter.equals("exodusNumbers")) {
            refactorTriggerFilterItems(this.exodusNumbers, exodusNumbers);
        } else {
            refactorFilterItems(this.exodusNumbers, exodusNumbers);
        }

        //
        if (triggerFilter.equals("driverNames")) {
            refactorTriggerFilterItems(this.driverNames, driverNames);
        } else {
            refactorFilterItems(this.driverNames, driverNames);
        }
        //
        if (triggerFilter.equals("tripPeriodTypes")) {
            refactorTriggerFilterItems(this.tripPeriodTypes, tripPeriodTypes);
        } else {
            refactorFilterItems(this.tripPeriodTypes, tripPeriodTypes);
        }
        //
        if (triggerFilter.equals("startTimesScheduled")) {
            refactorTriggerFilterItems(this.startTimesScheduled, startTimesScheduled);
        } else {
            refactorFilterItems(this.startTimesScheduled, startTimesScheduled);
        }
        //
        if (triggerFilter.equals("startTimesActual")) {
            refactorTriggerFilterItems(this.startTimesActual, startTimesActual);
        } else {
            refactorFilterItems(this.startTimesActual, startTimesActual);
        }
        //
        if (triggerFilter.equals("arrivalTimesScheduled")) {
            refactorTriggerFilterItems(this.arrivalTimesScheduled, arrivalTimesScheduled);
        } else {
            refactorFilterItems(this.arrivalTimesScheduled, arrivalTimesScheduled);
        }
        //
        if (triggerFilter.equals("arrivalTimesActual")) {
            refactorTriggerFilterItems(this.arrivalTimesActual, arrivalTimesActual);
        } else {
            refactorFilterItems(this.arrivalTimesActual, arrivalTimesActual);
        }
        //
        if (triggerFilter.equals("tripPeriodTimesScheduled")) {
            refactorTriggerFilterItems(this.tripPeriodTimesScheduled, tripPeriodTimesScheduled);
        } else {
            refactorFilterItems(this.tripPeriodTimesScheduled, tripPeriodTimesScheduled);
        }
        //
        if (triggerFilter.equals("tripPeriodTimesActual")) {
            refactorTriggerFilterItems(this.tripPeriodTimesActual, tripPeriodTimesActual);
        } else {
            refactorFilterItems(this.tripPeriodTimesActual, tripPeriodTimesActual);
        }
        //
        if (triggerFilter.equals("tripPeriodTimeDifferences")) {
            refactorTriggerFilterItems(this.tripPeriodTimeDifferences, tripPeriodTimeDifferences);
        } else {
            refactorFilterItems(this.tripPeriodTimeDifferences, tripPeriodTimeDifferences);
        }

        return this;
    }

    private void refactorTriggerFilterItems(TreeMap<String, Boolean> filterTreeMap, ArrayList<String> filteredItems) {

        for (Map.Entry<String, Boolean> entry : filterTreeMap.entrySet()) {
            if (filteredItems == null) {
                entry.setValue(Boolean.FALSE);
            } else {
                if (filteredItems.contains(entry.getKey())) {
                    entry.setValue(Boolean.TRUE);
                } else {
                    entry.setValue(Boolean.FALSE);
                }
            }
        }
    }

    private void refactorFilterItems(TreeMap<String, Boolean> filterTreeMap, ArrayList<String> filteredItems) {

        filterTreeMap = new TreeMap<>();
        if (filteredItems != null) {
            for (String filteredItem : filteredItems) {
                filterTreeMap.put(filteredItem, Boolean.TRUE);
            }
        }
    }

}
