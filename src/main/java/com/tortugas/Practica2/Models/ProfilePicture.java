package com.tortugas.Practica2.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import com.tortugas.Practica2.Models.*;
import java.util.Base64;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.*;



@Entity
@Table(name = "Pictures")
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String largeImage;

    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String shortImage;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLargeImage(){
        return this.largeImage;
    }

    public String getShortImage(){
        return this.shortImage;
    }

    public void setLargeImage(String image){
        this.largeImage = image;
    }

    public void setShortImage(String image){
        this.shortImage = image;
    }

    public void setLargeImage(MultipartFile image){
        try{
            byte[] imageBytes = image.getBytes();
            this.largeImage = Base64.getEncoder().encodeToString(imageBytes);
        }catch(Exception ex){
            // TODO
        }
        
    }

    public void setShortImage(MultipartFile image){
        try{
            byte[] imageBytes = image.getBytes();
            this.largeImage = Base64.getEncoder().encodeToString(imageBytes);
        }catch(Exception ex){
            // TODO
        }
    }
    
    public void setUser(User user){
        this.user = user;
    }
    
    public User getUser(){
        return this.user;
    }
}
