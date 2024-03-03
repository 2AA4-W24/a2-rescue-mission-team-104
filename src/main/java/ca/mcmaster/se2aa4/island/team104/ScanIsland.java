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
    Actions curr_action;
    Actions last_turn;


    //LinkedList<Task> tasks = new LinkedList<Task>();
    int count = 0;
    Actions[] U_right = {Actions.HEADING_RIGHT, Actions.HEADING_RIGHT, Actions.ECHO_FORWARD};
    Actions[] U_left = {Actions.HEADING_LEFT, Actions.HEADING_LEFT, Actions.ECHO_FORWARD};
    
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
    
    void UTurn() {
        if (last_turn == Actions.HEADING_LEFT) {
            curr_action = U_right[count];
        } else {
            curr_action = U_left[count];
        }
        count++;
    }

    void CWCircleBack() {
        curr_action = CWCircle[count];
        count++;
    }

    void CCWCircle() {
        curr_action = CCWCircle[count];
        count++;
    }

    Actions getNextMove() {
        State state = stats.getState();

        if (state == State.INIT_SCAN) {
            initializeScanning();
            stats.setState(State.SCAN_ISLAND);
            return curr_action;
        }

        if (state == State.SCAN_ISLAND) {
            return Actions.STOP;
        }

        return null;
    }
}
