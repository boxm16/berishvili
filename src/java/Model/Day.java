package Model;

import Controller.Converter;
import java.util.TreeMap;

public class Day {

    private String dateStamp;
    private TreeMap<Short, Exodus> exoduses;
    private Converter converter;

    public Day() {
        this.exoduses = new TreeMap<>();
        this.converter = new Converter();
    }

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public TreeMap<Short, Exodus> getExoduses() {
        return exoduses;
    }

    public void setExoduses(TreeMap<Short, Exodus> exoduses) {
        this.exoduses = exoduses;
    }

    public String getDateStampWeekFormat() {
        return converter.convertDateStampDatabaseFormatToExcelFormat(dateStamp);
    }

}
