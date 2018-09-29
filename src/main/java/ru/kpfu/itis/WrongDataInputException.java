package ru.kpfu.itis;


public class WrongDataInputException extends Exception {

    public WrongDataInputException() {
    }

    public WrongDataInputException(Throwable cause) {
        super(cause);
    }

    public WrongDataInputException(String message) {
        super(message);
    }

    public WrongDataInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongDataInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
