package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.ProductRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Product> findByDescription(String description) {
        return repository.findByDescription( description );
    }

    @Override
    public List<Product> findByDescriptionContaining(String description) {
        return repository.findByDescriptionContaining( description );
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findProduct(Long id) {
        return repository.findById(id)
                .orElseThrow( RuntimeException::new );
    }

    @Override
    public void delete(Long id) {
        Optional.of( findProduct( id ) )
                .ifPresent( product -> repository.delete( product ) );
    }
}
