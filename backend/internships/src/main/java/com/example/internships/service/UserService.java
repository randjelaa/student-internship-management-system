package com.example.internships.service;

import com.example.internships.dto.enums.Role;
import com.example.internships.entity.User;
import com.example.internships.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = getUserById(id);

        existing.setEmail(updatedUser.getEmail());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isBlank()) {
            existing.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
        }

        existing.setRole(updatedUser.getRole());
        existing.setActive(updatedUser.getActive());

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        User user = getUserById(id);

        userRepository.delete(user);
    }

    public User createUser(String email, String password, Role role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setActive(true);

        return userRepository.save(user);
    }

    public void validateEmailUnique(String email, Long currentUserId) {
        userRepository.findByEmail(email)
                .filter(u -> !u.getId().equals(currentUserId))
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists");
                });
    }

    public void updatePassword(User user, String rawPassword) {
        user.setPassword(passwordEncoder.encode(rawPassword));
    }
}