package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;

import java.util.List;


public interface ProductService {
    List<Product> findAll();
    List<Product> findByDescription( String description );
    List<Product> findByDescriptionContaining( String description );
    Product save(Product product);
    Product findProduct(Long id);
    void delete(Long id);
}
