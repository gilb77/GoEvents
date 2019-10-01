package com.GoEvent.service.impl;


import com.GoEvent.dao.RoleRepository;
import com.GoEvent.dao.UserRepository;
import com.GoEvent.model.User;
import com.GoEvent.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Service
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private static final String USER_ROLE = "ROLE_USER";

    private static Lock lockRepository = new ReentrantLock();

    @Autowired
    public UserServiceImp(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        lockRepository.lock();
        User user1;
        try {
            user1 = userRepository.saveAndFlush(user);
            // Encode plaintext password
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActive(1);
            // Set Role to ROLE_USER
            user.setRoles(Collections.singletonList(roleRepository.findByRole(USER_ROLE)));
        } finally {
            lockRepository.unlock();
        }
        return user1;
    }

    public void saveOnlyUser(User user) {
        lockRepository.lock();
        try {
            userRepository.save(user);
        } finally {
            lockRepository.unlock();
        }
    }

    public List<User> findAllUsers() {
        List<User> users;
        lockRepository.lock();
        try {
            users = userRepository.findAll();
        } finally {
            lockRepository.unlock();
        }
        return users;
    }

    public void deleteById(long id) {
        lockRepository.lock();
        try {
            userRepository.deleteById(id);
        } finally {
            lockRepository.unlock();
        }
    }
}
