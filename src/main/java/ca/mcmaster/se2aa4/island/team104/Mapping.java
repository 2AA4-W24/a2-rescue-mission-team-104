package ca.mcmaster.se2aa4.island.team104;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mapping {

    Map<Position, Tiles> pois = new HashMap<>();

    Position position = new Position();
    Orientation heading = Orientation.N;

    //test getter
    String getPos() {
        return String.valueOf(position.getPosition());
    }


    void setInitHeading(Orientation orient) {
        heading = orient;
        position.setOrientation(heading.giveStringOrientation2());
    }

    void updateTile(Statistics stats) {

        Tiles tile_type;

        //for creek
        if (stats.getCreekBool()) {
            tile_type = Tiles.CREEK;
        }

        //for site
        else if (stats.getSiteBool()) {
            tile_type = Tiles.SITE;
            stats.site_found = false; //make it false now that it is marked
        }

        //for ground
        else if (Objects.equals(stats.getFound(), "GROUND")) {
            tile_type = Tiles.GROUND;
        }

        //for home base
        else if (Objects.equals(stats.getState(), State.INIT)) {
            tile_type = Tiles.HOMEBASE;
        }

        //for water
        else {
            tile_type = Tiles.WATER;
        }

        //put the poi in the map
        pois.put(position, tile_type);
    }

    void updatePosition(Actions action) {

        if (action == Actions.FLY) {
            position.updateForward();
        }
        else if (action == Actions.HEADING_LEFT) {
            position.updateForward();
            position.updateLeft();
        }
        else if (action == Actions.HEADING_RIGHT) {
            position.updateForward();
            position.updateRight();
        }

    }

}
