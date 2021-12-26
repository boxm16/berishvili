/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author Michail Sitmalidis
 */
public class HaltFilter {

    private LocalDateTime startTime;
    private ArrayList<Halt> participantHalts;

    public HaltFilter() {
        this.participantHalts = new ArrayList<>();
    }

    public ArrayList<Halt> getParticipantHalts() {
        return participantHalts;
    }

    public void setParticipantHalts(ArrayList<Halt> participantHalts) {
        this.participantHalts = participantHalts;
    }

    public void addHalt(Halt halt) {
        this.participantHalts.add(halt);
        this.startTime = halt.getStartTime();

    }

    public void siftOutParticipantHaltsForGivenTime(LocalDateTime givenTime) {
        ArrayList<Halt> mirrorList = new ArrayList<>();
        for (int x = 0; x < this.participantHalts.size(); x++) {
            Halt halt = this.participantHalts.get(x);
            LocalDateTime haltEndTime = halt.getEndTime();
            if (haltEndTime.isAfter(givenTime)) {
                mirrorList.add(halt);
            }
            if (x == 2) {
                this.startTime = halt.getStartTime();
            }
        }
        this.participantHalts = mirrorList;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
}
