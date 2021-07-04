package rogue;
public class InvalidMoveException extends Exception {

    /**
     * Default constructor for exception.
     */
    public InvalidMoveException() {
        super();
    }

    /**
     * @param message - message to be said by exception.
     */
    public InvalidMoveException(String message) {
        super(message);
    }
}
