package ru.levin.tm.exception;

import org.jetbrains.annotations.NotNull;

public class AuthorizationException extends RuntimeException {

    @Override
    @NotNull
    public String getMessage() {
        return "Login and/or password incorrect.";
    }

}
