package Model;

import java.util.ArrayList;

public class GuarantyExodus {

    private short number;
    private ArrayList<GuarantyTripPeriod> guarantyTripPeriods;

    public GuarantyExodus() {
        this.guarantyTripPeriods = new ArrayList();
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public ArrayList<GuarantyTripPeriod> getGuarantyTripPeriods() {
        return guarantyTripPeriods;
    }

    public void setGuarantyTripPeriods(ArrayList<GuarantyTripPeriod> guarantyTripPeriods) {
        this.guarantyTripPeriods = guarantyTripPeriods;
    }

}
