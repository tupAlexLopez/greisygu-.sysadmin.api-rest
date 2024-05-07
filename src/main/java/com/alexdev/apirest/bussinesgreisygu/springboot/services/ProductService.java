package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.ProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ProductService {
    Page<Product> findAll(Pageable pageable);
    Page<Product> filterBy(String description, String category, Boolean available, Pageable pageable);
    Product findBy( Long productId );

    void save(ProductRequest request);
    void update(Long id, ProductRequest request);
    void updateAvailableBy(Long id, boolean available );
    void delete(Long id);
    void deleteAllByCategory( Long categoryID );
}
