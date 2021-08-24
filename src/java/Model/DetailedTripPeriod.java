package Model;

import java.time.Duration;

public class DetailedTripPeriod extends TripPeriod {

    private Duration haltTimeScheduled;
    private Duration haltTimeActual;

    public Duration getHaltTimeScheduled() {
        return haltTimeScheduled;
    }

    public void setHaltTimeScheduled(Duration haltTimeScheduled) {
        this.haltTimeScheduled = haltTimeScheduled;
    }

    public Duration getHaltTimeActual() {
        return haltTimeActual;
    }

    public void setHaltTimeActual(Duration haltTimeActual) {
        this.haltTimeActual = haltTimeActual;
    }
    
    

}
