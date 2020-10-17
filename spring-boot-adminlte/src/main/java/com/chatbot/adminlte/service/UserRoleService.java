package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.UserRole;
import com.chatbot.adminlte.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
public class UserRoleService extends AbstractService<UserRole, Long> {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    protected JpaRepository<UserRole, Long> getRepository() {
        return userRoleRepository;
    }

    public List<UserRole> findRoleIdByUserId(long userId) {
        return userRoleRepository.findRoleIdByUserId(userId);
    }


    @Transactional
    public boolean insertUserRoleWithRoleName(String name, Long userId) {
        try {
            String sql= "INSERT INTO user_role (user_id, role_id ,status) VALUES (:userId,(SELECT id  FROM role WHERE NAME =:name),1)";
            Query query = entityManager.createNativeQuery(sql);
            query.setParameter("userId",userId);
            query.setParameter("name",name);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
