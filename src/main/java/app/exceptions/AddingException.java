package app.exceptions;

public class AddingException extends Exception {
    public static String PLEASURE_ALREADY_ADDED = "This pleasure is already added";
    public static String DUTY_ALREADY_ADDED = "This duty is already added";

    public AddingException() {
        super();
    }

    public AddingException(String message) {
        super(message);
    }
}
