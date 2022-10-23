package com.textaddict.userapi.repository;

import com.textaddict.userapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("select u from User u left join fetch u.roles r where u.email = ?1")
    User findByEmail(String email);

    @Query("select u from User u left join fetch u.roles r")
    List<User> findAllWithRole();
    @Query("select u from User u left join fetch u.roles r where u.id = ?1")
    User findByIdForRoleUpdate(UUID id);
}
