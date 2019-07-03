package ru.levin.tm.exception;

import org.jetbrains.annotations.NotNull;

public class NoIdForCurrentUserException extends RuntimeException {

    @Override
    @NotNull
    public String getMessage() {
        return "Illegal state of current user. There is no id.";
    }

}
