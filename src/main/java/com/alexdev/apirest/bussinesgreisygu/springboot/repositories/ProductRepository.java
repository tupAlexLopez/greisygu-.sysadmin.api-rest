package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findByCategoryName(String name, Pageable pageable);
    Page<Product> findByDescriptionContainingIgnoreCase(String description, Pageable pageable);
    Page<Product> findByAvailable( Boolean available , Pageable pageable);

    Page<Product> findByDescriptionContainingIgnoreCaseAndCategoryNameAndAvailable(String description, String category, Boolean available, Pageable pageable);
    Page<Product> findByDescriptionContainingIgnoreCaseAndCategoryName(String description, String category, Pageable pageable);
    Page<Product> findByDescriptionContainingIgnoreCaseAndAvailable(String description, Boolean available, Pageable pageable );
    Page<Product> findByCategoryNameAndAvailable(String name, Boolean available, Pageable pageable );


    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Product p WHERE p.category_id = :id", nativeQuery = true)
    void deleteAllProductsByCategoryId( Long id );
}
