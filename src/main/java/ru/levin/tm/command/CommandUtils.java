package ru.levin.tm.command;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;


public class CommandUtils {
    public static Date parseDate(Scanner scanner) {
        String date = scanner.nextLine();

        if ("".equals(date)) {
            return null;
        }

        try {
            return Command.DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(Command.ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
