package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;


//Note: this is simply a controller, so it only has the capabilities of one
public class Controller {

    private final Logger logger = LogManager.getLogger();

    /*
    input: takes in takeDecision JSONObject
    use: want to fly forward
     */
    void goForward(JSONObject action) {

        try {
            //put fly function
            action.put("action", "fly");

        }catch (JSONException e) {
            logger.error("There was an error when putting action fly.");
        }
    }

    /*
    input: takes in takeDecision JSONObject, and direction of travel as String (N/E/S/W)
    use: want to turn in given direction
     */
    void actionHeading(JSONObject action, String new_direction) {

        try {
            //input heading decision in takeDecision() object
            action.put("action", "heading");

            //create new JSONObject to put in direction of desired heading
            JSONObject direction = new JSONObject();
            direction.put("direction", new_direction);

            //put new direction JSONObject in "parameters" JSONObject
            action.put("parameters", direction);

        }catch (JSONException e) {
            logger.error("There was an error when putting action heading.");
        }
    }

    /*
    input: takes in takeDecision JSONObject, and direction of scan as String (N/E/S/W)
    use: want to scan given direction
     */
    void actionEcho(JSONObject action, String new_direction) {

        try {

            //input heading decision in takeDecision() object
            action.put("action", "echo");

            //create new JSONObject to put in direction of desired heading
            JSONObject direction = new JSONObject();
            direction.put("direction", new_direction);

            //put new direction JSONObject in "parameters" JSONObject
            action.put("parameters", direction);


        } catch (JSONException e) {
            logger.error("There was an error when putting action echo.");
        }
    }

    /*
    input: takes in takeDecision JSONObject
    use: want to scan ground
     */
    void actionScan(JSONObject action) {

        try {
            action.put("action", "scan");
        } catch (JSONException e) {
            logger.error("There was an error when putting action scan.");
        }
    }

    /*
    input: takes in takeDecision JSONObject
    use: want to stop drone
     */
    void actionStop(JSONObject action) {

        try {
            action.put("action", "stop");
        } catch (JSONException e) {
            logger.error("There was an error when putting action stop.");
        }
    }
}
