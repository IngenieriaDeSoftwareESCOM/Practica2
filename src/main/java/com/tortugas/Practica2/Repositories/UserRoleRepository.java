package com.tortugas.Practica2.Repositories;

import com.tortugas.Practica2.Models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.Query;
import java.util.List;


import java.util.Optional;
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE user_id = :user_id AND role_id = :role_id", nativeQuery = true)
    void removeRoleByUserId(Long user_id, Long role_id);
    @Modifying
    @Query(value = "SELECT * FROM  user_roles WHERE user_id = :user_id", nativeQuery = true)
    List<UserRole> findAllByUser_Id(Long user_id);
}
