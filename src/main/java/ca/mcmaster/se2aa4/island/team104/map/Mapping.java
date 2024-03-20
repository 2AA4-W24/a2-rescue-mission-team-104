package ca.mcmaster.se2aa4.island.team104.map;

import ca.mcmaster.se2aa4.island.team104.Drone;
import ca.mcmaster.se2aa4.island.team104.exploration.Actions;
import ca.mcmaster.se2aa4.island.team104.exploration.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Mapping {

    public Map<Integer[], Tiles> pois = new HashMap<>();
    public Map<Integer[], String> creeks = new HashMap<Integer[], String>();
    private final Logger logger = LogManager.getLogger();
    public String site_id = "";
    public Integer[] site_loc;

    public Position position = new Position();
    public Orientation heading = Orientation.N;

    public State state = State.INIT;

    //test getter
    public String getPos() {
        return String.valueOf(position.getPosition());
    }

    public State getState() {
        return state;
    }

        public void setState(State new_state) {
        state = new_state;
    }

    public void setInitHeading(Orientation orient) {
        heading = orient;
        position.setOrientation(heading.giveStringOrientation2());
    }

    public void updateTile(Drone stats) {

        Tiles tile_type = null;
        Integer[] coord2 = new Integer[2];

        coord2[0] = position.getX();
        coord2[1] = position.getY();

        //for creek
        if (stats.getCreekBool()) {
            tile_type = Tiles.CREEK;
            creeks.put(coord2, stats.creek);
            pois.put(coord2, tile_type);
            stats.creek_found = false;
        }

        //check if a site was found, if so, set it as site location, and put it in pois list
        if (stats.getSiteBool()) {
            tile_type = Tiles.SITE;
            site_loc = position.coordsToArr(position);
            site_id = stats.site;
            pois.put(coord2, tile_type);
            stats.site_found = false; //make it false now that it is marked
        }

    }

    public void printPois() {
        StringBuilder points = new StringBuilder();
        for (Integer[] coord : pois.keySet()) {
            String coord_str = Arrays.toString(coord);
            points.append(coord_str).append(": ").append(pois.get(coord)).append(", ");
            logger.info("pois2: " + points + ", ");
        }
    }

    public void printCreeks() {
        StringBuilder points = new StringBuilder();
        for (Integer[] coord : creeks.keySet()) {
            String coord_str = Arrays.toString(coord);
            points.append(coord_str).append(": ").append(creeks.get(coord)).append(", ");
        }
    }

    public String retCreek() {

        //if there are no sites and creeks return NA
        if (creeks.isEmpty() && !pois.containsValue(Tiles.SITE)) {
            return "NA";
        }
        //if there are no creeks in "creeks" return inlet
        else if (creeks.isEmpty()) {
            return site_id;
        }
        //if there is only one creek return it or if there is no site but multiple creeks
        else if (creeks.size() == 1 || (!pois.containsValue(Tiles.SITE))) {
            for (Integer[] coord : creeks.keySet()) {
                return creeks.get(coord);
            }

        }

        //determine shortest distance between creeks and site
        int i = 0;
        String shortest_dist_creek = "";
        Double shortest_distance = 0.0;
        Double current_distance = 0.0;
        //iterate over creeks in creek list
        for (Integer[] coord : creeks.keySet()) {
            //on the first iteration calculate the diff between site coordinates and first creek and set it as shortest distanced creek
            if (i == 0) {
                shortest_distance = position.diffBtwnPoints(coord[0], site_loc[0], coord[1], site_loc[1]);
                shortest_dist_creek = creeks.get(coord);
            }
            //after the first iteration determine the distance between the site and current coordinate
            else if (i > 0) {
                current_distance = position.diffBtwnPoints(coord[0], site_loc[0], coord[1], site_loc[1]);
                //if the current distance is less than the "shortest" coordinate switch the shortest distance and creek id
                if (current_distance < shortest_distance) {
                    shortest_distance = current_distance;
                    shortest_dist_creek = creeks.get(coord);
                }
            }
            i++;
        }
        return shortest_dist_creek;
    }

    public void updatePosition(Actions action) {

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

    public void getPois() {
        StringBuilder creek_sites = new StringBuilder();

        for (Map.Entry<Integer[], String> pair : creeks.entrySet()) {
            creek_sites.append(pair.getValue()).append(" ");
        }
        logger.info("*****PRINTING CREEKS: ");
        logger.info(creek_sites);
    }



}
