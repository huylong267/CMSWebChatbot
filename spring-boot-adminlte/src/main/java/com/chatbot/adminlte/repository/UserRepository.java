package com.chatbot.adminlte.repository;


import com.chatbot.adminlte.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface UserRepository extends JpaRepository<User,Long> {
    @Transactional(readOnly = true)
    @Query(value = "select * from user  where user_name = :username", nativeQuery = true)
    User findByUsername(@Param("username") String username);

    @Query(value = "select * from user ", nativeQuery = true)
    ArrayList<User> getAll();

    @Query(value = "select * from user  where id = :id" , nativeQuery = true)
    User findUserById(@Param("id") long id);

    @Transactional(readOnly = true)
    @Query(value = "select * from user  where email = :email and  id <> :id", nativeQuery = true)
    User findByEmailCheckExist(@Param("email") String email, @Param("id") long id);

    @Transactional(readOnly = true)
    @Query(value = "select * from user  where user_name = :username and  id <> :id", nativeQuery = true)
    User findByUsernameCheckExist(@Param("username") String username, @Param("id") long id);
}
