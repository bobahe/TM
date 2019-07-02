package ru.levin.tm.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public final class Task extends AbstractHasOwnerEntity {
    @Nullable
    private String name;

    @Nullable
    private String description;

    @Nullable
    private String projectId;

    @Nullable
    private Date startDate;

    @Nullable
    private Date endDate;

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@NotNull final String id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    public void setName(@NotNull final String name) {
        this.name = name;
    }

    @Nullable
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@Nullable final Date startDate) {
        this.startDate = startDate;
    }

    @Nullable
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@Nullable final Date endDate) {
        this.endDate = endDate;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    public void setDescription(@Nullable final String description) {
        this.description = description;
    }

    @Nullable
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(@NotNull final String projectId) {
        this.projectId = projectId;
    }

    @Nullable
    public String getUserId() {
        return userId;
    }

    public void setUserId(@NotNull final String userId) {
        this.userId = userId;
    }

    @Override
    @NotNull
    public String toString() {
        return name + " (" + id + ")";
    }
}
