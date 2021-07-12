package app.exceptions;

public class ActivityNotEligibleToBeBoughtException extends Exception {

    public ActivityNotEligibleToBeBoughtException() {
        super();
    }

    public ActivityNotEligibleToBeBoughtException(String message) {
        super(message);
    }
}
