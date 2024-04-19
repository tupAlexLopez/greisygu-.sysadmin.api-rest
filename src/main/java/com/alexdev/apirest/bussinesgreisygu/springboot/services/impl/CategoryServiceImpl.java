package com.alexdev.apirest.bussinesgreisygu.springboot.services.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.CategoryRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        Category categorySaved = findById( id ).orElseThrow();
        categorySaved.setName(category.getName());

        return repository.save( categorySaved );
    }


    @Override
    public Optional<Category> findById(Long id) {
        return repository.findById( id );
    }

    @Override
    public void delete(Long id) {
        findById(id)
                .ifPresent( category -> repository.delete( category ));
    }
}
