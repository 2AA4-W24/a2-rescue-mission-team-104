package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import java.util.Objects;

public class DecisionMaker {

    Statistics stats = new Statistics();
    Mapping map = new Mapping();
    FindIsland find_island = new FindIsland();
    JSONParser parser = new JSONParser();

    private final Logger logger = LogManager.getLogger();



    //now make it so that when you see ground you get the current action of find_island and turn in that direction
    JSONObject nextAction() {
        //the 1000 is a placeholder
        if (stats.budget > 1000) {
            logger.info("this is the state: " + stats.state);

            if (stats.state == State.INIT) {
                logger.info("This is action after init: "+ find_island.current_action);

                JSONObject actions = parser.createJSON();
                JSONObject parameters = parser.createJSON();
                parser.put(actions, "action", "echo");

                String head_str = stats.heading.giveStringOrientation(stats.heading);
                JSONObject ret_action = parser.mergeJSONObjects(actions, parameters, "parameters", "direction", head_str);
                logger.info(ret_action);

                //update
                stats.state = stats.state.incrementState(stats.state);

                logger.info("new state: " + stats.state);
                return ret_action;



            }
            if (stats.state == State.FIND_ISLAND) {

                if (Objects.equals(stats.found, "GROUND")) {
                    stats.state = stats.state.incrementState(stats.state);
                }
                else {

                    find_island.current_action = find_island.getNextMove(stats);

                    logger.info("this is the action: " + find_island.current_action + " this is it in string version: " + find_island.current_action.getAction());
                    JSONObject actions = parser.createJSON();
                    JSONObject parameters = parser.createJSON();

                    if (find_island.current_action == Actions.ECHO_FORWARD) {
                        String head_str = stats.heading.giveStringOrientation(stats.heading);
                        logger.info("this is orientation: " + stats.heading + " this is it in string version: " + head_str);
                        actions.put("action", find_island.current_action.getAction());
                        parameters.put("direction", head_str);
                        actions.put("parameters", parameters);
                    }

                    else if (find_island.current_action == Actions.ECHO_LEFT) {
                        String head_left_str = stats.heading.giveStringOrientation(stats.heading.turnLeft(stats.heading));
                        logger.info("this is orientation: " + stats.heading + "this is it in string version: " + head_left_str);
                        actions.put("action", find_island.current_action.getAction());
                        parameters.put("direction", head_left_str);
                        actions.put("parameters", parameters);
                    }

                    else if (find_island.current_action == Actions.ECHO_RIGHT) {
                        String head_right_str = stats.heading.giveStringOrientation(stats.heading.turnRight(stats.heading));
                        logger.info("this is orientation: " + stats.heading + "this is it in string version: " + head_right_str);
                        actions.put("action", find_island.current_action.getAction());
                        parameters.put("direction", head_right_str);
                        actions.put("parameters", parameters);
                    }

                    else if (find_island.current_action == Actions.FLY) {
                        actions.put("action", find_island.current_action.getAction());
                    }
                    else if (find_island.current_action == Actions.HEADING_RIGHT) {
                        parser.put(actions, "action", "heading");
                        String head_right_str = stats.heading.giveStringOrientation(stats.heading.turnRight(stats.heading));
                        parser.put(parameters, "direction", head_right_str);
                        actions.put("parameters", parameters);
                        stats.heading = stats.heading.turnRight(stats.heading);
                    }
                    else if (find_island.current_action == Actions.HEADING_LEFT) {
                        parser.put(actions, "action", "heading");
                        String head_left_str = stats.heading.giveStringOrientation(stats.heading.turnLeft(stats.heading));
                        parser.put(parameters, "direction", head_left_str);
                        actions.put("parameters", parameters);
                        stats.heading = stats.heading.turnLeft(stats.heading);
                    }



                    //return string version of Actions
                    return actions;
                }
            }
        }


        return null;
    }


}
