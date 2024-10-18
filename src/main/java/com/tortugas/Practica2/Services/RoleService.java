package com.tortugas.Practica2.Services;

import com.tortugas.Practica2.Models.UserRole;
import com.tortugas.Practica2.Repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    // Method to get all UserRole entries from the repository
    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }

    // Method to get all UserRole entries for a specific user
    public List<UserRole> getUserRoles(Long userId) {
        return userRoleRepository.findByUserId(userId);
    }

    // Method to get a specific UserRole by its ID
    public Optional<UserRole> getUserRoleById(Long id) {
        return userRoleRepository.findById(id);
    }

    // Method to create a new UserRole
    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    // Method to delete a UserRole by its ID
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id);
    }
}
