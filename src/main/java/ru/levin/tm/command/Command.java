package ru.levin.tm.command;

import java.util.List;

public abstract class Command {
    static final String ERROR_MESSAGE = "[ERROR]\n";
    static final String SUCCESS_MESSAGE = "[OK]\n";
    static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";
    static final String ALL_TASK_REMOVED_MESSAGE = "[ALL TASKS REMOVED]\n";
    static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";

    String name;
    String title;
    String description;
    List<String> argNameList;

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getArgsList() {
        return argNameList;
    }

    public abstract String run(List<String> args);
}
