package Model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TreeMap;

public class GuarantyRoute extends BasicRoute {

    private String dateStamp;
    private String busType;
    private TreeMap<Short, GuarantyExodus> exoduses;
    private String aPoint;
    private String bPoint;
    private String scheme;
    private short baseNumber;
    //-------------
    private Duration standardIntervalTime;
    private Duration standardTripPeriodTime;
    private Duration standardBreakTime;
    private LocalDateTime abGuarantyTripPeriodStartTimeScheduled;
    private LocalDateTime abSubguarantyTripPeriodStartTimeScheduled;
    private LocalDateTime baGuarantyTripPeriodStartTimeScheduled;
    private LocalDateTime baSubguarantyTripPeriodStartTimeScheduled;
    private int totalRaces;
    private LocalDateTime routeStartTime;
    private LocalDateTime routeEndTime;

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

    public Double getAbGuarantyTripPeriodStartTimeScheduledExcelFormat() {
        long h = abGuarantyTripPeriodStartTimeScheduled.getHour();
        long m = abGuarantyTripPeriodStartTimeScheduled.getMinute();
        long s = abGuarantyTripPeriodStartTimeScheduled.getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getAbSubguarantyTripPeriodStartTimeScheduledExcelFormat() {
        long h = abSubguarantyTripPeriodStartTimeScheduled.getHour();
        long m = abSubguarantyTripPeriodStartTimeScheduled.getMinute();
        long s = abSubguarantyTripPeriodStartTimeScheduled.getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getBaGuarantyTripPeriodStartTimeScheduledExcelFormat() {
        if (baGuarantyTripPeriodStartTimeScheduled == null) {
            return null;
        }
        long h = baGuarantyTripPeriodStartTimeScheduled.getHour();
        long m = baGuarantyTripPeriodStartTimeScheduled.getMinute();
        long s = baGuarantyTripPeriodStartTimeScheduled.getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getBaSubguarantyTripPeriodStartTimeScheduledExcelFormat() {
        if (baSubguarantyTripPeriodStartTimeScheduled == null) {
            return null;
        }
        long h = baSubguarantyTripPeriodStartTimeScheduled.getHour();
        long m = baSubguarantyTripPeriodStartTimeScheduled.getMinute();
        long s = baSubguarantyTripPeriodStartTimeScheduled.getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Duration getStandardIntervalTime() {
        return standardIntervalTime;
    }

    public void setStandardIntervalTime(Duration standardIntervalTime) {
        this.standardIntervalTime = standardIntervalTime;
    }

    public Duration getStandardTripPeriodTime() {
        return standardTripPeriodTime;
    }

    public void setStandardTripPeriodTime(Duration standardTripPeriodTime) {
        this.standardTripPeriodTime = standardTripPeriodTime;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public void setTotalRaces(int totalRaces) {
        this.totalRaces = totalRaces;
    }

    public LocalDateTime getRouteStartTime() {
        return routeStartTime;
    }

    public Double getRouteStartTimeExcelFormat() {
        if (routeStartTime == null) {
            return null;
        }
        long h = routeStartTime.getHour();
        long m = routeStartTime.getMinute();
        long s = routeStartTime.getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;

    }

    public void setRouteStartTime(LocalDateTime routeStartTime) {
        this.routeStartTime = routeStartTime;
    }

    public LocalDateTime getRouteEndTime() {
        return routeEndTime;
    }

    public Double getRouteEndTimeExcelFormat() {
        if (routeEndTime == null) {
            return null;
        }
        LocalDateTime pointZero = LocalDateTime.of(1970, Month.JANUARY, 01, 00, 00, 00);

        long intervalSeconds = Duration.between(pointZero, routeEndTime).getSeconds();
        return intervalSeconds * 0.1 / 8640;
    }

    public void setRouteEndTime(LocalDateTime routeEndTime) {
        this.routeEndTime = routeEndTime;
    }

    public Double getLastBaseReturnTime() {
        LocalDateTime lastBaseReturnTime = null;
        for (GuarantyExodus exodus : this.exoduses.values()) {
            if (lastBaseReturnTime == null) {
                lastBaseReturnTime = LocalDateTime.of(1970, Month.JANUARY, 01, 00, 00, 00);
            }
            ArrayList<GuarantyTripPeriod> tripPeriods = exodus.getGuarantyTripPeriods();
            GuarantyTripPeriod baseReturnTripPeriod = tripPeriods.get(tripPeriods.size() - 1);
            LocalDateTime baseReturnTripPeriodArrialTime = baseReturnTripPeriod.getArrivalTimeScheduled();
            if (lastBaseReturnTime.isBefore(baseReturnTripPeriodArrialTime)) {
                lastBaseReturnTime = baseReturnTripPeriodArrialTime;
            }
        }
        LocalDateTime pointZero = LocalDateTime.of(1970, Month.JANUARY, 01, 00, 00, 00);
        long intervalSeconds = Duration.between(pointZero, lastBaseReturnTime).getSeconds();
        return intervalSeconds * 0.1 / 8640;
    }

    public String getaPoint() {
        return aPoint;
    }

    public void setaPoint(String aPoint) {
        this.aPoint = aPoint;
    }

    public String getbPoint() {
        return bPoint;
    }

    public void setbPoint(String bPoint) {
        this.bPoint = bPoint;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public double getFirstBaseLeavingTime() {
        LocalDateTime firstBaseLeavingTime = null;
        for (GuarantyExodus exodus : this.exoduses.values()) {
            if (firstBaseLeavingTime == null) {
                firstBaseLeavingTime = LocalDateTime.of(1970, Month.JANUARY, 31, 23, 59, 59);
            }
            ArrayList<GuarantyTripPeriod> tripPeriods = exodus.getGuarantyTripPeriods();
            GuarantyTripPeriod baseLeavingTripPeriod = tripPeriods.get(0);
            LocalDateTime baseLeavingTripPeriodStartTime = baseLeavingTripPeriod.getStartTimeScheduled();
            if (firstBaseLeavingTime.isAfter(baseLeavingTripPeriodStartTime)) {
                firstBaseLeavingTime = baseLeavingTripPeriodStartTime;
            }
        }
        LocalDateTime pointZero = LocalDateTime.of(1970, Month.JANUARY, 01, 00, 00, 00);
        long intervalSeconds = Duration.between(pointZero, firstBaseLeavingTime).getSeconds();
        return intervalSeconds * 0.1 / 8640;

    }

    public Duration getStandardBreakTime() {
        return standardBreakTime;
    }

    public void setStandardBreakTime(Duration standardBreakTime) {
        this.standardBreakTime = standardBreakTime;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    
    
}
