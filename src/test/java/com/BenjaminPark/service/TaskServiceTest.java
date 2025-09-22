package com.BenjaminPark.service;

import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {

    private User user;
    private TaskService taskService;


    @BeforeEach
    void setUp() {
        user = new User("Ben", "test".toCharArray());
        taskService = new TaskService();
    }


    @Test
    @DisplayName("Add a task to a user's task list")
    void addTask() {
        Task addTask = new Task("Dishes", "Clean all the dishes");
        taskService.addTask(user, addTask);
        assertEquals(addTask.getTaskId(), user.getTasks().get(0).getTaskId());
        assertEquals(addTask.getTitle(), user.getTasks().get(0).getTitle());
        assertEquals(addTask.getDescription(), user.getTasks().get(0).getDescription());
        assertEquals(addTask.getStatus(), user.getTasks().get(0).getStatus());
        assertEquals(1, user.getTasks().size());
    }

    @Test
    @DisplayName("Delete a task from a user's task list")
    void deleteTask() throws MissingTaskException {
        Task deleteTask = new Task("Dishes", "Clean all the dishes");
        taskService.addTask(user, deleteTask);
        taskService.deleteTask(user, deleteTask.getTaskId());
        assertFalse(user.getTasks().contains(deleteTask));
        assertEquals(0, user.getTasks().size());
    }

    @Test
    @DisplayName("Deleting a non-existent task from a user's task list throws MissingTaskException")
    void deleteNonExistentTask() {
        Task deleteTask = new Task("Dishes", "Clean all the dishes");
        assertThrows(MissingTaskException.class, () -> taskService.deleteTask(user, deleteTask.getTaskId()));
        assertEquals(0, user.getTasks().size());
    }

    @Test
    @DisplayName("Update a task in a user's task list")
    void updateTask() throws MissingTaskException {
        Task addTask = new Task("Dishes", "Clean all the dishes");
        Task updateTask = new Task("Dishes", "Clean only my dishes");
        taskService.addTask(user, addTask);
        taskService.updateTask(user, addTask.getTaskId(), updateTask);
        assertEquals(user.getTasks().get(0).getTitle(),updateTask.getTitle());
        assertEquals(user.getTasks().get(0).getDescription(),updateTask.getDescription());
        assertEquals(user.getTasks().get(0).getStatus(),updateTask.getStatus());
        assertNotEquals(user.getTasks().get(0).getTaskId(),updateTask.getTaskId());
        assertEquals(1, user.getTasks().size());
    }

    @Test
    @DisplayName("Updating a non-existent task in a user's task list throws MissingTaskException")
    void updateNonExistentTask() {
        Task addTask = new Task("Dishes", "Clean all the dishes");
        Task updateTask = new Task("Dishes", "Clean only my dishes");
        assertThrows(MissingTaskException.class, () -> taskService.updateTask(user, addTask.getTaskId(), updateTask));
        assertEquals(0, user.getTasks().size());
    }
}