package app.exceptions;

public class DeletingException extends Exception {
    public static String SELL_PLEASURE_BEFORE_DELETING = "Pleasures must be sold before deletion";
    public static String ADD_PLEASURE_BEFORE_DELETING = "Trying to delete not added pleasure";
    public static String ADD_DUTY_BEFORE_DELETING = "Trying to delete not added duty";

    public DeletingException() {
        super();
    }

    public DeletingException(String message) {
        super(message);
    }
}
