package ru.levin.tm.command.system;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.dto.Domain;
import ru.levin.tm.exception.DeserializeException;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

public final class LoadSerializedCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public LoadSerializedCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "load-serialized";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Deserialize data from file";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @Nullable Domain domain;
        try (FileInputStream dataFile = new FileInputStream("data.ser");
             ObjectInputStream objectInputStream = new ObjectInputStream(dataFile)) {
            domain = (Domain) objectInputStream.readObject();
        } catch (Exception e) {
            throw new DeserializeException();
        }

        domain.getProjectList().forEach(project -> bootstrap.getProjectService().save(project));
        domain.getTaskList().forEach(task -> bootstrap.getTaskService().save(task));
        terminalService.println("Data was successfully loaded.");
    }

}
