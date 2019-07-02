package ru.levin.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
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
