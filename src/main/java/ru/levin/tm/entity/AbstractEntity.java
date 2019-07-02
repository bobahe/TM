package ru.levin.tm.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractEntity {
    @Nullable
    protected String id;

    @Nullable
    public abstract String getId();

    public abstract void setId(@NotNull final String id);
}
