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


import com.tortugas.Practica2.Models.*;
import com.tortugas.Practica2.Repositories.*;
import com.tortugas.Practica2.Services.*;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;


// Mark this class as a REST controller that handles HTTP requests
@RestController
@RequestMapping("/") // Set the base URL for this controller
public class UserController {

    // Declare services used within the controller
    private UserService _service;
    private ProfilePictureService _pictureService;
    private JwtTokenGeneratorSelf _tokenGenerator;
    @Autowired
    private CacheService _cacheService;

    // Constructor for dependency injection of the UserService
    public UserController(UserService service, ProfilePictureService pictureService){
        _service = service;
        _pictureService = pictureService;
    }

    // Endpoint to create a new user using POST
    @PostMapping("api/Create/User")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try{
            // Call the service to create the user
            var created = _service.createUser(user);
            // Return success with the created user
            return ResponseEntity.ok(user);
        } catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }

    // Endpoint to update a user using PUT
    @PutMapping("api/Update/User")
    public ResponseEntity<User> updateUser(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try{
            // Check if the user ID is valid
            if(user.getId() == null || user.getId() == 0) return ResponseEntity.notFound().build();

            // Get the existing user by ID
            var userOld = _service.getUserById(user.getId());
            if(!userOld.isPresent()) return ResponseEntity.notFound().build();

            // Check if the token is valid by looking in the cache
            if(_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                // Update the user if valid
                var created = _service.createUser(user);
                return ResponseEntity.ok(user);
            } else {
                // Return unauthorized if token is invalid
                return ResponseEntity.status(401).build();
            }
        } catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }

    // Endpoint to get a user by ID using GET
    @GetMapping("api/Get/User/{id}")
    public ResponseEntity<User> getUserById(@RequestHeader("Authorization") String token, @PathVariable Long id) {
        try{
            // Validate the token
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            // Retrieve the user by ID
            var user = _service.getUserById(id);
            // Return the user if found, otherwise return not found
            return user.isPresent() ? ResponseEntity.ok(user.get()) : ResponseEntity.notFound().build();
        } catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }

    // Endpoint to get all users using GET
    @GetMapping("api/Get/Users")
    public ResponseEntity<List<User>> getUserById(@RequestHeader("Authorization") String token) {
        try{
            // Validate the token
            if(!_cacheService.isKeyInCache(token.replace("Bearer ", ""))){
                return ResponseEntity.status(401).build();
            }
            // Get all users from the service
            var users = _service.getAllUsers();
            return ResponseEntity.ok(users);
        } catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }
    @GetMapping("api/Get/User/Picture")
    public ResponseEntity<String> getImageString(@RequestHeader("Authorization") String token){
        try{
            String email = _cacheService.getFromCache(token);
            var user = _service.getUserByEmail(email);
            if (user.isPresent()) {
                var photo = _pictureService.getprofilePicutreByUserId(user.get().getId());
                if(photo.isPresent()){
                    return ResponseEntity.ok(photo.get().getLargeImage());
                }
                return ResponseEntity.ok("");
            }
            return ResponseEntity.badRequest().build();
        }catch(Exception ex){
            return ResponseEntity.internalServerError().build();
        }
        
    }
    @PostMapping("api/Set/User/Picture")
    public ResponseEntity<ProfilePicture> uploadUserPicture(@RequestParam("image") MultipartFile image, @RequestParam("id") Long id){
        try{
            var user = _service.getUserById(id);
            if(user.isPresent()){
                ProfilePicture picture = new ProfilePicture();
                var old = _pictureService.getprofilePicutreByUserId(id);
                if(old.isPresent()){
                    picture.setId(old.get().getId());
                }
                picture.setLargeImage(image);
                picture.setShortImage(image);
                picture.setUser(user.get());
                picture = _pictureService.createPicture(picture);
                return ResponseEntity.ok(picture);
            }else{
                return ResponseEntity.badRequest().build();
            }
        }
        catch(Exception ex) {
            // Return internal server error in case of an exception
            return ResponseEntity.internalServerError().build();
        }
    }
}
