package ru.levin.tm.command;

import ru.levin.tm.console.TerminalCommandRunner;

import java.util.Scanner;

public class HelpCommand extends Command {
    public HelpCommand(Scanner scanner) {
        super(scanner);
        name = "help";
        description = "Show all commands";
    }

    public void run() {
        for (Command cmd : TerminalCommandRunner.getInstance().getCommands()) {
            System.out.println(cmd.getName() + ": " + cmd.getDescription());
        }
    }
}
