package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.UploadFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final UploadFileService uploadFile;

    @Autowired
    public ProductController(ProductService service, UploadFileService uploadFile) {
        this.service = service;
        this.uploadFile = uploadFile;
    }

    @GetMapping
    public List<Product> getAllproducts(){
        return service.findAll();
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product saveProduct(@RequestBody Product product) throws IOException {
        return service.save( product );
    }

    @PostMapping("/{id}/save-image")
    @ResponseStatus(HttpStatus.OK)
    public void saveImageProduct(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {
        Product product =Optional.of( service.findById( id ) )
                .orElseThrow( RuntimeException::new );

        String filename= uploadFile.insert( file );
        product.setImg( filename );
        service.save( product );
    }

    @GetMapping("/search")
    public List<Product> findByFilter(@RequestParam Map<String, String> params){
        if(params.isEmpty()) throw new RuntimeException();

        if(params.containsKey("description") && params.containsKey("category")){
           return service.findByDescriptionAndCategoryName( params.get("description"), params.get("category"));
        }

        return service.findByDescription( params.get("description") );
    }


    @GetMapping("/category/{name}")
    public List<Product> findProductCategoryByName(@PathVariable String name){
        return service.findByCategoryName( name );
    }

    @GetMapping("/{id}")
    public Product findProductById(@PathVariable Long id){
        return service.findById( id );
    }
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProductById( @PathVariable Long id ){
        Product product =service.findById( id );
        uploadFile.delete( product.getImg() );
        service.delete( id );
    }
}