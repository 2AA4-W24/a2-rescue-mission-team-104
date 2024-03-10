package ca.mcmaster.se2aa4.island.team104;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PositionTest {

    @Test
    public void setInitOrient() {
        Mapping m1 = new Mapping();
        m1.setInitHeading(Orientation.E);
        assertEquals(Orientation.E, m1.position.current_orient);
    }

    @Test
    public void moveForward() {
//        Mapping m1 = new Mapping();
//        m1.setInitHeading(Orientation.E);
//        m1.position.updateForward();

        Position p1 = new Position();
        p1.setOrientation("E");
        p1.updateForward();
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(0, 1);
        pos.add(1, 0);
        assertEquals(pos, p1.coordinates);
    }

    @Test
    public void MoveRight() {
        Position p2 = new Position();
        p2.setOrientation("E");
        p2.updateForward();
        p2.updateRight();
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(0, 1);
        pos.add(1, -1);
        assertEquals(pos, p2.coordinates);
        assertEquals(Orientation.S, p2.current_orient);
    }

}