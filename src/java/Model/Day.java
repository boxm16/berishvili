package Model;

import java.util.TreeMap;

public class Day {

    private String dateStamp;
    private TreeMap<Short, Exodus> exoduses;

    public Day() {
        this.exoduses = new TreeMap<>();
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

}
