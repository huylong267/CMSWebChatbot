package com.chatbot.adminlte.repository;

import com.chatbot.adminlte.model.Category;
import com.chatbot.adminlte.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query(value = "select * from product where status <> -1",nativeQuery = true)
    List<Product> findWithOutStatusDelete();
    @Query(value = "select * from product where status <> -1 and category_id = :categoryId",nativeQuery = true)
    List<Product> findByCategoryAndStatus(@Param("categoryId") long categoryId);
}
