package com.BenjaminPark.repository;

import com.BenjaminPark.model.User;
import com.BenjaminPark.storage.StorageManager;

import java.security.KeyStore;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {

    private final Map<UUID, User> users;
    //StorageManager storageManager = new StorageManager();

    public UserRepository() {
        this.users = new ConcurrentHashMap<>();
    }

    public boolean addIfAbsent(UUID id, User user) {
        return users.putIfAbsent(id, user) == null;
    }

    public void delete(User user) {
        users.remove(user.getUserId());
    }

    public void update(User user) {
        users.put(user.getUserId(), user);
    }

    public boolean userExistsById(UUID id) {

        return users.containsKey(id);
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
