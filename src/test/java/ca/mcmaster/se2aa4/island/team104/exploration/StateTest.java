package ca.mcmaster.se2aa4.island.team104.exploration;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StateTest {

    @Test
    public void testincrementstate(){

        State start = State.INIT;
        State new_state = start.incrementState(start); 
        assertEquals(State.FIND_ISLAND, new_state);

        State start2 = State.SCAN_ISLAND;
        State new_state2 =start2.incrementState(start2);
        assertEquals(State.STOP, new_state2);

        State start3 = State.GO_TO_ISLAND;
        State new_state3 = start3.incrementState(start3);
        assertEquals(State.INIT_SCAN, new_state3);

    }
}

