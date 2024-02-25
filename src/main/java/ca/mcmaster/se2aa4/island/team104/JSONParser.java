package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser {
    private final Logger logger = LogManager.getLogger();

    public JSONObject createJSON() {
        return new JSONObject();
    }
  
    public JSONObject loadString(String s){
        try {            
//            logger.info("JSON String loaded");
            return new JSONObject(new JSONTokener(new StringReader(s)));
        } catch (JSONException e) {
            logger.error("Error in loaded string");
            return null;
        }
    }
    public void put(JSONObject json, String key, String value) {
        json.put(key, value);
//        logger.info("Placed " + key + ": " + value);

    }
    public String getValue(JSONObject json, String key) {
        if (json.has(key)) {
            return String.valueOf(json.getString(key));
        } else {
            logger.info("Key does not exist");
            return null;
        }
    }
    public Integer getIntValue(JSONObject json, String key) {
        if (json.has(key)) {
            return json.getInt(key);
        } else {
            logger.info("Key does not exist");
            return null;
        }
    }
    
    public JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2, String key1, String key2, String value2) {
        //{...,"parameters": {"direction": "E"}}
        return json1.put(key1, json2.put(key2, value2));
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
