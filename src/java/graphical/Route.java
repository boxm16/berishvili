/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphical;

import java.util.ArrayList;

public class Route {

    private ArrayList<Exodus> exoduses;

    public Route() {
        exoduses = new ArrayList();
    }

    public ArrayList<Exodus> getExoduses() {
        return exoduses;
    }

    public void setExoduses(ArrayList<Exodus> exoduses) {
        this.exoduses = exoduses;
    }

}
