package ca.mcmaster.se2aa4.island.team104.drone;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrientationTest {

    @Test
    public void giveDirection() {

        Orientation orient1 = Orientation.E;

        String direction = "E";
        assertEquals(Orientation.E, orient1.giveOrientation(direction)); 
    }

    @Test
    public void turningRight() {


        Orientation orient1 = Orientation.E;
        assertEquals(Orientation.S, orient1.turnRight());
        assertEquals(Orientation.E, orient1);

      
        Orientation orient2 = Orientation.W; 
        assertEquals(Orientation.N, orient2.turnRight());

        Orientation orient3 = Orientation.N;
        assertEquals(Orientation.E, orient3.turnRight());

        Orientation orient4 = Orientation.S;
        assertEquals(Orientation.W, orient4.turnRight());
    }

    @Test
    public void turningLeft() {

        Orientation orient1 = Orientation.E;
        assertEquals(Orientation.N, orient1.turnLeft());
      
        Orientation orient2 = Orientation.W; 
        assertEquals(Orientation.S, orient2.turnLeft()); 

        Orientation orient3 = Orientation.N;
        assertEquals(Orientation.W, orient3.turnLeft());

        Orientation orient4 = Orientation.S;
        assertEquals(Orientation.E, orient4.turnLeft());
    }

    @Test 
    public void multipleTurns(){

        Orientation orient1 = Orientation.N;

        Orientation newdir = orient1.turnRight();
        Orientation newdir2 = newdir.turnRight(); 
        Orientation newdir3 = newdir2.turnRight(); 

        assertEquals(Orientation.N, newdir3.turnRight()); 
    }

    @Test
    public void giveOrientStringUsingThis() {

        Orientation orient1 = Orientation.E;
        assertEquals("E", orient1.giveStringOrientation());
        assertEquals(Orientation.E, orient1);

    }
}