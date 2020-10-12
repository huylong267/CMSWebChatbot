package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.UserRole;
import com.chatbot.adminlte.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserRoleService extends AbstractService<UserRole, Long> {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    protected JpaRepository<UserRole, Long> getRepository() {
        return userRoleRepository;
    }

}
