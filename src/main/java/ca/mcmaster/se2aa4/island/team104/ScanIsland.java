package ca.mcmaster.se2aa4.island.team104;

import java.util.LinkedList;

import javax.swing.Action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

// zig zag to find creeks and site :)
public class ScanIsland {
    private final Logger logger = LogManager.getLogger();

    Statistics stats;
    Orientation init_heading;
    Actions curr_action = Actions.FLY;
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
    void initializeScanning() {
        init_heading = stats.getHeading();
        curr_action = Actions.HEADING_LEFT;
        last_turn = Actions.HEADING_LEFT;
    }

//alternate between flying and scanning each tile
    void scanning() {
        if (curr_action == Actions.SCAN) {
            curr_action = Actions.FLY;
        } else {
            curr_action = Actions.SCAN;
        }
    }

    //Boolean correctStats() {
    //     if (stats.found == water)
    //}

    int count = 0;
    private Actions UTurn() {
        if (count <= 1) {
            logger.info("help " + last_turn);
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

    void CWCircleBack() {
        curr_action = CWCircle[count];
        count++;
    }

    void CCWCircle() {
        curr_action = CCWCircle[count];
        count++;
    }


    int counter = 0;
    int counting = 0;

    Actions getNextMove() {

        logger.info("LAST ACTION: " + curr_action);

        if (counting > 200) {
            logger.info("200 actions?");
            return Actions.STOP;
        }
        counting++;

        State state = stats.getState();
        if (state == State.INIT_SCAN) {
            initializeScanning();
            stats.setState(State.SCAN_ISLAND);
            stats.resetRange();
            return curr_action;
        }

        if (state == State.SCAN_ISLAND) {
            
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
            counter = 0;
            curr_action = Actions.SCAN;
            return curr_action;
        }

        else if (state == State.UTURN) {
            if (curr_action == Actions.ECHO_FORWARD) {
                if (stats.getFound().equalsIgnoreCase("GROUND")){
                    stats.setState(State.SCAN_ISLAND);
                    curr_action = Actions.SCAN;
                    return curr_action;
                }
                else {
                    logger.info("FORWARD NO GROUND :(");
                    return Actions.STOP;
                }
            }
            UTurn();
            logger.info("!!uturning");
            return curr_action;
        }
        
        return Actions.STOP;

    }
}
