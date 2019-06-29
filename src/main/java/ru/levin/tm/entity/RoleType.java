package ru.levin.tm.entity;

public enum RoleType {
    ADMIN ("Administrator"),
    USER ("User");

    private final String displayName;

    RoleType(final String displayName) {
        this.displayName = displayName;
    }
}
