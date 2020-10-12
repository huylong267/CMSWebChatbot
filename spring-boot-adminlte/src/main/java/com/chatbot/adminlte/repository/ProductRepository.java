package com.chatbot.adminlte.repository;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
