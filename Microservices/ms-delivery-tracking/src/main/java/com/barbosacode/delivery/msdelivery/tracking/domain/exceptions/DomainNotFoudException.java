package com.barbosacode.delivery.msdelivery.tracking.domain.exceptions;

public class DomainNotFoudException extends RuntimeException {
    public DomainNotFoudException(String message) {
        super(message);
    }

    public DomainNotFoudException() {
    }

    public DomainNotFoudException(String message, Throwable cause) {
        super(message, cause);
    }
}
