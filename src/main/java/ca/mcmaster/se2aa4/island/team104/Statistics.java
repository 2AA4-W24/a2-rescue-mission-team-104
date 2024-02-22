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
    int budget;

    //current heading of drone
    String heading;

    //current state - turn into enum?
    String state;

    //results of echo
    Integer range;

    //results of echo/scan
    String found;

    Actions act;

    Boolean creek_found = false;

    Boolean site_found = false;

    String creek = "";

    String site = "";

    JSONParser parser = new JSONParser();

    //takes in initial JSONObject to parse and get initial budget
    private void setBudget(JSONObject initInfo) {
        budget = parser.getIntValue(initInfo, "cost");
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
        heading = parser.getValue(info, "heading");
    }

    //keeps track of state decided in decision maker
    void updateState(String current_state) {
        state = current_state;
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

/* 
    //once drone stops, this function will use the pois.json to get coordinates of sites
    void compileSites() {

        try {

            //read _pois.json file
            FileReader pois_file = new FileReader("./outputs/_pois.json");
            BufferedReader pois_buffer = new BufferedReader(pois_file);
            ArrayList<JSONObject> pois_json_arr = new ArrayList<JSONObject>();
            String pois_line = pois_buffer.readLine();


            while (pois_line != null) {

                System.out.println(pois_line);

            }
//            System.out.println(pois_json_arr);

        } catch (IOException e) {
            logger.error("_pois.json file not found.");
        }

    }
*/
}
