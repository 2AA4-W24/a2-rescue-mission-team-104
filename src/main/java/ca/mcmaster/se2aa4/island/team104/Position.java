package ca.mcmaster.se2aa4.island.team104;
import java.util.ArrayList;

public class Position {

    ArrayList<Integer> coordinates = new ArrayList<Integer>();

    //Initializing coordinate system upon beginning. 
    private Integer X = 0; 
    private Integer Y = 0;

    private Orientation current_orient;
    
    public Position(Orientation current_orient){
        this.current_orient = current_orient;

        coordinates.add(X);
        coordinates.add(Y);
    }

    // Position is only keeping track of the coordinate System?

    // Default orientation. sET TO north?
   
    
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
    }

    // is this the actual movement?
    public void updateRight(){

        if (this.current_orient == Orientation.N){
            X++;
        }
        else if (this.current_orient == Orientation.E){
            Y--;
        }
        else if (this.current_orient == Orientation.S){
            X--; 
        }
        else if (this.current_orient == Orientation.W){
            X++;
        }

    }

    public void updateLeft(){

        if (this.current_orient == Orientation.N){
            X--;
        }
        else if (this.current_orient == Orientation.E){
            Y++;
        }
        else if (this.current_orient == Orientation.S){
            X++; 
        }
        else if (this.current_orient == Orientation.W){
            X--;
        }

    }
 
}
