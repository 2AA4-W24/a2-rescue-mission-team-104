package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

public class FindIslandTest {

    private static Drone drone = new Drone();
    private Mapping map = new Mapping();
    private Actions act = Actions.STANDBY;
    private FindIsland findIsland = new FindIsland(drone, map);

    @BeforeAll
    public static void init() {
        drone.setHeading(Orientation.E);
    }

    @Before
    public void initGTI() {
        act = Actions.ECHO_FORWARD;
        map.setState(State.GO_TO_ISLAND);
        drone.setRange(1);
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
        drone.setRange(0);
    }

    @Before
    public void initFI() {
        act = Actions.ECHO_LEFT;
        map.setState(State.FIND_ISLAND);
        drone.setRange(200);
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
        drone.setRange(0);
    }


}