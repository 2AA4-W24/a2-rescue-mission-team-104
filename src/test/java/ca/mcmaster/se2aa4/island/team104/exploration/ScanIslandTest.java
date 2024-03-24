package ca.mcmaster.se2aa4.island.team104.exploration;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;

import ca.mcmaster.se2aa4.island.team104.JSONParser;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.drone.Position;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import static org.junit.jupiter.api.Assertions.*;

public class ScanIslandTest {
    private static Drone drone = new Drone();
    private Mapping map = new Mapping();
    private Actions action;
    private ScanIsland scan = new ScanIsland(drone, map);

    JSONParser parser = new JSONParser();

    @BeforeAll
    public static void initialize() {
        String s = " {\"heading\":\"E\",\"men\":5,\"contracts\":[{\"amount\":1000,\"resource\":\"WOOD\"}],\"budget\":7000}";
        drone.initializeStats(s);
    }
    @AfterEach
    public void reset() {
        drone.setRange(0);
    }

    @Test
    public void initialTurnInMove() {
        drone.setFacingIsland();
        map.setState(State.INIT_SCAN);
        action = scan.getNextMove();
        assertEquals(Actions.HEADING_RIGHT, action);
    }

    // Drone should stop if U-turn will go out of range
    @Test
    public void evalEchoMove() {
        map.setState(State.EVAL_ECHO);
        String s = "{'cost': 5,'extras': {'found': 'OUT_OF_RANGE','range': 0},'status': 'OK'}";  
        drone.updateStats(s);
        action = scan.getNextMove();
        assertEquals(Actions.STOP, action);
    }

    @Test
    public void determineNotWater() {
        map.setState(State.SCAN_ISLAND);
        String s = "{'cost': 5,'extras': {'creeks': [],'biomes': ['OCEAN', 'BEACH'],'sites': []},'status': 'OK'}";
        JSONObject found = parser.loadString(s);
        drone.updateScan(found);
        assertFalse(drone.isWater());
    }
    @Test
    public void determineisWater() {
        map.setState(State.SCAN_ISLAND);
        String s = "{'cost': 5,'extras': {'creeks': [],'biomes': ['OCEAN'],'sites': []},'status': 'OK'}";
        JSONObject found = parser.loadString(s);
        drone.updateScan(found);
        assertTrue(drone.isWater());
    }

    @Test
    public void UturnFirstMove() {
        map.setState(State.UTURN);
        action = scan.getNextMove();
        assertEquals(Actions.FLY, action);
    }
}
