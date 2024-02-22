package ca.mcmaster.se2aa4.island.team104;

public class Drone {

    // Setting as a default direction
    Orientation direction  = Orientation.N;
    Position position = new Position(direction);

    public void moveForward(){
        // Keeps same direction;
        position.updateForward();
    }

    public void moveRight(){
        // Setting it equal to direction to update the current direction and store returned results 
        direction = direction.turnRight(direction);
        position.updateRight();
    }

    public void moveLeft(){
        direction = direction.turnLeft(direction);
        position.updateLeft();
    }
    
}
