package ru.levin.tm.command.system;

import ru.levin.tm.api.ICommandHandlerServiceLocator;
import ru.levin.tm.command.AbstractCommand;

import java.util.Map;

public final class HelpCommand extends AbstractCommand {
    private final Map<String, AbstractCommand> commands;

    public HelpCommand(final ICommandHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "help";
        this.description = "Show all commands";
        this.commands = bootstrap.getCommands();
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
