package com.chatbot.adminlte.config;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ProductByIdConverter
        implements Converter<String, Product> {

    @Autowired
    private ProductService productService;



    @Override
    public Product convert(String id) {
        return productService.get(Long.parseLong(id));
    }
}