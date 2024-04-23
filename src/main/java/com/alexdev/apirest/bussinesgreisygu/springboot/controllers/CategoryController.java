package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;


import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired CategoryService service;

    @GetMapping
    public List<Category> getAll(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Category postCategory(@RequestBody Category category){
        return service.save( category );
    }

    @PutMapping("/{id}")
    public Category updateCategory(@PathVariable Long id, @RequestBody Category category){
        return service.update( id, category );
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable Long id){
        return service.findBy( id );
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id){
        service.delete( id );
    }
    @GetMapping("/search")
    public Category findCategoryByName(@RequestParam String name){
        return service.findBy( name );
    }
}
