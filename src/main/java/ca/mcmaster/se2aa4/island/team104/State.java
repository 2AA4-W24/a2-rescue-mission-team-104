package ca.mcmaster.se2aa4.island.team104;

import org.junit.Test;

public enum State {

    FIND_ISLAND,
    GO_TO_ISLAND,
    COAST_THE_COAST,
    FIND_SITE,
    STOP;

    public void incrementState(State state) {

        switch (state) {
            case FIND_ISLAND -> state = GO_TO_ISLAND;
            case GO_TO_ISLAND -> state = COAST_THE_COAST;
            case COAST_THE_COAST -> state = FIND_SITE;
            case FIND_SITE -> state = STOP;
        }
    }

    public void decrementState(State state) {

        switch (state) {
            case GO_TO_ISLAND -> state = FIND_ISLAND;
            case COAST_THE_COAST -> state = GO_TO_ISLAND;
            case FIND_SITE -> state = COAST_THE_COAST;
            case STOP -> state = FIND_SITE;
        }
    }


    public void stopExploration(State state) {
        state = STOP;

    }

    public void startExploration(State state) {
        state = FIND_ISLAND;
    }



}
