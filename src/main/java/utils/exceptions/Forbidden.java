package utils.exceptions;

import utils.enums.Status;

public class Forbidden extends CustomException {
    public Forbidden(String message) {
        super(message, Status.FORBIDDEN);
    }
}