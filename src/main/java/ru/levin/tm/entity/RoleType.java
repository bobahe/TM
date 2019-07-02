package ru.levin.tm.entity;

import org.jetbrains.annotations.NotNull;

public enum RoleType {

    ADMIN ("Administrator"),
    USER ("User");

    @NotNull
    private final String displayName;

    RoleType(@NotNull final String displayName) {
        this.displayName = displayName;
    }

}
