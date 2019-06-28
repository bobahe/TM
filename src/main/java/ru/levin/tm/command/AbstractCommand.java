package ru.levin.tm.command;

import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

import java.text.SimpleDateFormat;
import java.util.Scanner;

public abstract class AbstractCommand {
    protected static final String ERROR_MESSAGE = "[ERROR]\n";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";
    public static final String ERR_PARSE_DATE_MESSAGE = "[CAN'T PARSE DATE, SAVING NULL]\n";

    protected static final String NAME_PROMPT = "ENTER NAME:";
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";

    protected static final String LOGIN_PROMPT = "ENTER LOGIN:";
    protected static final String PASSWORD_PROMPT = "ENTER PASSWORD:";

    protected static Project selectedProject;
    protected static Task selectedTask;

    protected Scanner scanner;

    protected String name;
    protected String title;
    protected String description;

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public AbstractCommand(Bootstrap bootstrap) {
        this.scanner = bootstrap.getScanner();
    }

    public abstract String getName();

    public abstract String getTitle();

    public abstract String getDescription();

    public abstract void execute();
}
