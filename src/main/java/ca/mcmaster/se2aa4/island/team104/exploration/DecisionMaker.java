package ca.mcmaster.se2aa4.island.team104.exploration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team104.JSONParser;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;

public class DecisionMaker {
    private final Logger logger = LogManager.getLogger();
    public Drone drone = new Drone();
    public Mapping map = new Mapping();
    private FindIsland find_island = new FindIsland(drone, map);
    private ScanIsland scan_island = new ScanIsland(drone, map);
    private final JSONParser parser = new JSONParser();

    //now make it so that when you see ground you get the current action of find_island and turn in that direction
    public JSONObject nextAction() {
        Controller controller = new Controller(drone);


        //the 1000 is a placeholder
        if (drone.getBudget() > 100) {
            logger.info("this is the state: " + map.getState());
            logger.info("BUDGET LEFT: " + drone.getBudget());

            if (map.getState() == State.INIT) {
                logger.info("This is action after init: "+ find_island.current_action);

                JSONObject parameters = parser.createJSON();
                JSONObject actions = parser.createAndPut("action", "echo");

                Orientation current_head = drone.getHeading();
                String current_head_str = current_head.giveStringOrientation();
                map.setInitHeading(current_head); //set init heading


                JSONObject ret_action = parser.mergeJSONObjects(actions, parameters, "parameters", "direction", current_head_str);
                logger.info(ret_action);

                //update state
                State current_state = map.getState();
                map.setState(current_state.incrementState(current_state));

                logger.info("new state: " + map.getState());
                return ret_action;

            }
            if (map.getState() == State.FIND_ISLAND || map.getState() == State.GO_TO_ISLAND) {

                Actions current_act = find_island.getNextMove();
                map.updatePosition(current_act);
                logger.info("*new coordinates: " + map.position.coordinates);
                return controller.convertActionToJSON(current_act);

            }
            else if (map.getState() == State.INIT_SCAN || map.getState() == State.SCAN_ISLAND || map.getState() == State.UTURN || map.getState() == State.EVAL_ECHO) {
                if (drone.creek_found || drone.site_found) {
                    map.updateTile(drone);
                }
                Actions current_act = scan_island.getNextMove();
                map.updatePosition(current_act);

                map.printPois();
                return controller.convertActionToJSON(current_act);

            }
            else if (map.getState() == State.STOP) {
                return parser.createAndPut("action", "stop");
            }
        }

        logger.info("BUDGET BELOW 100");
        return controller.convertActionToJSON(Actions.STOP);
    }

}