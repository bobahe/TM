package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.util.List;

public class ProjectListCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectListCommand() {
        this.name = "project-list";
        this.title = "[PROJECT LIST]";
        this.description = "Show all projects";
        this.argNameList = null;
    }

    public String run(List<String> args) {
        StringBuilder result = new StringBuilder();
        List<Project> projectList = projectService.getAll();
        for (Project project : projectList) {
            result.append(project.getId()).append(". ").append(project.getName()).append(System.lineSeparator());
        }

        return result.toString();
    }
}
