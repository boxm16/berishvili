package Model;

import java.util.ArrayList;

public class Route {

    private String number;
    private short baseNumber;
    private ArrayList<Day> days;

    public Route() {
        days = new ArrayList();
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

    public short getBaseNumber() {
        return baseNumber;
    }

    public void setBaseNumber(short baseNumber) {
        this.baseNumber = baseNumber;
    }
    


}
