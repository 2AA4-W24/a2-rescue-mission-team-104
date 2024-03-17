package ca.mcmaster.se2aa4.island.team104.exploration;

public enum Actions {

    STANDBY,
    FLY, //Fly forward
    ECHO_FORWARD,
    ECHO_RIGHT,
    ECHO_LEFT,
    SCAN,
    HEADING_RIGHT,
    HEADING_LEFT,
    STOP;

     

    public String getAction() {
        switch (this) {
            case FLY:
                return "fly";
            
            case ECHO_FORWARD, ECHO_LEFT, ECHO_RIGHT:
                return "echo";

            case SCAN:
                return "scan";

            case HEADING_RIGHT, HEADING_LEFT:
                return "heading";

            case STOP:
                return "stop";
        
            default:
                return null;
        }
    }
    
}
