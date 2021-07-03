package Model;

import java.util.Date;
import java.util.TreeMap;

public class BasicRoute {

    private String number;
    private short baseNumber;
    private TreeMap<Date, Day> days;

    public BasicRoute() {
        days = new TreeMap();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }

    public TreeMap<Date, Day> getDays() {
        return days;
    }

    public void setDays(TreeMap<Date, Day> days) {
        this.days = days;
    }

}
