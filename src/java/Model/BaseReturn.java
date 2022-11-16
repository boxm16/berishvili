/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Converter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class BaseReturn extends TripVoucher {

    private TreeMap<LocalDateTime, TripPeriod> tripPeriodsV3;

    private Duration lastHaltTimeScheduled;
    private Duration lastHaltTimeActual;
    private LocalDateTime baseReturnTripStartTimeScheduled;
    private LocalDateTime baseReturnTripStartTimeGPS;
    private LocalDateTime baseReturnTripArrivalTimeScheduled;
    private LocalDateTime baseReturnTripArrivalTimeGPS;
    private LocalDateTime baseReturnTripTimeFakeGPSString;
    private String misconduct;
    private Converter converter;

    public BaseReturn() {
        converter = new Converter();
        tripPeriodsV3 = new TreeMap<>();
        misconduct = "inherited";
    }

    public Duration getLastHaltTimeActual() {
        return lastHaltTimeActual;
    }

    public String getLastHaltTimeActualString() {
        if (lastHaltTimeActual == null) {
            return "";
        }
        return converter.convertDurationToString(lastHaltTimeActual);
    }

    public void setLastHaltTimeActual(Duration lastHaltTimeActual) {
        this.lastHaltTimeActual = lastHaltTimeActual;
    }

    public String getLastHaltTimeDifferenceString() {
        if (lastHaltTimeActual == null || lastHaltTimeScheduled == null) {
            return "";
        }
        return converter.convertDurationToString(lastHaltTimeActual.minus(lastHaltTimeScheduled));
    }

    public String getLastHaltTimeDifferenceColor() {
        return convertStringDurationToThreeColors(getLastHaltTimeDifferenceString());
    }

    public TreeMap<LocalDateTime, TripPeriod> getTripPeriodsV3() {
        return tripPeriodsV3;
    }

    public void setTripPeriodsV3(TreeMap<LocalDateTime, TripPeriod> tripPeriodsV3) {
        this.tripPeriodsV3 = tripPeriodsV3;
    }

    public Duration getLastHaltTimeScheduled() {
        return lastHaltTimeScheduled;
    }

    public void setLastHaltTimeScheduled(Duration lastHaltTimeScheduled) {
        this.lastHaltTimeScheduled = lastHaltTimeScheduled;
    }

    public String getLastHaltTimeScheduledString() {
        if (lastHaltTimeScheduled == null) {
            return "";
        }
        return converter.convertDurationToString(lastHaltTimeScheduled);
    }

    public String getBaseReturnTimeScheduledString() {
        if (super.getBaseReturnTimeScheduled() == null) {
            return "";
        }
        return super.getBaseReturnTimeScheduled().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String getBaseReturnTimeConfirmedString() {
        if (super.getBaseReturnTimeRedacted() != null) {
            return super.getBaseReturnTimeRedacted().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        } else {
            if (super.getBaseReturnTimeActual() != null) {
                return super.getBaseReturnTimeActual().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            }
        }
        return "";
    }

    public String getBaseReturnTimeDifferenceString() {
        if (super.getBaseReturnTimeScheduled() != null) {
            LocalDateTime baseReturnConfirmedTime = null;
            if (super.getBaseReturnTimeRedacted() != null) {
                baseReturnConfirmedTime = super.getBaseReturnTimeRedacted();
            } else if (super.getBaseReturnTimeActual() != null) {
                baseReturnConfirmedTime = super.getBaseReturnTimeActual();
            }
            if (baseReturnConfirmedTime != null) {
                return converter.convertDurationToString(Duration.between(super.getBaseReturnTimeScheduled(), baseReturnConfirmedTime));
            } else {
                return "";
            }
        }
        return "";
    }

    public LocalDateTime getBaseReturnTripStartTimeScheduled() {
        return baseReturnTripStartTimeScheduled;
    }

    public String getBaseReturnTripStartTimeScheduledString() {
        if (baseReturnTripStartTimeScheduled == null) {
            return "";
        }
        return baseReturnTripStartTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseReturnTripStartTimeScheduled(LocalDateTime baseReturnTripStartTimeScheduled) {
        this.baseReturnTripStartTimeScheduled = baseReturnTripStartTimeScheduled;
    }

    public LocalDateTime getBaseReturnTripStartTimeGPS() {
        return baseReturnTripStartTimeGPS;
    }

    public String getBaseReturnTripStartTimeGPSString() {
        if (baseReturnTripStartTimeGPS == null) {
            return "";
        }
        return baseReturnTripStartTimeGPS.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseReturnTripStartTimeGPS(LocalDateTime baseReturnTripStartTimeActualGPS) {
        this.baseReturnTripStartTimeGPS = baseReturnTripStartTimeActualGPS;
    }

    public LocalDateTime getBaseReturnTripArrivalTimeScheduled() {
        return baseReturnTripArrivalTimeScheduled;
    }

    public String getBaseReturnTripArrivalTimeScheduledString() {
        if (baseReturnTripArrivalTimeScheduled == null) {
            return "";
        }
        return baseReturnTripArrivalTimeScheduled.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseReturnTripArrivalTimeScheduled(LocalDateTime baseReturnTripArrivalTimeScheduled) {
        this.baseReturnTripArrivalTimeScheduled = baseReturnTripArrivalTimeScheduled;
    }

    public LocalDateTime getBaseReturnTripArrivalTimeGPS() {
        return baseReturnTripArrivalTimeGPS;
    }

    public String getBaseReturnTripArrivalTimeGPSString() {
        if (baseReturnTripArrivalTimeGPS == null) {
            return "";
        }
        return baseReturnTripArrivalTimeGPS.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public void setBaseReturnTripArrivalTimeGPS(LocalDateTime baseReturnTripArrivalTimeActualGPS) {
        this.baseReturnTripArrivalTimeGPS = baseReturnTripArrivalTimeActualGPS;
    }

    public Duration getBaseReturnTripTimeScheduled() {

        return Duration.between(this.baseReturnTripStartTimeScheduled, this.baseReturnTripArrivalTimeScheduled);
    }

    public String getBaseReturnTripTimeScheduledString() {
        if (baseReturnTripStartTimeScheduled == null || baseReturnTripArrivalTimeScheduled == null) {
            return "";
        }

        return converter.convertDurationToString(Duration.between(this.baseReturnTripStartTimeScheduled, this.baseReturnTripArrivalTimeScheduled));
    }

    public String getBaseReturnTripTimeGPSString() {
        if (baseReturnTripStartTimeGPS == null || baseReturnTripArrivalTimeGPS == null) {
            return "";
        }
        return converter.convertDurationToString(Duration.between(this.baseReturnTripStartTimeGPS, this.baseReturnTripArrivalTimeGPS));

    }

    public String getBaseReturTripTimeDifferenceString() {
        if (baseReturnTripStartTimeGPS == null || baseReturnTripArrivalTimeGPS == null) {
            return "";
        }
        Duration baseReturnTripTimeGPS = Duration.between(this.baseReturnTripStartTimeGPS, this.baseReturnTripArrivalTimeGPS);
        return converter.convertDurationToString(baseReturnTripTimeGPS.minus(getBaseReturnTripTimeScheduled()));
    }

    public String getBaseReturGPSAndConfirmedTimeDifferenceString() {
        if (baseReturnTripArrivalTimeGPS == null) {
            if (getBaseReturnTripArrivalTimeFakeGPSString().equals("")) {
                return "";
            }

        }
        LocalDateTime baseReturnConfirmedTime = null;
        if (super.getBaseReturnTimeRedacted() != null) {
            baseReturnConfirmedTime = super.getBaseReturnTimeRedacted();
        } else {
            if (super.getBaseReturnTimeActual() != null) {
                baseReturnConfirmedTime = super.getBaseReturnTimeActual();
            }
        }
        if (baseReturnConfirmedTime == null) {
            return "";
        }
        if (baseReturnTripArrivalTimeGPS == null) {
            return converter.convertDurationToString(Duration.between(getBaseReturnTripArrivalTimeFakeGPS(), baseReturnConfirmedTime));

        }
        return converter.convertDurationToString(Duration.between(baseReturnTripArrivalTimeGPS, baseReturnConfirmedTime));
    }

    private String convertStringDurationToThreeColors(String stringDuration) {
        if (stringDuration == null || stringDuration.isEmpty()) {
            return "inherited";
        }
        String isoStr = stringDuration.replaceFirst(
                "^([+-]?)(\\d{2}):(\\d{2}):(\\d{2})$", "$1PT$2H$3M$4S");
        // Parse to Duration
        Duration differenceDuration = Duration.parse(isoStr);
        // if (differenceDuration.getSeconds() < 61 && differenceDuration.getSeconds() > -61) {
        //   return "inherited";
        // }
        if (differenceDuration.getSeconds() < 15*60) {
            return "inherited";
        }
        misconduct = "red";
        return "red";
    }

    public String getBaseReturTripTimeDifferenceColor() {
        return convertStringDurationToThreeColors(getBaseReturTripTimeDifferenceString());
    }

    public String getBaseReturGPSAndConfirmedTimeDifferenceColor() {
        return convertStringDurationToThreeColors(getBaseReturGPSAndConfirmedTimeDifferenceString());
    }

    public String getMisconduct() {
        return misconduct;
    }

    public void setMisconduct(String misconduct) {
        this.misconduct = misconduct;
    }

    //-----------------------fake -----------
    public LocalDateTime getBaseReturnTripArrivalTimeFakeGPS() {
        if (baseReturnTripStartTimeGPS == null) {
            return null;
        }

        if (baseReturnTripStartTimeGPS == null || baseReturnTripStartTimeScheduled == null || baseReturnTripArrivalTimeScheduled == null) {
            return null;
        }

        return baseReturnTripStartTimeGPS.plus(getBaseReturnTripTimeScheduled());
    }

    public String getBaseReturnTripArrivalTimeFakeGPSString() {
        if (baseReturnTripArrivalTimeGPS != null) {
            return "";
        }

        if (baseReturnTripStartTimeGPS == null || baseReturnTripStartTimeScheduled == null || baseReturnTripArrivalTimeScheduled == null) {
            return "";
        }

        return baseReturnTripStartTimeGPS.plus(getBaseReturnTripTimeScheduled()).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

}
