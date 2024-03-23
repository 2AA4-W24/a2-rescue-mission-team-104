package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private final Logger logger = LogManager.getLogger();

    private Mapping map = new Mapping();
    private static Drone drone = new Drone();
    private Controller controller = new Controller(drone);

    @BeforeAll
    public static void init() {
        drone.setHeading(Orientation.E);
    }
    
    @Test
    public void testActionToJSONHeading() {

        JSONObject ret_object = new JSONObject();
        JSONObject direction = new JSONObject();
        direction.put("direction", "S");
        ret_object.put("action", "heading");
        ret_object.put("parameters", direction);

        logger.info(ret_object);
        assertEquals(ret_object.toString(), controller.convertActionToJSON(Actions.HEADING_RIGHT).toString());
    }

    @Test
    public void testEchoRight() {

        JSONObject ret_object = new JSONObject();
        JSONObject direction = new JSONObject();
        direction.put("direction", "S");
        ret_object.put("action", "echo");
        ret_object.put("parameters", direction);

        logger.info(ret_object);
        assertEquals(ret_object.toString(), controller.convertActionToJSON(Actions.ECHO_RIGHT).toString());
    }
}