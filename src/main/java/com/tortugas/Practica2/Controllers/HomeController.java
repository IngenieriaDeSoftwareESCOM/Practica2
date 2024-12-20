package com.tortugas.Practica2.Controllers;

// Import necessary components from Spring and Java
import org.springframework.stereotype.Controller;
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

import jakarta.servlet.http.HttpServletRequest;



import java.util.List;

// The HomeController class is annotated as a controller that handles HTTP requests
@Controller
public class HomeController {
    
    // Declare services for handling user-related operations
    private UserService _userService;
    private RoleService _roleService;
    private UserRoleService _userRoleService;
    private JwtTokenGeneratorSelf _tokenGenerator;

    // Autowired CacheService for caching operations
    @Autowired
    private CacheService _cacheService;

    // Constructor injection of UserService
    public HomeController(UserService service, RoleService roleService, UserRoleService userRoleService){
        _userService = service;
        _roleService = roleService;
        _userRoleService = userRoleService;
    }

    // Mapping to return the "index" view
    @GetMapping("/")
    public String index() {
        return "index"; // Returns the "index" HTML view
    }

    // Mapping to return the "login" view
    @GetMapping("/login")
    public String login() {
        return "login"; // Returns the "login" HTML view
    }

    // Mapping to return the "users" view
    @GetMapping("/users")
    public String users(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String proxyIpAddress = request.getHeader("X-Forwarded-For");
        if (proxyIpAddress != null) {
            ipAddress = proxyIpAddress.split(",")[0]; // First IP is the client's real IP
        }
        if (!_cacheService.isKeyInCache(ipAddress)) {
            return "unauth"; // Unauthenticated view
        } else {
            String token = _cacheService.getFromCache(ipAddress);
            String email = _cacheService.getFromCache(token);
            var user = _userService.getUserByEmail(email);
            if (user.isPresent()) {
                return "users"; // Authenticated view
            }
        }
        return "unauth"; // Unauthenticated view
    }


    // Mapping to return the "roles" view
    @GetMapping("/roles")
    public String roles(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String proxyIpAddress = request.getHeader("X-Forwarded-For");
        if (proxyIpAddress != null) {
            ipAddress = proxyIpAddress.split(",")[0]; // First IP is the client's real IP
        }
        if (!_cacheService.isKeyInCache(ipAddress)) {
            return "unauth"; // Unauthenticated view
        } else {
            String token = _cacheService.getFromCache(ipAddress);
            String email = _cacheService.getFromCache(token);
            var user = _userService.getUserByEmail(email);
            if (user.isPresent()) {
                return "roles"; // Authenticated view
            }
        }
        return "unauth"; // Unauthenticated view
    }

    // Mapping to return the "userroles" view
    @GetMapping("/userroles")
    public String userroles(HttpServletRequest request) {
        String ipAddress = request.getRemoteAddr();
        String proxyIpAddress = request.getHeader("X-Forwarded-For");
        if (proxyIpAddress != null) {
            ipAddress = proxyIpAddress.split(",")[0]; // First IP is the client's real IP
        }
        if (!_cacheService.isKeyInCache(ipAddress)) {
            return "unauth"; // Unauthenticated view
        } else {
            String token = _cacheService.getFromCache(ipAddress);
            String email = _cacheService.getFromCache(token);
            var user = _userService.getUserByEmail(email);
            if (user.isPresent()) {
                return "userroles"; // Authenticated view
            }
        }
        return "unauth"; // Unauthenticated view
    }
}
