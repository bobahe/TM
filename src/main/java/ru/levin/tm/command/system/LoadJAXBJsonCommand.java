package ru.levin.tm.command.system;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.dto.Domain;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.DeserializeException;
import ru.levin.tm.exception.NoCurrentUserException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class LoadJAXBJsonCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public LoadJAXBJsonCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "load-jaxb-json";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Unmarshal data from json via JAXB";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @Nullable final User user = bootstrap.getUserService().getCurrentUser();
        if (user == null) throw new NoCurrentUserException();
        @NotNull final String fileName = user.getLogin() + "jaxbdata.json";

        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        @NotNull final Map<String, Object> jaxbProperties = new HashMap<>();
        jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        jaxbProperties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, true);
        try {
            @NotNull final JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {Domain.class}, jaxbProperties);
            @NotNull final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            @NotNull final Domain domain = (Domain) unmarshaller.unmarshal(new File(fileName));
            domain.getProjects().getProjects().forEach(project -> bootstrap.getProjectService().save(project));
            domain.getTasks().getTasks().forEach(task -> bootstrap.getTaskService().save(task));
        } catch (Exception e) {
            e.printStackTrace();
            throw new DeserializeException();
        }

        terminalService.println("Load JAXB XML successfully from " + fileName);
    }

}
