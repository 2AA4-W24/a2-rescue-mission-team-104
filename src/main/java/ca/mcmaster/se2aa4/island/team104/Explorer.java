package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import eu.ace_design.island.bot.IExplorerRaid;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;



import java.util.Objects;

import javax.xml.stream.events.StartDocument;

// mvn exec:java -q -Dexec.args="./maps/map03.json"
public class Explorer implements IExplorerRaid {

    private final Logger logger = LogManager.getLogger();

    String next_action = "echo";
    int start = 0; //if start = 0 dont start making decisions
    String initial_head;
    int budget;
    Integer range = 1;
    String found = "OUT_OF_RANGE";
    int begin = 0; //count echos before moving

    boolean init_heading = true;
    boolean[] can_go = new boolean[4]; //lets you know in which direction drone can go in
    String flight_direction = "E";

    JSONParser parser = new JSONParser();
    Statistics stats = new Statistics();

    @Override
    public void initialize(String s) {
        logger.info("\n************ Initializing the Exploration Command Center ************");
        stats.initializeStats(s);
        budget = 7000;
        initial_head = "E";
    }

    @Override
    public String takeDecision() {
        JSONObject decision = new JSONObject();

        ////////   code added    ///////

        logger.info("this is start: " + start + " this is found: " + found);

        //this tells if the drone should stop/fly/echo --> will keep flying until range = 0
        if (Objects.equals(next_action, "stop")) {
            decision.put("action", "stop");
        }
        else if (Objects.equals(next_action, "fly")) {
            decision.put("action", "fly");
            next_action = "echo";
            if (budget < 30) {
                next_action = "stop";
            }
        }
        else if (Objects.equals(next_action, "heading")) {
            String go_in_direction = null;
            init_heading = false;

            //checks in which direction it can turn in
            for (int i = 0; i < can_go.length; i++) {
                if (can_go[i]) {
                    if (i == 0) {
                        go_in_direction = "N";
                    }
                    else if (i == 1) {
                        go_in_direction = "E";
                    }
                    else if (i == 2) {
                        go_in_direction = "S";
                    }
                    else if (i == 3) {
                        go_in_direction = "W";
                    }
                }
            }
            decision.put("action", "heading");
            JSONObject parameter = new JSONObject();
            parameter.put("direction", go_in_direction);
            decision.put("parameters",parameter);
            next_action = "fly";

        }
        else if (Objects.equals(next_action, "echo")) {
            JSONObject checker = new JSONObject();

            decision.put("action", "echo");

            if (begin == 3) {
                init_heading = false;
            }

            if (init_heading) {

                switch (initial_head) {
                    case ("E"):
                        if (begin == 0) {
                            checker.put("direction", "E");
                            begin++;
                        }
                        else if (begin == 1) {
                            checker.put("direction", "S");
                            begin++;
                        }
                        else if (begin == 2) {
                            checker.put("direction", "N");
                            begin++;
                        }
                }
            }
            else {
                checker.put("direction", "E");
            }
            decision.put("parameters", checker);
            next_action = "fly";
        }

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
            if (!extraInfo.isEmpty()) {
                found = extraInfo.getString("found");
                range = extraInfo.getInt("range");
                logger.info("this is range: " + range);
                if (range == 0 && !init_heading) {
                    next_action = "stop";
                }
                else if (begin == 3) {
                    next_action = "fly";
                }



            //only checks for initial heading
            if (Objects.equals(found, "GROUND") && !init_heading) {
                next_action = "heading";
                if (Objects.equals(flight_direction, "N")) {
                    can_go[0] = true;
                }
                else if (Objects.equals(flight_direction, "E")) {
                    can_go[1] = true;
                }
                else if (Objects.equals(flight_direction, "S")) {
                    can_go[2] = true;
                }
                else if (Objects.equals(flight_direction, "W")) {
                    can_go[3] = true;
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
