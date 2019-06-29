package ru.levin.tm.api;

import ru.levin.tm.command.AbstractCommand;

import java.util.Map;

public interface ICommandHandlerServiceLocator extends IServiceLocator {
    Map<String, AbstractCommand> getCommands();
}
