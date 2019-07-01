package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.util.CommandUtil;

public final class ProjectCreateCommand extends AbstractCommand {
    protected static final String NAME_PROMPT = "ENTER NAME:";
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    private final IProjectService projectService;

    public ProjectCreateCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.name = "project-create";
        this.title = "[PROJECT CREATE]";
        this.description = "Create new project";
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

    public void execute() {
        final Project project = new Project();

        System.out.println(this.title);
        System.out.println(NAME_PROMPT);
        project.setName(scanner.nextLine());
        System.out.println(DESCRIPTION_PROMPT);
        project.setDescription(scanner.nextLine());
        System.out.println(START_DATE_PROMPT);
        project.setStartDate(CommandUtil.parseDate(scanner));
        System.out.println(END_DATE_PROMPT);
        project.setEndDate(CommandUtil.parseDate(scanner));

        project.setUserId(bootstrap.getUserService().getCurrentUser().getId());

        try {
            projectService.save(project);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        System.out.println(SUCCESS_MESSAGE);
    }
}
