package ca.mcmaster.se2aa4.island.team104.drone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Objects;

public enum Orientation {

    N, E, S, W;

    private Orientation heading;
    private final Logger logger = LogManager.getLogger();

    /*
    Input: String
    Output: Orientation Object
    Returns the Orientation object of the given string.
     */
    public Orientation giveOrientation(String orient) {

        try {

            if (Objects.equals(orient, "E")) {
                return E;
            } else if (Objects.equals(orient, "N")) {
                return N;
            } else if (Objects.equals(orient, "S")) {
                return S;
            } else if (Objects.equals(orient, "W")) {
                return W;
            }
        } catch (Exception e) {
                logger.info("No valid direction was inputted.");
        }
        return heading;
    }

    /*
    Input: N/A
    Output: String
    Returns the String object of the current orientation object.
     */
    public String giveStringOrientation() {

        try {

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
            }
        } catch (Exception e) {
            logger.info("Something went wrong.");
        }
        return "STOP";
    }

    /*
    Input: N/A
    Output: Orientation
    Returns the updated orientation after turning right.
     */
    public Orientation turnRight() {
        switch (this) {
            case N -> this.heading = E;
            case E -> this.heading = S;
            case S -> this.heading = W;
            case W -> this.heading = N;
        }
        return this.heading;
    }

    /*
    Input: N/A
    Output: Orientation
    Returns the updated orientation after turning left.
     */
    public Orientation turnLeft() {
        switch (this) {
            case N -> this.heading = W;
            case E -> this.heading = N;
            case S -> this.heading = E;
            case W -> this.heading = S;
        }
        return this.heading;
    }
}
