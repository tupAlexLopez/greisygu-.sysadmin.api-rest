package com.alexdev.apirest.bussinesgreisygu.springboot.models.mappers.impl;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.ProductRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.mappers.Mapper;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper implements Mapper<Product, ProductRequest> {
    @Override
    public ProductRequest entityToDto(Product product) {
        return ProductRequest.builder()
                .description(product.getDescription())
                .price(product.getPrice())
                .available(product.getAvailable())
                .img(product.getImg())
                .category(product.getCategory().getId())
                .build();
    }

    @Override
    public Product dtoToEntity(ProductRequest productRequest) {
        return Product.builder()
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .available(productRequest.getAvailable())
                .img(productRequest.getImg())
                .category(Category.builder().id(productRequest.getCategory()).build())
                .build();
    }
}
