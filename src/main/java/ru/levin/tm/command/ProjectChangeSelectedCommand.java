package ru.levin.tm.command;

import ru.levin.tm.crud.ProjectService;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

public class ProjectChangeSelectedCommand extends Command {
    private static final String PROJECT_NOT_SELECTED = "PROJECT IS NOT SELECTED\n";

    private ProjectService projectService = ProjectService.getInstance();

    public ProjectChangeSelectedCommand(Scanner scanner) {
        super(scanner);
        this.name = "project-change";
        this.description = "Change selected project";
        this.title = "[CHANGE PROJECT]";
    }

    @Override
    public void run() {
        if (selectedProject == null) {
            System.out.println(PROJECT_NOT_SELECTED);
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

        projectService.update(selectedProject);
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
