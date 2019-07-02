package ru.levin.tm.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.command.AbstractCommand;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class CommandUtil {

    @NotNull
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");

    @Nullable
    public static Date parseDate(@Nullable final String date) {
        if (date == null || date.isEmpty()) return null;

        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(AbstractCommand.ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }

}
