package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

import java.util.Iterator;
import java.util.Map;

import static java.util.LinkedHashMap.newLinkedHashMap;

public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    int count = 0; // tells drone the action
    int start = 0; //if start = 0 dont start making decisions
    String initial_head = "";
    int budget;
    Integer range = 1;

    @Override
    public void initialize(String s) {
        logger.info("** Initializing the Exploration Command Center");
        JSONObject info = new JSONObject(new JSONTokener(new StringReader(s)));
        logger.info("** Initialization info:\n {}",info.toString(2));
        String direction = info.getString("heading");
        Integer batteryLevel = info.getInt("budget");

        initial_head = direction;
        budget = batteryLevel;


        logger.info("The drone is facing {}", direction);
        logger.info("Battery level is {}", batteryLevel);
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();

        ////////   code added    ///////

        //this tells if the drone should stop/fly/echo --> will keep flying until range = 0
        if (start == 2) {
            decision.put("action", "stop");
        }
        else if (start == 0) {
            decision.put("action", "fly");
            start = 1;
        }
        else if (start == 1) {
            decision.put("action", "echo");
            JSONObject checker = new JSONObject();

            if (count == 0) {
                checker.put("direction", initial_head);
                start = 0;
            }

            decision.put("parameters", checker);


        }

        // if initial head is == East --> scan East North South
            //if east found: ground --> go east for range
            // if north found: ground --> go north for range
            // ...
        // if initial head is == North --> scan North West East
        // if initial head is == South --> scan South East West
        // if initial head is West --> scan west north east

        //////////////////////////////////



//        decision.put("action", "stop"); // we stop the exploration immediately
        logger.info("** Decision: {}",decision.toString());
        return decision.toString();
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


        //////     code added      //////

        //this code fetches the value of range
        if (start == 0) {
            if (!extraInfo.isEmpty()) {
                range = extraInfo.getInt("range");
                logger.info("this is range: " + range);
                if (range == 0) {
                    start = 2;
                }
            }
        }


        ////////////////////////////////
    }

    @Override
    public String deliverFinalReport() {
        return "no creek found";
    }

}
