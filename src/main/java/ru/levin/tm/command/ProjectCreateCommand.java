package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class ProjectCreateCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectCreateCommand() {
        this.name = "project-create";
        this.title = "[PROJECT CREATE]";
        this.description = "Create new project";
        this.argNameList = new ArrayList<>(2);

        this.argNameList.add("NAME");
        this.argNameList.add("START DATE");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        Project project = new Project();
        project.setName(args.get(0));
        try {
            project.setStartDate(dateFormat.parse(args.get(1)));
        } catch (ParseException pe) {
            return Command.ERROR_MESSAGE;
        }

        boolean result = projectService.save(project);

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
