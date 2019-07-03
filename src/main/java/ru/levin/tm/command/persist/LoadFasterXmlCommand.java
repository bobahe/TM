package ru.levin.tm.command.persist;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.dto.Domain;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.DeserializeException;
import ru.levin.tm.exception.NoCurrentUserException;

import java.io.File;

public final class LoadFasterXmlCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public LoadFasterXmlCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "load-fxml-xml";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Unmarshal data from xml via FasterXML";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @Nullable final User user = bootstrap.getUserService().getCurrentUser();
        if (user == null) throw new NoCurrentUserException();
        @NotNull final String fileName = user.getLogin() + "fxmldata.xml";

        try {
            @NotNull final XmlMapper mapper = new XmlMapper();
            @NotNull final Domain domain = mapper.readValue(new File(fileName), Domain.class);
            domain.getProjects().forEach(project -> bootstrap.getProjectService().save(project));
            domain.getTasks().forEach(task -> bootstrap.getTaskService().save(task));
        } catch (Exception e) {
            throw new DeserializeException();
        }

        terminalService.println("Load JAXB XML successfully from " + fileName);
    }

}
