package ca.mcmaster.se2aa4.island.team104.map;

import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.exploration.Actions;
import ca.mcmaster.se2aa4.island.team104.exploration.State;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class MappingTest {

    Mapping mapping = new Mapping();

    @Test
    public void testState() {
    mapping.setState(State.INIT);
    assertEquals(State.INIT, mapping.getState());
    }

    @Before
    public void updatePositionInit(){
        mapping.setInitHeading(Orientation.E);
        mapping.updatePosition(Actions.FLY);
    }

    @Test
    public void testUpdatePosition() {
        ArrayList<Integer> coord_test = new ArrayList<>();
        coord_test.add(1);
        coord_test.add(0);

        assertEquals(coord_test.getFirst(), mapping.position.getX());
        assertEquals(coord_test.getLast(), mapping.position.getY());
    }


    @Test
    public void testRetCreek() {
        String na = "NA";
        assertEquals(na, mapping.retCreek());
    }

}