package ca.mcmaster.se2aa4.island.team104;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Mapping {

    Map<Tiles, Position> pois = new HashMap<>();

    ArrayList<Integer[]> history; //will be replaced by position type

    /*

    1. I can replace Integer[] position with Drone once the Drone and Position class are developed. This way I can
    extract the location of drone from its Position parameter.
    2. I can remove String found_from_stats with Statistics to extract what was found. For example, if found (which in this
    case would be tile_type == "GROUND", the Tiles parameter of the thing being added to the map is of type GROUND
    from the enum.

     */
    Position position;
    Statistics stats;

    void markTile() {

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
        else if (Objects.equals(stats.state, "initialize")) {
            tile_type = Tiles.HOMEBASE;
        }

        //for water
        else {
            tile_type = Tiles.WATER;
        }

        //put the poi in the map
        pois.put(tile_type, position);

    }

    //this function checks if anything useful came from stats, if so it marks if by calling the above function
    void updateMap() {

        if (stats.site_found || stats.creek_found) {

            markTile();
        }

    }
}
