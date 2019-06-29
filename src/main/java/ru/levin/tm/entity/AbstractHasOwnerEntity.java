package ru.levin.tm.entity;

public abstract class AbstractHasOwnerEntity extends AbstractEntity {
    protected String userId;

    public abstract String getUserId();

    public abstract void setUserId(final String userId);
}
