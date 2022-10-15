package com.textaddict.userapi.repository;

import com.textaddict.userapi.model.Role;
import com.textaddict.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    @Query("select u from Role u where u.role = ?1")
    Role findByRoleName(String role);
}
