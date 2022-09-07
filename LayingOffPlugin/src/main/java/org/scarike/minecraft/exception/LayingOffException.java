package org.scarike.minecraft.exception;

public class LayingOffException extends Exception{
    public LayingOffException() {
    }

    public LayingOffException(String message) {
        super(message);
    }

    public LayingOffException(String message, Throwable cause) {
        super(message, cause);
    }

    public LayingOffException(Throwable cause) {
        super(cause);
    }

    public LayingOffException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
