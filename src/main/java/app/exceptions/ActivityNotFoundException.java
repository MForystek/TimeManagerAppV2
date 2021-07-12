package app.exceptions;

public class ActivityNotFoundException extends Exception {
    public ActivityNotFoundException() {
        super();
    }

    public ActivityNotFoundException(String message) {
        super(message);
    }
}
