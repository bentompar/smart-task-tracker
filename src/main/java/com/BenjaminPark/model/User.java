package com.BenjaminPark.model;

import com.BenjaminPark.service.InvalidPasswordException;

import java.util.*;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private String name;
    private String hashedPassword;
    private final UUID userId;
    private final List<Task> tasks;
    private final BCryptPasswordEncoder  passwordEncoder =  new BCryptPasswordEncoder(10);

    public User(String name, char[] password) {
        this.name = name;
        this.hashedPassword = passwordEncoder.encode(new String(password));
        this.userId = UUID.randomUUID();
        tasks = new ArrayList<>();
        Arrays.fill(password, '0');
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


    public void setPassword(char[] password) {
        this.hashedPassword = passwordEncoder.encode(new String(password));
    }

    /**
     * Method is only for authenticating password
     *
     * @param password The input password to be authenticated with the user's password
     * @throws InvalidPasswordException when passwords do not match
     */
    public void authenticatePassword(char[] password) throws InvalidPasswordException {
        if (!passwordEncoder.matches(new String(password), hashedPassword)) {
            throw new InvalidPasswordException("Incorrect Password");
        }
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

    public void updateTaskInternal(Task task) {
        for (Task t : getTasks()) {
            if (t.getTaskId().equals(task.getTaskId())) {
                t.setTitle(task.getTitle());
                t.setDescription(task.getDescription());
                t.setStatus(task.getStatus());
                break;
            }
        }
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
