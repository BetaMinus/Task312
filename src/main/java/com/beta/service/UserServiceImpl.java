package com.beta.service;

import com.beta.model.User;
import com.beta.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void create(Map<String, String> params, String[] roles) {
        User user = new User();
        if (!params.get("firstName").isEmpty()) {
            user.setFirstName(params.get("firstName"));
        }
        if (!params.get("lastName").isEmpty()) {
            user.setLastName(params.get("lastName"));
        }
        if (!params.get("age").isEmpty()) {
            user.setAge(Integer.parseInt(params.get("age")));
        }
        if (!params.get("email").isEmpty()) {
            user.setEmail(params.get("email"));
        }
        if (!params.get("password").isEmpty()) {
            user.setPassword(passwordEncoder.encode(params.get("password")));
        }
        user.setRoles(Arrays.stream(roles)
                .map(role ->roleService.findByRoleName(role).get())
                .collect(Collectors.toList()));
        userRepository.save(user);
        }

    @Override
    public void update(Map<String, String> params, String[] roles) {
        User user = userRepository.findById(Long.parseLong(params.get("id"))).get();
        if (!params.get("firstName").isEmpty()) {
            user.setFirstName(params.get("firstName"));
        }
        if (!params.get("lastName").isEmpty()) {
            user.setLastName(params.get("lastName"));
        }
        if (!params.get("age").isEmpty()) {
            user.setAge(Integer.parseInt(params.get("age")));
        }
        if (!params.get("email").isEmpty()) {
            user.setEmail(params.get("email"));
        }
        if (!params.get("password").isEmpty()) {
            user.setPassword(passwordEncoder.encode(params.get("password")));
        }
        if (roles != null) {
            user.setRoles(Arrays.stream(roles)
                    .map(role ->roleService.findByRoleName(role).get())
                    .collect(Collectors.toList()));
        }
        userRepository.save(user);
        }

    @Override
    public Optional<User> readById(long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<User> readAll() {
        return userRepository.findAll();
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findByEmail(s);
    }
}
