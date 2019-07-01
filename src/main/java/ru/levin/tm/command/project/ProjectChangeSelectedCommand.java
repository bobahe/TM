package ru.levin.tm.command.project;

import ru.levin.tm.api.IServiceLocator;
import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.command.AbstractCommand;
import ru.levin.tm.entity.Project;
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
    private final ITerminalService terminalService;

    public ProjectChangeSelectedCommand(final IServiceLocator bootstrap) {
        super(bootstrap);
        this.projectService = bootstrap.getProjectService();
        this.terminalService = bootstrap.getTerminalService();
    }

    @Override
    public String getName() {
        return "project-change";
    }

    @Override
    public String getTitle() {
        return "[CHANGE PROJECT]";
    }

    @Override
    public String getDescription() {
        return "Change selected project";
    }

    @Override
    public boolean isRequiredAuthorization() {
        return true;
    }

    @Override
    public void execute() {
        if (selectedProject == null) return;

        terminalService.println(this.getTitle());
        terminalService.println(NAME_PROMPT);
        selectedProject.setName(terminalService.getLine());
        terminalService.println(DESCRIPTION_PROMPT);
        selectedProject.setDescription(terminalService.getLine());
        terminalService.println(START_DATE_PROMPT);
        selectedProject.setStartDate(parseDate(true));
        terminalService.println(END_DATE_PROMPT);
        selectedProject.setEndDate(parseDate(false));

        try {
            projectService.update(selectedProject);
        } catch (Exception e) {
            terminalService.println(e.getMessage());
            return;
        }
        terminalService.println(SUCCESS_MESSAGE);
    }

    private Date parseDate(final boolean isStartDate) {
        final String date = terminalService.getLine();

        if (date.isEmpty()) {
            return isStartDate ? selectedProject.getStartDate() : selectedProject.getEndDate();
        }

        try {
            return CommandUtil.DATE_FORMAT.parse(date);
        } catch (ParseException pe) {
            terminalService.println(ERR_PARSE_DATE_MESSAGE);
            return null;
        }
    }
}
