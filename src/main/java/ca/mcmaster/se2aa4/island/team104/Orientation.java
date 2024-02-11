package ca.mcmaster.se2aa4.island.team104;

enum Orientation {

    N, E, S, W;

    Orientation turnRight(Orientation current_orient) {

        switch (current_orient) {
            case N -> current_orient = E;
            case E -> current_orient = S;
            case S -> current_orient = W;
            case W -> current_orient = N;
        }
        return current_orient;
    }

    Orientation turnLeft(Orientation current_orient) {

        switch (current_orient) {
            case N -> current_orient = W;
            case E -> current_orient = N;
            case S -> current_orient = E;
            case W -> current_orient = S;
        }
        return current_orient;
    }
}
