package app.exceptions;

public class ActivityNotEligibleToBeSoldException extends Exception {
    public ActivityNotEligibleToBeSoldException() {
        super();
    }

    public ActivityNotEligibleToBeSoldException(String message) {
        super(message);
    }
}
