package ca.mcmaster.se2aa4.island.team104.drone;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;


public class Position {

    private final Logger logger = LogManager.getLogger();
    private Integer X = 0;
    private Integer Y = 0;
    public ArrayList<Integer> coordinates = new ArrayList<Integer>();
    public Orientation current_orient = Orientation.N;

    //constructor
    public Position(){
        coordinates.add(0, X);
        coordinates.add(1, Y);
    }

    /*
    Input: String
    Output: N/A
    Sets given Orientation
     */
    public void setOrientation(String orient) {
        current_orient = current_orient.giveOrientation(orient);
    }

    /*
    Updates position of object when it moves forward based on its orientation.
     */
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
            coordinates.set(1, Y);        }
        else if (this.current_orient == Orientation.W){
            X = X - 1;
            coordinates.set(0, X);        }

    }

    /*
    Updates position of object when it moves right based on its orientation.
     */
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

    /*
    Updates position of object when it moves left based on its orientation.
     */
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

    /*
    Input: Integer for all parameters
    Output: Double
    Calculates the distance between two coordinates.
     */
    public Double diffBtwnPoints(Integer x1, Integer x2, Integer y1, Integer y2) {
        Double term1 = Math.pow((x2-x1), 2);
        Double term2 = Math.pow((y2-y1), 2);
        return Math.sqrt(term1+term2);
    }

    /*
    Input: Position Object
    Output: Integer[]
    Converts coordinates of the given position to an Integer array.
     */
    public Integer[] coordsToArr(Position position1) {
        Integer[] coordinates_arr = new Integer[2];
        coordinates_arr[0] = position1.getX();
        coordinates_arr[1] = position1.getY();
        return coordinates_arr;

    }

    //returns X coordinate of position object
    public Integer getX() {return this.X;}

    //returns Y coordinate of position obejct
    public Integer getY() {return this.Y;}




}
