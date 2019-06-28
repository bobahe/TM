package ru.levin.tm.entity;

public enum RoleType {
    ADMIN ("Administrator"),
    USER ("User");

    private String displayName;

    RoleType(String displayName) {
        this.displayName = displayName;
    }
}
