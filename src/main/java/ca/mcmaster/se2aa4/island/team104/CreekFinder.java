package ca.mcmaster.se2aa4.island.team104;

public class CreekFinder {
    Mapping map = new Mapping();
    Statistics stats = new Statistics();
    
    Boolean echoF;
    Boolean echoR;
    Boolean echoL;

    Actions act = Actions.ECHO;
    //scan echoF echoR echoL -> update statistics each time and determine action based on if echo reveals: 1)GROUND 2)range:0
    //if no ground in front of you, turn right
    //fly: FR or F is ground
    //turn left: FRL or FL is ground

    private Actions getNextMove(Statistics stats) {
        return null;

    }
    private void updateMaps(Position pos, Tiles tile){

    }

    public void CreekFinder() {


    }

}
