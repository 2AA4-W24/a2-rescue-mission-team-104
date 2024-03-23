package ca.mcmaster.se2aa4.island.team104.exploration;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ActionsTest {

    @Test
    public void testGetActionFLY() {
        Actions act = Actions.FLY;
        assertEquals("fly", act.getAction());
    }

    @Test
    public void testGetActionECHO() {
        Actions act = Actions.ECHO_FORWARD;
        assertEquals("echo", act.getAction());

        Actions actr = Actions.ECHO_RIGHT;
        assertEquals("echo", actr.getAction());

        Actions actl = Actions.ECHO_LEFT;
        assertEquals("echo", actl.getAction());
    }

    @Test
    public void testActionHEADING() {
        Actions actl = Actions.HEADING_LEFT;
        assertEquals("heading", actl.getAction());

        Actions actr = Actions.HEADING_RIGHT;
        assertEquals("heading", actr.getAction());
    }

    @Test
    public void testGetActionSCAN() {
        Actions act = Actions.SCAN;
        assertEquals("scan", act.getAction());
    }
}