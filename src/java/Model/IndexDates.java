/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Controller.Converter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case 1:
                indexDate.setDayOfWeek("კვირა");
                indexDate.setDayColor("red");
                break;
            case 2:
                indexDate.setDayOfWeek("ორშაბათი");
                indexDate.setDayColor("black");
                break;
            case 3:
                indexDate.setDayOfWeek("სამშაბათი");
                indexDate.setDayColor("black");
                break;
            case 4:
                indexDate.setDayOfWeek("ოთხშაბათი");
                indexDate.setDayColor("black");
                break;
            case 5:
                indexDate.setDayOfWeek("ხუთშაბათი");
                indexDate.setDayColor("black");
                break;
            case 6:
                indexDate.setDayOfWeek("პარასკევი");
                indexDate.setDayColor("black");
                break;
            case 7:
                indexDate.setDayOfWeek("შაბათი");
                indexDate.setDayColor("red");
                break;
        }

        dates.put(date, indexDate);
    }

    public TreeMap<Date, IndexDate> getDates() {
        return dates;
    }

    public void setDates(TreeMap<Date, IndexDate> dates) {
        this.dates = dates;
    }

}
