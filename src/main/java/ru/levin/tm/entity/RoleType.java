package ru.levin.tm.entity;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

@Getter
public enum RoleType {

    ADMIN ("Administrator"),
    USER ("User");

    @NotNull
    private final String displayName;

    RoleType(@NotNull final String displayName) {
        this.displayName = displayName;
    }

}
