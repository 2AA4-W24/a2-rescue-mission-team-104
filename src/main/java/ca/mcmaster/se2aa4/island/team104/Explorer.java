package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONTokener;
import ca.mcmaster.se2aa4.island.team104.exploration.DecisionMaker;
import java.util.Objects;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();
    private DecisionMaker dm = new DecisionMaker();
    private JSONParser parser = new JSONParser();

    @Override
    public void initialize(String s) {
        logger.info("\n************ Initializing the Exploration Command Center ************");
        JSONObject initial = parser.loadString(s);
        logger.info("************ Initialization info: {}\n", initial);
        logger.info("\n************ Initialize End\n");
        logger.info("setting budget and heading");

        dm.initDrone(s);
        dm.setStopBudget();
    }

    @Override
    public String takeDecision() {
        JSONObject next_action = dm.nextAction();
        logger.info("*****Action going to perform: " + next_action);

        return next_action.toString();
    }

    @Override
    public void acknowledgeResults(String s) {
        JSONObject response = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Response received:\n"+response.toString(2));
        String status = response.getString("status");
        logger.info("The status of the drone is {}", status);

        dm.updateDrone(s); 
    }

    @Override
    public String deliverFinalReport() {
        String result = dm.getCreek();
        logger.info("This is the creek/inlet to be returned: " + result);
        if (Objects.equals(result, "NA")) {
            return "no creek found";
        }
        return result;
    }

}
