package com.chatbot.adminlte.service;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.repository.CategoryRepository;
import com.chatbot.adminlte.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductService extends AbstractService<Product, Long> {

    @Autowired
    private ProductRepository productRepository;

    @Override
    protected JpaRepository<Product, Long> getRepository() {
        return productRepository;
    }

}
