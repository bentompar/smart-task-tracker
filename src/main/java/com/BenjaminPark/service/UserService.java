package com.BenjaminPark.service;

import com.BenjaminPark.dto.UserUpdateRequest;
import com.BenjaminPark.model.User;
import com.BenjaminPark.repository.DuplicateUserException;
import com.BenjaminPark.repository.DuplicateUserNameException;
import com.BenjaminPark.repository.MissingUserException;
import com.BenjaminPark.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User loginUser(String name, char[] password) throws MissingUserException, InvalidPasswordException {
        if (!userRepository.userExistsByName(name)) {
            throw new MissingUserException("User with name " + name + " does not exist");
        }

        User user = userRepository.getUserByName(name);

        user.authenticatePassword(password);

        return user;
    }

    public User addUser(String name, char[] password) throws DuplicateUserException, DuplicateUserNameException {

        if (userRepository.userExistsByName(name)) {
            throw new DuplicateUserNameException("User with name " + name + " already exists");
        }

        User user = new User(name, password);

        if (!userRepository.addIfAbsent(user.getUserId(), user)) {
            throw new DuplicateUserException("Unexpected user UUID collision");
        }
        return user;
    }

    public void deleteUser(String name, char[] password) throws MissingUserException, InvalidPasswordException {
        if (!userRepository.userExistsByName(name)) {
            throw new MissingUserException("User with name " + name + " does not exist");
        }
        User user = userRepository.getUserByName(name);
        user.authenticatePassword(password);
        userRepository.delete(user);

    }

    public void updateUser(String name, char[] password, UserUpdateRequest userUpdateRequest) throws MissingUserException, InvalidPasswordException {
        if (!userRepository.userExistsByName(name)) {
            throw new MissingUserException("User with name " + name + " does not exist");
        }

        User user = userRepository.getUserByName(name);
        user.authenticatePassword(password);

        if (userUpdateRequest.getName() != null) {
            user.setName(userUpdateRequest.getName());
        }

        if (userUpdateRequest.getPassword() != null) {
            user.setPassword(userUpdateRequest.getPassword());
        }
    }

}
