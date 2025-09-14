package com.BenjaminPark.service;

import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;

import java.util.UUID;

public class TaskService {

    /**
     * Adds task using task.java internal method.
     *
     * @param user  The user to whom the task will belong.
     * @param task  The task to be added.
     */
    public void addTask(User user, Task task) {
        user.addTaskInternal(task);
    }

    /**
     * Deletes task using task.java internal method.
     *
     * @param user      The user to whom the task belongs.
     * @param taskId    The taskId of the task to be deleted.
     * @throws MissingTaskException when task with taskId not found.
     */
    public void deleteTask(User user, UUID taskId) throws MissingTaskException {
        Task removeTask = null;
        for (Task task : user.getTasks()) {
            if (task.getTaskId().equals(taskId)) {
                removeTask = task;
                break;
            }
        }
        if (removeTask != null) {
            user.removeTaskInternal(removeTask);
        } else {
            throw new MissingTaskException("Task with id " + taskId + " not found");
        }
    }

    /**
     * Updates task using task.java internal method.
     *
     * @param user      The user to whom the task belongs.
     * @param taskId    The taskId of the task to be updated.
     * @param task      The updated task.
     * @throws MissingTaskException when task with taskId not found.
     */
    public void updateTask(User user, UUID taskId, Task task) throws MissingTaskException {
        for (Task task1 : user.getTasks()) {
            if (task1.getTaskId().equals(taskId)) {
                task1.setTitle(task.getTitle());
                task1.setDescription(task.getDescription());
                task1.setStatus(task.getStatus());
                return;
            }
        }
        throw new MissingTaskException("Task with id " + taskId + " not found");
    }

}


