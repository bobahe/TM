package ru.levin.tm.command.system;

import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.dto.Domain;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.NoCurrentUserException;
import ru.levin.tm.exception.SerializeException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public final class SaveJAXBJsonCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public SaveJAXBJsonCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "save-jaxb-json";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Marshal data into json via JAXB";
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
        @NotNull final Domain domain = new Domain();
        domain.initFromInternalStorage(bootstrap);

        System.setProperty("javax.xml.bind.context.factory", "org.eclipse.persistence.jaxb.JAXBContextFactory");
        @NotNull final Map<String, Object> jaxbProperties = new HashMap<>();
        jaxbProperties.put(JAXBContextProperties.MEDIA_TYPE, "application/json");
        jaxbProperties.put(JAXBContextProperties.JSON_INCLUDE_ROOT, true);
        try {
            @NotNull final JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] {Domain.class}, jaxbProperties);
            @NotNull final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            @NotNull final JAXBElement<Domain> jaxbElement = new JAXBElement<>(
                    new QName(null, "Domain"),
                    Domain.class,
                    domain
            );
            marshaller.marshal(jaxbElement, new File(fileName));
        } catch (Exception e) {
            throw new SerializeException();
        }

        terminalService.println("Saved JAXB Json successfully to " + fileName);
    }

}
