package ca.mcmaster.se2aa4.island.team104.exploration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import ca.mcmaster.se2aa4.island.team104.JSONParser;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;

// determines the next action for the drone
public class DecisionMaker {
    private final Logger logger = LogManager.getLogger();
    private Drone drone = new Drone();
    private Mapping map = new Mapping();
    private FindIsland find_island = new FindIsland(drone, map);
    private ScanIsland scan_island = new ScanIsland(drone, map);
    private final JSONParser parser = new JSONParser();
    private int STOP_BUDGET;

    /*
     Input: N/A
     Output: JSONObject
     Decide next drone action based on the state of the map and the drone budget
    */
    public JSONObject nextAction() {
        Controller controller = new Controller(drone);

        // ensure drone has enough battery to perform stop action
        if (drone.getBudget() > STOP_BUDGET) {
            logger.info("this is the state: " + map.getState());
            logger.info("BUDGET LEFT: " + drone.getBudget());

            if (map.getState() == State.INIT) {
                JSONObject parameters = parser.createJSON();
                JSONObject actions = parser.createAndPut("action", "echo");

                Orientation current_head = drone.getHeading();
                String current_head_str = current_head.giveStringOrientation();
                map.setInitHeading(current_head);

                JSONObject ret_action = parser.mergeJSONObjects(actions, parameters, "parameters", "direction", current_head_str);
                logger.info(ret_action);

                State current_state = map.getState();
                map.setState(current_state.incrementState(current_state));

                logger.info("new state: " + map.getState());
                return ret_action;
            }
            // state requires next action to be determined through FindIsland
            if (map.getState() == State.FIND_ISLAND || map.getState() == State.GO_TO_ISLAND) {

                Actions current_act = find_island.getNextMove();
                map.updatePosition(current_act);
                logger.info("*new coordinates: " + map.position.coordinates);
                return controller.convertActionToJSON(current_act);

            }
            // state requires next action to be determined through ScanIsland
            else if (map.getState() == State.INIT_SCAN || map.getState() == State.SCAN_ISLAND || map.getState() == State.UTURN || map.getState() == State.EVAL_ECHO) {
                if (drone.getCreekFound() || drone.getSiteFound()) {
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
        // force drone to stop if battery has reached allocated stop budget value
        logger.info("BUDGET BELOW STOP BUDGET");
        return controller.convertActionToJSON(Actions.STOP);
    }

    /*
    Input: N/A
    Output: N/A
    Allocates 2% of the inital budget for stop action
     */
    public void setStopBudget() {
        int init_budget = drone.getBudget();
        STOP_BUDGET = (int) (init_budget*0.02);
        logger.info(STOP_BUDGET);
    }

    /*
    Input: String
    Output: N/A
    Initializes drone information.
     */
    public void initDrone(String s) {
        drone.initializeStats(s);
    }

    /*
    Input: String
    Output: N/A
    Updates drone information.
     */
    public void updateDrone(String s) {
        drone.updateStats(s);
    }

    /*
    Input: N/A
    Output: String
    Returns sites found.
     */
    public String getCreek() {
        return map.retCreek();
    }

}