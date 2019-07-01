package ru.levin.tm.command.system;

import ru.levin.tm.api.ICommandHandlerServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

import java.util.Map;

public final class HelpCommand extends AbstractCommand {
    private final Map<String, AbstractCommand> commands;
    private final ITerminalService terminalService;

    public HelpCommand(final ICommandHandlerServiceLocator bootstrap) {
        super(bootstrap);
        this.commands = bootstrap.getCommands();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public String getTitle() {
        return "";
    }

    @Override
    public String getDescription() {
        return "Show all commands";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return false;
    }

    public void execute() {
        commands.values().forEach(command -> terminalService.println(command.getName() + ": " + command.getDescription()));
        terminalService.println("exit: Exit from application");
    }
}
