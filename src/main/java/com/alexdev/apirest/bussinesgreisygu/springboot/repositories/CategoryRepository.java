package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName( String name );
}
