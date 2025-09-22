package com.BenjaminPark.cli;

import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.*;
import com.BenjaminPark.service.DuplicateTaskException;
import com.BenjaminPark.service.InvalidPasswordException;
import com.BenjaminPark.service.TaskService;

import java.util.Scanner;

public class TaskManagerCLI {

    public static void main(String[] args) throws MissingUserException, InvalidPasswordException, DuplicateUserNameException, DuplicateUserException, DuplicateTaskException {
        UserRepository  userRepository = new UserRepository();
        TaskRepository taskRepository = new TaskRepository();
        TaskService taskService = new TaskService(taskRepository);
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        UserCLI userCLI = new UserCLI(userRepository, scanner);
        TaskCLI taskCLI = new TaskCLI(userRepository, taskRepository, taskService, scanner);

        System.out.println("Welcome to smart task tracker");


        while (true) {
            if (currentUser == null) {
                currentUser = userCLI.handleUserCLI();
            } else {
                currentUser = taskCLI.handleTaskCLI(currentUser);
            }
        }
    }
}
