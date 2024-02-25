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


    JSONObject nextAction() {
        //the 1000 is a placeholder
        if (stats.budget > 1000) {
            logger.info("This is state: " + stats.state);

            if (stats.state == State.INIT) {
                logger.info("This is action after init: "+ find_island.current_action);
                JSONObject actions = parser.createJSON();
                JSONObject parameters = parser.createJSON();
                String head_str = stats.heading.giveStringOrientation(stats.heading);
                logger.info("this is orientation: " + stats.heading + " this is it in string version: " + head_str);
                actions.put("action", "echo");
                parameters.put("direction", head_str);
                actions.put("parameters", parameters);
                stats.state = stats.state.incrementState(stats.state);
                logger.info("new state: " + stats.state);
                return actions;



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
                        logger.info("passes check");
                        String head_str = stats.heading.giveStringOrientation(stats.heading);
                        logger.info("this is orientation: " + stats.heading + "this is it in string version: " + head_str);
                        actions.put("action", find_island.current_action.getAction());
                        parameters.put("direction", head_str);
                        actions.put("parameters", parameters);
                    }

                    //return string version of Actions
                    return actions;
                }
            }
        }


        return null;
    }


}
