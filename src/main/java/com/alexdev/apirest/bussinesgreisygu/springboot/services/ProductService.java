package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    
    List<Product> findByDescription(String description);
    List<Product> findByCategoryName( String name);
    List<Product> findByDescriptionAndCategoryName(String description, String nameCategory);
    List<Product> findByDescriptionAndCategoryNameAndAvailable(String description, String nameCategory, Boolean available);
    List<Product> findByAvailable(Boolean available);
    Product findBy( Long productId );

    Product save(Product product);
    Product update(Long id, Product productUpdated);
    void updateAvailableBy(Long id, boolean available );
    void delete(Long id);
}
