package ca.mcmaster.se2aa4.island.team104;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.exploration.Actions;
import ca.mcmaster.se2aa4.island.team104.exploration.FindIsland;
import ca.mcmaster.se2aa4.island.team104.exploration.State;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

public class FindIslandTest {

    private final Logger logger = LogManager.getLogger();
    private static Drone drone = new Drone();
    private Mapping map = new Mapping();
    private Actions act = Actions.STANDBY;
    private FindIsland findIsland = new FindIsland(drone, map);

    @BeforeAll
    public static void createClasses() {
        drone.setHeading(Orientation.E);

    }

    @Before
    public void initGTI() {
        act = Actions.ECHO_FORWARD;
        map.setState(State.GO_TO_ISLAND);
        drone.range = 1;
    }

    @Test
    public void testGetNextMoveGTI() {

        Actions current_act = findIsland.getNextMove();
        assertEquals(act, current_act);
    }

    @After
    public void finishGTI() {
        act = Actions.STANDBY;
        map.setState(State.STOP);
        drone.range =0;
    }

    @Before
    public void initFI() {
        act = Actions.ECHO_LEFT;
        map.setState(State.FIND_ISLAND);
        drone.range = 200;
    }
    @Test
    public void testGetNextMoveFI() {

        Actions current_act = findIsland.getNextMove();
        assertEquals(act, current_act);
    }

    @After
    public void finishFI() {
        act = Actions.STANDBY;
        map.setState(State.STOP);
        drone.range =0;
    }


}