package org.scarike.minecraft.exception;

public class ChatSyncException extends RuntimeException{
    public ChatSyncException() {
    }

    public ChatSyncException(String message) {
        super(message);
    }

    public ChatSyncException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChatSyncException(Throwable cause) {
        super(cause);
    }

    public ChatSyncException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
