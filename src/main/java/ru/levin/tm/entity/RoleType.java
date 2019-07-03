package ru.levin.tm.entity;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
public enum RoleType implements Serializable {

    ADMIN ("Administrator"),
    USER ("User");

    @NotNull
    private final String displayName;

    RoleType(@NotNull final String displayName) {
        this.displayName = displayName;
    }

}
