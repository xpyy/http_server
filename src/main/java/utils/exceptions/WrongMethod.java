package utils.exceptions;

import utils.enums.Status;

public class WrongMethod extends CustomException {
    public WrongMethod(String message) {
        super(message, Status.METHOD_NOT_ALLOWED);
    }
}
