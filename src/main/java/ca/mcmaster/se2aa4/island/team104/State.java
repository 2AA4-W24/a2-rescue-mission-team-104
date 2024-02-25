package ca.mcmaster.se2aa4.island.team104;

public enum State {

    INIT,
    FIND_ISLAND,
    GO_TO_ISLAND,
    COAST_THE_COAST,
    FIND_SITE,
    STOP;

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

    public State decrementState(State state) {

        switch (state) {
            case GO_TO_ISLAND -> state = FIND_ISLAND;
            case COAST_THE_COAST -> state = GO_TO_ISLAND;
            case FIND_SITE -> state = COAST_THE_COAST;
            case STOP -> state = FIND_SITE;
        }

        return state;
    }


    public State stopExploration(State state) {
        return state = STOP;

    }

    public State startExploration(State state) {
        return state = INIT;
    }



}
