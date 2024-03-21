package ca.mcmaster.se2aa4.island.team104.exploration;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionsTest {

    @Test
    public void testGetAction() {
        Actions act = Actions.FLY;
        assertEquals("fly", act.getAction());
    }
}