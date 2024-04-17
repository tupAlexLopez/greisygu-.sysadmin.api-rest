package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;


    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public List<Product> getAllproducts(){
        return service.findAll();
    }

    @GetMapping("/search")
    public List<Product> findByName(@RequestParam String description){
        return service.findByDescriptionContaining( description );
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product){
        return service.save(product);
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return service.findProduct( id );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById( @PathVariable Long id ){
       service.delete( id );
    }
}