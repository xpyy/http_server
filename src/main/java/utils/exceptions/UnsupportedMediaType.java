package utils.exceptions;

import utils.enums.Status;

public class UnsupportedMediaType extends CustomException {
    public UnsupportedMediaType(String message) {
        super(message, Status.UNSUPPORTED_MEDIA_TYPE);
    }
}
