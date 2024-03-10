package ca.mcmaster.se2aa4.island.team104;
import org.apache.xpath.operations.Or;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import scala.collection.parallel.ParIterableLike;

public class Position {

    private final Logger logger = LogManager.getLogger();

    ArrayList<Integer> coordinates = new ArrayList<Integer>();

    private Integer X = 0; 
    private Integer Y = 0;

   
    private Orientation current_orient = Orientation.N;


    public Position(){
//        X = 0;
//        Y = 0;
        coordinates.add(0, X);
        coordinates.add(1, Y);
//        current_orient = current_orient.giveOrientation(heading);
    }

    public void setOrientation(String orient) {
        current_orient = current_orient.giveOrientation(orient);
    }
    
    public void updateForward(){

        if (this.current_orient == Orientation.N){
            Y = Y + 1;
            coordinates.set(1, Y);
        }
        else if (this.current_orient == Orientation.E){
            X = X + 1;
            coordinates.set(0, X);
        }
        else if (this.current_orient == Orientation.S){
            Y = Y - 1;
            coordinates.set(1, Y);
        }
        else if (this.current_orient == Orientation.W){
            X = X - 1;
            coordinates.set(0, X);
        }
        else {
            logger.error("Drone is Lost"); 
        }
    }

    public void updateRight(){

        if (this.current_orient == Orientation.N){
            X = X + 1;
            coordinates.set(0, X);
            current_orient = Orientation.E;
        }
        else if (this.current_orient == Orientation.E){
            Y = Y - 1;
            coordinates.set(1, Y);
            current_orient = Orientation.S;
        }
        else if (this.current_orient == Orientation.S){
            X = X - 1;
            coordinates.set(0, X);
            current_orient = Orientation.W;
        }
        else if (this.current_orient == Orientation.W){
            X = X + 1;
            coordinates.set(0, X);
            current_orient = Orientation.N;
        }
        // Implementing error handling
        else {
            logger.error("Drone is Lost"); 
        }
    }

    public void updateLeft(){

        if (this.current_orient == Orientation.N){
            X = X - 1;
            coordinates.set(0, X);
            current_orient = Orientation.W;
        }
        else if (this.current_orient == Orientation.E){
            Y = Y + 1;
            coordinates.set(1, Y);
            current_orient = Orientation.N;
        }
        else if (this.current_orient == Orientation.S){
            X = X + 1;
            coordinates.set(0, X);
            current_orient = Orientation.E;
        }
        else if (this.current_orient == Orientation.W){
            X = X - 1;
            coordinates.set(0, X);
            current_orient = Orientation.S;
        }
        else {
            logger.error("Drone is lost"); 
        }

    }
 
}
