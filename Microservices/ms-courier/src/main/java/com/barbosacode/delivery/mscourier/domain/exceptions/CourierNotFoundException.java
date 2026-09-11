package com.barbosacode.delivery.mscourier.domain.exceptions;

public class CourierNotFoundException extends RuntimeException {
    public CourierNotFoundException() {
        super();
    }

    public CourierNotFoundException(String message) {
        super(message);
    }

    public CourierNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
