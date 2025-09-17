package com.BenjaminPark.repository;

import com.BenjaminPark.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    @DisplayName("Add a user to the userRepository's users HashMap")
    void addUser() throws DuplicateUserException {
        User user = new User("Sherlock");
        userRepository.addUser(user);
        assertTrue(userRepository.getUsers().contains(user));
        assertEquals(1, userRepository.getUsers().size());
    }

    @Test
    @DisplayName("Adding users that already exists in the userRepository's users HashMap throws DuplicateUserException")
    void addDuplicateUser() throws DuplicateUserException {
        User user = new User("Sherlock");
        userRepository.addUser(user);
        assertThrows(DuplicateUserException.class, () -> userRepository.addUser(user));
    }

    @Test
    @DisplayName("Delete a user from the userRepository's users HashMap")
    void deleteUser() throws DuplicateUserException, MissingUserException {
        User user = new User("Sherlock");
        User user2 = new User("Watson");
        userRepository.addUser(user);
        userRepository.addUser(user2);
        userRepository.deleteUser(user.getUserId());
        assertFalse(userRepository.getUsers().contains(user));
        assertEquals(1, userRepository.getUsers().size());
    }

    @Test
    @DisplayName("Deleting a user that doesn't exist in the userRepository's users HashMap throws MissingUserException")
    void deleteMissingUser() throws MissingUserException, DuplicateUserException {
        User user = new User("Sherlock");
        User user2 = new User("Watson");
        userRepository.addUser(user);
        assertThrows(MissingUserException.class, () -> userRepository.deleteUser(user2.getUserId()));

    }

    @Test
    @DisplayName("Update a user from the userRepository's users HashMap")
    void updateUser() throws DuplicateUserException, MissingUserException {
        User user = new User("Sherlock");
        userRepository.addUser(user);
        user.setName("Holmes");
        userRepository.updateUser(user);
        assertTrue(userRepository.getUsers().contains(user));
        assertEquals(1, userRepository.getUsers().size());
        assertEquals(userRepository.getUserById(user.getUserId()), user);
        assertSame(userRepository.getUserById(user.getUserId()), user);
    }

    @Test
    @DisplayName("Updating a user that doesn't exist in the userRepository's users Hashmap throws MissingUserException")
    void updateMissingUser() throws MissingUserException, DuplicateUserException {
        User user = new User("Sherlock");
        userRepository.addUser(user);
        User user2 = new User("Watson");
        assertThrows(MissingUserException.class, () -> userRepository.updateUser(user2));
    }

    @Test
    @DisplayName("Returns user by userId from userRepository's users HashMap")
    void getUserById() throws DuplicateUserException, MissingUserException {
        User user = new User("Sherlock");
        User user2 = new User("Watson");
        userRepository.addUser(user);
        userRepository.addUser(user2);
        assertEquals(userRepository.getUserById(user.getUserId()), user);
    }

    @Test
    @DisplayName("Returning a user that doesn't exist in the userRepository's users HashMap throws MissingUserException")
    void getMissingUser() throws MissingUserException, DuplicateUserException {
        User user = new User("Sherlock");
        User user2 = new User("Watson");
        userRepository.addUser(user);
        assertThrows(MissingUserException.class, () -> userRepository.getUserById(user2.getUserId()));
    }

    @Test
    @DisplayName("Returns an unmodifiable collection of the userRepository's users HashMap")
    void getUsers() throws DuplicateUserException {
        User user = new User("Sherlock");
        User user2 = new User("Watson");
        User user3 = new User("Moriarty");
        userRepository.addUser(user);
        userRepository.addUser(user2);
        assertEquals(2, userRepository.getUsers().size());
        assertThrows(UnsupportedOperationException.class, () -> userRepository.getUsers().remove(user2));
        assertThrows(UnsupportedOperationException.class, () -> userRepository.getUsers().add(user3));
    }
}