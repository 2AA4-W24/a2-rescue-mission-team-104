package ca.mcmaster.se2aa4.island.team104;

public enum State {

    INIT,
    FIND_ISLAND,
    GO_TO_ISLAND,
    COAST_THE_COAST,
    FIND_SITE,
    STOP;

    private State ret_state;

    State incrementState(State state) {

        switch (state) {
            case INIT -> state = FIND_ISLAND;
            case FIND_ISLAND -> state = GO_TO_ISLAND;
            case GO_TO_ISLAND -> state = COAST_THE_COAST;
            case COAST_THE_COAST -> state = FIND_SITE;
            case FIND_SITE -> state = STOP;
        }
        return state;
    }

    State incrementState2() {

        switch (this) {
            case INIT -> this.ret_state = FIND_ISLAND;
            case FIND_ISLAND -> this.ret_state = GO_TO_ISLAND;
            case GO_TO_ISLAND -> this.ret_state = COAST_THE_COAST;
            case COAST_THE_COAST -> this.ret_state = FIND_SITE;
            case FIND_SITE -> this.ret_state = STOP;
        }
        return ret_state;

    }

    public State decrementState(State state) {

        switch (state) {
            case GO_TO_ISLAND -> this.ret_state = FIND_ISLAND;
            case COAST_THE_COAST -> this.ret_state = GO_TO_ISLAND;
            case FIND_SITE -> this.ret_state = COAST_THE_COAST;
            case STOP -> this.ret_state = FIND_SITE;
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
