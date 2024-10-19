package com.tortugas.Practica2.Services;

import com.tortugas.Practica2.Models.*;
import com.tortugas.Practica2.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public Optional<Role> getRoleById(Long id) {
        return roleRepository.findById(id);
    }

    public Role createRole(Role Role) {
        return roleRepository.save(Role);
    }

    public void deleteRole(Long id) {
        roleRepository.deleteById(id);
    }
}