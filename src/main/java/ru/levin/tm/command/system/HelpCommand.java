package ru.levin.tm.command.system;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;

import java.util.Map;

public class HelpCommand extends AbstractCommand {
    private Map<String, AbstractCommand> commands;

    public HelpCommand(IServiceLocator bootstrap) {
        super(bootstrap);
        name = "help";
        description = "Show all commands";
        this.commands = ((Bootstrap) bootstrap).getCommands();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void execute() {
        commands.values().forEach(command -> System.out.println(command.getName() + ": " + command.getDescription()));
        System.out.println("exit: Exit from application");
    }
}
