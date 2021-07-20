package Model;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class TripPeriodsFilter {

    private TreeMap<String, String> routeNumbers;
    private TreeMap<String, String> dateStamps;
    private TreeMap<String, String> busNumbers;
    private TreeMap<String, String> exodusNumbers;
    private TreeMap<String, String> driverNames;
    private TreeMap<String, String> tripPeriodTypes;
    private TreeMap<String, String> startTimesScheduled;
    private TreeMap<String, String> startTimesActual;
    private TreeMap<String, String> tripPeriodTimesScheduled;
    private TreeMap<String, String> arrivalTimesScheduled;
    private TreeMap<String, String> tripPeriodTimesActual;
    private TreeMap<String, String> arrivalTimesActual;
    private TreeMap<String, String> tripPeriodTimeDifferences;

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
        this.routeNumbers.put(routeNumber, "checked");
    }

    public void addDateStamp(String dateStamp) {
        this.dateStamps.put(dateStamp, "checked");
    }

    public void addBusNumber(String busNumber) {
        this.busNumbers.put(busNumber, "checked");
    }

    public void addExodusNumber(String exodusNumber) {
        this.exodusNumbers.put(exodusNumber, "checked");
    }

    public void addDriverName(String driverName) {
        this.driverNames.put(driverName, "checked");
    }

    public void addTripPeriodType(String tripPeriodType) {

        switch (tripPeriodType) {
            case "baseLeaving_A":
                this.tripPeriodTypes.put("ბაზა_A", "checked");
            case "baseLeaving_B":
                this.tripPeriodTypes.put("ბაზა_B", "checked");
            case "break":
                this.tripPeriodTypes.put("შესვენება", "checked");
            case "ab":
                this.tripPeriodTypes.put("A_B", "checked");
            case "ba":
                this.tripPeriodTypes.put("B_A", "checked");
            case "A_baseReturn":
                this.tripPeriodTypes.put("A_ბაზა", "checked");
            case "B_baseReturn":
                this.tripPeriodTypes.put("B_ბაზა", "checked");

        }
    }

    public void addStartTimeScheduled(String startTimeScheduled) {
        this.startTimesScheduled.put(startTimeScheduled, "checked");
    }

    public void addStartTimeActual(String startTimeActual) {
        if (startTimeActual != null) {
            this.startTimesActual.put(startTimeActual, "checked");
        }
    }

    public void addArrivalTimeScheduled(String arrivalTimeScheduled) {
        this.arrivalTimesScheduled.put(arrivalTimeScheduled, "checked");
    }

    public void addArrivalTimeActual(String arrivalTimesActual) {
        if (arrivalTimesActual != null) {
            this.arrivalTimesActual.put(arrivalTimesActual, "checked");
        }
    }

    //-------------------
    public TreeMap<String, String> getRouteNumbers() {
        return routeNumbers;
    }

    public void setRouteNumbers(TreeMap<String, String> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public TreeMap<String, String> getDateStamps() {
        return dateStamps;
    }

    public void setDateStamps(TreeMap<String, String> dateStamps) {
        this.dateStamps = dateStamps;
    }

    public TreeMap<String, String> getBusNumbers() {
        return busNumbers;
    }

    public void setBusNumbers(TreeMap<String, String> busNumbers) {
        this.busNumbers = busNumbers;
    }

    public TreeMap<String, String> getExodusNumbers() {
        return exodusNumbers;
    }

    public void setExodusNumbers(TreeMap<String, String> exodusNumbers) {
        this.exodusNumbers = exodusNumbers;
    }

    public TreeMap<String, String> getDriverNames() {
        return driverNames;
    }

    public void setDriverNames(TreeMap<String, String> driverNames) {
        this.driverNames = driverNames;
    }

    public TreeMap<String, String> getTripPeriodTypes() {
        return tripPeriodTypes;
    }

    public void setTripPeriodTypes(TreeMap<String, String> tripPeriodTypes) {
        this.tripPeriodTypes = tripPeriodTypes;
    }

    public TreeMap<String, String> getStartTimesScheduled() {
        return startTimesScheduled;
    }

    public void setStartTimesScheduled(TreeMap<String, String> startTimesScheduled) {
        this.startTimesScheduled = startTimesScheduled;
    }

    public TreeMap<String, String> getStartTimesActual() {
        return startTimesActual;
    }

    public void setStartTimesActual(TreeMap<String, String> startTimesActual) {
        this.startTimesActual = startTimesActual;
    }

    public TreeMap<String, String> getTripPeriodTimesScheduled() {
        return tripPeriodTimesScheduled;
    }

    public void setTripPeriodTimesScheduled(TreeMap<String, String> tripPeriodTimesScheduled) {
        this.tripPeriodTimesScheduled = tripPeriodTimesScheduled;
    }

    public TreeMap<String, String> getArrivalTimesScheduled() {
        return arrivalTimesScheduled;
    }

    public void setArrivalTimesScheduled(TreeMap<String, String> arrivalTimesScheduled) {
        this.arrivalTimesScheduled = arrivalTimesScheduled;
    }

    public TreeMap<String, String> getTripPeriodTimesActual() {
        return tripPeriodTimesActual;
    }

    public void setTripPeriodTimesActual(TreeMap<String, String> tripPeriodTimesActual) {
        this.tripPeriodTimesActual = tripPeriodTimesActual;
    }

    public TreeMap<String, String> getArrivalTimesActual() {
        return arrivalTimesActual;
    }

    public void setArrivalTimesActual(TreeMap<String, String> arrivalTimesActual) {
        this.arrivalTimesActual = arrivalTimesActual;
    }

    public TreeMap<String, String> getTripPeriodTimeDifferences() {
        return tripPeriodTimeDifferences;
    }

    public void setTripPeriodTimeDifferences(TreeMap<String, String> tripPeriodTimeDifferences) {
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

    private void refactorTriggerFilterItems(TreeMap<String, String> filterTreeMap, ArrayList<String> filteredItems) {

        for (Map.Entry<String, String> entry : filterTreeMap.entrySet()) {
            if (filteredItems == null) {
                entry.setValue("");
            } else {
                if (filteredItems.contains(entry.getKey())) {
                    entry.setValue("checked");
                } else {
                    entry.setValue("");
                }
            }
        }
    }

    private void refactorFilterItems(TreeMap<String, String> filterTreeMap, ArrayList<String> filteredItems) {

        filterTreeMap = new TreeMap<>();
        if (filteredItems != null) {
            for (String filteredItem : filteredItems) {
                filterTreeMap.put(filteredItem, "checked");
            }
        }
    }

}
