package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class TripVoucher {

    private String number;
    private short baseNumber;
    private String busNumber;
    private String busType;
    private String driverNumber;
    private String driverName;

    private LocalDateTime baseLeavingTimeScheduled;
    private LocalDateTime baseLeavingTimeActual;
    private LocalDateTime baseLeavingTimeRedacted;

    private LocalDateTime baseReturnTimeScheduled;
    private LocalDateTime baseReturnTimeActual;
    private LocalDateTime baseReturnTimeRedacted;

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

    public LocalDateTime getBaseLeavingTimeScheduled() {
        return baseLeavingTimeScheduled;
    }

    public void setBaseLeavingTimeScheduled(LocalDateTime baseLeavingTimeScheduled) {
        this.baseLeavingTimeScheduled = baseLeavingTimeScheduled;
    }

    public LocalDateTime getBaseLeavingTimeActual() {
        return baseLeavingTimeActual;
    }

    public void setBaseLeavingTimeActual(LocalDateTime baseLeavingTimeActual) {
        this.baseLeavingTimeActual = baseLeavingTimeActual;
    }

    public LocalDateTime getBaseLeavingTimeRedacted() {
        return baseLeavingTimeRedacted;
    }

    public void setBaseLeavingTimeRedacted(LocalDateTime baseLeavingTimeRedacted) {
        this.baseLeavingTimeRedacted = baseLeavingTimeRedacted;
    }

    public LocalDateTime getBaseReturnTimeScheduled() {
        return baseReturnTimeScheduled;
    }

    public void setBaseReturnTimeScheduled(LocalDateTime baseReturnTimeScheduled) {
        this.baseReturnTimeScheduled = baseReturnTimeScheduled;
    }

    public LocalDateTime getBaseReturnTimeActual() {
        return baseReturnTimeActual;
    }

    public void setBaseReturnTimeActual(LocalDateTime baseReturnTimeActual) {
        this.baseReturnTimeActual = baseReturnTimeActual;
    }

    public LocalDateTime getBaseReturnTimeRedacted() {
        return baseReturnTimeRedacted;
    }

    public void setBaseReturnTimeRedacted(LocalDateTime baseReturnTimeRedacted) {
        this.baseReturnTimeRedacted = baseReturnTimeRedacted;
    }

//here i will write tripPeriodsArrayList sorting method
}
