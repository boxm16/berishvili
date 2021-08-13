package Model;

import java.time.Duration;

public class RouteAverages {

    private String routeNumber;
    private Duration abTripPeriodTimeStandart;
    private String abTripPeriodTimeMultipleStandart;
    private int abLowCount;
    private long abLowTotal;
    private Duration abLowAverage;
    private int abHighCount;
    private long abHighTotal;
    private Duration abHighAverage;
    private Duration baTripPeriodTimeStandart;
    private String baTripPeriodTimeMultipleStandart;
    private int baLowCount;
    private long baLowTotal;
    private Duration baLowAverage;
    private int baHighCount;
    private long baHighTotal;
    private Duration baHighAverage;
    private int abLowAndHighCount;
    private int baLowAndHighCount;
    private Duration abLowAndHighAverage;
    private int baLowAndHighAverage;

    public RouteAverages() {
        abTripPeriodTimeStandart = Duration.ZERO;
        baTripPeriodTimeStandart = Duration.ZERO;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public int getAbLowCount() {
        return abLowCount;
    }

    public void setAbLowCount(int abLowCount) {
        this.abLowCount = abLowCount;
    }

    public int getBaLowCount() {
        return baLowCount;
    }

    public void setBaLowCount(int baLowCount) {
        this.baLowCount = baLowCount;
    }

    public long getAbLowTotal() {
        return abLowTotal;
    }

    public void setAbLowTotal(long abLowTotal) {
        this.abLowTotal = abLowTotal;
    }

    public long getBaLowTotal() {
        return baLowTotal;
    }

    public void setBaLowTotal(long baLowTotal) {
        this.baLowTotal = baLowTotal;
    }

    public int getAbHighCount() {
        return abHighCount;
    }

    public void setAbHighCount(int abHighCount) {
        this.abHighCount = abHighCount;
    }

    public long getAbHighTotal() {
        return abHighTotal;
    }

    public void setAbHighTotal(long abHighTotal) {
        this.abHighTotal = abHighTotal;
    }

    public int getBaHighCount() {
        return baHighCount;
    }

    public void setBaHighCount(int baHighCount) {
        this.baHighCount = baHighCount;
    }

    public long getBaHighTotal() {
        return baHighTotal;
    }

    public void setBaHighTotal(long baHighTotal) {
        this.baHighTotal = baHighTotal;
    }

    public Duration getAbTripPeriodTimeStandart() {
        return abTripPeriodTimeStandart;
    }

    public void setAbTripPeriodTimeStandart(Duration abTripPeriodTimeStandart) {
        this.abTripPeriodTimeStandart = abTripPeriodTimeStandart;
    }

    public Duration getBaTripPeriodTimeStandart() {
        return baTripPeriodTimeStandart;
    }

    public void setBaTripPeriodTimeStandart(Duration baTripPeriodTimeStandart) {
        this.baTripPeriodTimeStandart = baTripPeriodTimeStandart;
    }

    public String getAbTripPeriodTimeMultipleStandart() {
        return abTripPeriodTimeMultipleStandart;
    }

    public void setAbTripPeriodTimeMultipleStandart(String abTripPeriodTimeMultipleStandart) {
        this.abTripPeriodTimeMultipleStandart = abTripPeriodTimeMultipleStandart;
    }

    public String getBaTripPeriodTimeMultipleStandart() {
        return baTripPeriodTimeMultipleStandart;
    }

    public void setBaTripPeriodTimeMultipleStandart(String baTripPeriodTimeMultipleStandart) {
        this.baTripPeriodTimeMultipleStandart = baTripPeriodTimeMultipleStandart;
    }

}
