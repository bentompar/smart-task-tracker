package com.BenjaminPark.repository;

import com.BenjaminPark.model.User;

import java.security.KeyStore;
import java.util.*;

public class UserRepository {

    private final Map<UUID, User> users;

    public UserRepository() {
        this.users = new HashMap<>();
    }

    public void add(User user) {
        users.put(user.getUserId(), user);
    }

    public void delete(User user) {
        users.remove(user.getUserId());
    }

    public void update(User user) {
        users.put(user.getUserId(), user);
    }

    public boolean userExistsById(UUID id) {

        if (users.containsKey(id)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean userExistsByName(String name) {
        return users.values().stream().anyMatch(u -> u.getName().equalsIgnoreCase(name));
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

    public User getUserByName(String name) throws MissingUserException {
        return users.values().stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElseThrow(() -> new MissingUserException("User with name " + name + " not found"));

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
