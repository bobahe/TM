package ru.levin.tm.exception;

import org.jetbrains.annotations.NotNull;

public class PasswordsNotEqualException extends RuntimeException {

    @Override
    @NotNull
    public String getMessage() {
        return "Password are not equal. Try again.";
    }

}
