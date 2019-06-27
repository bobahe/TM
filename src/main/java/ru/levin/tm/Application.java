package ru.levin.tm;

import ru.levin.tm.console.TerminalCommandRunner;
import ru.levin.tm.crud.ProjectService;
import ru.levin.tm.crud.TaskService;
import ru.levin.tm.entity.Project;
import ru.levin.tm.entity.Task;

public class Application {
    public static void main(String[] args) {
        testData();
        TerminalCommandRunner.getInstance().process();
    }

    private static void testData() {
        Project p1 = new Project();
        p1.setName("First Project");
        p1.setDescription("Just first project");
        ProjectService.getInstance().save(p1);

        Project p2 = new Project();
        p2.setName("Second project");
        p2.setDescription("Other project");
        ProjectService.getInstance().save(p2);

        Task t1 = new Task();
        t1.setName("Task for First project");
        t1.setDescription("Description");
        t1.setProjectId(ProjectService.getInstance().getAll().get(0).getId());
        TaskService.getInstance().save(t1);

        Task t2 = new Task();
        t2.setName("asdfg");
        t2.setDescription("Separated task");
        TaskService.getInstance().save(t2);
    }
}
