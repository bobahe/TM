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
        commands.add(new HelpCommand());
        commands.add(new ProjectRemoveAllCommand());
        commands.add(new ProjectCreateCommand());
        commands.add(new ProjectListCommand());
        commands.add(new ProjectChangeByIdCommand());
        commands.add(new ProjectRemoveByIdCommand());
        commands.add(new TaskRemoveAllCommand());
        commands.add(new TaskCreateCommand());
        commands.add(new TaskListCommand());
        commands.add(new TaskChangeByIdCommand());
        commands.add(new TaskRemoveByIdCommand());
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
                if (cmd.getTitle() != null) {
                    System.out.println(cmd.getTitle());
                }

                List<String> args = prepareArgs(cmd.getArgsList());
                String result = cmd.run(args);

                if (result != null) {
                    System.out.println(result);
                }

                return;
            }
        }

        System.err.println("There is not such command");
    }

    private List<String> prepareArgs(List<String> argNameList) {
        if (argNameList == null) {
            return null;
        }

        List<String> argList = new ArrayList<>(argNameList.size());
        for (String s : argNameList) {
            System.out.println("ENTER " + s + ":");
            argList.add(scanner.nextLine());
        }

        return argList;
    }

    public static TerminalCommandRunner getInstance() {
        return INSTANCE;
    }
}
