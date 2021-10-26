/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Michail Sitmalidis
 */
public class GuarantyTripsData extends IntervalTripPeriod {

    private String dateStamp;
    private String routeNumber;

    private String aPoint;

    private String guarantyType;
    private short exodusScheduled;
    private short exodusActual;
    private LocalDateTime guarantyStartTimeScheduled;
    private LocalDateTime guarantyStartTimeActual;

    private LocalDateTime abSubguarantyTripStartTimeScheduled;
    private short abSubguarantyExodusScheduled;

    private LocalDateTime abGuarantyTripStartTimeScheduled;
    private short abGuarantyExodusScheduled;

    private LocalDateTime abSubguarantyTripStartTimeActual;
    private short abSubguarantyExodusActual;

    private LocalDateTime abGuarantyTripStartTimeActual;
    private short abGuarantyExodusActual;
    //------------
    private String bPoint;

    private LocalDateTime baSubguarantyTripStartTimeScheduled;
    private short baSubguarantyExodusScheduled;

    private LocalDateTime baGuarantyTripStartTimeScheduled;
    private short baGuarantyExodusScheduled;

    private LocalDateTime baSubguarantyTripStartTimeActual;
    private short baSubguarantyExodusActual;

    private LocalDateTime baGuarantyTripStartTimeActual;
    private short baGuarantyExodusActual;

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public String getRouteNumber() {
        return routeNumber;
    }

    public void setRouteNumber(String routeNumber) {
        this.routeNumber = routeNumber;
    }

    public String getaPoint() {
        return aPoint;
    }

    public void setaPoint(String aPoint) {
        this.aPoint = aPoint;
    }

    public LocalDateTime getAbSubguarantyTripStartTimeScheduled() {
        return abSubguarantyTripStartTimeScheduled;
    }

    public String getAbSubguarantyTripStartTimeScheduledString() {
        if (abSubguarantyTripStartTimeScheduled == null) {
            return "errororo";
        }
        return abSubguarantyTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    }

    public void setAbSubguarantyTripStartTimeScheduled(LocalDateTime abSubguarantyTripStartTimeScheduled) {
        this.abSubguarantyTripStartTimeScheduled = abSubguarantyTripStartTimeScheduled;
    }

    public short getAbSubguarantyExodusScheduled() {
        return abSubguarantyExodusScheduled;
    }

    public void setAbSubguarantyExodusScheduled(short abSubguarantyExodusScheduled) {
        this.abSubguarantyExodusScheduled = abSubguarantyExodusScheduled;
    }

    public LocalDateTime getAbGuarantyTripStartTimeScheduled() {
        return abGuarantyTripStartTimeScheduled;
    }

    public String getAbGuarantyTripStartTimeScheduledString() {
        return abGuarantyTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setAbGuarantyTripStartTimeScheduled(LocalDateTime abGuarantyTripStartTimeScheduled) {
        this.abGuarantyTripStartTimeScheduled = abGuarantyTripStartTimeScheduled;
    }

    public short getAbGuarantyExodusScheduled() {
        return abGuarantyExodusScheduled;
    }

    public void setAbGuarantyExodusScheduled(short abGuarantyExodusScheduled) {
        this.abGuarantyExodusScheduled = abGuarantyExodusScheduled;
    }

    public LocalDateTime getAbSubguarantyTripstartTimeActual() {
        return abSubguarantyTripStartTimeActual;
    }

    public String getAbSubguarantyTripStartTimeActualString() {
        if (abSubguarantyTripStartTimeActual == null) {
            return "err";
        }
        return abSubguarantyTripStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setAbSubguarantyTripstartTimeActual(LocalDateTime abSubguarantyTripstartTimeActual) {
        this.abSubguarantyTripStartTimeActual = abSubguarantyTripstartTimeActual;
    }

    public short getAbSubguarantyExodusActual() {
        return abSubguarantyExodusActual;
    }

    public void setAbSubguarantyExodusActual(short abSubguarantyExodusActual) {
        this.abSubguarantyExodusActual = abSubguarantyExodusActual;
    }

    public LocalDateTime getAbGuarantyTripStartTimeActual() {
        return abGuarantyTripStartTimeActual;
    }

    public String getAbGuarantyTripStartTimeActualString() {
        return abGuarantyTripStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));

    }

    public void setAbGuarantyTripstartTimeActual(LocalDateTime abGuarantyTripstartTimeActual) {
        this.abGuarantyTripStartTimeActual = abGuarantyTripstartTimeActual;
    }

    public short getAbGuarantyExodusActual() {
        return abGuarantyExodusActual;
    }

    public void setAbGuarantyExodusActual(short abGuarantyExodusActual) {
        this.abGuarantyExodusActual = abGuarantyExodusActual;
    }

