package ru.levin.tm.api;

import ru.levin.tm.entity.User;
import ru.levin.tm.service.ProjectService;
import ru.levin.tm.service.TaskService;
import ru.levin.tm.service.UserService;

public interface IServiceLocator {
    ProjectService getProjectService();
    TaskService getTaskService();
    UserService getUserService();
    User getCurrentUser();
}
