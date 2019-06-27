package ru.levin.tm.command.system;

import ru.levin.tm.command.AbstractCommand;

import java.util.List;
import java.util.Scanner;

public class HelpCommand extends AbstractCommand {
    private List<AbstractCommand> commands;

    public HelpCommand(Scanner scanner, List<AbstractCommand> commands) {
        super(scanner);
        name = "help";
        description = "Show all commands";
        this.commands = commands;
    }

    public void run() {
        for (AbstractCommand cmd : commands) {
            System.out.println(cmd.getName() + ": " + cmd.getDescription());
        }
    }
}
