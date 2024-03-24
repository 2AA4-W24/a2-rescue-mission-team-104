package ca.mcmaster.se2aa4.island.team104.drone;

import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

import ca.mcmaster.se2aa4.island.team104.drone.Position;


import java.util.ArrayList;

public class PositionTest {

    
    @Test
    public void settingDirection() {

        Position position = new Position();

        String direction = "N"; 
        position.setOrientation(direction);
        //assertEquals(Orientation.N, Orientation.N.giveOrientation(direction));
        assertEquals(Orientation.N, position.current_orient); // accessing variable inside class.

    }

    
    @Test 
    public void movingForward(){

        Position position = new Position();
        Integer initial_Y = position.getY(); 
        position.updateForward();
        assertEquals(initial_Y+1, position.getY());

    }

    @Test 
    public void movingRight(){

        Position position = new Position();

        Integer initial_X = position.getX();
        position.updateRight(); 
        assertEquals(initial_X+1,position.getX());
        assertEquals(Orientation.E, position.current_orient);

    }

    @Test 
    public void movingLeft(){

        Position position = new Position();
        position.current_orient = Orientation.N;
        Integer initial_X = position.getX(); 
        position.updateLeft();
        assertEquals(initial_X-1, position.getX());
        assertEquals(Orientation.W, position.current_orient); 

    }

    @Test 
    public void coordsDistance() {

        Position position = new Position();
        Double calc_distance = position.diffBtwnPoints(2,6,2,5);
        assertEquals(5.0, calc_distance); 

    }



}
