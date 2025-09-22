package com.BenjaminPark.cli;

import com.BenjaminPark.dto.TaskUpdateRequest;
import com.BenjaminPark.model.Status;
import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.MissingUserException;
import com.BenjaminPark.repository.TaskRepository;
import com.BenjaminPark.repository.UserRepository;
import com.BenjaminPark.service.DuplicateTaskException;
import com.BenjaminPark.service.MissingTaskException;
import com.BenjaminPark.service.TaskService;

import java.util.Scanner;
import java.util.UUID;

public class TaskCLI {

    UserRepository userRepository;
    TaskRepository taskRepository;
    TaskService taskService;
    Scanner scanner;


    TaskCLI(UserRepository userRepository, TaskRepository taskRepository, TaskService taskService, Scanner scanner) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
        this.taskService = taskService;
        this.scanner = scanner;
    }

    protected User handleTaskCLI(User user) throws MissingUserException, DuplicateTaskException {
        printTaskMenu();

        outerloop:
        while (true) {
            String input = scanner.nextLine();
            switch (input.toLowerCase()) {
                case "1":
                case "add":
                case "add task":
                    addTaskCLI(user);
                    break;
                case "2":
                case "delete":
                case "delete task":
                    deleteTaskCLI(user);
                    break;
                case "3":
                case "update":
                case "update task":
                    updateTaskCLI(user);
                    break;
                case "4":
                case "view":
                case "view tasks":
                    viewTasksCLI(user);
                    break;
                case "5":
                case "option":
                case "options":
                    printTaskMenu();
                    break;
                case "6":
                case "logout":
                    user = null;
                    break outerloop;
            }
        }
        return user;
    }

    private void addTaskCLI(User user) throws MissingUserException, DuplicateTaskException {
        System.out.println("Please enter the task name: ");
        String title = scanner.nextLine();
        System.out.println("Please enter the task description: ");
        String description = scanner.nextLine();
        taskService.addTask(user, new Task(title, description));
        System.out.println("Task added successfully.");
    }

    private void updateTaskCLI(User user) {
        TaskUpdateRequest taskUpdateRequest = new TaskUpdateRequest();
        System.out.println("Please enter the task id: ");
        UUID id;
        try {
            id = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task id.");
            return;
        }
        System.out.println("Please enter new title");
        String title = scanner.nextLine();
        taskUpdateRequest.setTitle(title);
        System.out.println("Please enter new description");
        String description = scanner.nextLine();
        taskUpdateRequest.setDescription(description);
        System.out.println("Please enter new status: ");
        Status status;
        try {
            status = Status.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status");
            System.out.println("Status must be: PENDING, IN_PROGRESS, COMPLETED");
            return;
        }
        taskUpdateRequest.setStatus(status);

        try {
            taskService.updateTask(user, taskRepository.getTaskById(user, id), taskUpdateRequest);
            System.out.println("Task updated successfully.");
        } catch (MissingTaskException e) {
            System.out.println(e.getMessage());
            System.out.println("Maybe view the tasks to make sure you have the right task id.");
        }
    }

    private void deleteTaskCLI(User user) {
        System.out.println("Please enter the task id: ");
        UUID id;
        try {
            id = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task id.");
            return;
        }
        try {
            taskService.deleteTask(user, id);
            System.out.println("Task deleted successfully.");
        } catch (MissingTaskException e) {
            System.out.println(e.getMessage());
            System.out.println("Maybe view the tasks to make sure you have the right task id.");
        }
    }

    private void viewTasksCLI(User user) {
        System.out.println(user.tasksToString());
    }

    private static void printTaskMenu() {
        System.out.println("Your task options are: ");
        System.out.println("1. Add Task");
        System.out.println("2. Delete Task");
        System.out.println("3. Update Task");
        System.out.println("4. View Tasks");
        System.out.println("5. Options");
        System.out.println("6. Logout");
        System.out.println();
        System.out.println("Please enter what you'd like to do.");
    }
}



    /**private void addTaskCLI(User user) throws MissingUserException {
        System.out.println("Please enter the task name: ");
        String title = scanner.nextLine();
        System.out.println("Please enter the task description: ");
        String description = scanner.nextLine();
        taskService.addTask(userRepository.getUserById(user.getUserId()), new Task(title, description));
        System.out.println("Task added successfully.");
    }

    private void updateTaskCLI(User user) {
        System.out.println("Please enter the task id: ");
        UUID id;
        try {
            id = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task id.");
            return;
        }
        System.out.println("Please enter new title");
        String title = scanner.nextLine();
        System.out.println("Please enter new description");
        String description = scanner.nextLine();
        System.out.println("Please enter new status: ");
        Status status;
        try {
            status = Status.valueOf(scanner.nextLine().toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status");
            System.out.println("Status must be: PENDING, IN_PROGRESS, COMPLETED");
            return;
        }
        Task task = new Task(title, description);
        task.setStatus(status);
        try {
            taskService.updateTask(userRepository.getUserById(user.getUserId()), id, task);
            System.out.println("Task updated successfully.");
        } catch (MissingTaskException e) {
            System.out.println(e.getMessage());
            System.out.println("Maybe view the tasks to make sure you have the right task id.");
        } catch (MissingUserException e) {
            System.out.println(e.getMessage());
            System.out.println("Maybe view the user list to make sure you have the right user id");
        }
    }

    private void deleteTaskCLI(User user) {
        System.out.println("Please enter the task id: ");
        UUID id;
        try {
            id = UUID.fromString(scanner.nextLine());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid task id.");
            return;
        }
        try {
            taskService.deleteTask(userRepository.getUserById(user.getUserId()), id);
            System.out.println("Task deleted successfully.");
        } catch (MissingTaskException e) {
            System.out.println(e.getMessage());
            System.out.println("Maybe view the tasks to make sure you have the right task id.");
        } catch (MissingUserException e) {
            System.out.println(e.getMessage());
            System.out.println("Maybe view the user list to make sure you have the right user id");
        }
    }**/

