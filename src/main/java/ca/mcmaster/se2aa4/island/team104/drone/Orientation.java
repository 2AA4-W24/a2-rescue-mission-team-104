package ca.mcmaster.se2aa4.island.team104.drone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public enum Orientation {

    N, E, S, W;
    private Orientation heading;

    private final Logger logger = LogManager.getLogger();

    public Orientation giveOrientation(String orient) {

//        switch (orient) {
//            case "E" -> {
//                return  E;
//            }
//            case "N" -> {
//                return N;
//            }
//            case "S" -> {
//                return S;
//            }
//            case "W" -> {
//                return W;
//            }
//            default -> {
//                return null;
//            }
//        }

        if (Objects.equals(orient, "E")) {
            return E;
        }
        else if (Objects.equals(orient, "N")) {
            return N;
        }
        else if (Objects.equals(orient, "S")) {
            return S;
        }
        else if (Objects.equals(orient, "W")) {
            return W;
        }
        else {
            logger.info("No valid direction was inputted.");
            return null;
        }

    }

   public  String giveStringOrientation(Orientation orient) {

        switch (orient) {
            case N -> {
                return "N";
            }
            case E -> {
                return "E";
            }
            case S -> {
                return "S";
            }
            case W -> {
                return "W";
            }
            default -> {
                return null;
            }

        }
    }
    public String giveStringOrientation2() {

        switch (this) {
            case N -> {
                return "N";
            }
            case E -> {
                return "E";
            }
            case S -> {
                return "S";
            }
            case W -> {
                return "W";
            }
            default -> {
                return null;
            }

        }
    }


    Orientation turnRight(Orientation current_orient) {

        switch (current_orient) {
            case N -> current_orient = E;
            case E -> current_orient = S;
            case S -> current_orient = W;
            case W -> current_orient = N;
        }
        return current_orient;
    }

    public Orientation turnRight2() {

        switch (this) {
            case N -> this.heading = E;
            case E -> this.heading = S;
            case S -> this.heading = W;
            case W -> this.heading = N;
        }
        return this.heading;
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

    public Orientation turnLeft2() {

        switch (this) {
            case N -> this.heading = W;
            case E -> this.heading = N;
            case S -> this.heading = E;
            case W -> this.heading = S;
        }
        return this.heading;
    }

}
