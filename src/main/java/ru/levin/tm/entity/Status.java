package ru.levin.tm.entity;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
public enum Status implements Serializable {

    PLANNED ("Planned"),
    IN_PROCESS ("In process"),
    READY ("Ready");

    @NotNull
    private final String displayName;

    Status(@NotNull final String displayName) {
        this.displayName = displayName;
    }

}
