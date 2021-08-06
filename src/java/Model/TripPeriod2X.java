package Model;

public class TripPeriod2X extends TripPeriod {

    private String routeNumber;
    private String dateStamp;
    private String busNumber;
    private short exodusNumber;
    private String driverName;

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public short getExodusNumber() {
        return exodusNumber;
    }

    public void setExodusNumber(short exodusNumber) {
        this.exodusNumber = exodusNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public Double getStartTimeScheduledExcelFormat() {
        if (getStartTimeScheduled() == null) {
            return null;
        }
        long h = getStartTimeScheduled().getHour();
        long m = getStartTimeScheduled().getMinute();
        long s = getStartTimeScheduled().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getStartTimeActualExcelFormat() {
        if (getStartTimeActual() == null) {
            return null;
        }
        long h = getStartTimeActual().getHour();
        long m = getStartTimeActual().getMinute();
        long s = getStartTimeActual().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getArrivaltTimeScheduledExcelFormat() {
        if (getArrivalTimeScheduled() == null) {
            return null;
        }
        long h = getArrivalTimeScheduled().getHour();
        long m = getArrivalTimeScheduled().getMinute();
        long s = getArrivalTimeScheduled().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

    public Double getArrivalTimeActualExcelFormat() {
        if (getArrivalTimeActual() == null) {
            return null;
        }
        long h = getArrivalTimeActual().getHour();
        long m = getArrivalTimeActual().getMinute();
        long s = getArrivalTimeActual().getSecond();
        double ss = (h * 3600) + (m * 60) + s;
        return ss / 86400;
    }

}
