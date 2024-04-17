package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;

import java.util.List;
import java.util.Optional;

public class ProductServiceImpl implements ProductService {

    @Override
    public List<Product> findAll() {
        return List.of();
    }

    @Override
    public Product save() {
        return null;
    }

    @Override
    public Optional<Product> findProduct(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
