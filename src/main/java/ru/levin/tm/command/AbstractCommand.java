package ru.levin.tm.command;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

import java.util.Scanner;

public abstract class AbstractCommand {
    public static final String ERR_PARSE_DATE_MESSAGE = "[CAN'T PARSE DATE, SAVING NULL]\n";

    protected static Project selectedProject;
    protected static Task selectedTask;

    protected final IServiceLocator bootstrap;

    public AbstractCommand(final IServiceLocator bootstrap) {
        this.bootstrap = bootstrap;
    }

    public abstract String getName();

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract boolean isRequiredAuthorization();

    public abstract void execute();
}
