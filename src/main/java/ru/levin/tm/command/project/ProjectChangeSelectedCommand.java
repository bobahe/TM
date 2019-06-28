package ru.levin.tm.command.project;

import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.console.Bootstrap;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.util.CommandUtil;

import java.text.ParseException;
import java.util.Date;

public class ProjectChangeSelectedCommand extends AbstractCommand {
    private ProjectService projectService;

    public ProjectChangeSelectedCommand(Bootstrap bootstrap) {
        super(bootstrap);
        this.name = "project-change";
        this.description = "Change selected project";
        this.title = "[CHANGE PROJECT]";
        this.projectService = bootstrap.getProjectService();
    }

    @Override
    public void execute() {
        if (CommandUtil.isSelectedObjectNull(selectedProject, Project.class)){
            return;
        }

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        selectedProject.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        selectedProject.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        selectedProject.setStartDate(parseDate(true));
        System.out.println(END_DATE_PROMPT);
        selectedProject.setEndDate(parseDate(false));

        try {
            projectService.update(selectedProject);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(SUCCESS_MESSAGE);
    }

    private Date parseDate(boolean isStartDate) {
        String date = scanner.nextLine();

        if ("".equals(date)) {
            return isStartDate ? selectedProject.getStartDate() : selectedProject.getEndDate();
        }

        try {
            return DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
