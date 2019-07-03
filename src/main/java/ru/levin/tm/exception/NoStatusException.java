package ru.levin.tm.exception;

import org.jetbrains.annotations.NotNull;

public class NoStatusException extends RuntimeException {

    @Override
    @NotNull
    public String getMessage() {
        return "Illegal state of entity: state is null.";
    }

}
