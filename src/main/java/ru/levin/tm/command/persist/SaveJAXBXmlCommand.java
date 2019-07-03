package ru.levin.tm.command.persist;

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

public final class SaveJAXBXmlCommand extends AbstractCommand {

    @NotNull
    private final ITerminalService terminalService;

    public SaveJAXBXmlCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "save-jaxb-xml";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Marshal data into xml via JAXB";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @Nullable final User user = bootstrap.getUserService().getCurrentUser();
        if (user == null) throw new NoCurrentUserException();
        @NotNull final String fileName = user.getLogin() + "jaxbdata.xml";
        @NotNull final Domain domain = new Domain();
        domain.initFromInternalStorage(bootstrap);

        try {
            @NotNull final JAXBContext jaxbContext = JAXBContext.newInstance(Domain.class);
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

        terminalService.println("Saved JAXB XML successfully to " + fileName);
    }

}
