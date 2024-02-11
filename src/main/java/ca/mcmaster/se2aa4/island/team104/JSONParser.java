package ca.mcmaster.se2aa4.island.team104;

import java.io.StringReader;

import org.json.JSONObject;
import org.json.JSONTokener;

public class JSONParser {
    private JSONObject decision; 
    private JSONObject results;

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
        JSONObject extraInfo = results.getJSONObject("extras");
        return extraInfo;
    }
}
