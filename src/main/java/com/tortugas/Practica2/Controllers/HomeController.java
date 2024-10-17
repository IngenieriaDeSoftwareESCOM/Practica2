package com.tortugas.Practica2.Controllers;

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

import java.util.List;



// The HomeController class is annotated as a REST controller, meaning it handles HTTP requests
@Controller
public class HomeController {
    private UserService _userService;
    private RoleService _roleService;
    private UserRoleService _userRoleService;
    private JwtTokenGeneratorSelf _tokenGenerator;
    @Autowired
    private CacheService _cacheService;
    public HomeController(UserService service){
        _service = service;
    }
    @GetMapping("/")
    public String index() {
        return "index";
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/users")
    public String users() {
        return "users";
    }
    @GetMapping("/roles")
    public String roles() {
        return "roles";
    }
    @GetMapping("/userroles")
    public String userroles() {
        return "userroles";
    }
}
