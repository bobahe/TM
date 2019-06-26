package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProjectRemoveByIdCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectRemoveByIdCommand() {
        this.name = "project-remove";
        this.description = "Remove selected project";
        this.argNameList = new ArrayList<>(1);

        this.argNameList.add("ID");
    }

    public String run(List<String> args) {
        if (args == null) {
            return Command.ERROR_MESSAGE;
        }

        Optional<Project> prj = projectService.get(Long.parseLong(args.get(0)));

        if (!prj.isPresent()) {
            return NO_SUCH_ITEM;
        }

        boolean result = projectService.delete(prj.get());

        return result ? Command.SUCCESS_MESSAGE : Command.ERROR_MESSAGE;
    }
}
