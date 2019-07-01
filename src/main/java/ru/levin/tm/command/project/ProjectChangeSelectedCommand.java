package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.util.CommandUtil;

import java.text.ParseException;
import java.util.Date;

public final class ProjectChangeSelectedCommand extends AbstractCommand {
    protected static final String NAME_PROMPT = "ENTER NAME:";
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final IProjectService projectService;

    public ProjectChangeSelectedCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "project-change";
        this.description = "Change selected project";
        this.title = "[CHANGE PROJECT]";
        this.projectService = bootstrap.getProjectService();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
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

    private Date parseDate(final boolean isStartDate) {
        final String date = scanner.nextLine();

        if ("".equals(date)) {
            return isStartDate ? selectedProject.getStartDate() : selectedProject.getEndDate();
        }

        try {
            return CommandUtil.DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            System.out.println(ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
