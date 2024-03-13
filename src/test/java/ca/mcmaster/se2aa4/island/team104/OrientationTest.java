package ca.mcmaster.se2aa4.island.team104;

import org.junit.Test;

import ca.mcmaster.se2aa4.island.team104.map.Orientation;

import static org.junit.jupiter.api.Assertions.*;

public class OrientationTest {

    @Test
    public void giveDirection() {

//        DecisionMaker dm = new DecisionMaker();
//        String current_head = "E";
//        dm.stats.heading = Orientation.N;
//        dm.stats.heading = dm.stats.heading.giveOrientation(current_head);
//        assertEquals(Orientation.E, dm.stats.heading);
    }


    @Test
    public void turningUsingThis() {

        Orientation orient1 = Orientation.E;
        assertEquals(Orientation.S, orient1.turnRight2());
        assertEquals(Orientation.E, orient1);

    }

    @Test
    public void giveOrientStringUsingThis() {
        Orientation orient1 = Orientation.E;
        assertEquals("E", orient1.giveStringOrientation2());
        assertEquals(Orientation.E, orient1);
    }

}