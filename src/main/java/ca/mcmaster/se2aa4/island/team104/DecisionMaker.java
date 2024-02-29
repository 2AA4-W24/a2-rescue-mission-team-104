package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.Objects;

public class DecisionMaker {

    Statistics stats = new Statistics();
    Mapping map = new Mapping();
    FindIsland find_island = new FindIsland(stats);
    JSONParser parser = new JSONParser();

    private final Logger logger = LogManager.getLogger();




    //now make it so that when you see ground you get the current action of find_island and turn in that direction
    JSONObject nextAction() {
        //the 1000 is a placeholder
        if (stats.budget > 1000) {
            logger.info("this is the state: " + stats.state);

            if (stats.state == State.INIT) {
                logger.info("This is action after init: "+ find_island.current_action);

                JSONObject parameters = parser.createJSON();
                JSONObject actions = parser.createAndPut("action", "echo");

                Orientation current_head = stats.getHeading();
                String current_head_str = current_head.giveStringOrientation(current_head);

                JSONObject ret_action = parser.mergeJSONObjects(actions, parameters, "parameters", "direction", current_head_str);
                logger.info(ret_action);

                //update
                State current_state = stats.getState();
                stats.setState(current_state.incrementState(current_state));



                logger.info("new state: " + stats.getState());
                return ret_action;



            }
            if (stats.state == State.FIND_ISLAND) {

                if (Objects.equals(stats.found, "GROUND")) {
                    stats.state = stats.state.incrementState(stats.state);
                }
                else {
                    //return string version of Actions
                    return find_island.getNextMove();
                }
            }
        }


        return null;
    }


}
