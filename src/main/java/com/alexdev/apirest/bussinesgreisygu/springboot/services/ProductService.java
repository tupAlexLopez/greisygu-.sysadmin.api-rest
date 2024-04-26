package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Page<Product> filterBy(String description, String category, Boolean available, Pageable pageable);
    Page<Product> findByCategoryName( String name, Pageable pageable);
    Page<Product> findByAvailable(Boolean available, Pageable pageable);
    Product findBy( Long productId );

    Product save(Product product);
    Product update(Long id, Product productUpdated);
    void updateAvailableBy(Long id, boolean available );
    void delete(Long id);
}
