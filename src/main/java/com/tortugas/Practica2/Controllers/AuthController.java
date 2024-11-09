package com.tortugas.Practica2.Controllers;

// Import necessary Spring and Java components
import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tortugas.Practica2.Models.User;
import com.tortugas.Practica2.Repositories.*;
import com.tortugas.Practica2.Services.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;



// Mark this class as a REST controller that handles HTTP requests
@RestController
@RequestMapping("/") // Set the base URL for this controller
public class AuthController {

    // Declare services used within the controller
    private UserService _service;
    private JwtTokenGeneratorSelf _tokenGenerator;
    @Autowired
    private CacheService _cacheService;

    // Constructor for dependency injection of the UserService
    public AuthController(UserService service){
        _service = service;
    }

    // Endpoint for login, returns a JWT token using POST
    @PostMapping("api/login")
    public ResponseEntity<String> getToken(@RequestBody User user, HttpServletRequest request) {
        String newToken = "";
        // Validate that the input user data is not null or empty
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty() || 
            user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Find user by email in the database
        var userDb = _service.getUserByEmail(user.getEmail());
        if(userDb.isPresent()){
            var us = userDb.get();
            // Check if the password is correct
            if(us.getPassword().equals(user.getPassword())){                
                // Generate token if password is correct
                newToken = _tokenGenerator.generateToken(user.getEmail());
                // Store the token in the cache
                _cacheService.addToCache(newToken, user.getEmail());
                String ipAddress = request.getRemoteAddr();
                String proxyIpAddress = request.getHeader("X-Forwarded-For");
                if (proxyIpAddress != null) {
                    ipAddress = proxyIpAddress.split(",")[0]; // First IP is the client's real IP
                }
                _cacheService.addToCache(ipAddress, newToken);
            } else {
                // Return unauthorized if the password is incorrect
                return ResponseEntity.status(401).build();
            }
        } else {
            // Return bad request if user is not found
            return ResponseEntity.badRequest().build();
        }
        // Return the generated token
        return ResponseEntity.ok(newToken);
    }

    // Endpoint to test if the token is valid using GET
    @GetMapping("api/Test/Token")
    public ResponseEntity testToken(@RequestHeader("Authorization") String token) {
        try{
            // Check if the token exists in the cache
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            // Return OK if the token is valid
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        try{
            // Check if the token exists in the cache
            String ipAddress = request.getRemoteAddr();
            String proxyIpAddress = request.getHeader("X-Forwarded-For");
            if (proxyIpAddress != null) {
                ipAddress = proxyIpAddress.split(",")[0]; // First IP is the client's real IP
            }
            _cacheService.removeFromCache(ipAddress);
            // Return OK if the token is valid
            return ResponseEntity.ok().build();
        } catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }
    
}
