package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.operations.Or;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticsTest {

    JSONParser parser = new JSONParser();
    private final Logger logger = LogManager.getLogger();
    Statistics stats = new Statistics();

    @Test
    public void initStats() {

        String s = " {\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}";

        stats.initializeStats(s);
        assertEquals(7000, stats.budget);
        assertEquals(Orientation.E, stats.heading);

    }

    @Test
    public void updateStats() {

        initStats();

        String s1 = "{\"cost\": 2, \"extras\": { \"biomes\": [\"BEACH\"], \"creeks\": [], \"sites\": [\"id\"]}, \"status\": \"OK\"}";
        stats.updateStats(s1);

        assertEquals(6998, stats.budget);
//        assertEquals("OUT_OF_RANGE", stats.found);
//
        assertEquals(Orientation.E, stats.heading);
//        assertEquals(52, stats.range);

        assertEquals("id", stats.site);
        assertEquals(true, stats.site_found);
        assertEquals("", stats.creek);
        assertEquals(false, stats.creek_found);

    }

}