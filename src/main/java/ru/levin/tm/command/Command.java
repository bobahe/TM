package ru.levin.tm.command;

import java.text.SimpleDateFormat;
import java.util.List;

public abstract class Command {
    protected static final String ERROR_MESSAGE = "[ERROR]\n";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";

    protected String name;
    protected String title;
    protected String description;
    protected List<String> argNameList;

    protected final SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

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
