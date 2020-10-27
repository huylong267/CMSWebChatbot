package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.repository.RoleRepository;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService extends AbstractService<Role, Long> {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private EntityManager entityManager;

    @Override
    protected JpaRepository<Role, Long> getRepository() {
        return roleRepository;
    }


    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    public List<Role> findRoleNotExistOfUser(Long id) {
        return roleRepository.findRoleNotExistOfUser(id);
    }

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public boolean deleteRoleExist(List<String> roleList, long userId) {
        try {
            String sql ="delete ur from user_role   ur left join role  r on ur.role_id = r.id where ur.user_id = :userId and r.name not  in (:roleList)";
            Query query = (Query) entityManager.createNativeQuery(sql);
            query.setParameterList("roleList",roleList);
            query.setParameter("userId",userId);
            query.executeUpdate();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return  false;
        }

    }


    public List<Role> getRoleNameExistOfUser(List<String> roleList,Long userId) {
        try {
            List<Role> roleExist = roleRepository.findRoleExistOfUser(userId);
            return roleExist;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }


}
