package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> { }
