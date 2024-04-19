package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryName(String name);

    List<Product> findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCase(String description, String name);

    List<Product> findByDescriptionContaining(String description);
}
