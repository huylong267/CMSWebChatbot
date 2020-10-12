package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Customers;
import com.chatbot.adminlte.repository.CategoryRepository;
import com.chatbot.adminlte.repository.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends AbstractService<Category, Long> {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    protected JpaRepository<Category, Long> getRepository() {
        return categoryRepository;
    }

}
