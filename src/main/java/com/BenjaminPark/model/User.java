package com.BenjaminPark.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class User {
    private String name;
    private final UUID userId;
    private final List<Task> tasks;

    public User(String name) {
        this.name = name;
        this.userId = UUID.randomUUID();
        tasks = new ArrayList<>();
    }

    public UUID getUserId() {
        return userId;
    }

    public List<Task> getTasks() {
        return Collections.unmodifiableList(tasks);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method is only intended for TaskService. Potentially later in a repository layer.
     *
     * @param task The task to be added.
     */
    public void addTaskInternal(Task task) {
        tasks.add(task);
    }

    /**
     * Method is only intended for TaskService. Potentially later in a repository layer.
     *
     * @param task The task to be removed.
     */
    public void removeTaskInternal(Task task) {
        tasks.remove(task);
    }

    /**
     * Method returns string with list of tasks.
     */

    public String tasksToString() {
        StringBuilder sb = new StringBuilder();
        if (tasks.isEmpty()) {
            sb.append("The user ").append(name).append(" has no tasks");
        } else {
            sb.append("The user ").append(name).append(" has the following tasks: \n");
            for (Task task : tasks) {
                sb.append(task.toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "User: name=" + name + ", userId=" + userId + ", taskCount=" + tasks.size();
    }
}
