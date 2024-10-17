package com.tortugas.Practica2.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tortugas.Practica2.Models.Role;
import com.tortugas.Practica2.Models.UserRole;
import com.tortugas.Practica2.Repositories.*;
import com.tortugas.Practica2.Services.*;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;



// The HomeController class is annotated as a REST controller, meaning it handles HTTP requests
@RestController
@RequestMapping("/")
public class UserRoleController {
    private RoleService _service;
    private UserService _userService;
    private UserRoleService _userRoleService;
    private JwtTokenGeneratorSelf _tokenGenerator;
    @Autowired
    private CacheService _cacheService;
    public UserRoleController(RoleService service, UserService uservice, UserRoleService urservice){
        _service = service;
        _userService = uservice;
        _userRoleService = urservice;
    }
    @PostMapping("api/Create/User/Role")
    public ResponseEntity<UserRole> registerUserToRole(@RequestHeader("Authorization") String token, @RequestBody UserRole userRole) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var user = userRole.getUser();
            var role = userRole.getRole();
            var existsUser = _userService.getUserById(user.getId());
            var existsRole = _service.getRoleById(role.getId());
            if(existsUser.isPresent()){
                if(existsRole.isPresent()){
                    userRole.setRole(existsRole.get());
                    userRole.setUser(existsUser.get());
                    var created = _userRoleService.createUserRole(userRole);
                    return ResponseEntity.ok(created);
                }
            }
            return ResponseEntity.badRequest().build();
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("api/Remove/User/Role/{id}")
    public ResponseEntity removeRoleFromUser(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            _userRoleService.deleteById(id);
            return ResponseEntity.ok().build();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/Get/User/{id}/Roles")
    public ResponseEntity<List<UserRole>> getUserRoles(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var t = _userRoleService.getUserRoles(id);
            return ResponseEntity.ok(t);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/Get/Users/Roles")
    public ResponseEntity<List<UserRole>> getUsersRoles(@RequestHeader("Authorization") String token){
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var t = _userRoleService.getAll();
            return ResponseEntity.ok(t);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}