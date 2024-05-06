package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;


import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.CategoryRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.response.MessageResponse;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final CategoryService service;

    @Autowired
    public CategoryController(CategoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<Category> getAll(){
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse postCategory(@RequestBody CategoryRequest categoryRequest){
        service.save( categoryRequest );

        return MessageResponse.builder()
                .message("Categoria correctamente creada.")
                .build();
    }

    @PutMapping("/{id}")
    public MessageResponse updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest){
        service.update( id, categoryRequest );

        return MessageResponse.builder()
                .message( "Categoria correctamente modificada." )
                .build();
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable Long id){
        return service.findBy( id );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long id){
        service.delete( id );
    }

    @GetMapping("/search")
    public Category findCategoryByName(@RequestParam String name){
        return service.findBy( name );
    }
}
