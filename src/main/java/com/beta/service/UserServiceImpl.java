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
    public void create(User user, String[] roles) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.stream(roles)
                .map(role ->roleService.findByRoleName(role).get())
                .collect(Collectors.toList()));
        userRepository.save(user);
    }

    @Override
    public void update(User user, String[] roles) {
        if (user.getPassword().isEmpty()) {
            user.setPassword(userRepository.findById(user.getId()).get().getPassword());
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        if (roles != null) {
            user.setRoles(Arrays.stream(roles)
                    .map(role ->roleService.findByRoleName(role).get())
                    .collect(Collectors.toList()));
        } else {
            user.setRoles(userRepository.findById(user.getId()).get().getRoles());
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
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException { return userRepository.findByEmail(s); }
}
