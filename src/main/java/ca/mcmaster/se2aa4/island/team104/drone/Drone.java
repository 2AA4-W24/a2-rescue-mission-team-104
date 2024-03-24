package ca.mcmaster.se2aa4.island.team104.drone;

import ca.mcmaster.se2aa4.island.team104.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class Drone {

    private final Logger logger = LogManager.getLogger();

    //energy of drone
    public Integer budget = 0;

    //current heading of drone
    public Orientation heading = Orientation.N; //setting as default

    //current state - turn into enum?
//    public State state = State.INIT;

    //results of echo
    public String found;
    public Integer range;

    //results
    public Boolean water = false;

    public Boolean creek_found = false;

    public Boolean site_found = false;

    public String creek = "";

    public String site = "";

    private Boolean facing_island = false;

    public JSONParser parser = new JSONParser();

    //takes in initial JSONObject to parse and get initial budget
    public void setBudget(JSONObject initInfo) {

        budget = parser.getIntValue(initInfo, "budget");
        logger.info("This is budget: " + budget);
    }

    //takes in cost to deduct from budget
    public void updateBudget(JSONObject info) {
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

    public void updateHeading(JSONObject info) {

        if (info.has("heading")) {

            String current_head = parser.getValue(info, "heading");
            logger.info("This is current_head:" + current_head);
            //convert heading to Orientation type
            heading = heading.giveOrientation(current_head);
            logger.info("the heading: " + heading);
        }
    }

    //takes in !!response!! JSONObject to get range parameter
    public void updateRange(JSONObject info) {

        if (info.has("extras")) {
            JSONObject extras = info.getJSONObject("extras");
            if (extras.has("range")) {
                range = parser.getIntValue(extras, "range");
                logger.info("This is new range: " + range);
            }
        }
    }

    //updates the "found" parameter and throws an exception if the given JSONObject or needed parameters are empty
    public void updateFound(JSONObject info) {

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
    }

    //updates statistics with each response
    public void updateStats(String s) {
        JSONObject info = parser.loadString(s);
        logger.info(info);
        updateBudget(info);
        updateHeading(info);
        updateFound(info);
        updateScan(info);
        updateRange(info);

        updateCreeks(info);
        updateSites(info);
        if (creek_found) {
            logger.info("CREEK FOUND: " + creek);
        }
        if (site_found) {
            logger.info("SITE FOUND: " + site);
        }
    }

    public void updateScan(JSONObject info) {
        
        if (info.has("extras")) {
            JSONObject extraInfo = info.getJSONObject("extras");

            if (extraInfo.has("biomes")) {
                //create JSONArray to loop through
                JSONArray biomes_json = new JSONArray();
                biomes_json = extraInfo.getJSONArray("biomes");
                ArrayList<String> biomes = new ArrayList<String>();

                for (int i = 0; i < biomes_json.length(); i++) {
                    biomes.add(String.valueOf(biomes_json.get(i)));
                }
                determineWater(biomes);
            }
        }       
    }
    public void determineWater(ArrayList<String> biomes) {
        ArrayList<String> waterBiome = new ArrayList<String>();
        waterBiome.add("OCEAN");
        if (waterBiome.equals(biomes)) {
            logger.info("WATER TILE");
            this.water = true;
        } else {
            this.water = false;
        }

    }

    //takes in !!response!! JSONObject to parse and adds creek id to creek list
    public void updateCreeks(JSONObject info) {

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

    public void updateSites(JSONObject info) {

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

    public Integer getBudget() {
        return budget;
    }

    public Orientation getHeading() {
        return heading;
    }

    public Integer getRange() {
        return range;
    }

    public void resetRange() {
        this.range = 0;
    }

    public String getFound() {
        return found;
    }

    public Boolean isWater() {
        return water;
    }
    public void resetWater() {
        this.water = false;
    }

    public void setFacingIsland() {
        this.facing_island = true;
    }
    public Boolean facing_island() {
        return facing_island;
    }

    public Boolean getCreekBool() { return creek_found; }

    public Boolean getSiteBool() { return site_found; }

    public void setHeading(Orientation new_orient) {
        heading = new_orient;
    }

}
