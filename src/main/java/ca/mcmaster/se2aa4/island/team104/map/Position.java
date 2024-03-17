package ca.mcmaster.se2aa4.island.team104.map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;


public class Position {

    private final Logger logger = LogManager.getLogger();

    public ArrayList<Integer> coordinates = new ArrayList<Integer>();

    private Integer X = 0;
    private Integer Y = 0;


    //create new constructor that takes in x and y
    public Position(Integer x, Integer y) {
        X = x;
        Y = y;
    }

    ArrayList<Integer> getPosition() {
        return this.coordinates;
    }

    // Keeping track of orientation
    Orientation current_orient = Orientation.N;



    public Position(){
        coordinates.add(0, X);
        coordinates.add(1, Y);
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
            coordinates.set(1, Y);        }
        else if (this.current_orient == Orientation.W){
            X = X - 1;
            coordinates.set(0, X);        }

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

    public Double diffBtwnPoints(Integer x1, Integer x2, Integer y1, Integer y2) {
        Double term1 = Math.pow((x2-x1), 2);
        Double term2 = Math.pow((y2-y1), 2);
        return Math.sqrt(term1+term2);
    }

    public Integer[] coordsToArr(Position position1) {
        Integer[] coordinates_arr = new Integer[2];
        coordinates_arr[0] = position1.getX();
        coordinates_arr[1] = position1.getY();
        return coordinates_arr;

    }

    public Integer getX() {return this.X;}
    public Integer getY() {return this.Y;}




}
