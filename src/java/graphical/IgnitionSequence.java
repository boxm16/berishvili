/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphical;

import java.util.ArrayList;

public class IgnitionSequence {

    ArrayList<ExodusIgnitionCode> ignitionSequence;

    public IgnitionSequence() {
        ignitionSequence = new ArrayList();
    }

    public void addExodusIgnitionCode(ExodusIgnitionCode exodusIgnitionCode) {
        ignitionSequence.add(exodusIgnitionCode);
    }

    public ArrayList<ExodusIgnitionCode> getSequence() {
        return ignitionSequence;
    }

    public void setSequence(ArrayList<ExodusIgnitionCode> ignitionSequence) {
        this.ignitionSequence = ignitionSequence;
    }
    
}
