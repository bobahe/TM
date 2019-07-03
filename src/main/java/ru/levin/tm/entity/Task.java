package ru.levin.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IContainsDatesAndStatus;

import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public final class Task extends AbstractHasOwnerEntity implements IContainsDatesAndStatus, Serializable {

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
    private Status status = Status.PLANNED;

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

}
