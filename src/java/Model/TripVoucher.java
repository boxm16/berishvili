package Model;

import java.util.ArrayList;

public class TripVoucher {

    private String number;
    private short baseNumber;
    private String busNumber;
    private String busType;
    private String driverNumber;
    private String driverName;
    private String notes;
    private ArrayList<TripPeriod> tripPeriods;

    public TripVoucher() {
        this.tripPeriods = new ArrayList();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getDriverNumber() {
        return driverNumber;
    }

    public void setDriverNumber(String driverNumber) {
        this.driverNumber = driverNumber;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public ArrayList<TripPeriod> getTripPeriods() {
        return tripPeriods;
    }

    public void setTripPeriods(ArrayList<TripPeriod> tripPeriods) {
        this.tripPeriods = tripPeriods;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }
    
    

}
