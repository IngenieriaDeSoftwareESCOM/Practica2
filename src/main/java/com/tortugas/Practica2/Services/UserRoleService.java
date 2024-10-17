package com.tortugas.Practica2.Services;

import com.tortugas.Practica2.Models.*;
import com.tortugas.Practica2.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    public List<UserRole> getAll() {
        return userRoleRepository.findAll();
    }
    public void removeUserFromRole(Long user_id, Long role_id){
        userRoleRepository.removeRoleByUserId(user_id, role_id);   
    }
    public UserRole createUserRole(UserRole urole){
        return userRoleRepository.save(urole);
    }
    public List<UserRole> getUserRoles(Long user_id){
        return userRoleRepository.findAllByUser_Id(user_id);
    }
    public void deleteById(Long id){
        userRoleRepository.deleteById(id);
    }
}
