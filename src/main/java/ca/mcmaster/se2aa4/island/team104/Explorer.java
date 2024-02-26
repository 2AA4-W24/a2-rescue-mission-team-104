package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;



import java.util.Objects;

// mvn exec:java -q -Dexec.args="./maps/map03.json"
public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    DecisionMaker dm = new DecisionMaker();

    JSONParser parser = new JSONParser();

    @Override
    public void initialize(String s) {
        logger.info("\n************ Initializing the Exploration Command Center ************");
        
        JSONObject initial = parser.loadString(s);
        logger.info("************ Initialization info: {}\n", initial);
    
//        initial_head = parser.getValue(initial, "heading");
//        budget = parser.getIntValue(initial, "budget");

//        logger.info("** The drone is facing {}", initial_head);
//        logger.info("** Battery level is {}", budget);

        logger.info("\n************ Initialize End\n");

        ///////////////////////////////////////////// 2-25
        logger.info("setting budget and heading");
        dm.stats.initializeStats(s); //set budget, heading, and state
        dm.map.updateTile(dm.stats); //set home base
        dm.stats.state.incrementState(dm.stats.state); //increment initial state to find island
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();

        JSONObject next_action = dm.nextAction();


//        return decision.toString();
        return next_action.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        Integer cost = response.getInt("cost");
        logger.info("The cost of the action was {}", cost);
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);
        JSONObject extraInfo = response.getJSONObject("extras");
        logger.info("Additional information received: {}", extraInfo);



        ////////////////////////////////////
        dm.stats.updateStats(s); //update stats
        dm.map.updateTile(dm.stats); // then update map
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
