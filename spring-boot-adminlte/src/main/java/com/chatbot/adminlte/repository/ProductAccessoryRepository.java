package com.chatbot.adminlte.repository;


import com.chatbot.adminlte.model.ProductAccessory;
import com.chatbot.adminlte.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

public interface ProductAccessoryRepository extends JpaRepository<ProductAccessory,Long> {
    @Query(value = "select * from product_accessory", nativeQuery = true)
    ArrayList<ProductAccessory> findProductAccessories();
    @Query(value = "select * from product_accessory  where product_id=:productId", nativeQuery = true)
    ArrayList<ProductAccessory> findAccessoryIdByProductId(@Param("productId") long productId);

}
