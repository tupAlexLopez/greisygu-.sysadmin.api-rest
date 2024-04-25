package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;

    @Autowired
    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public Page<Product> getAllproducts(@PageableDefault(size = 5) Pageable page){
        return service.findAll( page );
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product) {
        return service.save( product );
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return service.findBy( id );
    }

    @PutMapping("/{id}")
    public Product updateProduct( @PathVariable Long id, @RequestBody Product product ){
        return service.update( id, product );
    }

    @PatchMapping("/{id}/{available}")
    public void updateAvailableProductBy( @PathVariable Long id, @PathVariable boolean available ){
        service.updateAvailableBy( id, available );
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductBy( @PathVariable Long id ){
        service.delete( id );
    }


    @GetMapping("/category/{name}")
    public List<Product> findProductCategoryByName(@PathVariable String name){
        return service.findByCategoryName( name );
    }

    @GetMapping("/available/{available}")
    public List<Product> findProductCategoryByName(@PathVariable Boolean available){
        return service.findByAvailable( available );
    }

    @GetMapping("/search")
    public List<Product> findByFilter(@RequestParam Map<String, String> params) {
        if (params.containsKey("description") && params.containsKey("category") && params.containsKey("available")) {
            return service.findByDescriptionAndCategoryNameAndAvailable(params.get("description"), params.get("category"), Boolean.valueOf(params.get("available")));
        }else if( params.containsKey("description") && params.containsKey("category") ){
            return service.findByDescriptionAndCategoryName( params.get("description"), params.get("category") );
        }


        return service.findByDescription(params.get("description"));
    }
}