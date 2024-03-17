package ca.mcmaster.se2aa4.island.team104.exploration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team104.JSONParser;
import ca.mcmaster.se2aa4.island.team104.Statistics;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import ca.mcmaster.se2aa4.island.team104.map.Orientation;

public class DecisionMaker {
    private final Logger logger = LogManager.getLogger();

    public Statistics stats = new Statistics();
    public Mapping map = new Mapping();
    public FindIsland find_island = new FindIsland(stats);
    public ScanIsland scan_island = new ScanIsland(stats);

    JSONParser parser = new JSONParser();

    //now make it so that when you see ground you get the current action of find_island and turn in that direction
    public JSONObject nextAction() {
        Controller controller = new Controller(stats);


        //the 1000 is a placeholder
        if (stats.getBudget() > 100) {
            logger.info("this is the state: " + stats.state);
            logger.info("BUDGET LEFT: " + stats.getBudget());

            if (stats.getState() == State.INIT) {
                logger.info("This is action after init: "+ find_island.current_action);

                JSONObject parameters = parser.createJSON();
                JSONObject actions = parser.createAndPut("action", "echo");

                Orientation current_head = stats.getHeading();
                String current_head_str = current_head.giveStringOrientation2();
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
                logger.info("*new coordinates: " + map.position.coordinates);
                return controller.convertActionToJSON(current_act);

            }
            else if (stats.getState() == State.INIT_SCAN || stats.getState() == State.SCAN_ISLAND || stats.getState() == State.UTURN || stats.getState() == State.EVAL_ECHO) {

                if (stats.creek_found || stats.site_found) {
                    map.updateTile(stats);
                }
                Actions current_act = scan_island.getNextMove();
                map.updatePosition(current_act);
                //logger.info("*new coordinates: " + map.position.coordinates);

                map.printPois();
                return controller.convertActionToJSON(current_act);

            }
            else if (stats.getState() == State.STOP) {
                return parser.createAndPut("action", "stop");
            }
        }

        logger.info("BUDGET BELOW 100");
        return controller.convertActionToJSON(Actions.STOP);
    }


}