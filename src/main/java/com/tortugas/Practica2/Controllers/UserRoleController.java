package com.tortugas.Practica2.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.tortugas.Practica2.Models.Role;
import com.tortugas.Practica2.Models.UserRole;
import com.tortugas.Practica2.Services.*;

import java.util.List;

// The UserRoleController class is annotated as a REST controller, meaning it handles HTTP requests related to the User-Role relationships
@RestController
@RequestMapping("/")
public class UserRoleController {
    private RoleService _service;                // Service for role management
    private UserService _userService;            // Service for user management
    private UserRoleService _userRoleService;    // Service for user-role relationship management
    private JwtTokenGeneratorSelf _tokenGenerator; // Token generator service

    @Autowired
    private CacheService _cacheService; // Cache service for token validation

    // Constructor for injecting required services
    public UserRoleController(RoleService service, UserService uservice, UserRoleService urservice) {
        _service = service;
        _userService = uservice;
        _userRoleService = urservice;
    }

    // POST endpoint for assigning a role to a user
    @PostMapping("api/Create/User/Role")
    public ResponseEntity<UserRole> registerUserToRole(@RequestHeader("Authorization") String token, @RequestBody UserRole userRole) {
        try {
            // Validate token by checking the cache
            if (!_cacheService.isKeyInCache(token.replace("Bearer ", ""))) {
                return ResponseEntity.status(401).build(); // Unauthorized if token is not valid
            }

            // Get user and role from the UserRole request body
            var user = userRole.getUser();
            var role = userRole.getRole();

            // Check if the user and role exist
            var existsUser = _userService.getUserById(user.getId());
            var existsRole = _service.getRoleById(role.getId());

            // If both user and role exist, create the user-role association
            if (existsUser.isPresent() && existsRole.isPresent()) {
                userRole.setUser(existsUser.get());
                userRole.setRole(existsRole.get());
                var created = _userRoleService.createUserRole(userRole);
                return ResponseEntity.ok(created); // Return the created user-role relationship
            }

            return ResponseEntity.badRequest().build(); // Return 400 Bad Request if user or role doesn't exist
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error in case of exception
        }
    }

    // DELETE endpoint for removing a role from a user
    @DeleteMapping("api/Remove/User/Role/{id}")
    public ResponseEntity removeRoleFromUser(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            // Validate token by checking the cache
            if (!_cacheService.isKeyInCache(token.replace("Bearer ", ""))) {
                return ResponseEntity.status(401).build(); // Unauthorized if token is not valid
            }

            // Delete the user-role relationship by ID
            _userRoleService.deleteById(id);
            return ResponseEntity.ok().build(); // Return 200 OK on successful deletion
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error in case of exception
        }
    }

    // GET endpoint for retrieving roles assigned to a specific user by user ID
    @GetMapping("api/Get/User/{id}/Roles")
    public ResponseEntity<List<UserRole>> getUserRoles(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try {
            // Validate token by checking the cache
            if (!_cacheService.isKeyInCache(token.replace("Bearer ", ""))) {
                return ResponseEntity.status(401).build(); // Unauthorized if token is not valid
            }

            // Get all roles assigned to the user
            var userRoles = _userRoleService.getUserRoles(id);
            return ResponseEntity.ok(userRoles); // Return the list of user roles
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error in case of exception
        }
    }

    // GET endpoint for retrieving all user-role relationships
    @GetMapping("api/Get/Users/Roles")
    public ResponseEntity<List<UserRole>> getUsersRoles(@RequestHeader("Authorization") String token) {
        try {
            // Validate token by checking the cache
            if (!_cacheService.isKeyInCache(token.replace("Bearer ", ""))) {
                return ResponseEntity.status(401).build(); // Unauthorized if token is not valid
            }

            // Get all user-role relationships
            var allUserRoles = _userRoleService.getAll();
            return ResponseEntity.ok(allUserRoles); // Return the list of all user-role relationships
        } catch (Exception ex) {
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error in case of exception
        }
    }
}
