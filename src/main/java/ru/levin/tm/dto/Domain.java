package ru.levin.tm.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.NoCurrentUserException;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@XmlRootElement(name = "Domain")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Domain implements Serializable {

    private static final long serialVersionUID = 3451213614811997006L;

    @NotNull
    @XmlElement(name = "project")
    @XmlElementWrapper(name = "projects")
    private final List<Project> projects = new ArrayList<>();

    @NotNull
    @XmlElement(name = "task")
    @XmlElementWrapper(name = "tasks")
    private final List<Task> tasks = new ArrayList<>();

    public void initFromInternalStorage(@NotNull final IServiceLocator bootstrap) {
        @Nullable final User user = bootstrap.getUserService().getCurrentUser();
        if (user == null) throw new NoCurrentUserException();
        projects.addAll(bootstrap.getProjectService().findAllByUserId(user.getId()));
        tasks.addAll(bootstrap.getTaskService().findAllByUserId(user.getId()));
    }
}
