package com.example.shauto.services;

import com.example.shauto.models.User;
import com.example.shauto.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(User user) {
        String rawPassword = user.getPassword();
        String hashedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Raw Password: " + rawPassword);
        System.out.println("Hashed Password to Store: " + hashedPassword);

        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "User registered successfully.";
    }

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println("User not found.");
            return false;
        }

        boolean matches = passwordEncoder.matches(password, user.getPassword());
        System.out.println("Password Matches: " + matches);
        return matches;
    }

    public User getUserDetails(String username) {
        return userRepository.findByUsername(username);
    }
}
