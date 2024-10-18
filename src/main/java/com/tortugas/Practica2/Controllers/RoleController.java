package com.tortugas.Practica2.Controllers;

// Import necessary components from Spring and Java
import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tortugas.Practica2.Models.Role;
import com.tortugas.Practica2.Repositories.*;
import com.tortugas.Practica2.Services.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

// The RoleController class is annotated as a REST controller, meaning it handles HTTP requests related to Role management
@RestController
@RequestMapping("/")
public class RoleController {
    // Declare services for handling role-related and user-related operations
    private RoleService _service;
    private UserService _userService;
    private JwtTokenGeneratorSelf _tokenGenerator;

    // Autowired CacheService for caching operations
    @Autowired
    private CacheService _cacheService;

    // Constructor injection of RoleService and UserService
    public RoleController(RoleService service, UserService uservice){
        _service = service;
        _userService = uservice;
    }

    // POST endpoint for creating a new role
    @PostMapping("api/Create/Role")
    public ResponseEntity<Role> registerRole(@RequestHeader("Authorization") String token, @RequestBody Role role) {
        try{
            // Check if token is valid by looking it up in the cache
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build(); // Return 401 Unauthorized if token is invalid
            }
            // Create the new role using the RoleService
            var created = _service.createRole(role);
            return ResponseEntity.ok(created); // Return the created role
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error if something goes wrong
        }
    }

    // GET endpoint for retrieving all roles
    @GetMapping("api/Get/Roles")
    public ResponseEntity<List<Role>> getRoles(@RequestHeader("Authorization") String token) {
        try{
            // Check if token is valid by looking it up in the cache
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build(); // Return 401 Unauthorized if token is invalid
            }
            // Get all roles using the RoleService
            var roles = _service.getAllRoles();
            return ResponseEntity.ok(roles); // Return the list of roles
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error if something goes wrong
        }
    }

    // DELETE endpoint for deleting a role by ID
    @DeleteMapping("api/Delete/Role/{id}")
    public ResponseEntity deleteRole(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try{
            // Check if token is valid by looking it up in the cache
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build(); // Return 401 Unauthorized if token is invalid
            }
            // Delete the role by ID using the RoleService
            _service.deleteRole(id);
            return ResponseEntity.status(204).build(); // Return 204 No Content to indicate successful deletion
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build(); // Return 500 Internal Server Error if something goes wrong
        }
    }
}
