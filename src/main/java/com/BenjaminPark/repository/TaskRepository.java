package com.BenjaminPark.repository;

import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.service.MissingTaskException;

import java.util.UUID;

public class TaskRepository {

    public void add(User user, Task task) {
        user.addTaskInternal(task);
    }

    public void delete(User user, UUID taskId) {
        for (Task task : user.getTasks()) {
            if (task.getTaskId().equals(taskId)) {
                user.removeTaskInternal(task);
            }
        }
    }

    public void update(User user, Task task) {
        user.updateTaskInternal(task);
    }

    public boolean taskExistsByName(User user, String name) {
        return user.getTasks().stream().anyMatch(task -> task.getTitle().equalsIgnoreCase(name));
    }

    public boolean taskExistsById(User user, UUID id) {
        return user.getTasks().stream().anyMatch(task -> task.getTaskId().equals(id));
    }

    public Task getTaskById(User user, UUID id) throws MissingTaskException {
        return user.getTasks().stream().filter(t -> t.getTaskId().equals(id)).findFirst().orElseThrow(
                () -> new MissingTaskException("Task with id " + id + " not found"));
    }

}
