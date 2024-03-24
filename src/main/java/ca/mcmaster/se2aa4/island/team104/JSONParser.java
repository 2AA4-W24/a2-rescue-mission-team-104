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

    /*
    Input: String
    Output: JSONObject
    Loads String.
     */
    public JSONObject loadString(String s){
        try {
            return new JSONObject(new JSONTokener(new StringReader(s)));
        } catch (JSONException e) {
            logger.error("Error in loaded string");
            return null;
        }
    }

    /*
    Input: JSONObject, String
    Output: String
    Returns the String value stored in a JSONObject.
     */
    public String getValue(JSONObject json, String key) {
        if (json.has(key)) {
            return (json.getString(key));
        } else {
            logger.info("Key does not exist");
            return null;
        }
    }

    /*
    Input: JSONObject, String
    Output: Integer
    Returns the Integer value stored in a JSONObject.
     */
    public Integer getIntValue(JSONObject json, String key) {
        if (json.has(key)) {
            return json.getInt(key);
        } else {
            logger.info("Key does not exist");
            return null;
        }
    }
    
    /*
    Input: JSONObject
    Output: JSONObject
    Combine two JSONObjects to produce JSONObjects in JSONObjects
     */
    public JSONObject mergeJSONObjects(JSONObject json1, JSONObject json2, String key1, String key2, String value2) {
        // ex. {...,"parameters": {"direction": "E"}}
        return json1.put(key1, json2.put(key2, value2));
    }

    /*
    Input: JSONObject
    Output: N/A
    Merges two JSONObjects.
     */
    public void mergeJSONObjectsVoid(JSONObject json1, JSONObject json2, String key1, String key2, String value2) {
        json1.put(key1, json2.put(key2, value2));
    }

    /*
    Input: JSONObject
    Output: JSONObject
    Creates and returns a JSONObject
     */
    public JSONObject createAndPut(String key, String value) {
        JSONObject json1 = new JSONObject();
        json1.put(key, value);
        return json1;
    }
}
