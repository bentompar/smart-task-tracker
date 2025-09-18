package com.BenjaminPark.cli;

import com.BenjaminPark.model.Status;
import com.BenjaminPark.model.Task;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.DuplicateUserException;
import com.BenjaminPark.repository.MissingUserException;
import com.BenjaminPark.repository.UserRepository;
import com.BenjaminPark.service.MissingTaskException;
import com.BenjaminPark.service.TaskService;

import java.util.Scanner;
import java.util.UUID;

public class TaskManagerCLI {
    static private final UserRepository userRepository = new UserRepository();
    static private final TaskService taskService = new TaskService();
    static private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws DuplicateUserException, MissingUserException {
        User user;
        System.out.println("Welcome to your smart task tracker");
        System.out.println("Please enter a user name or \"exit\" if you want to quit");
        String userName = scanner.nextLine();
        if (userName.equals("exit")) {
            return;
        } else {
            user = new User(userName);
            userRepository.addUser(user);
        }
        System.out.println("Welcome " + userName);
        System.out.println("Your tasks contain an id, a title, a description, and a status (PENDING, IN_PROGRESS, COMPLETED)");
        String command;

        while (true) {
            printMenu();
            System.out.println("Enter your choice: ");
            command = scanner.nextLine();
            switch (command.toLowerCase().trim()) {
                case "add task":
                case "add":
                case "1":
                    addTaskCLI(user);
                    break;
                case "delete task":
                case "delete":
                case "2":
                    deleteTaskCLI(user);
                    break;
                case "update task":
                case "update":
                case "3":
                    updateTaskCLI(user);
                    break;
                case "view tasks":
                case "view":
                case "4":
                    System.out.println(user.tasksToString());
                    break;
                case "exit":
                case "5":
                //case "f":
                    System.out.println("Thank you for using this smart task tracker");
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Invalid input");
                    break;
            }
        }

    }

    private static void addTaskCLI(User user) throws MissingUserException {
        System.out.println("Please enter the task name: ");
        String title = scanner.nextLine();
        System.out.println("Please enter the task description: ");
        String description = scanner.nextLine();
        taskService.addTask(userRepository.getUserById(user.getUserId()), new Task(title, description));
        System.out.println("Task added successfully.");
    }

    private static void updateTaskCLI(User user) {
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

    private static void deleteTaskCLI(User user) {
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
    }

    private static void printMenu() {
        System.out.println("Your task options are: ");
        System.out.println("1. Add Task");
        System.out.println("2. Delete Task");
        System.out.println("3. Update Task");
        System.out.println("4. View Tasks");
        System.out.println("5. Exit");
        System.out.println();
        /*
         * TO BE IMPLEMENTED
         System.out.println("Your user options are: ");
         System.out.println("A. Add user");
         System.out.println("B. Delete user");
         System.out.println("C. Update user");
         System.out.println("D. Change user");
         System.out.println("E. View users");
         System.out.println("F. Exit");
         */
    }
}
