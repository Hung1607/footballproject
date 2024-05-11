package nl.fontys.individualproject.services;

import nl.fontys.individualproject.domain.response.User;

import java.util.List;
import java.util.Optional;

public interface UserFunctionality {
    List<User> getAllUser(Class<?> classType);
    Optional<User> getUserByID(int id, Class<?> classType);
    void deleteUserById(int id);
    void updateUser(User user);
}
