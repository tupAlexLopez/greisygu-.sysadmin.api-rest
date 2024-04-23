package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAvailable( Boolean available );
    List<Product> findByCategoryName(String name);
    List<Product> findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(String description, String name);
    List<Product> findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCaseAndAvailable(String description, String name, Boolean available);

    List<Product> findByDescriptionContainingIgnoreCase(String description);
}
