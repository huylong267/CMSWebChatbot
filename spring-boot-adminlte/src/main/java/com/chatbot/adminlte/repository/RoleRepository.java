package com.chatbot.adminlte.repository;

import com.chatbot.adminlte.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String name);

    @Query(value = "select * from role  where id = :id", nativeQuery = true)
    Role findRoleById(@Param("id") int id);

    @Query(value = "SELECT * FROM  role WHERE  id NOT IN (SELECT role_id FROM user_role  WHERE user_id =:userId)",nativeQuery = true)
    List<Role> findRoleNotExistOfUser(@Param("userId") int userId);

    @Query(value = "SELECT * FROM  role WHERE  id  IN (SELECT role_id FROM user_role  WHERE user_id =:userId)",nativeQuery = true)
    List<Role> findRoleExistOfUser(@Param("userId") int userId);


}
