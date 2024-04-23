package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.exceptions.NotFoundException;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.CategoryRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired CategoryRepository repository;

    @Override
    public List<Category> findAll() {
        return repository.findAll();
    }

    @Override
    public Category save(Category category) {
        return repository.save(category);
    }

    @Override
    public Category update(Long id, Category category) {
        Category categorySaved = findBy( id );
        categorySaved.setName(category.getName());

        return repository.save( categorySaved );
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
        Category categoryToDelete = findBy( id );

        repository.delete( categoryToDelete );
    }
}
