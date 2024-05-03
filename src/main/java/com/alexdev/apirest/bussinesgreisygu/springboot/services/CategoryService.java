package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    Category findBy(Long id);
    Category findBy(String name);

    void save(CategoryRequest category);
    void update(Long id, CategoryRequest category);
    void delete(Long id);
}
