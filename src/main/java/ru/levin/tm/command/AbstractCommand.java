package ru.levin.tm.command;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

public abstract class AbstractCommand {
    @NotNull
    public static final String ERR_PARSE_DATE_MESSAGE = "[CAN'T PARSE DATE, SAVING NULL]\n";

    @Nullable
    protected static Project selectedProject;

    @Nullable
    protected static Task selectedTask;

    @NotNull
    protected final IServiceLocator bootstrap;

    public AbstractCommand(@NotNull final IServiceLocator bootstrap) {
        this.bootstrap = bootstrap;
    }

    @NotNull
    public abstract String getName();

    @NotNull
    public abstract String getTitle();

    @NotNull
    public abstract String getDescription();

    public abstract boolean isRequiredAuthorization();

    public abstract void execute();
}
