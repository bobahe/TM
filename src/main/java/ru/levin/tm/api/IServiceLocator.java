package ru.levin.tm.api;

import ru.levin.tm.api.service.IProjectService;
import ru.levin.tm.api.service.ITaskService;
import ru.levin.tm.api.service.ITerminalService;
import ru.levin.tm.api.service.IUserService;

public interface IServiceLocator {
    ProjectService getProjectService();
    TaskService getTaskService();
    UserService getUserService();
    User getCurrentUser();
}
