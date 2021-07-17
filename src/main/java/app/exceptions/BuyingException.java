package app.exceptions;

public class BuyingException extends Exception {
    public static String CANNOT_BUY_DUTY = "Duties cannot be bought";
    public static String PLEASURE_NOT_FOUND = "Pleasure eligible to be bought not found";
    public static String NOT_ENOUGH_CLOCKS = "Not enough clocks";

    public BuyingException() {
        super();
    }

    public BuyingException(String message) {
        super(message);
    }
}
