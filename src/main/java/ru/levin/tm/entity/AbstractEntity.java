package ru.levin.tm.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

public abstract class AbstractEntity implements Serializable {

    @Nullable
    protected String id;

    @Nullable
    public abstract String getId();

    public abstract void setId(@NotNull final String id);

}
