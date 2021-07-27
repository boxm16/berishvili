package Model;

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

    public void addRouteNumber(String routeNumber) {
        if (this.routeNumbers == null) {
            this.routeNumbers = new TreeMap<>();
        }
        this.routeNumbers.put(routeNumber, "checked");
    }

    public void addDateStamp(String dateStamp) {
        if (this.dateStamps == null) {
            this.dateStamps = new TreeMap<>();
        }
        this.dateStamps.put(dateStamp, "checked");
    }

    public void addBusNumber(String busNumber) {
        if (this.busNumbers == null) {
            this.busNumbers = new TreeMap<>();
        }
        this.busNumbers.put(busNumber, "checked");
    }

    public void addExodusNumber(String exodusNumber) {
        if (this.exodusNumbers == null) {
            this.exodusNumbers = new TreeMap<>();
        }
        this.exodusNumbers.put(exodusNumber, "checked");
    }

    public void addDriverName(String driverName) {
        if (this.driverNames == null) {
            this.driverNames = new TreeMap<>();
        }
        this.driverNames.put(driverName, "checked");
    }

    public void addTripPeriodType(String tripPeriodType) {
        if (this.tripPeriodTypes == null) {
            this.tripPeriodTypes = new TreeMap<>();
        }
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
//--------
            case "ბაზა_A":
                this.tripPeriodTypes.put("ბაზა_A", "checked");
            case "ბაზა_B":
                this.tripPeriodTypes.put("ბაზა_B", "checked");
            case "შესვენება":
                this.tripPeriodTypes.put("შესვენება", "checked");
            case "A_B":
                this.tripPeriodTypes.put("A_B", "checked");
            case "B_A":
                this.tripPeriodTypes.put("B_A", "checked");
            case "A_ბაზა":
                this.tripPeriodTypes.put("A_ბაზა", "checked");
            case "B_ბაზა":
                this.tripPeriodTypes.put("B_ბაზა", "checked");
        }
    }

    public void addStartTimeScheduled(String startTimeScheduled) {
        if (this.startTimesScheduled == null) {
            this.startTimesScheduled = new TreeMap<>();
        }
        this.startTimesScheduled.put(startTimeScheduled, "checked");
    }

    public void addStartTimeActual(String startTimeActual) {
        if (this.startTimesActual == null) {
            this.startTimesActual = new TreeMap<>();
        }
        if (startTimeActual != null) {
            this.startTimesActual.put(startTimeActual, "checked");
        }
    }

    public void addArrivalTimeScheduled(String arrivalTimeScheduled) {
        if (this.arrivalTimesScheduled == null) {
            this.arrivalTimesScheduled = new TreeMap<>();
        }
        this.arrivalTimesScheduled.put(arrivalTimeScheduled, "checked");
    }

    public void addArrivalTimeActual(String arrivalTimesActual) {
        if (this.arrivalTimesActual == null) {
            this.arrivalTimesActual = new TreeMap<>();
        }
        if (arrivalTimesActual != null) {
            this.arrivalTimesActual.put(arrivalTimesActual, "checked");
        }
    }
    //---------------------

    public TreeMap<String, String> getRouteNumbers() {
        return routeNumbers;
    }

    public TreeMap<String, String> getDateStamps() {
        return dateStamps;
    }

    public TreeMap<String, String> getBusNumbers() {
        return busNumbers;
    }

    public TreeMap<String, String> getExodusNumbers() {
        return exodusNumbers;
    }

    public TreeMap<String, String> getDriverNames() {
        return driverNames;
    }

    public TreeMap<String, String> getTripPeriodTypes() {
        return tripPeriodTypes;
    }

    public TreeMap<String, String> getStartTimesScheduled() {
        return startTimesScheduled;
    }

    public TreeMap<String, String> getStartTimesActual() {
        return startTimesActual;
    }

    public TreeMap<String, String> getTripPeriodTimesScheduled() {
        return tripPeriodTimesScheduled;
    }

    public TreeMap<String, String> getArrivalTimesScheduled() {
        return arrivalTimesScheduled;
    }

    public TreeMap<String, String> getTripPeriodTimesActual() {
        return tripPeriodTimesActual;
    }

    public TreeMap<String, String> getArrivalTimesActual() {
        return arrivalTimesActual;
    }

    public TreeMap<String, String> getTripPeriodTimeDifferences() {
        return tripPeriodTimeDifferences;
    }

    public void setRouteNumbers(TreeMap<String, String> routeNumbers) {
        this.routeNumbers = routeNumbers;
    }

    public void setDateStamps(TreeMap<String, String> dateStamps) {
        this.dateStamps = dateStamps;
    }

    public void setBusNumbers(TreeMap<String, String> busNumbers) {
        this.busNumbers = busNumbers;
    }

    public void setExodusNumbers(TreeMap<String, String> exodusNumbers) {
        this.exodusNumbers = exodusNumbers;
    }

    public void setDriverNames(TreeMap<String, String> driverNames) {
        this.driverNames = driverNames;
    }

    public void setTripPeriodTypes(TreeMap<String, String> tripPeriodTypes) {
        this.tripPeriodTypes = tripPeriodTypes;
    }

    public void setStartTimesScheduled(TreeMap<String, String> startTimesScheduled) {
        this.startTimesScheduled = startTimesScheduled;
    }

    public void setStartTimesActual(TreeMap<String, String> startTimesActual) {
        this.startTimesActual = startTimesActual;
    }

    public void setTripPeriodTimesScheduled(TreeMap<String, String> tripPeriodTimesScheduled) {
        this.tripPeriodTimesScheduled = tripPeriodTimesScheduled;
    }

    public void setArrivalTimesScheduled(TreeMap<String, String> arrivalTimesScheduled) {
        this.arrivalTimesScheduled = arrivalTimesScheduled;
    }

    public void setTripPeriodTimesActual(TreeMap<String, String> tripPeriodTimesActual) {
        this.tripPeriodTimesActual = tripPeriodTimesActual;
    }

    public void setArrivalTimesActual(TreeMap<String, String> arrivalTimesActual) {
        this.arrivalTimesActual = arrivalTimesActual;
    }

    public void setTripPeriodTimeDifferences(TreeMap<String, String> tripPeriodTimeDifferences) {
        this.tripPeriodTimeDifferences = tripPeriodTimeDifferences;
    }

    public TripPeriodsFilter getDeepCopy() {
        TripPeriodsFilter copy = new TripPeriodsFilter();
        if (this.routeNumbers != null) {
            for (Map.Entry<String, String> entry : this.routeNumbers.entrySet()) {
                String key = entry.getKey();
                copy.addRouteNumber(key);
            }
        }
        if (this.dateStamps != null) {
            for (Map.Entry<String, String> entry : this.dateStamps.entrySet()) {
                String key = entry.getKey();
                copy.addDateStamp(key);
            }
        }
        if (this.busNumbers != null) {
            for (Map.Entry<String, String> entry : this.busNumbers.entrySet()) {
                String key = entry.getKey();
                copy.addBusNumber(key);
            }
        }
        if (this.exodusNumbers != null) {
            for (Map.Entry<String, String> entry : this.exodusNumbers.entrySet()) {
                String key = entry.getKey();
                copy.addExodusNumber(key);
            }
        }
        if (this.driverNames != null) {
            for (Map.Entry<String, String> entry : this.driverNames.entrySet()) {
                String key = entry.getKey();
                copy.addDriverName(key);
            }
        }
        if (this.tripPeriodTypes != null) {
            for (Map.Entry<String, String> entry : this.tripPeriodTypes.entrySet()) {
                String key = entry.getKey();
                copy.addTripPeriodType(key);
            }
        }
        if (this.startTimesScheduled != null) {
            for (Map.Entry<String, String> entry : this.startTimesScheduled.entrySet()) {
                String key = entry.getKey();
                copy.addStartTimeScheduled(key);
            }
        }
        if (this.startTimesActual != null) {
            for (Map.Entry<String, String> entry : this.startTimesActual.entrySet()) {
                String key = entry.getKey();
                copy.addStartTimeActual(key);
            }
        }

        if (this.arrivalTimesScheduled != null) {
            for (Map.Entry<String, String> entry : this.arrivalTimesScheduled.entrySet()) {
                String key = entry.getKey();
                copy.addArrivalTimeScheduled(key);
            }
        }
        if (this.arrivalTimesActual != null) {
            for (Map.Entry<String, String> entry : this.arrivalTimesActual.entrySet()) {
                String key = entry.getKey();
                copy.addArrivalTimeActual(key);
            }
        }
        return copy;
    }

}
