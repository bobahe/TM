package ru.levin.tm.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractHasOwnerEntity extends AbstractEntity {

    @Nullable
    protected String userId;

    @Nullable
    public abstract String getUserId();

    public abstract void setUserId(@NotNull final String userId);

}
