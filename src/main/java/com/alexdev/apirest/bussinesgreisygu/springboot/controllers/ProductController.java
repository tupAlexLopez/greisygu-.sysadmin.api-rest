package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping
    public List<Product> getAllproducts(){
        return List.of(
                Product.builder()
                        .id(1L)
                        .description("Producto 1")
                        .stock(10)
                        .available(true)
                        .category(new Category(1L, "Producto Categoria"))
                        .urlImg("")
                        .build(),
                Product.builder()
                        .id(1L)
                        .description("Producto 1")
                        .stock(10)
                        .available(true)
                        .category(new Category(1L, "Producto Categoria"))
                        .urlImg("")
                        .build(),
                Product.builder()
                        .id(1L)
                        .description("Producto 1")
                        .stock(10)
                        .available(true)
                        .category(new Category(1L, "Producto Categoria"))
                        .urlImg("")
                        .build(),
                Product.builder()
                        .id(1L)
                        .description("Producto 1")
                        .stock(10)
                        .available(true)
                        .category(new Category(1L, "Producto Categoria"))
                        .urlImg("")
                        .build()
        );
    }

}
