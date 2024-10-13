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
public class RoleController {
    private RoleService _service;
    private JwtTokenGeneratorSelf _tokenGenerator;
    @Autowired
    private CacheService _cacheService;
    public RoleController(RoleService service){
        _service = service;
    }
    @PostMapping("api/Create/Role")
    public ResponseEntity<Role> registerRole(@RequestHeader("Authorization") String token, @RequestBody Role Role) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var created = _service.createRole(Role);
            return ResponseEntity.ok(Role);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/Get/Roles")
    public ResponseEntity<List<Role>> getRoles(@RequestHeader("Authorization") String token) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            var roles = _service.getAllRoles();
            return ResponseEntity.ok(roles);
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("api/Delete/Role/{id}")
    public ResponseEntity deleteRole(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try{
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            _service.deleteRole(id);
            return ResponseEntity.status(204).build();
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
    }
}