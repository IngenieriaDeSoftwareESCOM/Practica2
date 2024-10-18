package com.tortugas.Practica2.Services;

import com.tortugas.Practica2.Models.User;
import com.tortugas.Practica2.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository; // Injecting the UserRepository to interact with the database

    // Method to retrieve all User entries from the repository
    public List<User> getAllUsers() {
        return userRepository.findAll(); // Fetching all User records
    }

    // Method to retrieve a User by their email address
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email); // Finding a User record by email
    }

    // Method to retrieve a User by their unique ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id); // Finding a User record by ID
    }

    // Method to create a new User entry in the repository
    public User createUser(User user) {
        return userRepository.save(user); // Saving the new User in the database
    }

    // Method to delete a User entry by its unique ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id); // Deleting the User record with the specified ID
    }
}
