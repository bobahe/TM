package ru.levin.tm.exception;

import org.jetbrains.annotations.NotNull;

public class SerializeException extends RuntimeException {

    @Override
    @NotNull
    public String getMessage() {
        return "Can't serialize data into file.";
    }

}
