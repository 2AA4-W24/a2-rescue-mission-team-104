package ca.mcmaster.se2aa4.island.team104;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.json.JSONObject;


public class JSONParserTest {

    JSONParser parser = new JSONParser();
    private JSONObject json_obj;

    @BeforeEach
    public void initJSONObject() {
        json_obj = parser.createJSON();
    }

    @Test
    public void getIntValue() {
        String s = "{'cost':20,'extras':{},'status':'OK'}";  
        json_obj = parser.loadString(s);

        int cost = parser.getIntValue(json_obj, "cost");
        assertEquals(20, cost);
    }

    @Test
    public void getValueCrossCheck() {
        String s = "{'cost':20,'extras':{},'status':'OK'}";
        json_obj = parser.loadString(s);

        String parsedVal = parser.getValue(json_obj, "status");
        String crossCheckedVal = json_obj.getString("status");

        assertEquals(crossCheckedVal, parsedVal);
    }

    @Test 
    public void createAndPut() {
        json_obj = parser.createAndPut("action", "fly");
        assertTrue(json_obj.has("action"));
    }


}
