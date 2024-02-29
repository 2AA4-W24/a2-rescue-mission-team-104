package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import scala.collection.parallel.ParIterableLike;

import java.util.Objects;

public class FindIsland {

    private final Logger logger = LogManager.getLogger();

    Actions current_action = Actions.ECHO_FORWARD;

    private Integer forward_range;

    private Integer left_range;

    private Integer right_range;

    private final Boolean turned = false;
    Statistics stats;


    FindIsland(Statistics statistics) {
        stats = statistics;

    }


    private Actions intersection() {
        logger.info("this is f_range: " + forward_range + " this is right range: " + right_range + " this is left range: " + left_range);

        //forward range has the greatest value
        if (forward_range > right_range && forward_range > left_range) {
            return Actions.FLY;
        }
        //left range has the greatest value
        else if (left_range > right_range && left_range > forward_range) {
            return Actions.HEADING_LEFT;
        }

        //right range has the greatest value
        else if (right_range > left_range && right_range > forward_range) {
            return Actions.HEADING_RIGHT;
        }

        else if (forward_range.equals(left_range) || forward_range.equals(right_range)) {
            return  Actions.FLY;
        }

        else {
            logger.info("Something went wrong with the computation.");
            return null;
        }
    }



    JSONObject getNextMove() {

        Controller controller = new Controller(stats);

        logger.info("this is current action: " + current_action);

        //initialization
        if (current_action == Actions.STANDBY) {
            current_action = Actions.ECHO_FORWARD;
            return controller.convertActionToJSON(current_action);
        }

        //first echo forward then left
        if (current_action == Actions.ECHO_FORWARD) {
            forward_range = stats.range;
            current_action = Actions.ECHO_LEFT;
            return controller.convertActionToJSON(current_action);
        }

        //echo left then echo right
        else if (current_action == Actions.ECHO_LEFT) {
            left_range = stats.range;
            current_action = Actions.ECHO_RIGHT;
            return controller.convertActionToJSON(current_action);
        }
        //echo right then determine which is the best way to go
        else if (current_action == Actions.ECHO_RIGHT) {
            right_range = stats.range;

            if (turned) {
                current_action = intersection();
            }
            else {
                current_action = Actions.FLY;
            }
            return controller.convertActionToJSON(current_action);
        }
        //no matter the direction echo forward
        else if (current_action == Actions.FLY) {
            current_action = Actions.ECHO_FORWARD;
            return controller.convertActionToJSON(current_action);
        }
        else if (current_action == Actions.HEADING_LEFT) {
            current_action = Actions.ECHO_FORWARD;
            return controller.convertActionToJSON(current_action);
        }
        else if (current_action == Actions.HEADING_RIGHT) {
            current_action = Actions.ECHO_FORWARD;
            return controller.convertActionToJSON(current_action);
        }
        else {
            logger.info("Something went wrong.");
            return null;
        }
    }
}
