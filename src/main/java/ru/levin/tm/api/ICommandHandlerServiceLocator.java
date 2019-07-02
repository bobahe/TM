package ru.levin.tm.api;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.command.AbstractCommand;

import java.util.Map;

public interface ICommandHandlerServiceLocator extends IServiceLocator {
    @NotNull Map<String, AbstractCommand> getCommands();
}
