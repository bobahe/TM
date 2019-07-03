package ru.levin.tm.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.NoCurrentUserException;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Getter
@XmlRootElement(name = "Domain")
public class Domain implements Serializable {

    private static final long serialVersionUID = 3451213614811997006L;

    @NotNull
    @XmlElement
    private final ProjectDomain projects = new ProjectDomain();

    @NotNull
    @XmlElement
    private final TaskDomain tasks = new TaskDomain();

    public void initFromInternalStorage(@NotNull final IServiceLocator bootstrap) {
        @Nullable final User user = bootstrap.getUserService().getCurrentUser();
        if (user == null) throw new NoCurrentUserException();
        projects.getProjects().addAll(bootstrap.getProjectService().findAllByUserId(user.getId()));
        tasks.getTasks().addAll(bootstrap.getTaskService().findAllByUserId(user.getId()));
    }
}
