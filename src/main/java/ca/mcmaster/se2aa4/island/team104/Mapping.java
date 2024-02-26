package ca.mcmaster.se2aa4.island.team104;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mapping {

    Map<Position, Tiles> pois = new HashMap<>();
    Position position;

    void updateTile(Statistics stats) {

        Tiles tile_type = null;

        //for creek
        if (stats.creek_found) {
            tile_type = Tiles.CREEK;
        }

        //for site
        else if (stats.site_found) {
            tile_type = Tiles.SITE;
            stats.site_found = false; //make it false now that it is marked
        }

        //for ground
        else if (Objects.equals(stats.found, "GROUND")) {
            tile_type = Tiles.GROUND;
        }

        //for home base
        else if (Objects.equals(stats.state, State.INIT)) {
            tile_type = Tiles.HOMEBASE;
        }

        //for water
        else {
            tile_type = Tiles.WATER;
        }

        //put the poi in the map
        pois.put(position, tile_type);

    }

}
