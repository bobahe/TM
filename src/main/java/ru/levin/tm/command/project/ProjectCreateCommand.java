package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.util.CommandUtil;

import java.util.Scanner;

public class ProjectCreateCommand extends AbstractCommand {
    private ProjectService projectService;

    public ProjectCreateCommand(Scanner scanner, ProjectService service) {
        super(scanner);
        this.name = "project-create";
        this.title = "[PROJECT CREATE]";
        this.description = "Create new project";
        this.projectService = service;
    }

    public void run() {
        Project project = new Project();

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        project.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        project.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        project.setStartDate(CommandUtil.parseDate(scanner));
        System.out.println(END_DATE_PROMPT);
        project.setEndDate(CommandUtil.parseDate(scanner));

        try {
            projectService.save(project);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(SUCCESS_MESSAGE);
    }
}
