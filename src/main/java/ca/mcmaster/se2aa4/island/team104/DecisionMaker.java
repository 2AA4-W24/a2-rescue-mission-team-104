package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

public class DecisionMaker {

    Statistics stats = new Statistics();
    Mapping map = new Mapping();
    FindIsland find_island = new FindIsland(stats);
    CoastTheCoast coast_the_coast;
    FindSite find_site;
    JSONParser parser = new JSONParser();


    private final Logger logger = LogManager.getLogger();





    //now make it so that when you see ground you get the current action of find_island and turn in that direction
    JSONObject nextAction() {
        Controller controller = new Controller(stats);


        //the 1000 is a placeholder
        if (stats.getBudget() > 1000) {
            logger.info("this is the state: " + stats.state);

            if (stats.getState() == State.INIT) {
                logger.info("This is action after init: "+ find_island.current_action);

                JSONObject parameters = parser.createJSON();
                JSONObject actions = parser.createAndPut("action", "echo");

                Orientation current_head = stats.getHeading();
                String current_head_str = current_head.giveStringOrientation(current_head);
                map.setInitHeading(current_head); //set init heading

                JSONObject ret_action = parser.mergeJSONObjects(actions, parameters, "parameters", "direction", current_head_str);
                logger.info(ret_action);

                //update state
                State current_state = stats.getState();
                stats.setState(current_state.incrementState(current_state));


                logger.info("new state: " + stats.getState());
                return ret_action;


            }
            if (stats.getState() == State.FIND_ISLAND || stats.getState() == State.GO_TO_ISLAND) {

                Actions current_act = find_island.getNextMove();
                map.updatePosition(current_act);
                logger.info("this is the coordinates: " + map.position.coordinates);
                return controller.convertActionToJSON(current_act);
            }
            else if (stats.getState() == State.COAST_THE_COAST) {
                //this is temporary so that the program doesn't return null pointer.
                return parser.createAndPut("action", "stop");
            }
            else if (stats.getState() == State.FIND_SITE) {
                return find_site.getNextMove();
            }
            else if (stats.getState() == State.STOP) {
                return parser.createAndPut("action", "stop");
            }
        }


        return null;
    }


}
