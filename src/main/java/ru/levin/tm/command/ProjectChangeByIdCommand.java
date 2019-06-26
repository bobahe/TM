package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectChangeByIdCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectChangeByIdCommand() {
        this.name = "project-change";
        this.description = "Change selected project";
        this.title = "[CHANGE PROJECT]";
        this.argNameList = new ArrayList<>(1);

        this.argNameList.add("ID TO CHANGE");
        this.argNameList.add("NAME");
        this.argNameList.add("START DATE");
        this.argNameList.add("END DATE");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        Optional<Project> optionalProject = projectService.get(Long.parseLong(args.get(0)));

        if (!optionalProject.isPresent()) {
            return Command.NO_SUCH_ITEM;
        }

        Project project = optionalProject.get();
        project.setName(args.get(1));
        try {
            project.setStartDate(dateFormat.parse(args.get(2)));
            project.setEndDate(dateFormat.parse(args.get(3)));
        } catch (ParseException e) {
            return Command.ERROR_MESSAGE;
        }

        boolean result = projectService.update(project);

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
