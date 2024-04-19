package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;

import java.util.List;


public interface ProductService {
    List<Product> findAll();
    List<Product> findByDescription(String description);
    List<Product> findByDescriptionAndCategoryName(String description, String nameCategory);
    List<Product> findByCategoryName( String name);

    Product findById( Long id );
    Product save(Product product);
    void delete(Long id);
}
