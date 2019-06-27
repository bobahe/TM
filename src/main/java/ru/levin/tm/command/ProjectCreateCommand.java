package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.entity.Project;

import java.util.Scanner;

public class ProjectCreateCommand extends Command {
    private ProjectService projectService = ProjectService.getInstance();

    public ProjectCreateCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-create";
        this.title = "[PROJECT CREATE]";
        this.description = "Create new project";
    }

    public void run() {
        Project project = new Project();

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        project.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        project.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        project.setStartDate(CommandUtils.parseDate(scanner));
        System.out.println(END_DATE_PROMPT);
        project.setEndDate(CommandUtils.parseDate(scanner));

        projectService.save(project);
        System.out.println(SUCCESS_MESSAGE);
    }
}
