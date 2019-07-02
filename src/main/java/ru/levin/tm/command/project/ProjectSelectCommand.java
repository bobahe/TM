package ru.levin.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class ProjectSelectCommand extends AbstractCommand {
    @NotNull
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED PROJECT: ";

    @NotNull
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";

    @NotNull
    protected static final String ERROR_MESSAGE = "[ERROR]\n";

    @NotNull
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ITerminalService terminalService;

    public ProjectSelectCommand(@NotNull final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    @NotNull
    public String getName() {
        return "project-select";
    }

    @Override
    @NotNull
    public String getTitle() {
        return "[PROJECT SELECT]";
    }

    @Override
    @NotNull
    public String getDescription() {
        return "Select project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    public void execute() {
        if (bootstrap.getUserService().getCurrentUser() == null) return;

        terminalService.println(this.getTitle());
        terminalService.println(SERIAL_NUMBER_PROMPT);
        try {
            final int index = Integer.parseInt(terminalService.getLine());
            selectedProject = projectService.findOneByIndex(bootstrap.getUserService().getCurrentUser().getId(), index);
            terminalService.println(SELECTED_PROJECT_MESSAGE + selectedProject);
        } catch (NumberFormatException nfe) {
            terminalService.println(ERROR_MESSAGE);
        } catch (IndexOutOfBoundsException iobe) {
            terminalService.println(NO_SUCH_ITEM);
        }
    }
}
