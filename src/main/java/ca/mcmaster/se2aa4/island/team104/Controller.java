package ca.mcmaster.se2aa4.island.team104;

import org.json.JSONObject;

public class Controller {

    private Statistics stats = new Statistics();

    JSONParser parser = new JSONParser();

    Controller(Statistics statistics) {
        stats = statistics;
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
//                JSONObject parameters = parser.createAndPut("direction", head_str);
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
                stats.setHeading(head);
            }
            case HEADING_RIGHT -> {
                head = head.turnRight2();
                parser.mergeJSONObjectsVoid(actions, parameters, "parameters", "direction",head.giveStringOrientation2());
                stats.setHeading(head);
            }
            case STANDBY -> {
                return null;
            }
        }
        return actions;
    }
}
