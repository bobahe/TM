package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.User;
import ru.levin.tm.exception.NoCurrentUserException;
import ru.levin.tm.exception.NoIdForCurrentUserException;
import ru.levin.tm.exception.SaveException;
import ru.levin.tm.util.CommandUtil;

public final class ProjectCreateCommand extends AbstractCommand {

    @NotNull
    protected static final String NAME_PROMPT = "ENTER NAME:";

    @NotNull
    protected static final String DESCRIPTION_PROMPT = "ENTER DESCRIPTION:";

    @NotNull
    protected static final String START_DATE_PROMPT = "ENTER START DATE:";

    @NotNull
    protected static final String END_DATE_PROMPT = "ENTER END DATE:";

    @NotNull
    protected static final String SUCCESS_MESSAGE = "[OK]\n";

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITerminalService terminalService;

    public ProjectCreateCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-create";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[PROJECT CREATE]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Create new project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        @Nullable final User currentUser = bootstrap.getUserService().getCurrentUser();
        if (currentUser == null) throw new NoCurrentUserException();
        if (currentUser.getId() == null) throw new NoIdForCurrentUserException();
        @NotNull final Project project = new Project();

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        project.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        project.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        project.setStartDate(CommandUtil.parseDate(terminalService.getLine()));
        terminalService.println(END_DATE_PROMPT);
        project.setEndDate(CommandUtil.parseDate(terminalService.getLine()));

        project.setUserId(currentUser.getId());

        if (projectService.save(project) == null) throw new SaveException();
        terminalService.println(SUCCESS_MESSAGE);
    }

}
