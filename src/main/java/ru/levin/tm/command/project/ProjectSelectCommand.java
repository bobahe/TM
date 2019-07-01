package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;

public final class ProjectSelectCommand extends AbstractCommand {
    private static final String SELECTED_PROJECT_MESSAGE = "SELECTED PROJECT: ";
    private static final String SERIAL_NUMBER_PROMPT = "ENTER SERIAL NUMBER:";
    protected static final String ERROR_MESSAGE = "[ERROR]\n";
    protected static final String NO_SUCH_ITEM = "[THERE IS NO SUCH ITEM]\n";

    private final IProjectService projectService;
    private final ITerminalService terminalService;

    public ProjectSelectCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "project-select";
    }

    @Override
    public String getTitle() {
        return "[PROJECT SELECT]";
    }

    @Override
    public String getDescription() {
        return "Select project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    public void execute() {
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
