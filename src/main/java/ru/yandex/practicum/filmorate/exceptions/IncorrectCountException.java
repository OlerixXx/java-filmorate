package ru.yandex.practicum.filmorate.exceptions;

public class IncorrectCountException extends IllegalArgumentException {
    public IncorrectCountException() {
    }

    public IncorrectCountException(String s) {
        super(s);
    }

    public IncorrectCountException(String message, Throwable cause) {
        super(message, cause);
    }

    public IncorrectCountException(Throwable cause) {
        super(cause);
    }
}
