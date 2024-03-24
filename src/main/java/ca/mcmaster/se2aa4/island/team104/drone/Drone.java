package ca.mcmaster.se2aa4.island.team104.drone;

import ca.mcmaster.se2aa4.island.team104.JSONParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class Drone {

    private final Logger logger = LogManager.getLogger();
    private Integer budget = 0;
    private Orientation heading = Orientation.N;
    private String found;
    private Integer range;
    private Boolean water = false;
    private Boolean creek_found = false;
    private Boolean site_found = false;
    private Boolean facing_island = false;
    public String creek = "";
    public String site = "";

    private JSONParser parser = new JSONParser();
    
    /*
    Input: String
    Output: N/A
    Initializes the Drone's budget and heading.
     */
    public void initializeStats(String s) {
        JSONObject initial = parser.loadString(s);
        setBudget(initial);
        updateHeading(initial);
    }

    /*
    Input: String
    Output: N/A
    Updates statistics with each response
     */
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

    /*
    Input: JSONObject
    Output: N/A
    Determines if drone is on water after scan action.
     */
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

    /*
    Input: ArrayList<String>
    Output: N/A
    Determines if there is water after scan.
     */
    private void determineWater(ArrayList<String> biomes) {
        ArrayList<String> waterBiome = new ArrayList<String>();
        waterBiome.add("OCEAN");
        if (waterBiome.equals(biomes)) {
            logger.info("WATER TILE");
            this.water = true;
        } else {
            this.water = false;
        }

    }

    /*
    Input: JSONObject
    Output: N/A
    Takes in initial JSONObject to parse and get initial budget.
     */
    private void setBudget(JSONObject initInfo) {
        budget = parser.getIntValue(initInfo, "budget");
        logger.info("This is budget: " + budget);
    }

    /*
    Input: JSONObject
    Output: N/A
    Updates the Drone's budget
     */
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

    /*
    Input: JSONObject
    Output: N/A
    Updates the Drone's heading
     */
    private void updateHeading(JSONObject info) {
        if (info.has("heading")) {
            String current_head = parser.getValue(info, "heading");
            logger.info("This is current_head:" + current_head);
            //convert heading to Orientation type
            heading = heading.giveOrientation(current_head);
            logger.info("the heading: " + heading);
        }
    }

    /*
    Input: JSONObject
    Output: N/A
    Takes in response JSONObject to get range parameter.
     */
    private void updateRange(JSONObject info) {
        if (info.has("extras")) {
            JSONObject extras = info.getJSONObject("extras");
            if (extras.has("range")) {
                range = parser.getIntValue(extras, "range");
                logger.info("This is new range: " + range);
            }
        }
    }

    /*
    Input: JSONObject
    Output: N/A
    Updates the "found" parameter and throws an exception if the given JSONObject or needed parameters are empty.
     */
    private void updateFound(JSONObject info) {
        if (info.has("extras")) {
            JSONObject extras = info.getJSONObject("extras");
            if (extras.has("found")) {
                found = parser.getValue(extras, "found");
            }
        }
    }


    /*
    Input: JSONObject
    Output: N/A
    Takes in response JSONObject to parse and adds creek id to creek list.
     */
    private void updateCreeks(JSONObject info) {
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

    /*
    Input: JSONObject
    Output: N/A
    Takes in response JSONObject to parse and saves site id to site.
     */
    private void updateSites(JSONObject info) {
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

    /*
     Getters and Setters
     */
    public Integer getBudget() {
        return budget;
    }

    public Orientation getHeading() {
        return heading;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(int value) {
        this.range = value;
    }

    public String getFound() {
        return found;
    }

    public Boolean isWater() {
        return water;
    }

    public void setFacingIsland() {
        this.facing_island = true;
    }
    public Boolean facing_island() {
        return facing_island;
    }
    
    public Boolean getCreekFound() {
        return creek_found;
    }
    public void setCreekFound(Boolean bool) {
        this.creek_found = bool;
    }

    public Boolean getSiteFound() {
        return site_found;
    }
    public void setSiteFound(Boolean bool) {
        this.site_found = bool;
    }

    public void setHeading(Orientation new_orient) {
        heading = new_orient;
    }

}
