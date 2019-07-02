package ru.levin.tm.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class User extends AbstractEntity {
    @Nullable
    private String id;

    @Nullable
    private String login;

    @Nullable
    private String password;

    @Nullable
    private RoleType roleType;

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@NotNull final String id) {
        this.id = id;
    }

    @Nullable
    public String getLogin() {
        return login;
    }

    public void setLogin(@NotNull final String login) {
        this.login = login;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull final String password) {
        this.password = password;
    }

    @Nullable
    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(@NotNull final RoleType roleType) {
        this.roleType = roleType;
    }
}
