package ca.mcmaster.se2aa4.island.team104.exploration;

import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team104.JSONParser;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;

public class Controller {
    private Drone drone;
    private JSONParser parser = new JSONParser();

    /*
    Input: Drone
    Output: N/A
    Constructor for Controller to control a Drone.
     */
    Controller(Drone in_drone) {
        drone = in_drone;
    }

    /*
    Input: Action Object
    Output: JSONObject
    This function takes in an Action and returns it in a JSONObject.
     */
    JSONObject convertActionToJSON(Actions action) {

        //get action as string
        String action_str = action.getAction();

        //get current heading as string
        Orientation head = drone.getHeading();
        String head_str = head.giveStringOrientation();
        JSONObject parameters = parser.createJSON();

        //put action in JSONObject
        JSONObject actions = parser.createAndPut("action", action_str);

        //now determine extra parameters based on action
        switch (action) {
            case ECHO_FORWARD -> {
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head_str);
            }
            case ECHO_RIGHT -> {
                head = head.turnRight();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation());
            }
            case ECHO_LEFT -> {
                head = head.turnLeft();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation());
            }
            case FLY, STOP, SCAN -> {
                return actions;
            }
            case HEADING_LEFT -> {
                head = head.turnLeft();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation());
                drone.setHeading(head); //update heading
            }
            case HEADING_RIGHT -> {
                head = head.turnRight();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation());
                drone.setHeading(head); //update heading
            }
            case STANDBY -> {
                return null;
            }
        }
        return actions;
    }
}
