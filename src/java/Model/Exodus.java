package Model;

import java.util.ArrayList;

public class Exodus {

    private short number;
    private ArrayList<TripVoucher> tripVouchers;

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public ArrayList<TripVoucher> getTripVouchers() {
        return tripVouchers;
    }

    public void setTripVouchers(ArrayList<TripVoucher> tripVouchers) {
        this.tripVouchers = tripVouchers;
    }

}
