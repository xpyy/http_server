package utils.exceptions;

import utils.enums.Status;

public class BadRequest extends CustomException {
    public BadRequest(String message) {
        super(message, Status.BAD_REQUEST);
    }
}
