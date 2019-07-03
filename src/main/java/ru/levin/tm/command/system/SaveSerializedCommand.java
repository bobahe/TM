package ru.levin.tm.command.system;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.dto.Domain;
import ru.levin.tm.exception.SerializeException;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public final class SaveSerializedCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public SaveSerializedCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "save-serialized";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Serialize data into file";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @NotNull final Domain domain = new Domain();
        domain.initFromInternalStorage(bootstrap);
        try (FileOutputStream dataFile = new FileOutputStream("data.ser");
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataFile)) {
            objectOutputStream.writeObject(domain);
        } catch (Exception e) {
            throw new SerializeException();
        }

        terminalService.println("Data saved successfully.");
    }

}
