package ca.mcmaster.se2aa4.island.team104;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrientationTest {

    @Test
    public void giveDirection() {

        DecisionMaker dm = new DecisionMaker();
        String current_head = "E";
        dm.stats.heading = Orientation.N;
        dm.stats.heading = dm.stats.heading.giveOrientation(current_head);
        assertEquals(Orientation.E, dm.stats.heading);
    }

}