package ca.mcmaster.se2aa4.island.team104.exploration;

import java.util.LinkedList;

import javax.swing.Action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import ca.mcmaster.se2aa4.island.team104.Statistics;
import ca.mcmaster.se2aa4.island.team104.map.Orientation;
import ca.mcmaster.se2aa4.island.team104.map.Tiles;

// zig zag to find creeks and site :)
public class ScanIsland {
    private final Logger logger = LogManager.getLogger();

    private Statistics stats;
    private Orientation init_heading;
    private Actions curr_action = Actions.FLY;
    private Actions last_turn;

    private Boolean fly_to_ground = false;
    private Boolean U_turned = false;
    private Boolean last_turn_Left = false;

    // Actions sequence for performing u-turns
    private Actions[] U_right = {Actions.FLY, Actions.FLY, Actions.HEADING_RIGHT, Actions.FLY, Actions.HEADING_RIGHT, Actions.HEADING_RIGHT, Actions.HEADING_LEFT};
    private Actions[] U_left = {Actions.FLY, Actions.FLY, Actions.HEADING_LEFT, Actions.FLY, Actions.HEADING_LEFT, Actions.HEADING_LEFT, Actions.HEADING_RIGHT};
    

    ScanIsland(Statistics statistics) {
        this.stats = statistics;
    }

// turn left on to the island to begin first zig
    private void initializeScanning() {
        this.init_heading = stats.getHeading();
        last_turn_Left = false;
        curr_action = Actions.SCAN;
    }


//alternate between flying and scanning each tile until a water tile is scanned
    private Actions scanning() {
        if (curr_action == Actions.SCAN) {
            curr_action = Actions.FLY;
            // change action to echo forward if drone is on water tile
            if (stats.isWater()) { 
                curr_action = Actions.ECHO_FORWARD;
                stats.setState(State.EVAL_ECHO);
                logger.info("Switching states: "+stats.getState());
            }
            return curr_action;
        } else {
            curr_action = Actions.SCAN;
            return curr_action;
        }
    }

    private int fly_counter = 0;
    private Actions flyToGround() {
        if (fly_counter <= stats.getRange()) {
            curr_action = Actions.FLY;
            fly_counter++;
        } else {
            fly_to_ground = false;
            fly_counter = 0;
            curr_action = Actions.SCAN;
            stats.setState(State.SCAN_ISLAND);
            logger.info("Switching states: "+stats.getState());
        }
        return curr_action;
    }

    private Actions evaluateEcho() {
        String forward = stats.getFound();
        
        // more ground ahead, fly to located ground
        if (forward.equals("GROUND")){
            stats.setState(State.SCAN_ISLAND);
            logger.info("Switching states: "+stats.getState());
            fly_to_ground = true;
            U_turned = false;
            curr_action = Actions.FLY;
            return curr_action;
        }
        else {
            logger.info("!!!!!ECHO FOUND NO GROUND AHEAD");
            if (U_turned) {
                logger.info("Drone has Uturned into an empty line, stopping drone...");
                return Actions.STOP;
            }
            stats.setState(State.UTURN);
            logger.info("Switching states: "+stats.getState());
            return Actions.FLY;
        }
    }

    private int U_counter = 0;
    private Actions UTurn() {
        if (U_counter < U_left.length) {
            // alternate left and right turn sequence each U-TURN
            if (last_turn_Left) {
                logger.info("TURNING RIGHT");
                curr_action = U_right[U_counter];
                U_counter++;
                last_turn = Actions.HEADING_RIGHT;
                return curr_action;
            } 
            else {
                logger.info("TURNING LEFT");
                curr_action = U_left[U_counter];
                U_counter++;
                last_turn = Actions.HEADING_LEFT;
                return curr_action;
            }
        }

        saveLastTurn(last_turn);
        U_turned = true;
        U_counter = 0;

        curr_action = Actions.ECHO_FORWARD;
        stats.setState(State.EVAL_ECHO);
        logger.info("Switching states: "+stats.getState());

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
        
        logger.info("____________________LAST ACTION: " + curr_action);

        if (stats.getState() == State.INIT_SCAN) {
            initializeScanning();
            stats.setState(State.SCAN_ISLAND);
            stats.resetRange();
            return curr_action;
        }
        if (stats.getState() == State.EVAL_ECHO) {
            return evaluateEcho();
        }

        if (stats.getState() == State.SCAN_ISLAND) {
            if (fly_to_ground) {
                logger.info("flying to ground!");
                return flyToGround();
            }
            return scanning();
        }

        if (stats.getState() == State.UTURN) {
            return UTurn();
        }

        logger.info("something happened :()");
        return Actions.STOP;
    }
}
