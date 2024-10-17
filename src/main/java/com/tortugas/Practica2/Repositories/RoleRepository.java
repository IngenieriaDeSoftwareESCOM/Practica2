package com.tortugas.Practica2.Repositories;

import com.tortugas.Practica2.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
