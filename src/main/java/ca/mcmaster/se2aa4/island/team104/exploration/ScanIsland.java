package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.map.Mapping;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


// Determines next move with the goal of finding creeks and the emergency site
public class ScanIsland implements StateInterface {
    private final Logger logger = LogManager.getLogger();

    private Drone drone;
    private Mapping map;

    // Hold last action and turn heading
    private Actions last_action = Actions.FLY;
    private Actions last_turn;

    // Flags to store previous states
    private Boolean flying_to_ground = false;
    private Boolean u_turned = false;
    private Boolean u_turned_left = false;

    // Counters to save previous moves
    private int fly_counter = 0;
    private int uturn_idx = 0;

    // Actions sequence for performing u-turns
    private Actions[] uturn_right = {Actions.FLY, Actions.FLY, Actions.FLY, Actions.HEADING_RIGHT, Actions.ECHO_FORWARD, Actions.FLY, Actions.HEADING_RIGHT, Actions.HEADING_RIGHT, Actions.HEADING_LEFT};
    private Actions[] uturn_left = {Actions.FLY, Actions.FLY, Actions.FLY, Actions.HEADING_LEFT, Actions.ECHO_FORWARD, Actions.FLY, Actions.HEADING_LEFT, Actions.HEADING_LEFT, Actions.HEADING_RIGHT};

    public ScanIsland(Drone drone_in, Mapping mapping) {
        this.drone = drone_in;
        this.map = mapping;
    }

    /*
     * Input: N/A
     * Output: Actions
     * Determine next Action depending on the drone, map, and map state
     * Drone will 'zig-zag" through the island to scan for creeks and the emergency site'
     */
    public Actions getNextMove() {
        
        if (map.getState() == State.INIT_SCAN) {
            return initializeScanIsland();
        }
        if (map.getState() == State.EVAL_ECHO) {
            return evaluateEcho();
        }
        if (map.getState() == State.SCAN_ISLAND) {
            if (flying_to_ground) {
                return flyToGround();
            }
            return scanning();
        }
        if (map.getState() == State.UTURN) {
            return UTurn();
        }

        logger.info("Drone is not in a valid state for ScanIsland");
        return Actions.STOP;
    }

    /*
    Make the first move after finding island, depending on where the drone spawned from
    */
    private Actions initializeScanIsland() {
        // if drone spawns facing island, it will turn into the first line
        //logger.info("drone facing island: " + drone.facing_island());
        if (drone.facing_island()) {
            logger.info("DRONE TURNING INTO ISLAND TO INITIALIZE");
            last_action = Actions.HEADING_RIGHT;
        } else {
            last_action = Actions.SCAN;
        }
        map.setState(State.SCAN_ISLAND);
        u_turned_left = false;
        return last_action;
    }

    /*
    Alternate between scanning and flying until the drone is above water
    */
    private Actions scanning() {
        if (last_action == Actions.SCAN) {
            last_action = Actions.FLY;
            // echo forward if drone is on water tile
            if (drone.isWater()) {
                last_action = Actions.ECHO_FORWARD;
                map.setState(State.EVAL_ECHO);
                logger.info("Switching states: "+ map.getState());
            }
            return last_action;
        } else {
            last_action = Actions.SCAN;
            return last_action;
        }
    }

    /*
    Fly directly to ground from determined range
    */
    private Actions flyToGround() {
        if (fly_counter <= drone.getRange()) {
            last_action = Actions.FLY;
            fly_counter++;
        } else {
            // switch state to SCAN_ISLAND when ground is reached
            flying_to_ground = false;
            fly_counter = 0;
            last_action = Actions.SCAN;
            map.setState(State.SCAN_ISLAND);
            logger.info("Switching states: "+ map.getState());
        }
        return last_action;
    }
    
    /*
    Echo results decide if drone should continue scanning the island or determine if a U-turn should be taken
    */
    private Actions evaluateEcho() {
        String forward = drone.getFound();
        
        // more ground ahead, fly to located ground
        if (forward.equals("GROUND")){
            map.setState(State.SCAN_ISLAND);
            logger.info("Switching states: "+ map.getState());
            flying_to_ground = true;
            u_turned = false;
            last_action = Actions.FLY;
            return last_action;
        }
        else { 
            int range = drone.getRange();
            return determineUturn(range);
        }
    }

    /*
    Determines if drone should stop scanning or if it should begin the UTURN state. 
    Determines the size of the UTURN if it is to occur
    */
    private Actions determineUturn(int range) {
        if (u_turned) {
            logger.info("Drone has Uturned into an empty line, stopping drone...");
            return Actions.STOP;
        } else if (range == 0) {
            logger.info("Drone is unable to U-turn");
            return Actions.STOP;
        } else if (range <= 3) { //shortens the flying portion of the U-turn sequence to accomodate lower ranges
            logger.info("SMALL UTURN");
            uturn_idx = 4 - range;
        }
        map.setState(State.UTURN);
        logger.info("Switching states: "+ map.getState());
        return UTurn();
    }

    private Actions UTurn() {
        // stop drone if it will go out of range mid turn
        if (uturn_idx == 5 && !drone.getFound().equals("GROUND")) {
            if (drone.getRange() < 2){
                return Actions.STOP;
            }
        } 
        // find next move in U-turn
        if (uturn_idx < uturn_left.length) {
            return nextUturnMove();
        }

        // save state accomplishments
        saveLastTurn(last_turn);
        u_turned = true;
        uturn_idx = 0;
        last_action = Actions.ECHO_FORWARD;
        
        map.setState(State.EVAL_ECHO);
        logger.info("Switching states: "+ map.getState());

        return last_action;
    }

    /*
    Iterate through performing a CW or CCW U-turn with a counter to save the index
    */
    private Actions nextUturnMove() {
        // alternate left and right U-turn sequence 
        if (u_turned_left) {
            last_action = uturn_right[uturn_idx];
            uturn_idx++;
            last_turn = Actions.HEADING_RIGHT;
            return last_action;
        } 
        else {
            last_action = uturn_left[uturn_idx];
            uturn_idx++;
            last_turn = Actions.HEADING_LEFT;
            return last_action;
        }
    }

    /*
    Set flag to rememeber last U-turn direction. This allows the drone to "zig-zag" through the island
    */
    private void saveLastTurn(Actions last_turn) {
        if (last_turn == Actions.HEADING_LEFT) {
            u_turned_left = true;
        } else {
            u_turned_left = false;
        }
    }

}
