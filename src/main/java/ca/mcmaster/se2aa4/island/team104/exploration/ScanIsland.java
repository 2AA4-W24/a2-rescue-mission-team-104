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

    Statistics stats;
    Orientation init_heading;
    Actions curr_action = Actions.SCAN;
    Actions last_turn;

    Tiles water;

    Boolean secondPass = false;


    //LinkedList<Task> tasks = new LinkedList<Task>();
    Actions[] U_right = {Actions.HEADING_RIGHT, Actions.HEADING_RIGHT};
    Actions[] U_left = {Actions.HEADING_LEFT, Actions.HEADING_LEFT};
    
    Actions[] CWCircle = {Actions.HEADING_LEFT, Actions.HEADING_RIGHT, Actions.HEADING_RIGHT, Actions.FLY, Actions.HEADING_RIGHT};
    Actions[] CCWCircle = {Actions.HEADING_RIGHT, Actions.HEADING_LEFT, Actions.HEADING_LEFT, Actions.FLY, Actions.HEADING_LEFT};


    ScanIsland(Statistics statistics) {
        this.stats = statistics;
    }

// turn left on to the island to begin first zig
    private void initializeScanning() {
        init_heading = stats.getHeading();
        //curr_action = Actions.HEADING_LEFT;
        last_turn = Actions.HEADING_RIGHT;
        curr_action = Actions.SCAN;
    }

//alternate between flying and scanning each tile
    private void scanning() {
        if (curr_action == Actions.SCAN) {
            curr_action = Actions.FLY;
        } else {
            curr_action = Actions.SCAN;
        }
    }

    int count = 0;
    private Actions UTurn() {
        if (count <= 1) {
            logger.info("LAST TURN " + last_turn);
            if (last_turn == Actions.HEADING_LEFT) {
                logger.info("TURNING RIGHT");
                curr_action = U_right[count];
                return U_right[count++];
            } else {
                logger.info("TURNING LEFT");
                curr_action = U_left[count];
                return U_left[count++];
            }
        }
        count = 0;
        last_turn = curr_action;
        curr_action = Actions.ECHO_FORWARD;
        return curr_action;
    }

    boolean CWCircleBack() {
        logger.info("************cw count: " + count);
        if (count < CWCircle.length) {
            curr_action = CWCircle[count];
            count++;
            return true;
        }
        count = 0;
        
        return false;
    }

    boolean CCWCircle() {
        logger.info("************ccw count: " + count);
        if (count < CCWCircle.length) {
            curr_action = CCWCircle[count];
            count++;
            return true;
        }
        count = 0;
        return false;
    }

    int counter = 0;
    int counting = 0;

    Actions getNextMove() {
        
        logger.info("LAST ACTION: " + curr_action);
        //logger.info("ACTION COUNT" + counting);
        /*if (counting > 600) {
            logger.info("200 actions?");
            return Actions.STOP;
        }
        counting++;*/

        
        if (stats.getState() == State.INIT_SCAN) {
            initializeScanning();
            stats.setState(State.SCAN_ISLAND);
            stats.resetRange();
            return curr_action;
        }

        if (stats.getState() == State.SCAN_ISLAND) {
            
            if (counter <= stats.getRange()){
                stats.resetWater();
                logger.info("FLYING BACK TO ISLAND RANGE:" + stats.getRange());
                counter++;
                curr_action = Actions.FLY;
                return curr_action;
            }
            if (!stats.isWater()){
                scanning();
                return curr_action;
            }
            stats.setState(State.UTURN);
            logger.info("STATE SWITCHED: U-TURNING");
            counter = 0;
        }

        if (stats.getState() == State.UTURN) {
            if (curr_action == Actions.ECHO_FORWARD) {
                if (stats.getFound().equalsIgnoreCase("GROUND")){
                    stats.setState(State.SCAN_ISLAND);
                    curr_action = Actions.SCAN;
                    return curr_action; //fix after making into indiv functions
                }
                else {
                    logger.info("FORWARD NO GROUND :(");
                    if (secondPass == false) {
                        this.secondPass = true;
                        stats.setState(State.CIRCLE_BACK);
                        logger.info("STATE SWITCHED: CIRCLING BACK");
                        return Actions.SCAN;
                    }
                    else {
                        return Actions.STOP;
                    }
                }
            }
            UTurn();
            logger.info("!!uturning");
            return curr_action;
        }
        if (stats.getState() == State.CIRCLE_BACK) {
            if (last_turn == Actions.HEADING_LEFT) {
                if (CCWCircle()) {
                    logger.info("turning ccw");

                    return curr_action;
                }
                else {
                    stats.setState(State.SCAN_ISLAND);
                    logger.info("STATE SWITCHED: SCANNING");
                    return Actions.SCAN;
                }
            }
            else {
                if (CCWCircle()) {
                    logger.info("turning cw");

                    return curr_action;
                }
                else {
                    stats.setState(State.SCAN_ISLAND);
                    logger.info("STATE SWITCHED: SCANNING");
                    return Actions.SCAN;
                }
            }
        }


        logger.info("something happened :()");
        return Actions.STOP;

    }
}
