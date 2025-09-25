package com.BenjaminPark.model;

import com.BenjaminPark.service.InvalidPasswordException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class User {
    private String name;
    private String hashedPassword;
    private final UUID userId;
    private final Map<UUID, Task> tasks;
    private final BCryptPasswordEncoder  passwordEncoder =  new BCryptPasswordEncoder(10);

    public User(String name, char[] password) {
        this.name = name;
        this.hashedPassword = passwordEncoder.encode(new String(password));
        this.userId = UUID.randomUUID();
        tasks = new ConcurrentHashMap<>();
        Arrays.fill(password, '0');
    }

    /**
     * This constructor is for restoring users from data storage.
     *
     * @param userId
     * @param name
     * @param hashedPassword
     * @param tasks
     */
    public User(UUID userId, String name, String hashedPassword, Map<UUID, Task> tasks) {
        this.userId = userId;
        this.name = name;
        this.hashedPassword = hashedPassword;
        this.tasks = tasks;
    }


    public UUID getUserId() {
        return userId;
    }

    public Map<UUID, Task> getTasks() {
        return Collections.unmodifiableMap(tasks);
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
     * this method is only meant for the storage.
     *
     * @return
     */
    public String getHashedPasswordForStorage() {
        return hashedPassword;
    }

    /**
     * Method is only intended for TaskService. Potentially later in a repository layer.
     *
     * @param task The task to be added.
     */
    public void addTaskInternal(Task task) {tasks.put(task.getTaskId(), task);}

    public boolean addTaskIfAbsent(Task task) {
        return tasks.putIfAbsent(task.getTaskId(), task) == null;
    }

    /**
     * Method is only intended for TaskService. Potentially later in a repository layer.
     *
     * @param task The task to be removed.
     */
    public void removeTaskInternal(Task task) {
        tasks.remove(task.getTaskId());
    }

    public void updateTaskInternal(Task task) {
        Task t = tasks.get(task.getTaskId());
        t.setTitle(task.getTitle());
        t.setDescription(task.getDescription());
        t.setStatus(task.getStatus());
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

            for (Map.Entry<UUID, Task> task : tasks.entrySet()) {
                sb.append(task.getValue().toString()).append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return "User: name=" + name + ", userId=" + userId + ", taskCount=" + tasks.size();
    }
}
