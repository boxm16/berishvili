package Model;

import java.util.HashMap;

public class Route {

    private String number;
    private short baseNumber;
    private HashMap<String, Day> days;

    public Route() {
        days = new HashMap();
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

    public HashMap<String, Day> getDays() {
        return days;
    }

    public void setDays(HashMap<String, Day> days) {
        this.days = days;
    }

}
