package com.example.demo.service.impl;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long Id) {
        return userRepository.findById(Id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByLoginAndPassword(String email, String password) {
        User user = findByEmail(email);
        if (user != null) {
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        return null;
    }
}
