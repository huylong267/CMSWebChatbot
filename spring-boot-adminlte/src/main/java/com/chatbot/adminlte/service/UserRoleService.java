package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.model.UserRole;
import com.chatbot.adminlte.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService extends AbstractService<UserRole, Long> {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    protected JpaRepository<UserRole, Long> getRepository() {
        return userRoleRepository;
    }

    public List<UserRole> findRoleIdByUserId(long userId) {
        return userRoleRepository.findRoleIdByUserId(userId);
    }
}
