package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import ca.mcmaster.se2aa4.island.team104.drone.Orientation;

// zig zag to find creeks and site :)
public class ScanIsland {
    private final Logger logger = LogManager.getLogger();

    private Drone drone;
    private Mapping map = new Mapping();

    // Hold last action and turn heading
    private Actions curr_action = Actions.FLY;
    private Actions last_turn;

    // Flags to store previous states
    private Boolean fly_to_ground = false;
    private Boolean U_turned = false;
    private Boolean last_turn_Left = false;

    // Actions sequence for performing u-turns
    private Actions[] U_right = {Actions.FLY, Actions.FLY, Actions.FLY, Actions.HEADING_RIGHT, Actions.ECHO_FORWARD, Actions.FLY, Actions.HEADING_RIGHT, Actions.HEADING_RIGHT, Actions.HEADING_LEFT};
    private Actions[] U_left = {Actions.FLY, Actions.FLY, Actions.FLY, Actions.HEADING_LEFT, Actions.ECHO_FORWARD, Actions.FLY, Actions.HEADING_LEFT, Actions.HEADING_LEFT, Actions.HEADING_RIGHT};

    ScanIsland(Drone in_drone, Mapping mapping) {
        this.drone = in_drone;
        this.map = mapping;
    }

    /*
    Input: N/A
    Output: N/A
    Make the first move after finding island, depending on where the drone spawned from
    */
    private Actions initializeScanning() {
        // if drone spawns facing island, it will turn into the first line
        if (drone.facing_island) {
            curr_action = Actions.HEADING_RIGHT;
        } else {
            curr_action = Actions.SCAN;
        }
        map.setState(State.SCAN_ISLAND);
        last_turn_Left = false;
        return curr_action;
    }


//alternate between flying and scanning each tile until a water tile is scanned
    private Actions scanning() {
        if (curr_action == Actions.SCAN) {
            curr_action = Actions.FLY;
            // change action to echo forward if drone is on water tile
            if (drone.isWater()) {
                curr_action = Actions.ECHO_FORWARD;
                map.setState(State.EVAL_ECHO);
                logger.info("Switching states: "+ map.getState());
            }
            return curr_action;
        } else {
            curr_action = Actions.SCAN;
            return curr_action;
        }
    }

    private int fly_counter = 0;
    private Actions flyToGround() {
        if (fly_counter <= drone.getRange()) {
            curr_action = Actions.FLY;
            fly_counter++;
        } else {
            fly_to_ground = false;
            fly_counter = 0;
            curr_action = Actions.SCAN;
            map.setState(State.SCAN_ISLAND);
            logger.info("Switching states: "+ map.getState());
        }
        return curr_action;
    }

    private Actions evaluateEcho() {
        String forward = drone.getFound();
        
        // more ground ahead, fly to located ground
        if (forward.equals("GROUND")){
            map.setState(State.SCAN_ISLAND);
            logger.info("Switching states: "+ map.getState());
            fly_to_ground = true;
            U_turned = false;
            curr_action = Actions.FLY;
            return curr_action;
        }
        else {
            int range = drone.getRange();
            if (U_turned) {
                logger.info("Drone has Uturned into an empty line, stopping drone...");
                return Actions.STOP;
            } else if (range == 0) {
                logger.info("Drone is unable to U-turn");
                return Actions.STOP;
            } else if (range <= 3) {
                logger.info("SMALL UTURN");
                uturn_idx = 4 - range; //4 tiles forward in base UTURN
            }
            map.setState(State.UTURN);
            logger.info("Switching states: "+ map.getState());
            return UTurn();
        }
    }

    private int uturn_idx = 0;
    private Actions UTurn() {
        // stop drone if it will go out of range mid turn
        if (uturn_idx == 5 && !drone.getFound().equals("GROUND")) {
            if (drone.getRange() < 2){
                logger.info("Drone is unable to U-turn");
                return Actions.STOP;
            }
        }

        if (uturn_idx < U_left.length) {
            // alternate left and right U-turn sequence 
            if (last_turn_Left) {
                curr_action = U_right[uturn_idx];
                uturn_idx++;
                last_turn = Actions.HEADING_RIGHT;
                return curr_action;
            } 
            else {
                curr_action = U_left[uturn_idx];
                uturn_idx++;
                last_turn = Actions.HEADING_LEFT;
                return curr_action;
            }
        }

        saveLastTurn(last_turn);
        U_turned = true;
        uturn_idx = 0;

        curr_action = Actions.ECHO_FORWARD;
        map.setState(State.EVAL_ECHO);
        logger.info("Switching states: "+ map.getState());

        return curr_action;
    }

    private void saveLastTurn(Actions last_turn) {
        if (last_turn == Actions.HEADING_LEFT) {
            last_turn_Left = true;
        } else {
            last_turn_Left = false;
        }
    }

    // determine next action for scanning island
    public Actions getNextMove() {
        
        if (map.getState() == State.INIT_SCAN) {
            return initializeScanning();
        }
        if (map.getState() == State.EVAL_ECHO) {
            return evaluateEcho();
        }

        if (map.getState() == State.SCAN_ISLAND) {
            if (fly_to_ground) {
                logger.info("flying to ground!");
                return flyToGround();
            }
            return scanning();
        }

        if (map.getState() == State.UTURN) {
            return UTurn();
        }

        logger.info("something happened :()");
        return Actions.STOP;
    }
}
