package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;

import java.util.List;

public class ProjectRemoveAllCommand extends Command {
    private static final String ALL_PROJECTS_REMOVED_MESSAGE = "[ALL PROJECTS REMOVED]\n";
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectRemoveAllCommand() {
        this.name = "project-clear";
        this.description = "Remove all projects";
        this.argNameList = null;
    }

    public String run(List<String> args) {
        projectService.deleteAll();
        return ALL_PROJECTS_REMOVED_MESSAGE;
    }
}
