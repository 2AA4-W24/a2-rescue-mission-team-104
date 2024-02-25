package ca.mcmaster.se2aa4.island.team104;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.xpath.operations.Or;

import java.util.Objects;

enum Orientation {

    N, E, S, W;

    private final Logger logger = LogManager.getLogger();

    Orientation giveOrientation(String orient) {

        if (Objects.equals(orient, "E")) {
            return Orientation.E;
        }
        else {
            return null;

//        switch (orient) {
//            case "N" -> {
//                return Orientation.N;
//            }
//            case "E" -> {
//                return Orientation.E;
//            }
//            case "S" -> {
//                return Orientation.S;
//            }
//            case "W" -> {
//                return Orientation.W;
//            }
//            default -> {
//                logger.info("Please input valid direction.");
//                return null;
//
//            }
        }

    }

    String giveStringOrientation(Orientation orient) {

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
