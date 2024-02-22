package ca.mcmaster.se2aa4.island.team104;

enum Actions {
    FLY, //Fly forward
    ECHO,
    SCAN,
    HEADING,
    STOP;

    

    public String getAction() {
        switch (this) {
            case FLY:
                return "fly";
            
            case ECHO:
                return "echo";
            
            case SCAN:
                return "scan";

            case HEADING:
                return "heading";

            case STOP:
                return "stop";
        
            default:
                return null;
        }
    }
    
}
