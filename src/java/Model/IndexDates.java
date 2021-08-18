/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TreeMap;

/**
 *
 * @author Michail Sitmalidis
 */
public class IndexDates {

    private TreeMap<Date, IndexDate> dates;
    private Converter converter;

    public IndexDates() {
        dates = new TreeMap<>();
    }

    public void addDate(Date date) {
        DateFormat excelFormatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateStampExcelFormat = excelFormatter.format(date);

        IndexDate indexDate = new IndexDate();
        indexDate.setDateStampExcelFormat(dateStampExcelFormat);

        dates.put(date, indexDate);
    }

    public TreeMap<Date, IndexDate> getDates() {
        return dates;
    }

    public void setDates(TreeMap<Date, IndexDate> dates) {
        this.dates = dates;
    }

}
