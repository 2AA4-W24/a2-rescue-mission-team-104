package ca.mcmaster.se2aa4.island.team104;

public enum State {

    INIT,
    FIND_ISLAND,
    GO_TO_ISLAND,
    INIT_SCAN,
    SCAN_ISLAND,
    UTURN,
    STOP;

    private State ret_state;

    State incrementState(State state) {

        switch (state) {
            case INIT -> state = FIND_ISLAND;
            case FIND_ISLAND -> state = GO_TO_ISLAND;
            case GO_TO_ISLAND -> state = INIT_SCAN;
            case INIT_SCAN -> state = SCAN_ISLAND;
            case SCAN_ISLAND -> state = STOP;
        }
        return state;
    }

    State incrementState2() {

        switch (this) {
            case INIT -> this.ret_state = FIND_ISLAND;
            case FIND_ISLAND -> this.ret_state = GO_TO_ISLAND;
            case GO_TO_ISLAND -> this.ret_state = SCAN_ISLAND;
            case SCAN_ISLAND -> this.ret_state = STOP;
        }
        return ret_state;

    }

    public State decrementState(State state) {

        switch (state) {
            case GO_TO_ISLAND -> this.ret_state = FIND_ISLAND;
            case SCAN_ISLAND -> this.ret_state = GO_TO_ISLAND;
            case STOP -> this.ret_state = SCAN_ISLAND;
        }

        return ret_state;
    }



    public State stopExploration() {
        return this.ret_state = STOP;

    }

    public State startExploration() {
        return this.ret_state = INIT;
    }



}
