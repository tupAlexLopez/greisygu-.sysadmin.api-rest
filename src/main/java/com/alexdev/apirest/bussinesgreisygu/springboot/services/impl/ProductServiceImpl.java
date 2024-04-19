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
        return repository.findByDescriptionContaining( description );
    }

    @Override
    public List<Product> findByDescriptionAndCategoryName(String description, String nameCategory) {
        return repository.findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCase( description, nameCategory );
    }

    @Override
    public List<Product> findByCategoryName(String name) {
        return repository.findByCategoryName(name);
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }
    @Override
    public Product findById( Long id ) {
        return repository.findById( id )
                .orElseThrow( RuntimeException::new );
    }

    @Override
    public void delete(Long id) {
        Optional.of( findById( id ) )
                .ifPresent( product -> repository.delete( product ) );
    }
}
