package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.util.ArrayList;
import java.util.List;

public class ProjectCreateCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectCreateCommand() {
        this.name = "project-create";
        this.title = "[PROJECT CREATE]";
        this.description = "Create new project";
        this.argNameList = new ArrayList<>(1);

        this.argNameList.add("NAME");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        boolean result = projectService.save(new Project(args.get(0)));

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
