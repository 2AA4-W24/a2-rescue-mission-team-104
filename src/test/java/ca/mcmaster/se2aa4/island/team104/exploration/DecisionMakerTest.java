package ca.mcmaster.se2aa4.island.team104.exploration;

import org.junit.Test;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;

public class DecisionMakerTest {
    private State state = State.INIT;
    private Drone drone = new Drone();
    private Mapping map;
    private FindIsland find_island = new FindIsland(drone, map);
    private ScanIsland scan_island = new ScanIsland(drone, map);

    @Test
    public void noBudget() {
        String s = " {\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}";
        drone.initializeStats(s);
    }
}
