package com.BenjaminPark.cli;

import com.BenjaminPark.dto.UserUpdateRequest;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.DuplicateUserException;
import com.BenjaminPark.repository.DuplicateUserNameException;
import com.BenjaminPark.repository.MissingUserException;
import com.BenjaminPark.repository.UserRepository;
import com.BenjaminPark.service.InvalidPasswordException;
import com.BenjaminPark.service.UserService;

import java.io.Console;
import java.util.Scanner;

public class UserCLI {
    UserService userService;
    UserRepository userRepository;
    Scanner scanner;
    Console console = System.console();

    UserCLI(UserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.userService = new UserService(userRepository);
        this.scanner = scanner;
    }

    protected void printUserMenu() {
        System.out.println("Your user options are: ");
        System.out.println("A. login User");
        System.out.println("B. Add User");
        System.out.println("C. Delete User");
        System.out.println("D. Update User");
        System.out.println("E. Change User");
        System.out.println("F. View Users");
        System.out.println("G. Options");
        System.out.println("H. Exit Program");
        System.out.println();
        System.out.println("Please enter what you'd like to do.");

    }

    protected User handleUserCLI() throws MissingUserException, InvalidPasswordException, DuplicateUserNameException, DuplicateUserException {
        printUserMenu();
        User user = null;
        outerloop:
        while (true) {
            String input = scanner.nextLine();

            switch (input.toLowerCase()) {
                case "a":
                case "login":
                case "login user":
                    user = loginUserCLI();
                    if (user == null) {
                        System.out.println("Invalid login.");
                    } else {
                        break outerloop;
                    }
                    break;
                case "b":
                case "add":
                case "add user":
                    user = addUserCLI();
                    if (user == null) {
                        System.out.println("Invalid new account.");
                    } else {
                        break outerloop;
                    }
                    break;
                case "c":
                case "delete":
                case "delete user":
                    deleteUserCLI();
                    break;
                case "d":
                case "update":
                case "update user":
                    updateUserCLI();
                    break;
                case "e":
                case "change":
                case "change user":
                    //user = changeUserCLI();
                    user = null;
                    if (user == null) {
                        System.out.println("Invalid change.");
                    } else {
                        break outerloop;
                    }
                    break;
                case "f":
                case "view":
                case "view users":
                    viewUserCLI();
                    break;
                case "g":
                case "option":
                case "options":
                    printUserMenu();
                    break;
                case "h":
                case "exit":
                case "exit program":
                    System.out.println("Exiting program, goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid input.");
                    break;
            }

        }
        return user;
    }

    protected User loginUserCLI() throws MissingUserException, InvalidPasswordException {
        System.out.println("Enter your user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your password: ");
        char[] password;
        if (console != null) {
            password = System.console().readPassword();
        } else {
            password = scanner.nextLine().toCharArray();
        }
        return userService.loginUser(name, password);
    }

    protected User addUserCLI() throws DuplicateUserNameException, DuplicateUserException {
        System.out.println("Enter your user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your password: ");
        char[] password;
        if (console != null) {
            password = System.console().readPassword();
        } else {
            password = scanner.nextLine().toCharArray();
        }
        return userService.addUser(name, password);
    }

    protected void deleteUserCLI() throws MissingUserException, InvalidPasswordException {
        System.out.println("Enter your user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your password: ");
        char[] password;
        if (console != null) {
            password = System.console().readPassword();
        } else {
            password = scanner.nextLine().toCharArray();
        }
        userService.deleteUser(name, password);
        System.out.println("User with name " + name + " has been deleted.");
    }

    protected void updateUserCLI() throws MissingUserException, InvalidPasswordException {
        System.out.println("Enter your user name: ");
        String name = scanner.nextLine();
        System.out.println("Enter your password: ");
        char[] password;
        if (console != null) {
            password = System.console().readPassword();
        } else {
            password = scanner.nextLine().toCharArray();
        }
        UserUpdateRequest userUpdateRequest = new UserUpdateRequest();
        System.out.println("Would you like to update your name?");
        String command = scanner.nextLine();
        switch (command.toLowerCase()) {
            case "yes":
            case "y":
                System.out.println("Please enter your new name: ");
                userUpdateRequest.setName(scanner.nextLine());
                break;
        }
        System.out.println("Would you like to update your password?");
        command = scanner.nextLine();
        switch (command.toLowerCase()) {
            case "yes":
            case "y":
                System.out.println("Please enter your new password: ");
                userUpdateRequest.setPassword(scanner.nextLine().toCharArray());
        }
        userService.updateUser(name, password, userUpdateRequest);
        System.out.println("User with name " + name + " has been updated.");
    }

    protected void viewUserCLI() {
        System.out.println(userRepository.getUsers().toString());
    }
}

