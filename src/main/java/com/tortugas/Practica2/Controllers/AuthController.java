package com.tortugas.Practica2.Controllers;

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



// The HomeController class is annotated as a REST controller, meaning it handles HTTP requests
@RestController
@RequestMapping("/")
public class AuthController {
    private UserService _service;
    private JwtTokenGeneratorSelf _tokenGenerator;
    @Autowired
    private CacheService _cacheService;
    public AuthController(UserService service){
        _service = service;
    }
    @PostMapping("api/Create/User")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try{
            var created = _service.createUser(user);
            return ResponseEntity.ok(user);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
        
    }
    @PutMapping("api/Update/User")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try{
            if(user.getId() == null || user.getId() == 0) return ResponseEntity.notFound().build();
            var userOld = _service.getUserById(user.getId());
            if(!userOld.isPresent()) return ResponseEntity.notFound().build();
            if(_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                var created = _service.createUser(user);
                return ResponseEntity.ok(user);
            }else{
                return ResponseEntity.status(401).build();
            }
            
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/Get/User/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var user = _service.getUserById(id);
            return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/Get/Users")
    public ResponseEntity<List<User>> getUserById(@RequestHeader("Authorization") String token) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var users = _service.getAllUsers();
            return ResponseEntity.ok(users);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @PostMapping("api/login")
    public ResponseEntity<String> getToken(@RequestBody User user) {
        String newToken = "";
        // Validate user input
        if (user == null || user.getEmail() == null || user.getEmail().isEmpty() || 
            user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var userDb = _service.getUserByEmail(user.getEmail());
        if(userDb.isPresent()){
            
            var us = userDb.get();
            if(us.getPassword().equals(user.getPassword())){                
                newToken = _tokenGenerator.generateToken(user.getEmail());
                _cacheService.addToCache(newToken, user.getEmail());
            }else{
                return ResponseEntity.status(401).build();
            }
        }
        else{
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(newToken);
    }
    
}
