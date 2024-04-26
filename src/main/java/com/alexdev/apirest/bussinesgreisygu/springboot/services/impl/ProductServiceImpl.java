package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.exceptions.NotFoundException;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.ProductRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired ProductRepository repository;

    @Override
    public Page<Product> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    public Page<Product> filterBy(String description, String category, Boolean available, Pageable pageable) {
        if( description != null && category != null && available != null ){
            return repository.findByDescriptionContainingIgnoreCaseAndCategoryNameAndAvailable( description, category , available, pageable );
        }else if( description != null && category != null  ){
            return repository.findByDescriptionContainingIgnoreCaseAndCategoryName( description, category , pageable);
        }else if( description != null && available != null  ){
            return repository.findByDescriptionContainingIgnoreCaseAndAvailable(description, available, pageable);
        }else if( category != null && available != null ) {
            return repository.findByCategoryNameAndAvailable( category, available, pageable );
        }

        if( description != null ){
            return repository.findByDescriptionContainingIgnoreCase(description, pageable);
        }
        if( category != null ) {
            return repository.findByCategoryName(category, pageable);
        }
        if( available != null ){
            return repository.findByAvailable(available, pageable);
        }

        throw new RuntimeException();
    }

    @Override
    public Product save(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findBy( Long id ) {
        return repository.findById( id )
                .orElseThrow( NotFoundException::new );
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
    public Page<Product> findByCategoryName(String name, Pageable pageable) {
        return repository.findByCategoryName( name, pageable );
    }

    @Override
    public Page<Product> findByAvailable(Boolean available, Pageable pageable) {
        return repository.findByAvailable( available, pageable );
    }
}
