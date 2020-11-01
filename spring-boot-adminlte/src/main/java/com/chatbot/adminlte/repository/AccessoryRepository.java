package com.chatbot.adminlte.repository;

import com.chatbot.adminlte.model.Accessory;
import com.chatbot.adminlte.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccessoryRepository extends JpaRepository<Accessory, Long> {

    List<Accessory> findAccessoriesByProducts_Id( Long id);

    @Query(value = "select * from accessory where id in (:listId)",nativeQuery = true)
    List<Accessory> findByListAccessoryId(@Param("listId") List<Long> listId);
}