package utils.exceptions;

import utils.enums.Status;

public class NotFound extends CustomException {
    public NotFound(String message) {
        super(message, Status.NOT_FOUND);
    }
}
