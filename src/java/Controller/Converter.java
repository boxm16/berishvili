/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Michail Sitmalidis
 */
public class Converter {

    public float convertRouteNumber(String routeNumber) {
        if (routeNumber.contains("-")) {

            routeNumber = routeNumber.replace("-", ".");
            return Float.parseFloat(routeNumber);

        } else {
            return Float.parseFloat(routeNumber);
        }
    }

    public String convertRouteNumber(float routeNumber) {
        if (routeNumber % 1 == 0) {
            int routeNumberInt = (int) Math.round(routeNumber);
            return String.valueOf(routeNumberInt);
        } else {
            String routeNumberStr = String.valueOf(routeNumber);
            return routeNumberStr.replace(".", "-");
        }
    }

    public String convertDateStampExcelFormatToDatabaseFormat(String excelFormatDateStamp) {
        try {
            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(excelFormatDateStamp);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public String convertDateStampDatabaseFormatToExcelFormat(String databaseFormatDateStamp) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(databaseFormatDateStamp);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public Date convertDateStampDatabaseFormatToDate(String databaseFormatDateStamp) {
        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd").parse(databaseFormatDateStamp);
            return date;
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public Date convertDateStampExcelFormatToDate(String dateStampExcelFormat) {
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(dateStampExcelFormat);
            return date;
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }

    }

    public LocalDateTime convertStringTimeToDate(String stringTime) {
        if (stringTime == null) {
            return null;
        }
        SimpleDateFormat dateTimeFormatter = new SimpleDateFormat("HH:mm");
        String turnOverStringTime = "03:00";
        Date dateTime = null;
        try {
            dateTime = dateTimeFormatter.parse(stringTime);
            Date turnOverDateTime = dateTimeFormatter.parse(turnOverStringTime);
            LocalDateTime localDateTime = dateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            LocalDateTime turnOverLocalDateTime = turnOverDateTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (localDateTime.isAfter(turnOverLocalDateTime)) {
                return localDateTime;
            } else {
                return localDateTime.plusDays(1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String convertDurationToString(Duration duration) {
        if (duration == null) {
            return "";
        }
        if (duration.isNegative()) {
            long seconds = duration.getSeconds() * (-1);
            int h = (int) seconds / 3600;
            int m = (int) (seconds % 3600) / 60;
            int s = (int) (seconds % 60);
            String hh = "00";
            String mm = "00";
            String ss = "00";
            if (h < 10) {
                hh = "0" + String.valueOf(h);
            } else {
                hh = String.valueOf(h);
            }

            if (m < 10) {
                mm = "0" + String.valueOf(m);
            } else {
                mm = String.valueOf(m);
            }
            if (s < 10) {
                ss = "0" + String.valueOf(s);
            } else {
                ss = String.valueOf(s);
            }

            return new StringBuilder("-").append(hh).append(":").append(mm).append(":").append(ss).toString();

        }

        long s = duration.getSeconds();
        return String.format("%02d:%02d:%02d", s / 3600, (s % 3600) / 60, (s % 60));
    }

    public String covertTripPeriodTypeFromDBFormatToExcelFormat(String type) {
        switch (type) {
            case "baseLeaving_A":
                return "ბაზა_A";
            case "baseLeaving_B":
                return "ბაზა_B";
            case "break":
                return "შესვენება";
            case "ab":
                return "A_B";
            case "ba":
                return "B_A";
            case "A_baseReturn":
                return "A_ბაზა";
            case "B_baseReturn":
                return "B_ბაზა";
            default:
                return "";
        }
    }

    public String convertTimeStampFromDBFormatToExcelFormat(String dbFormatTimeStamp) {
        if (dbFormatTimeStamp == null) {
            return "";
        }
        LocalDateTime dateFormat = convertStringTimeToDate(dbFormatTimeStamp);
        return dateFormat.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Duration convertStringToDuration(String timeStamp) {
        if (timeStamp == null||timeStamp.equals("")) {
            return null;
        }
        String isoStr = timeStamp.replaceFirst(
                "^([+-]?)(\\d{2}):(\\d{2}):(\\d{2})$", "$1PT$2H$3M$4S");
        // Parse to Duration
        Duration duration = Duration.parse(isoStr);
        return duration;
    }

    public String convertStringDurationToThreeColors(String stringDuration) {
        if (stringDuration == null || stringDuration.isEmpty()) {
            return "inherited";
        }
        String isoStr = stringDuration.replaceFirst(
                "^([+-]?)(\\d{2}):(\\d{2}):(\\d{2})$", "$1PT$2H$3M$4S");
        // Parse to Duration
        Duration differenceDuration = Duration.parse(isoStr);
        if (differenceDuration.getSeconds() < 61 && differenceDuration.getSeconds() > -61) {
            return "inherited";
        }
        if (differenceDuration.getSeconds() < 301 && differenceDuration.getSeconds() > -301) {
            return "yellow";
        }
        return "red";
    }

    public String convertDurationToThreeColors(Duration duration) {
        if (duration == null) {
            return "inherited";
        }

        if (duration.getSeconds() < 61 && duration.getSeconds() > -61) {
            return "inherited";
        }
        if (duration.getSeconds() < 301 && duration.getSeconds() > -301) {
            return "yellow";
        }
        return "red";
    }

}
