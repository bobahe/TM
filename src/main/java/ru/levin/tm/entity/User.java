package ru.levin.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

@NoArgsConstructor
@Getter
@Setter
public final class User extends AbstractEntity {
    @Nullable
    private String id;

    @Nullable
    private String login;

    @Nullable
    private String password;

    @Nullable
    private RoleType roleType;
}
