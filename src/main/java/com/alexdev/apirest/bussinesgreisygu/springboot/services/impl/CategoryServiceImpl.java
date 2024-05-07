package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.exceptions.NotFoundException;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.CategoryRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.CategoryRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.ProductRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl( CategoryRepository repository ) {
        this.repository = repository;
    }

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public void save(CategoryRequest categoryRequest) {
        repository.save( Category.builder().name(categoryRequest.getName() ).build() );
    }

    @Override
    public void update(Long id, CategoryRequest categoryRequest) {
        Optional.of( findBy( id ) )
            .ifPresent( category -> {
                category.setName( categoryRequest.getName() );
                repository.save( category );
            });
    }

    @Override
    public Category findBy(String name) {
        return repository.findByName( name )
                .orElseThrow( NotFoundException::new );
    }
    @Override
    public Category findBy(Long id) {
        return repository.findById( id )
                .orElseThrow( NotFoundException::new );
    }

    @Override
    public void delete(Long id) {
        Optional.of( findBy(id) )
                .ifPresent( category -> {
                    repository.deleteById( category.getId() );
                });
    }

}
