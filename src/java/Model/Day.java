package Model;

import java.util.ArrayList;

public class Day {

    private String dateStamp;
    private ArrayList<Exodus> exoduses;

    public String getDateStamp() {
        return dateStamp;
    }

    public void setDateStamp(String dateStamp) {
        this.dateStamp = dateStamp;
    }

    public ArrayList<Exodus> getExoduses() {
        return exoduses;
    }

    public void setExoduses(ArrayList<Exodus> exoduses) {
        this.exoduses = exoduses;
    }

}
