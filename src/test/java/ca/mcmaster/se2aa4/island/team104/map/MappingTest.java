package ca.mcmaster.se2aa4.island.team104.map;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.drone.Position;
import ca.mcmaster.se2aa4.island.team104.exploration.Actions;
import ca.mcmaster.se2aa4.island.team104.exploration.DecisionMaker;
import ca.mcmaster.se2aa4.island.team104.exploration.State;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import ca.mcmaster.se2aa4.island.team104.map.Tiles;
import eu.ace_design.island.game.Tile;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MappingTest {

    @Test
    public void testState() {
    Mapping mapping = new Mapping();
    mapping.setState(State.INIT);
    assertEquals(State.INIT, mapping.getState());
    }

    @Test
    public void testUpdatePosition() {
        Mapping mapping = new Mapping();
        mapping.setInitHeading(Orientation.E);

        mapping.updatePosition(Actions.FLY);

        ArrayList<Integer> coord_test = new ArrayList<>();
        coord_test.add(1);
        coord_test.add(0);

        assertEquals(coord_test.getFirst(), mapping.position.getX());
        assertEquals(coord_test.getLast(), mapping.position.getY());
    }

    @Test
    public void testRetCreek() {
        Mapping mapping = new Mapping();
        String na = "NA";
        assertEquals(na, mapping.retCreek());
    }

}