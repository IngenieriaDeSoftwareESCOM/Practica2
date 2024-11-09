package com.tortugas.Practica2.Services;

import com.tortugas.Practica2.Models.*;
import com.tortugas.Practica2.Repositories.ProfilePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.util.List;
import java.util.Optional;

@Service
public class ProfilePictureService {

    @Autowired
    private ProfilePictureRepository profilePicute; // Injecting the profilePicutreRepository to interact with the database

    // Method to create a new profile picture entry in the repository
    public ProfilePicture createPicture(ProfilePicture picture){
        return profilePicute.save(picture);
    }

    // Method to retrieve a profilePicutre by their unique ID
    public Optional<ProfilePicture> getprofilePicutreById(Long id) {
        return profilePicute.findById(id); // Finding a profilePicutre record by ID
    }

    // Method to retrieve a profilePicutre by their unique ID
    public Optional<ProfilePicture> getprofilePicutreByUserId(Long id) {
        return profilePicute.findByUser_Id(id); // Finding a profilePicutre record by ID
    }

    // Method to delete a profilePicutre entry by its unique ID
    public void deletePicture(Long id) {
        profilePicute.deleteById(id); // Deleting the profilePicutre record with the specified ID
    }
}
