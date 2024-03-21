package ca.mcmaster.se2aa4.island.team104.exploration;

public enum State { //adding a comment

    INIT,
    FIND_ISLAND,
    GO_TO_ISLAND,
    INIT_SCAN,
    SCAN_ISLAND,
    UTURN,
    EVAL_ECHO,
    STOP;

    public State incrementState(State state) {

        switch (state) {
            case INIT -> state = FIND_ISLAND;
            case FIND_ISLAND -> state = GO_TO_ISLAND;
            case GO_TO_ISLAND -> state = INIT_SCAN;
            case INIT_SCAN -> state = SCAN_ISLAND;
            case SCAN_ISLAND -> state = STOP;
        }
        return state;
    }


}
