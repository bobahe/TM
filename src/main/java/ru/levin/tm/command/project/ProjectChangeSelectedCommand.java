package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.util.CommandUtil;

public final class ProjectChangeSelectedCommand extends AbstractCommand {

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

    public ProjectChangeSelectedCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-change";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[CHANGE PROJECT]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Change selected project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedProject == null) {
            terminalService.println("You have to choose project first.");
            return;
        }

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        selectedProject.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        selectedProject.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        selectedProject.setStartDate(CommandUtil.parseDate(terminalService.getLine()));
        terminalService.println(END_DATE_PROMPT);
        selectedProject.setEndDate(CommandUtil.parseDate(terminalService.getLine()));

        try {
            projectService.update(selectedProject);
        } catch (Exception e) {
            terminalService.printerr(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }

}
