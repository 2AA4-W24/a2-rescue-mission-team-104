package ca.mcmaster.se2aa4.island.team104.exploration;

import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team104.JSONParser;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;

public class Controller {

    private Drone stats;

    JSONParser parser = new JSONParser();

    Controller(Drone drone) {
        stats = drone;
    }

    JSONObject convertActionToJSON(Actions action) {

        //get action as string
        String action_str = action.getAction();

        //get current heading as string
        Orientation head = stats.getHeading();
        String head_str = head.giveStringOrientation2();
        JSONObject parameters = parser.createJSON();

        //put action in JSONObject
        JSONObject actions = parser.createAndPut("action", action_str);

        //now determine extra parameters based on action
        switch (action) {
            case ECHO_FORWARD -> {
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head_str);
            }
            case ECHO_RIGHT -> {
                head = head.turnRight2();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation2());
            }
            case ECHO_LEFT -> {
                head = head.turnLeft2();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation2());
            }
            case FLY, STOP, SCAN -> {
                return actions;
            }
            case HEADING_LEFT -> {
                head = head.turnLeft2();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation2());
                stats.setHeading(head); //update heading
            }
            case HEADING_RIGHT -> {
                head = head.turnRight2();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation2());
                stats.setHeading(head); //update heading
            }
            case STANDBY -> {
                return null;
            }
        }
        return actions;
    }
}
