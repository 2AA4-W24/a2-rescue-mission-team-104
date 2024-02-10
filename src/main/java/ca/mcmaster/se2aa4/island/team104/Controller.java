package ca.mcmaster.se2aa4.island.team104;

import org.json.JSONObject;

public class Controller {

    //function to call to go forward
    String goForward() {

        //create JSON object
        JSONObject target = new JSONObject();

        //put desired function
        target.put("action", "fly");

        //return String version
        return target.toString();
    }
}
