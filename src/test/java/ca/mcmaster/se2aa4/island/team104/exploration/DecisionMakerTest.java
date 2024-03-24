package ca.mcmaster.se2aa4.island.team104.exploration;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;

public class DecisionMakerTest {
    private Drone drone2 = new Drone();
    private Mapping map = new Mapping();
    private DecisionMaker dm = new DecisionMaker();

    @BeforeAll
    public void initializeDrone() {
        String s = " {\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}";
        dm.initDrone(s); 
        dm.setStopBudget();
    }
    @Ignore
    public void initCheck() {
        String s = " {\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}";
        dm.initDrone(s); 
        drone2.initializeStats(s);
        //assertEquals(drone2.getBudget(), dm.drone.getBudget());
    }

    @Test
    public void intialState() {
        map.setState(State.INIT);
        dm.nextAction();
        assertEquals(State.INIT, map.getState());
    }
}
