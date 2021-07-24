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

}
