package ca.mcmaster.se2aa4.island.team104.drone;
import ca.mcmaster.se2aa4.island.team104.JSONParser;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DroneTest {

    JSONParser parser = new JSONParser();
    Drone stats = new Drone();

    @Test
    public void initStats() {

        String s = " {\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}";

        stats.initializeStats(s);
        assertEquals(7000, stats.getBudget());
        assertEquals(Orientation.E, stats.getHeading());

    }

    @Test
    public void updateStats() {

        initStats();

        String s1 = "{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [], \"sites\": [\"id\"]}, \"status\": \"OK\"}";
        stats.updateStats(s1);

        assertEquals(6998, stats.getBudget());
        assertEquals(Orientation.E, stats.getHeading());

        assertEquals("id", stats.site);
        assertEquals(true, stats.getSiteFound());
        assertEquals("", stats.creek);
        assertEquals(false, stats.getCreekFound());

    }

}