package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Repositorio - Categorias")
public class CategoryRepositoryTest {
    private Category testCategory;
    private List<Category> testCategories;

    private final CategoryRepository repository;

    @Autowired
    public CategoryRepositoryTest(CategoryRepository repository) {
        this.repository = repository;
    }

    @BeforeEach
    void initEach(){
        testCategory = Category.builder()
                .name("Lacteos")
                .build();


        testCategories = List.of(
                Category.builder()
                        .id(1L)
                        .name("Pastas")
                        .build(),
                Category.builder()
                        .id(2L)
                        .name("Bebidas")
                        .build()
        );
    }

    @Test
    @DisplayName("Deberia traer todas las categorias.")
    void testShouldGetAllCategories(){
        //Arrange
        repository.saveAll( testCategories );

        //Act
        List<Category> savedCategories =repository.findAll();

        //Assert
        assertNotNull( savedCategories );
        assertEquals( testCategories.size() , savedCategories.size() );
    }

    @Test
    @DisplayName("Deberia traer una categoria en especifico (por ID).")
    void testShouldGetCategoryByID(){
        //Arrange
        Category savedCategory =repository.save( testCategory );

        //Act
        Optional<Category> categoryFound =repository.findById( savedCategory.getId() );

        //Assert
        assertTrue( categoryFound.isPresent() );
        assertEquals( savedCategory.getId() , categoryFound.get().getId() );
    }
    @Test
    @DisplayName("Deberia traer una categoria en especifico (por nombre).")
    void testShouldGetCategoryByName(){
        //Arrange
        Category savedCategory =repository.save( testCategory );
        String categoryName =savedCategory.getName();

        //Act
        Optional<Category> categoryFound =repository.findByName( categoryName );

        //Assert
        assertFalse( categoryFound.isEmpty() );
        assertEquals( categoryName, categoryFound.get().getName() );
    }

    @Test
    @DisplayName("Deberia guardar una categoria.")
    void testShouldSaveCategory(){
        //Arrange
        Category categoryToSave =testCategory;

        //Act
        Category savedCategory =repository.save( categoryToSave );

        //Assert
        assertNotNull( savedCategory.getId() );
        assertEquals( savedCategory.getName(), categoryToSave.getName() );
    }

    @Test
    @DisplayName("Deberia actualizar una categoria existente.")
    void testShouldUpdateCategory(){
        //Arrange
        Category savedCategory =repository.save( testCategory );
        String newCategoryName    ="Categoria modificada";

        //Act
        savedCategory.setName(newCategoryName);
        Category updatedCategory = repository.save( savedCategory );

        //Assert
        assertNotNull( updatedCategory );
        assertEquals( newCategoryName, updatedCategory.getName() );
    }


    @Test
    @DisplayName("Deberia eliminar una categoria existente.")
    void testShouldDeleteCategory(){
        //Arrange
        Category savedCategory =repository.save( testCategory );

        //Act
        repository.deleteById( savedCategory.getId() );
        Optional<Category> categoryDeleted =repository.findById( savedCategory.getId() );

        //Assert
        assertTrue( categoryDeleted.isEmpty() );
    }
}
