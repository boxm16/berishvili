package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class GuarantyRoute extends Route {

    private String dateStamp;
    private String busType;
    private TreeMap<Short, GuarantyExodus> exoduses;
    //-------------
    private LocalDateTime abGuarantyTripPeriodStartTimeScheduled;
    private LocalDateTime abSubguarantyTripPeriodStartTimeScheduled;
    private LocalDateTime baGuarantyTripPeriodStartTimeScheduled;
    private LocalDateTime baSubguarantyTripPeriodStartTimeScheduled;

    public GuarantyRoute() {
        this.exoduses = new TreeMap<>();
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public TreeMap<Short, GuarantyExodus> getExoduses() {
        return exoduses;
    }

    public void setExoduses(TreeMap<Short, GuarantyExodus> exoduses) {
        this.exoduses = exoduses;
    }

    //---------
    public int getBusCount() {
        return this.exoduses.size();
    }

    public LocalDateTime getAbGuarantyTripPeriodStartTimeScheduled() {
        return abGuarantyTripPeriodStartTimeScheduled;
    }

    public String getAbGuarantyTripPeriodStartTimeScheduledString() {
        return abGuarantyTripPeriodStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setAbGuarantyTripPeriodStartTimeScheduled(LocalDateTime abGuarantyTripPeriodStartTimeScheduled) {
        this.abGuarantyTripPeriodStartTimeScheduled = abGuarantyTripPeriodStartTimeScheduled;
    }

    public LocalDateTime getAbSubguarantyTripPeriodStartTimeScheduled() {
        return abSubguarantyTripPeriodStartTimeScheduled;
    }

    public String getAbSubguarantyTripPeriodStartTimeScheduledString() {
        return abSubguarantyTripPeriodStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setAbSubguarantyTripPeriodStartTimeScheduled(LocalDateTime abSubguarantyTripPeriodStartTimeScheduled) {
        this.abSubguarantyTripPeriodStartTimeScheduled = abSubguarantyTripPeriodStartTimeScheduled;
    }

    public String getBaGuarantyTripPeriodStartTimeScheduledString() {
        return baGuarantyTripPeriodStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setBaGuarantyTripPeriodStartTimeScheduled(LocalDateTime baGuarantyTripPeriodStartTimeScheduled) {
        this.baGuarantyTripPeriodStartTimeScheduled = baGuarantyTripPeriodStartTimeScheduled;
    }

    public String getBaSubguarantyTripPeriodStartTimeScheduledString() {
        return baSubguarantyTripPeriodStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void setBaSubguarantyTripPeriodStartTimeScheduled(LocalDateTime baSubguarantyTripPeriodStartTimeScheduled) {
        this.baSubguarantyTripPeriodStartTimeScheduled = baSubguarantyTripPeriodStartTimeScheduled;
    }

}
