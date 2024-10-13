package com.tortugas.Practica2.Repositories;

import com.tortugas.Practica2.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define custom query methods here if needed
    Optional<User> findByEmail(String email);
}
