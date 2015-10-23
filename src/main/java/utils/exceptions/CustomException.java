package utils.exceptions;

public class CustomException extends Exception {
    private final String message;
    private final String status;

    CustomException(String message, String status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
