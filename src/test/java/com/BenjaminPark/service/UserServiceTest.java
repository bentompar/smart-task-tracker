package com.BenjaminPark.service;

import com.BenjaminPark.dto.UserUpdateRequest;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.DuplicateUserException;
import com.BenjaminPark.repository.DuplicateUserNameException;
import com.BenjaminPark.repository.MissingUserException;
import com.BenjaminPark.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;
    UserUpdateRequest userUpdateRequest;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        userService = new UserService(userRepository);
        userUpdateRequest = new UserUpdateRequest();

    }

    @Test
    @DisplayName("Add a user to the userRepository's users HashMap")
    void addUser() throws DuplicateUserException, DuplicateUserNameException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        assertTrue(userRepository.getUsers().contains(user));
        assertEquals(1, userRepository.getUsers().size());
    }

    @Test
    @DisplayName("Adding users that already exists in the userRepository's users HashMap throws DuplicateUserException")
    void addDuplicateUser() throws DuplicateUserException, DuplicateUserNameException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        userRepository.add(user);
        assertThrows(DuplicateUserNameException.class, () -> userService.addUser("Sherlock", "test".toCharArray()));
    }

    @Test
    @DisplayName("Delete a user from the userRepository's users HashMap")
    void deleteUser() throws DuplicateUserException, MissingUserException, DuplicateUserNameException, InvalidPasswordException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        User user2 = userService.addUser("Watson", "test".toCharArray());
        userService.deleteUser("Sherlock", "test".toCharArray());
        assertFalse(userRepository.getUsers().contains(user));
        assertEquals(1, userRepository.getUsers().size());
    }

    @Test
    @DisplayName("Deleting a user that doesn't exist in the userRepository's users HashMap throws MissingUserException")
    void deleteMissingUser() throws MissingUserException, DuplicateUserException, DuplicateUserNameException {
        User user = userService.addUser("Watson", "test".toCharArray());
        assertThrows(MissingUserException.class, () -> userService.deleteUser("Sherlock", "test".toCharArray()));
    }

    @Test
    @DisplayName("Update a user from the userRepository's users HashMap")
    void updateUser() throws DuplicateUserException, MissingUserException, DuplicateUserNameException, InvalidPasswordException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        userUpdateRequest.setName("Watson");
        userUpdateRequest.setPassword("test2".toCharArray());
        userService.updateUser("Sherlock", "test".toCharArray(), userUpdateRequest);
        assertTrue(userRepository.getUsers().contains(user));
        assertEquals(1, userRepository.getUsers().size());
        assertThrows(MissingUserException.class, () -> userRepository.getUserByName("Sherlock"));
        assertEquals(1, userRepository.getUsers().size());
        assertEquals("Watson", userRepository.getUserById(user.getUserId()).getName());
    }

    @Test
    @DisplayName("Updating a user that doesn't exist in the userRepository's users Hashmap throws MissingUserException")
    void updateMissingUser() throws MissingUserException, DuplicateUserException, DuplicateUserNameException {
        User user = userService.addUser("Sherlock", "test".toCharArray());

        assertThrows(MissingUserException.class, () -> userService.updateUser("Watson", "test".toCharArray(), userUpdateRequest));
    }

    @Test
    @DisplayName("Returns user by userId from userRepository's users HashMap")
    void getUserById() throws DuplicateUserException, MissingUserException, DuplicateUserNameException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        User user2 = userService.addUser("Watson", "test".toCharArray());

        assertEquals(userRepository.getUserById(user.getUserId()), user);
        assertEquals(userRepository.getUserById(user2.getUserId()), user2);
    }

    @Test
    @DisplayName("Returning a user that doesn't exist in the userRepository's users HashMap throws MissingUserException")
    void getMissingUser() throws MissingUserException, DuplicateUserException, DuplicateUserNameException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        User user2 = new User("Watson", "test".toCharArray());
        assertThrows(MissingUserException.class, () -> userRepository.getUserById(user2.getUserId()));
    }

    @Test
    @DisplayName("Returns an unmodifiable collection of the userRepository's users HashMap")
    void getUsers() throws DuplicateUserException, DuplicateUserNameException {
        User user = userService.addUser("Sherlock", "test".toCharArray());
        User user2 = userService.addUser("Watson", "test".toCharArray());
        User user3 = new User("Moriarty", "test".toCharArray());

        assertEquals(2, userRepository.getUsers().size());
        assertThrows(UnsupportedOperationException.class, () -> userRepository.getUsers().remove(user2));
        assertThrows(UnsupportedOperationException.class, () -> userRepository.getUsers().add(user3));
    }
}