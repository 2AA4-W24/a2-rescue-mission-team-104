package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import java.util.Objects;

public class FindIsland {

    private final Logger logger = LogManager.getLogger();
    Actions current_action = Actions.ECHO_FORWARD;
    private int init_facing = 0;
    private Integer forward_range;
    private Integer left_range;
    private Integer right_range;
    private Drone drone = new Drone();
    private Mapping map = new Mapping();


    private Boolean turned = false;
    private Boolean transition_state = false;
    private Integer temp_range = 0;
    public Boolean facing_island = false;



    //This is the constructor
    public FindIsland(Drone in_drone, Mapping in_map) {
        this.drone = in_drone;
        this.map = in_map;
    }


    /*
    Input: N/A
    Output: Action Object
    This function returns an action based on the distance between the three directions of the drone.
     */
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

    /*
    Input: N/A
    Output: Action Object
    Returns the final action of the FindIsland state.
     */
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



    /*
    Input: N/A
    Output: Action Object
    Returns the next action of the FindIsland state.
     */
    public Actions getNextMove() {
        if (!transition_state) {
            //initialization
            if (Objects.equals(drone.getFound(), "GROUND")) {
                //if the drone is initialized to face the island
                if (init_facing == 1) {
                    facing_island = true;
                    init_facing++;
                }
                transition_state = true;
                map.setState(State.GO_TO_ISLAND);
                current_action = finalMove();
                temp_range = drone.getRange();
                return current_action;

            }
        }
        if (map.getState() == State.GO_TO_ISLAND) {
            if (temp_range > 0) {
                temp_range -= 1;
                logger.info("new distance to island: " + temp_range);
                current_action = Actions.FLY;

                return current_action;
            }
            else {
                logger.info("______________________ISLAND REACHED");
                map.setState(State.INIT_SCAN);
                return current_action;            }
        }
        else if (map.getState() == State.FIND_ISLAND) {
            if (current_action == Actions.STANDBY) {
                current_action = Actions.ECHO_FORWARD;
                init_facing++;
                return current_action;            }

            //first echo forward then left
            else if (current_action == Actions.ECHO_FORWARD) {
                forward_range = drone.range;
                current_action = Actions.ECHO_LEFT;
                return current_action;            }

            //echo left then echo right
            else if (current_action == Actions.ECHO_LEFT) {
                left_range = drone.range;
                current_action = Actions.ECHO_RIGHT;
                return current_action;            }

            //echo right then determine which is the best way to go
            else if (current_action == Actions.ECHO_RIGHT) {
                right_range = drone.range;
                if (turned) {
                    current_action = intersection();
                } else {
                    current_action = Actions.FLY;
                }
                return current_action; }

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
