package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser {
    private final Logger logger = LogManager.getLogger();

    private JSONObject jsonObject;

    public JSONParser() {
        this.jsonObject = new JSONObject();
    }
    public JSONObject viewJSON() {
        return this.jsonObject;
    }
    public void loadString(String s){
        try {
            this.jsonObject = new JSONObject(new JSONTokener(new StringReader(s)));
            logger.info("JSON String loaded");
        } catch (JSONException e) {
            logger.error("Error in loaded string");
        }
    }
    public void put(String key, String value) {
        this.jsonObject.put(key, value);
        logger.info("Placed " + key + ": " + value);

    }
    public String getValue(String key) {
        if (this.jsonObject.has(key)) {
            return this.jsonObject.getString(key);
        } else {
            logger.info("Key does not exist");
            return null;
        }
    }
    public Integer getIntValue(String key) {
        if (this.jsonObject.has(key)) {
            return this.jsonObject.getInt(key);
        } else {
            logger.info("Key does not exist");
            return null;
        }
    }





    /* 
    public String getHeading() {
        String direction = initial.getString("heading");
        return direction;
    }
    public Integer getBudget() {
        Integer budget = initial.getInt("budget");
        return budget;
    }

    public void writeAction(String action) {
        decision.put("action", action);
    }
    public void translateResult(String s){
        this.results = new JSONObject(new JSONTokener(new StringReader(s)));
    }

    public int getCost() {
        int cost = results.getInt("cost");
        return cost;
    }
    public String getStatus() {
        String status = results.getString("status");
        return status;
    }
    public JSONObject getExtras() {
        this.extraInfo = results.getJSONObject("extras");
        return extraInfo;
    }
    private void extractExtras(JSONObject extraInfo) {

    }*/
}
