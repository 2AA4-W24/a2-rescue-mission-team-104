package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;

import java.util.ArrayList;
import java.util.Map;

public class Statistics {

    private final Logger logger = LogManager.getLogger();

    //energy of drone
    int budget;

    //current heading of drone
    String heading;

    int range;
    String found;

    //ids of creeks found
    ArrayList<String> creeks;

    //ids of sites found
    String sites;

    //x and y of specified creek
    Map<String, float[]> creek_locations;

    //x and y of specified site
    Map<String, float[]> site_locations;


    //takes in initial JSONObject to parse and get initial budget
    void initBudget(JSONObject info) {

        budget = info.getInt("budget");

    }

    //takes in cost to deduct from budget
    void updateBudget(int cost) {

        //don't have enough money
        if (budget < cost) {

            logger.warn("You don't have enough battery");
        }
        //otherwise deduct
        else {
            budget -= cost;
        }

    }

    //takes in !!response!! JSONObject to get range parameter
    void updateRange(JSONObject info) {

        //!!response!! object (from explorer) is empty
        if (!info.isEmpty()) {

            logger.warn("The JSONObject passed is empty");
        }
        //!!extras!! is empty
        else if (info.getJSONObject("extras").isEmpty()) {

            logger.warn("The extras parameter is empty.");
        }
        else {

            JSONObject extraInfo = info.getJSONObject("extras");

            //try to get range from extras parameter
            try {
                range = extraInfo.getInt("range");

            } catch (JSONException e) {

                logger.info("There was an error fetching range.");
            }

        }


    }

    void updateFound(JSONObject info) {




            try {
                //create JSONObject for extras parameter
                JSONObject extraInfo = info.getJSONObject("extras");

                //update found based on "found" parameter
                found = extraInfo.getString("found");

            } catch (JSONException e) {

                //!!response!! object (from explorer) is empty
                if (!info.isEmpty()) {

                    logger.warn("The JSONObject passed is empty");
                }
                //!!extras!! is empty
                else if (info.getJSONObject("extras").isEmpty()) {

                    logger.warn("The extras parameter is empty.");
                }
                else {

                    logger.info("There was an error fetching found.");
                }
            }



    }

    //takes in !!response!! JSONObject to parse
    void updateCreeks(JSONObject info) {

        try {

            //create JSONObject for extras parameter
            JSONObject extraInfo = info.getJSONObject("extras");

            //if creeks parameters is empty throw exception otherwise parse
            if (extraInfo.getJSONArray("creeks").isEmpty()) {
                throw new JSONException("creeks is empty");
            }
            else {

                //create JSONArray to loop through
                JSONArray creeks_json = new JSONArray();
                creeks_json = extraInfo.getJSONArray("creeks");

                //create loop to get all elements into list
                for (int i = 0; i < creeks_json.length(); i++) {

                    //add elements from JSONArray to creeks ArrayList
                    creeks.add(creeks_json.getString(i));
                }

            }

        } catch (JSONException e) {
            if (!info.isEmpty()) {
                logger.warn("The JSONObject passed is empty");
            }
            //!!extras!! is empty
            else if (info.getJSONObject("extras").isEmpty()) {
                logger.warn("The extras parameter is empty.");
            }
            else {
                logger.info("There was an error fetching found.");
            }
        }

    }

}
