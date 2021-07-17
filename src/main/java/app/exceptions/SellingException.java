package app.exceptions;

public class SellingException extends Exception {
    public static String CANNOT_SELL_DUTY = "Duties cannot be sold";
    public static String PLEASURE_NOT_FOUND = "Pleasure eligible to be sold not found";

    public SellingException() {
        super();
    }

    public SellingException(String message) {
        super(message);
    }
}
