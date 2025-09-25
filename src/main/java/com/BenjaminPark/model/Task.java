package com.BenjaminPark.model;

import java.util.UUID;

/**
 * Task contain an id, description, title and status.
 * Open to potential expansions in the future, i.e. due date, duration, etc.
 */

public class Task {
    private final UUID taskId;
    private String description;
    private String title;
    private Status status;

    /**
     * Creates new task with given title and description.
     * The status is PENDING by default.
     * @param title         The title of the task.
     * @param description   The description of the task.
     */

    public Task(String title, String description) {
        this.taskId = UUID.randomUUID();
        this.description = description;
        this.title = title;
        this.status = Status.PENDING;
    }


    /**
     * This constructor is for restoring tasks, not creating them.
     *
     * @param taskId
     * @param title
     * @param description
     * @param status
     */
    public Task(UUID taskId, String title, String description, Status status) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public UUID getTaskId() {
        return taskId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Task ID: " + taskId + "\n" +
                "Title: " + title + "\n" +
                "Description: " + description + "\n" +
                "Status: " + status + "\n";
    }
}
