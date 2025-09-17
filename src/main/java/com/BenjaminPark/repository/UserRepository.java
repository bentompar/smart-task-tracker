package com.BenjaminPark.repository;

import com.BenjaminPark.model.User;

import java.util.*;

public class UserRepository {

    private final Map<UUID, User> users = new HashMap<>();


    /**
     * Adds user to users HashMap.
     *
     * @param user The user to add to the users HashMap.
     * @throws DuplicateUserException if user with same userId already exists in users HashMap.
     */
    public User addUser(User user) throws DuplicateUserException {
        if (!users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            return user;
        } else {
            throw new DuplicateUserException("User with id " + user.getUserId() + " already exists");
        }
    }


    /**
     * Deletes a user from users HashMap.
     *
     * @param id The userID of the user to be deleted.
     * @throws MissingUserException when user not found in users HashMap.
     */
    public User deleteUser(UUID id) throws MissingUserException {
        if (users.containsKey(id)) {
            User user = users.get(id);
            users.remove(id);
            return user;
        } else {
            throw new MissingUserException("User with id " + id + " not found");
        }
    }


    /**
     * Updates a user from the users HashMap.
     *
     * @param user The user to be updated.
     * @throws MissingUserException when user not found in users HashMap.
     */
    public User updateUser(User user) throws MissingUserException {
        if (users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            return user;
        } else {
            throw new MissingUserException("User with id " + user.getUserId() + " not found");
        }
    }

    /**
     * Returns user with passed userId from users HashMap.
     *
     * @param id    The userId of the user to be returned.
     * @return      Returns the user with the passed userId.
     * @throws MissingUserException when user with passed userId not found in users HashMap.
     */
    public User getUserById(UUID id) throws MissingUserException {
        if (users.containsKey(id)) {
            return users.get(id);
        } else  {
            throw new MissingUserException("User with id " + id + " not found");
        }
    }

    /**
     * Returns unmodifiable users map.
     *
     * @return Returns an unmodifiable users map.
     */
    public Collection<User> getUsers() {
        return Collections.unmodifiableCollection(users.values());
    }
}
