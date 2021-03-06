package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.model.Role;
import com.chatbot.adminlte.repository.AccessoryRepository;
import com.chatbot.adminlte.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccessoryService extends AbstractService<Accessory, Long> {

    @Autowired
    private AccessoryRepository accessoryRepository;

    @Override
    protected JpaRepository<Accessory, Long> getRepository() {
        return accessoryRepository;
    }

    public List<Accessory> findByProducts_Id( Long id){
        return accessoryRepository.findAccessoriesByProducts_Id(id);
    }

    public List<Accessory>findByListAccessoryId( List<Long> listId){
        return  accessoryRepository.findByListAccessoryId(listId);
    }
}
