package com.chatbot.adminlte.repository;


import com.chatbot.adminlte.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    @Query(value = "select * from user_role", nativeQuery = true)
    ArrayList<UserRole> findUserRole();
    @Query(value = "select * from user_role  where user_id=:userId", nativeQuery = true)
    ArrayList<UserRole> findRoleIdByUserId(@Param("userId") long userId);

}
