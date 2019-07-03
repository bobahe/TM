package ru.levin.tm.dto;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
public class Domain implements Serializable {

    private static final long serialVersionUID = 8478406829761508623L;

    @NotNull
    private final List<Project> projectList = new ArrayList<>();

    @NotNull
    private final List<Task> taskList = new ArrayList<>();

    public void initFromInternalStorage(@NotNull final IServiceLocator bootstrap) {
        projectList.addAll(bootstrap.getProjectService().getAll());
        taskList.addAll(bootstrap.getTaskService().getAll());
    }
}
