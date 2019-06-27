package ru.levin.tm.crud;

import ru.levin.tm.api.AbstractService;
import ru.levin.tm.entity.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TaskService implements AbstractService<Task> {
    private static TaskService INSTANCE = new TaskService();
    private List<Task> taskList;

    private TaskService() {
        this.taskList = new ArrayList<>();
    }

    public static TaskService getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<Task> get(UUID id) {
        for (Task task : taskList) {
            if (task.getId().equals(id)) {
                return Optional.of(task);
            }
        }

        return Optional.empty();
    }

    @Override
    public List<Task> getAll() {
        return taskList;
    }

    public List<Task> getAllByProjectId(UUID id) {
        List<Task> resultList = new ArrayList<>();
        taskList.stream().filter(t -> id.equals(t.getProjectId())).forEach(resultList::add);
        return resultList;
    }

    @Override
    public boolean save(Task task) {
        if (taskList.stream().anyMatch(t -> t.getId() == task.getId())) {
            return false;
        }

        task.setId(UUID.randomUUID());
        taskList.add(task);
        return true;
    }

    @Override
    public boolean update(Task task) {
        for (int i = 0; i < taskList.size(); i++) {
            if (taskList.get(i).getId() == task.getId()) {
                taskList.set(i, task);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(Task task) {
        return taskList.removeIf(t -> t.getId().equals(task.getId()));
    }

    @Override
    public void deleteAll() {
        taskList.clear();
    }
}
