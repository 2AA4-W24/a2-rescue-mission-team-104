package ca.mcmaster.se2aa4.island.team104;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JSONParserTest2 {
    JSONParser parser = new JSONParser();
    JSONObject json_obj = parser.createJSON();


    @BeforeEach
    public void initJSONObject() {
    }

    @Test
    public void getIntValue() {
        String s = "{'cost':20,'extras':{},'status':'OK'}";  
        json_obj = parser.loadString(s);

        int cost = parser.getIntValue(json_obj, "cost");
        assertEquals(20, cost);
    }
}
