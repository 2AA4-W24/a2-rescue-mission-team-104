package ca.mcmaster.se2aa4.island.team104;

import eu.ace_design.island.game.Tile;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class MappingTest {

//    @Test
//    public void initAtHomeBase() {
//        Statistics statistics = new Statistics();
//        DecisionMaker dm = new DecisionMaker(); //create a decision maker
//        dm.stats.state = State.INIT; //make its state INIT
//        dm.map.updateTile(dm.stats); //update its tile so that it's homebase
//        dm.stats.heading = Orientation.E;
//        Position testPosition = new Position(dm.stats.heading);
//        Map<Tiles, Position> testMap = new HashMap<>(); //create test Map to test against
//        testMap.put(Tiles.HOMEBASE, testPosition);
////        assertEquals(testMap.keySet(), dm.map.pois.keySet());
////        assertEquals(testMap.get(Tiles.HOMEBASE), dm.map.pois.get(Tiles.HOMEBASE));
//
//    }

//    @Test
//    public void groundFound() {
////        Statistics statistics = new Statistics();
//        DecisionMaker dm = new DecisionMaker();
//        dm.stats.found = "GROUND";
//        dm.map.updateTile(dm.stats);
//        Map<Tiles, Position> testMap = new HashMap<>();
////        testMap.put(Tiles.GROUND)
//    }

    @Test
    public void updatePositionTest() {
        Mapping m1 = new Mapping();
        m1.setInitHeading(Orientation.E);
        m1.updatePosition(Actions.FLY);
        ArrayList<Integer> pos = new ArrayList<>();
        pos.add(0, 1);
        pos.add(1, 0);
        assertEquals(pos, m1.position.coordinates);
    }

}