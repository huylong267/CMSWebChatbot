package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.User;
import com.chatbot.adminlte.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<User, Long> {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected JpaRepository<User, Long> getRepository() {
        return userRepository;
    }

    public User findByUserName(String userName){
        try {
            return userRepository.findByUsername(userName);
        } catch (Exception e) {
            e.printStackTrace();
            return new User();
        }
    }

}
