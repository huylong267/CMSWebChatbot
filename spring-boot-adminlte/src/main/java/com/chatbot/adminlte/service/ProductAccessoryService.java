package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.ProductAccessory;
import com.chatbot.adminlte.model.UserRole;
import com.chatbot.adminlte.repository.ProductAccessoryRepository;
import com.chatbot.adminlte.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductAccessoryService extends AbstractService<ProductAccessory, Long> {

    @Autowired
    private ProductAccessoryRepository productAccessoryRepository;

    @Override
    protected JpaRepository<ProductAccessory, Long> getRepository() {
        return productAccessoryRepository;
    }

}
