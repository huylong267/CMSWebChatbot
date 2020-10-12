package com.chatbot.adminlte.config;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import com.chatbot.adminlte.repository.CategoryRepository;
import com.chatbot.adminlte.service.CategoryService;
import com.chatbot.adminlte.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CategoryByIdConverter
        implements Converter<String, Category> {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @Override
    public Category convert(String id) {
        return categoryService.get(Long.parseLong(id));
    }

}