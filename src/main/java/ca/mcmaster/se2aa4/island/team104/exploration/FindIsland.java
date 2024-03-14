package ca.mcmaster.se2aa4.island.team104.exploration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ca.mcmaster.se2aa4.island.team104.Statistics;

import java.util.Objects;

public class FindIsland {

    private final Logger logger = LogManager.getLogger();

    Actions current_action = Actions.ECHO_FORWARD;

    private Integer forward_range;

    private Integer left_range;

    private Integer right_range;

    Statistics stats;

    Boolean turned = false;
    Boolean transition_state = false;

    Integer temp_range = 0;


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
            return Actions.STOP;
        }
    }

    private Actions finalMove() {
        //if the previous action echoed left and found ground turn left
        if (current_action == Actions.ECHO_LEFT) {
            return Actions.HEADING_LEFT;
        }
        //if the previous action echoed right and found ground turn right
        else if (current_action == Actions.ECHO_RIGHT) {

            return Actions.HEADING_RIGHT;
        }
        else {
            logger.info("Something went wrong with final move.");
            return Actions.STOP;
        }

    }



    Actions getNextMove() {


        //        logger.info("this is current action: " + current_action);
        //        logger.info("this is what was found: "+ stats.getFound());
        if (!transition_state) {
            //initialization
            if (Objects.equals(stats.getFound(), "GROUND")) {
                transition_state = true;
                stats.setState(State.GO_TO_ISLAND);
                current_action = finalMove();
                temp_range = stats.getRange();
                return current_action;

            }
        }
        if (stats.getState() == State.GO_TO_ISLAND) {
            if (temp_range > 0) {
                temp_range -= 1;
                logger.info("new distance to island: " + temp_range);
                current_action = Actions.FLY;

                return current_action;

            }

//next state!!
            else {
                logger.info("______________________ISLAND REACHED");
                stats.setState(State.INIT_SCAN);
                return current_action;            }

        }
        else if (stats.getState() == State.FIND_ISLAND) {
            if (current_action == Actions.STANDBY) {
                current_action = Actions.ECHO_FORWARD;

                return current_action;            }


            //first echo forward then left
            else if (current_action == Actions.ECHO_FORWARD) {
                forward_range = stats.range;
                current_action = Actions.ECHO_LEFT;
                return current_action;            }


            //echo left then echo right
            else if (current_action == Actions.ECHO_LEFT) {
                left_range = stats.range;
                current_action = Actions.ECHO_RIGHT;
                return current_action;            }

            //echo right then determine which is the best way to go
            else if (current_action == Actions.ECHO_RIGHT) {
                right_range = stats.range;
                if (turned) {
                    current_action = intersection();
                } else {
                    current_action = Actions.FLY;
                }

                return current_action;            }
            //no matter the direction echo forward
            else if (current_action == Actions.FLY || current_action == Actions.HEADING_LEFT || current_action == Actions.HEADING_RIGHT) {
                current_action = Actions.ECHO_FORWARD;
                return current_action;            } else {
                logger.info("Something went wrong.");
                return current_action;            }


        }

        logger.info("In wrong State");
        return current_action;    }

}
