package com.BenjaminPark.service;

import com.BenjaminPark.dto.TaskUpdateRequest;
import com.BenjaminPark.model.Status;
import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;

import java.util.UUID;

public class TaskService {
    private TaskRepository taskRepository = new TaskRepository();

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    /**
     * Adds task using task.java internal method.
     *
     * @param user  The user to whom the task will belong.
     * @param task  The task to be added.
     */
    public void addTask(User user, Task task) throws DuplicateTaskException {
        if (taskRepository.taskExistsById(user, task.getTaskId())) {
            throw new DuplicateTaskException("Task with id " + task.getTaskId() + " already exists");
        }
        taskRepository.add(user, task);

    }

    /**
     * Deletes task using task.java internal method.
     *
     * @param user      The user to whom the task belongs.
     * @param taskId    The taskId of the task to be deleted.
     * @throws MissingTaskException when task with taskId not found.
     */
    public void deleteTask(User user, UUID taskId) throws MissingTaskException {
        if (!taskRepository.taskExistsById(user, taskId)) {
            throw new MissingTaskException("Task with id " + taskId + " does not exist");
        } else {
            taskRepository.delete(user, taskId);
        }
    }

    /**
     * Updates task using task.java internal method.
     *
     * @param user      The user to whom the task belongs.
     * @param task      The updated task.
     * @throws MissingTaskException when task with taskId not found.
     */
    public void updateTask(User user, Task task, TaskUpdateRequest taskUpdateRequest) throws MissingTaskException {
        if (!taskRepository.taskExistsById(user, task.getTaskId())) {
            throw new MissingTaskException("Task with id " + task.getTaskId() + " does not exist");
        } else {
            if (taskUpdateRequest.getTitle() != null) {
                task.setTitle(taskUpdateRequest.getTitle());
            }
            if (taskUpdateRequest.getDescription() != null) {
                task.setDescription(taskUpdateRequest.getDescription());
            }
            if (taskUpdateRequest.getStatus() != null) {
                task.setStatus(taskUpdateRequest.getStatus());
            }
            taskRepository.update(user, task);
        }
    }

}


