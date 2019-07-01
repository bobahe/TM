package ru.levin.tm.util;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public final class CommandUtil {
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED\n";
    private static final String TASK_NOT_SELECTED_ERR = "TASK IS NOT SELECTED\n";

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    public static Date parseDate(final Scanner scanner) {
        final String date = scanner.nextLine();

        if ("".equals(date)) {
            return null;
        }

        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(AbstractCommand.ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }

    public static boolean isSelectedObjectNull(final Object object, final Class objectClass) {
        if (objectClass == Project.class && object == null) {
            System.out.println(PROJECT_NOT_SELECTED);
            return true;
        }
        if (objectClass == Task.class && object == null) {
            System.out.println(TASK_NOT_SELECTED_ERR);
            return true;
        }

        return false;
    }
}
