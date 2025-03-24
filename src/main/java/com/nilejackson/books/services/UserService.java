package com.nilejackson.books.services;

import java.util.List;
import java.util.Optional;

import com.nilejackson.books.domain.User;

public interface UserService {
    User createUser(User user);
    Optional<User> findById(Long id);
    List<User> findAllUsers();
    User updateUser(Long id, User user);
    void deleteUser(Long id);
    boolean isActiveUser(Long id);
}
