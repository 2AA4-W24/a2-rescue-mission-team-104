package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONPointerException;
import org.apache.commons.io.IOUtils;

import java.io.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class Statistics {

    private final Logger logger = LogManager.getLogger();

    //energy of drone
    Integer budget;

    //current heading of drone
    Orientation heading = Orientation.N; //setting as default

    //current state - turn into enum?
    State state = State.INIT;

    //results of echo
    Integer range;

    //results of echo/scan
    String found;

    Boolean creek_found = false;

    Boolean site_found = false;

    String creek = "";

    String site = "";

    JSONParser parser = new JSONParser();

    //takes in initial JSONObject to parse and get initial budget
    private void setBudget(JSONObject initInfo) {

        budget = parser.getIntValue(initInfo, "budget");
        logger.info("This is budget: " + budget);
    }

    //takes in cost to deduct from budget
    private void updateBudget(JSONObject info) {
        Integer cost = parser.getIntValue(info, "cost");
        //don't have enough money
        if (budget < cost) {
            logger.warn("You don't have enough battery");
        }
        //otherwise deduct
        else {
            budget -= cost;
        }
    }

    private void updateHeading(JSONObject info) {

        String current_head = parser.getValue(info, "heading");
        logger.info("This is current_head:" + current_head);
        //convert heading to Orientation type
        heading = heading.giveOrientation(current_head);
        logger.info("the heading: " + heading);
    }

    //takes in !!response!! JSONObject to get range parameter
    void updateRange(JSONObject info) {

        try {
            //create JSON objects for "extras" parameter
            JSONObject extraInfo = parser.createJSON();
            parser.put(extraInfo, "extras", parser.getValue(info, "extras"));

            //get range from "range" parameter in "extras"
            range = parser.getIntValue(extraInfo, "range");

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
                logger.info("There was an error fetching range.");

            }
        }
    }

    //updates the "found" parameter and throws an exception if the given JSONObject or needed parameters are empty
    void updateFound(JSONObject info) {

        //I used parser instead, so now we can remove try and catch (since it's done in json parser)

            try {
                //create JSONObject for extras parameter
                JSONObject extraInfo = info.getJSONObject("extras");

                found = parser.getValue(extraInfo, "found");


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

    //initialize stats with explorer start
    public void initializeStats(String s) {
        JSONObject initial = parser.loadString(s);

        setBudget(initial);
        updateHeading(initial);
        logger.info("initialization complete. budget: " + budget + " heading: " + heading + " state: " + state);

    }

    //updates statistics with each response
    public void updateStats(String s) {
        JSONObject info = parser.loadString(s);
        updateBudget(info);
        updateHeading(info);
        updateFound(info);
        updateRange(info);

        updateCreeks(info);
        updateSites(info);
    }


    //takes in !!response!! JSONObject to parse and adds creek id to creek list
    void updateCreeks(JSONObject info) {

        //create JSONObject for extras parameter
        JSONObject extraInfo = info.getJSONObject("extras");

        if (parser.getValue(extraInfo, "creeks") == null) {
            creek_found = false;

        }
        else {

            //create JSONArray to loop through
            JSONArray creeks_json = new JSONArray();
            creeks_json = extraInfo.getJSONArray("creeks");

            //create loop to get all elements into list
            for (int i = 0; i < creeks_json.length(); i++) {

                //converts creek id of JSONArray to string
                creek = creeks_json.get(i).toString();
                creek_found = true;
            }

        }
    }

    void updateSites(JSONObject info) {

        //create JSONObject for extras parameter
        JSONObject extraInfo = info.getJSONObject("extras");

        if (parser.getValue(extraInfo, "sites") == null) {
            site_found = false;

        }
        else {

            //create JSONArray to loop through
            JSONArray sites_json = new JSONArray();
            sites_json = extraInfo.getJSONArray("sites");

            //create loop to get all elements into list
            for (int i = 0; i < sites_json.length(); i++) {

                //converts creek id of JSONArray to string
                site = sites_json.get(i).toString();
                site_found = true;
            }

        }
    }

}
