/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            return dateFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

    public String convertDateStampDatabaseFormatToExcelFormat(String databaseFormatDateStamp) {
        try {
            Date date = new SimpleDateFormat("yyyy-mm-dd").parse(databaseFormatDateStamp);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        } catch (ParseException ex) {
            Logger.getLogger(Converter.class.getName()).log(Level.SEVERE, null, ex);
            return "";
        }
    }

}