    public String getbPoint() {
        return bPoint;
    }

    public void setbPoint(String bPoint) {
        this.bPoint = bPoint;
    }

    public LocalDateTime getBaSubguarantyTripStartTimeScheduled() {
        return baSubguarantyTripStartTimeScheduled;
    }

    public String getBaSubguarantyTripStartTimeScheduledString() {
        if (baSubguarantyTripStartTimeScheduled == null) {
            return "BaSubguarant Error";
        }
        return baSubguarantyTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaSubguarantyTripStartTimeScheduled(LocalDateTime baSubguarantyTripStartTimeScheduled) {
        this.baSubguarantyTripStartTimeScheduled = baSubguarantyTripStartTimeScheduled;
    }

    public short getBaSubguarantyExodusScheduled() {
        return baSubguarantyExodusScheduled;
    }

    public void setBaSubguarantyExodusScheduled(short baSubguarantyExodusScheduled) {
        this.baSubguarantyExodusScheduled = baSubguarantyExodusScheduled;
    }

    public LocalDateTime getBaGuarantyTripstartTimeScheduled() {
        return baGuarantyTripStartTimeScheduled;
    }

    public String getBaGuarantyTripStartTimeScheduledString() {
        if (baGuarantyTripStartTimeScheduled == null) {
            return "BAGyarabty Error";
        }
        return baGuarantyTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaGuarantyTripstartTimeScheduled(LocalDateTime baGuarantyTripstartTimeScheduled) {
        this.baGuarantyTripStartTimeScheduled = baGuarantyTripstartTimeScheduled;
    }

    public short getBaGuarantyExodusScheduled() {
        return baGuarantyExodusScheduled;
    }

    public void setBaGuarantyExodusScheduled(short baGuarantyExodusScheduled) {
        this.baGuarantyExodusScheduled = baGuarantyExodusScheduled;
    }

    public LocalDateTime getBaSubguarantyTripstartTimeActual() {
        return baSubguarantyTripStartTimeActual;
    }

    public String getBaSubguarantyTripStartTimeActualString() {
        if (baSubguarantyTripStartTimeActual == null) {
            return "errrr";
        }
        return baSubguarantyTripStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaSubguarantyTripstartTimeActual(LocalDateTime baSubguarantyTripstartTimeActual) {
        this.baSubguarantyTripStartTimeActual = baSubguarantyTripstartTimeActual;
    }

    public short getBaSubguarantyExodusActual() {
        return baSubguarantyExodusActual;
    }

    public void setBaSubguarantyExodusActual(short baSubguarantyExodusActual) {
        this.baSubguarantyExodusActual = baSubguarantyExodusActual;
    }

    public LocalDateTime getBaGuarantyTripstartTimeActual() {
        return baGuarantyTripStartTimeActual;
    }

    public String getBaGuarantyTripStartTimeActualString() {
        return baGuarantyTripStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaGuarantyTripstartTimeActual(LocalDateTime baGuarantyTripstartTimeActual) {
        this.baGuarantyTripStartTimeActual = baGuarantyTripstartTimeActual;
    }

    public short getBaGuarantyExodusActual() {
        return baGuarantyExodusActual;
    }

    public void setBaGuarantyExodusActual(short baGuarantyExodusActual) {
        this.baGuarantyExodusActual = baGuarantyExodusActual;
    }

    public String getGuarantyType() {
        return guarantyType;
    }

    public void setGuarantyType(String guarantyType) {
        this.guarantyType = guarantyType;
    }

    public short getExodusScheduled() {
        return exodusScheduled;
    }

    public void setExodusScheduled(short exodusScheduled) {
        this.exodusScheduled = exodusScheduled;
    }

    public short getExodusActual() {
        return exodusActual;
    }

    public void setExodusActual(short exodusActual) {
        this.exodusActual = exodusActual;
    }

    public LocalDateTime getGuarantyStartTimeScheduled() {
        return guarantyStartTimeScheduled;
    }

    public void setGuarantyStartTimeScheduled(LocalDateTime guarantyStartTimeScheduled) {
        this.guarantyStartTimeScheduled = guarantyStartTimeScheduled;
    }

    public LocalDateTime getGuarantyStartTimeActual() {
        return guarantyStartTimeActual;
    }

    public void setGuarantyStartTimeActual(LocalDateTime guarantyStartTimeActual) {
        this.guarantyStartTimeActual = guarantyStartTimeActual;
    }

    public String getGuarantyStartTimeScheduledString() {
        return guarantyStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getGuarantyStartTimeActualString() {
        if (guarantyStartTimeActual == null) {
            return "";
        }
        return guarantyStartTimeActual.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
