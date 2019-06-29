package ru.levin.tm.entity;

public abstract class AbstractEntity {
    protected String id;

    public abstract String getId();

    public abstract void setId(final String id);
}
