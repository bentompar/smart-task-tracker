package com.BenjaminPark.demo;

import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.service.MissingTaskException;
import com.BenjaminPark.service.TaskService;

public class Main {
    public static void main(String[] args) throws MissingTaskException {
        User user = new User("Benjamin");
        TaskService taskService = new TaskService();
        Task task1 = new Task("Wash clothes", "Do the entire family's washing");
        Task task2 = new Task("Hoover apartment", "Hoover the entire apartment, including attic");
        Task task3 = new Task("Wash clothes", "Do only my washing");
        taskService.addTask(user, task1);
        taskService.addTask(user, task2);
        taskService.updateTask(user, task1.getTaskId(), task3);
        taskService.deleteTask(user, task2.getTaskId());

        System.out.println(user.toString());
        System.out.println(user.tasksToString());

    }
}
