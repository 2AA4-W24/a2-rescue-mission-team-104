package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.operations.Or;
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
    Integer budget = 0;

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

        if (info.has("heading")) {

            String current_head = parser.getValue(info, "heading");
            logger.info("This is current_head:" + current_head);
            //convert heading to Orientation type
            heading = heading.giveOrientation(current_head);
            logger.info("the heading: " + heading);
        }
    }

    //takes in !!response!! JSONObject to get range parameter
    void updateRange(JSONObject info) {

        if (info.has("extras")) {
            JSONObject extras = info.getJSONObject("extras");
            if (extras.has("range")) {
                range = parser.getIntValue(extras, "range");
            }
        }
    }

    //updates the "found" parameter and throws an exception if the given JSONObject or needed parameters are empty
    void updateFound(JSONObject info) {

        if (info.has("extras")) {
            JSONObject extras = info.getJSONObject("extras");
            if (extras.has("found")) {
                found = parser.getValue(extras, "found");
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
        logger.info(info);
        updateBudget(info);
        updateHeading(info);
        updateFound(info);
        updateRange(info);

        updateCreeks(info);
        updateSites(info);
    }


    //takes in !!response!! JSONObject to parse and adds creek id to creek list
    void updateCreeks(JSONObject info) {

        if (info.has("extras")) {
            JSONObject extraInfo = info.getJSONObject("extras");

            if (extraInfo.has("creeks")) {
                //create JSONArray to loop through
                JSONArray creeks_json = new JSONArray();
                creeks_json = extraInfo.getJSONArray("creeks");

                if (!creeks_json.isEmpty()) {
                    //create loop to get all elements into list
                    for (int i = 0; i < creeks_json.length(); i++) {

                        //converts creek id of JSONArray to string
                        creek = creeks_json.get(i).toString();
                        creek_found = true;
                    }
                }
            }
        }
        else {
            creek_found = false;
        }
    }

    void updateSites(JSONObject info) {

        if (info.has("extras")) {
            JSONObject extraInfo = info.getJSONObject("extras");

            if (extraInfo.has("sites")) {
                //create JSONArray to loop through
                JSONArray sites_json = new JSONArray();
                sites_json = extraInfo.getJSONArray("sites");

                if (!sites_json.isEmpty()) {
                    //create loop to get all elements into list
                    for (int i = 0; i < sites_json.length(); i++) {

                        //converts creek id of JSONArray to string
                        site = sites_json.get(i).toString();
                        site_found = true;
                    }
                }
            }
        }
        else {
            site_found = false;
        }
    }

    Integer getBudget() {
        return budget;
    }

    Orientation getHeading() {
        return heading;
    }

    State getState() {
        return state;
    }

    Integer getRange() {
        return range;
    }

    String getFound() {
        return found;
    }

    void setState(State new_state) {
        state = new_state;
    }

    void setHeading(Orientation new_orient) {
        heading = new_orient;
    }

}
