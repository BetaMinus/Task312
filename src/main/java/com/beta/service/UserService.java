package com.beta.service;

import com.beta.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserService {

    void create(User user, String[] roles);
    Optional<User> readById(long id);
    List<User> readAll();
    void update(User user, String[] roles);
    void delete (User user);
}
