package ru.levin.tm.console;

import ru.levin.tm.command.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TerminalCommandRunner {
    private static final TerminalCommandRunner INSTANCE = new TerminalCommandRunner();

    private Scanner scanner = new Scanner(new InputStreamReader(System.in));
    private List<Command> commands;

    private TerminalCommandRunner() {
        commands = new ArrayList<>();
        addCommands();
    }

    private void addCommands() {
        commands.add(new HelpCommand(scanner));
        commands.add(new ProjectListCommand(scanner));
        commands.add(new ProjectCreateCommand(scanner));
        commands.add(new ProjectSelectCommand(scanner));
        commands.add(new ProjectRemoveAllCommand(scanner));
        commands.add(new ProjectChangeSelectedCommand(scanner));
        commands.add(new ProjectRemoveSelectedCommand(scanner));
        commands.add(new ProjectTaskListCommand(scanner));
        commands.add(new TaskRemoveAllCommand(scanner));
        commands.add(new TaskCreateCommand(scanner));
        commands.add(new TaskSelectCommand(scanner));
        commands.add(new TaskListCommand(scanner));
        commands.add(new TaskChangeSelectedCommand(scanner));
        commands.add(new TaskRemoveSelectedCommand(scanner));
        commands.add(new TaskToProjectCommand(scanner));
    }

    public List<Command> getCommands() {
        return this.commands;
    }

    public void process() {
        System.out.println("*** WELCOME TO TASK MANAGER");
        String command = scanner.nextLine();

        while (!"exit".equals(command)) {
            if ("".equals(command)) {
                command = scanner.nextLine();
                continue;
            }

            invokeCommand(command);

            command = scanner.nextLine();
        }
    }

    private void invokeCommand(String command) {
        for (Command cmd : commands) {
            if (command.equals(cmd.getName())) {
                cmd.run();
                return;
            }
        }

        System.err.println("There is not such command");
    }

    public static TerminalCommandRunner getInstance() {
        return INSTANCE;
    }
}
