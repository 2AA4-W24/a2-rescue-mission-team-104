package ca.mcmaster.se2aa4.island.team104.exploration;

import ca.mcmaster.se2aa4.island.team104.map.Mapping;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ca.mcmaster.se2aa4.island.team104.drone.Drone;
import java.util.Objects;

public class FindIsland implements PhaseInterface {

    private final Logger logger = LogManager.getLogger();
    private Actions current_action = Actions.ECHO_FORWARD;
    private int init_facing = 0;
    private Integer forward_range;
    private Integer left_range;
    private Integer right_range;
    private Drone drone = new Drone();
    private Mapping map = new Mapping();


    private Boolean turned = false;
    private Boolean transition_state = false;
    private Integer temp_range = 0;


    /*
    Input: Drone, Mapping
    Output:N/A
    The constructor.
     */
    public FindIsland(Drone in_drone, Mapping in_map) {
        this.drone = in_drone;
        this.map = in_map;
    }


    /*
    Input: N/A
    Output: Action Object
    This function returns an action based on the distance between the three directions of the drone.
     */
    private Actions intersection(Integer fr, Integer lr, Integer rr) {
        logger.info("this is f_range: " + fr + " this is right range: " + rr + " this is left range: " + lr);

        //forward range has the greatest value
        if (fr > rr && fr > lr) {
            return Actions.FLY;
        }
        //left range has the greatest value
        else if (lr > rr && lr > fr) {
            return Actions.HEADING_LEFT;
        }
        //right range has the greatest value
        else if (rr > lr && rr > fr) {
            return Actions.HEADING_RIGHT;
        }
        else if (fr.equals(lr) || fr.equals(rr)) {
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
        else { //fly straight to island
            return Actions.FLY;
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
                if (init_facing == 0) {
                    if (current_action.equals(Actions.ECHO_FORWARD)) {
                        drone.setFacingIsland();
                        logger.info("counter = " + init_facing + " flag: " + drone.facing_island());
                        init_facing++;
                    }
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
                current_action = Actions.FLY;

                return current_action;
            }
            else {
                logger.info("ISLAND REACHED");
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
                forward_range = drone.getRange();
                current_action = Actions.ECHO_LEFT;
                return current_action;            }
            //echo left then echo right
            else if (current_action == Actions.ECHO_LEFT) {
                left_range = drone.getRange();
                current_action = Actions.ECHO_RIGHT;
                return current_action;            }

            //echo right then determine which is the best way to go
            else if (current_action == Actions.ECHO_RIGHT) {
                right_range = drone.getRange();
                if (turned) {
                    current_action = intersection(forward_range,left_range, right_range);
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
