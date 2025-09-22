package com.BenjaminPark.demo;

import com.BenjaminPark.dto.TaskUpdateRequest;
import com.BenjaminPark.model.Status;
import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.service.DuplicateTaskException;
import com.BenjaminPark.service.MissingTaskException;
import com.BenjaminPark.service.TaskService;

public class Main {
    public static void main(String[] args) throws MissingTaskException, DuplicateTaskException {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        User user = new User("Benjamin", "test".toCharArray());
        TaskService taskService = new TaskService(new TaskRepository());
        Task task1 = new Task("Wash clothes", "Do the entire family's washing");
        Task task2 = new Task("Hoover apartment", "Hoover the entire apartment, including attic");
        Task task3 = new Task("Wash clothes", "Do only my washing");
        taskUpdateRequest.setTitle("Wash clothes");
        taskUpdateRequest.setDescription("Do only my washing");
        taskUpdateRequest.setStatus(Status.IN_PROGRESS);

        taskService.addTask(user, task1);
        taskService.addTask(user, task2);
        taskService.updateTask(user, task1, taskUpdateRequest);
        taskService.deleteTask(user, task2.getTaskId());

        System.out.println(user.toString());
        System.out.println(user.tasksToString());

    }
}
