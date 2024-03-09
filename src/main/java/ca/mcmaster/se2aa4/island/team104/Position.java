package ca.mcmaster.se2aa4.island.team104;
import java.util.ArrayList;

public class Position {

    // Keeping everything relative to where it started. 

    ArrayList<Integer> coordinates = new ArrayList<Integer>();

    //Orientation direction  = Orientation.N;
    //Position position = new Position(direction);

    //Initializing coordinate system upon beginning. 
    private Integer X = 0; 
    private Integer Y = 0;

    // Keeping track of orientation 
    private Orientation current_orient = Orientation.N;

    public Position(){

        coordinates.add(X);
        coordinates.add(Y);
    }
    
    public void updateForward(){

        if (this.current_orient == Orientation.N){
            Y++;
        }
        else if (this.current_orient == Orientation.E){
            X++;
        }
        else if (this.current_orient == Orientation.S){
            Y--; 
        }
        else if (this.current_orient == Orientation.W){
            X--;
        }
        else {
            System.out.println("Drone is Lost"); 
        }
    }

    // is this the actual movement?
    public void updateRight(){

        if (this.current_orient == Orientation.N){
            X++;
            current_orient = Orientation.E;
        }
        else if (this.current_orient == Orientation.E){
            Y--;
            current_orient = Orientation.S;
        }
        else if (this.current_orient == Orientation.S){
            X--; 
            current_orient = Orientation.W;
        }
        else if (this.current_orient == Orientation.W){
            X++;
            current_orient = Orientation.N;
        }
        // Implementing error handling
        else {
            System.out.println("Drone is Lost"); 
        }
    }

    public void updateLeft(){

        if (this.current_orient == Orientation.N){
            X--;
            current_orient = Orientation.W;
        }
        else if (this.current_orient == Orientation.E){
            Y++;
            current_orient = Orientation.N;
        }
        else if (this.current_orient == Orientation.S){
            X++; 
            current_orient = Orientation.E;
        }
        else if (this.current_orient == Orientation.W){
            X--;
            current_orient = Orientation.S;
        }
        else {
            System.out.println("Drone is lost"); 
        }

    }
 
}
