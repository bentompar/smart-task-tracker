package com.BenjaminPark.service;

import com.BenjaminPark.dto.TaskUpdateRequest;
import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.TaskRepository;

import java.util.UUID;

public class TaskService {
    private final TaskRepository taskRepository;

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
        if (!taskRepository.add(user, task)) {
            throw new DuplicateTaskException("Unexpected task UUID collision");
        }

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
     * @param taskId      The updated task's id.
     * @throws MissingTaskException when task with taskId not found.
     */
    public void updateTask(User user, UUID taskId, TaskUpdateRequest taskUpdateRequest) throws MissingTaskException {
        Task task = taskRepository.getTaskById(user, taskId);

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


