package ru.levin.tm.command;

import ru.levin.tm.console.TerminalCommandRunner;

import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand() {
        name = "help";
        description = "Show all commands";
        argNameList = null;
    }

    public String run(List<String> args) {
        for (Command cmd : TerminalCommandRunner.getInstance().getCommands()) {
            System.out.println(cmd.getName() + ": " + cmd.getDescription());
        }

        return null;
    }
}
