package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.ProductRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired ProductRepository repository;

    @Override
    public List<Product> findAll() {
        return repository.findAll();
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findBy( Long id ) {
        return repository.findById( id )
                .orElseThrow( RuntimeException::new );
    }

    @Override
    public Product update(Long id, Product product) {
        Product productToUpdate = findBy( id );
        productToUpdate.setDescription( product.getDescription() );
        productToUpdate.setPrice( product.getPrice() );
        productToUpdate.setImg( product.getImg() );
        productToUpdate.setAvailable( product.getAvailable() );
        productToUpdate.setCategory( product.getCategory() );

        return repository.save( productToUpdate );
    }

    @Override
    public void delete(Long id) {
        Product productToDelete = findBy( id );

        repository.delete( productToDelete );
    }

    @Override
    public void updateAvailableBy(Long id, boolean available) {
        Product product = findBy( id );
        product.setAvailable( available );

        repository.save( product );
    }

    @Override
    public List<Product> findByCategoryName(String name) {
        return repository.findByCategoryName(name);
    }

    @Override
    public List<Product> findByDescription(String description) {
        return repository.findByDescriptionContainingIgnoreCase( description );
    }

    @Override
    public List<Product> findByAvailable(Boolean available) {
        return repository.findByAvailable( available );
    }

    @Override
    public List<Product> findByDescriptionAndCategoryName(String description, String nameCategory) {
        return repository.findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCase( description, nameCategory );
    }

    @Override
    public List<Product> findByDescriptionAndCategoryNameAndAvailable(String description, String nameCategory, Boolean available) {
        return repository.findByDescriptionContainingIgnoreCaseAndCategoryNameContainingIgnoreCaseAndAvailable( description, nameCategory, available );
    }

}
