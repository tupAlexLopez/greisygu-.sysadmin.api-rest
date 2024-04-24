package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailable( Boolean available );
    List<Product> findByCategoryName(String name);
    List<Product> findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(String description, String name);
    List<Product> findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCaseAndAvailable(String description, String name, Boolean available);
    List<Product> findByDescriptionContainingIgnoreCase(String description);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Product p WHERE p.category_id = :id", nativeQuery = true)
    void deleteAllProductsByCategoryId( Long id );
}
