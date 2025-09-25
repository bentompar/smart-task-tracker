package com.BenjaminPark.repository;

import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.service.MissingTaskException;

import java.util.Map;
import java.util.UUID;

public class TaskRepository {

    public boolean add(User user, Task task) {
        return user.addTaskIfAbsent(task);
    }

    public void delete(User user, UUID taskId) {
        Task task = user.getTasks().get(taskId);
        if (task != null) {
            user.removeTaskInternal(task);
        }

    }

    public void update(User user, Task task) {
        user.updateTaskInternal(task);
    }

    public boolean taskExistsByName(User user, String name) {
        for (Map.Entry<UUID, Task> entry : user.getTasks().entrySet()) {
            if (entry.getValue().getTitle().equalsIgnoreCase(name)) {
                return true;
            }
        } return false;
    }

    public boolean taskExistsById(User user, UUID id) {
        return user.getTasks().containsKey(id);
    }

    public Task getTaskById(User user, UUID id) throws MissingTaskException {
        if (user.getTasks().containsKey(id)) {
            return user.getTasks().get(id);
        } else {
            throw new MissingTaskException("Task with id " + id + " not found");
        }
    }

}
