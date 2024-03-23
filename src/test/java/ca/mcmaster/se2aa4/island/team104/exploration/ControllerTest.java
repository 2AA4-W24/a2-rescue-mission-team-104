package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    private final Logger logger = LogManager.getLogger();

    @Test
    public void testActionToJSONHeading() {
        Drone drone = new Drone();
        Controller controller = new Controller(drone);

        //set drone heading
        drone.setHeading(Orientation.E);

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
        Drone drone = new Drone();
        Controller controller = new Controller(drone);

        //set drone heading
        drone.setHeading(Orientation.E);

        JSONObject ret_object = new JSONObject();
        JSONObject direction = new JSONObject();
        direction.put("direction", "S");
        ret_object.put("action", "echo");
        ret_object.put("parameters", direction);

        logger.info(ret_object);
        assertEquals(ret_object.toString(), controller.convertActionToJSON(Actions.ECHO_RIGHT).toString());
    }
}