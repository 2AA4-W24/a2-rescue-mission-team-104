package ca.mcmaster.se2aa4.island.team104;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.exploration.Actions;
import ca.mcmaster.se2aa4.island.team104.exploration.FindIsland;
import ca.mcmaster.se2aa4.island.team104.exploration.State;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FindIslandTest {

    @Test
    public void testGetNextMoveGTI() {

        Actions act = Actions.ECHO_FORWARD;
        Drone drone = new Drone();
        Mapping map = new Mapping();
        FindIsland findIsland = new FindIsland(drone, map);

        drone.setHeading(Orientation.E);
        map.setState(State.GO_TO_ISLAND);
        drone.range = 1;

        Actions current_act = findIsland.getNextMove();
        assertEquals(act, current_act);
    }

    @Test
    public void testGetNextMoveFI() {

        Actions act = Actions.ECHO_LEFT;
        Drone drone = new Drone();
        Mapping map = new Mapping();
        FindIsland findIsland = new FindIsland(drone, map);

        drone.setHeading(Orientation.E);
        map.setState(State.FIND_ISLAND);
        drone.range = 200;

        Actions current_act = findIsland.getNextMove();
        assertEquals(act, current_act);
    }


}