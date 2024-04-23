package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category save(Category category);
    Category update(Long id, Category category);

    Category findBy(Long id);
    Category findBy(String name);

    void delete(Long id);
}
