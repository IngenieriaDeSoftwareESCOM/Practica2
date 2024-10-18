package com.tortugas.Practica2.Services;

import com.tortugas.Practica2.Models.UserRole;
import com.tortugas.Practica2.Repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository; // Injecting the UserRoleRepository to interact with the database

    // Method to get all UserRole entries from the repository
    public List<UserRole> getAll() {
        return userRoleRepository.findAll(); // Fetching all UserRole records
    }

    // Method to remove a user from a specific role based on user and role IDs
    public void removeUserFromRole(Long user_id, Long role_id) {
        userRoleRepository.removeRoleByUserId(user_id, role_id); // Removing the role association for the user
    }

    // Method to create a new UserRole association
    public UserRole createUserRole(UserRole urole) {
        return userRoleRepository.save(urole); // Saving the new UserRole entry in the database
    }

    // Method to get all roles assigned to a specific user by their user ID
    public List<UserRole> getUserRoles(Long user_id) {
        return userRoleRepository.findAllByUser_Id(user_id); // Fetching roles for the given user ID
    }

    // Method to delete a UserRole association by its ID
    public void deleteById(Long id) {
        userRoleRepository.deleteById(id); // Deleting the UserRole entry with the specified ID
    }
}
