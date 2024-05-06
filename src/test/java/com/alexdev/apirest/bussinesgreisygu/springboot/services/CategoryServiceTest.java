package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.CategoryRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.CategoryRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.impl.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    List<Category> testCategories;
    Category testCategory;

    @Mock
    CategoryRepository repository;

    @InjectMocks
    CategoryServiceImpl service;

    @BeforeEach
    void init(){
        testCategory = Category.builder()
                .name("Lacteos")
                .build();

        testCategories =List.of(
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
    @DisplayName("Deberia listar todos las categorias.")
    void testShouldGetAllCategories(){
        //Arrange
        when( repository.findAll() ).thenReturn( testCategories );

        //Act
        List<Category> resultCategories =service.findAll();

        //Assert
        verify( repository, times(1) ).findAll();

        assertNotNull( resultCategories );
        assertFalse( resultCategories.isEmpty() );
        assertEquals( testCategories.size(), resultCategories.size() );
    }

    @Test
    @DisplayName("Deberia traer una categoria en especifico (por ID).")
    void testShouldGetCategoryByID(){
        //Arrange
        Category categoryExpected =testCategories.get(0);
        Long categoryID =testCategories.get(0).getId();
        when( repository.findById( categoryID ) ).thenReturn( Optional.of( categoryExpected ) );

        //Act
        Category resultCategory =service.findBy( categoryID );

        //Assert
        verify( repository, times(1) ).findById( categoryID );

        assertNotNull( resultCategory );
        assertEquals( categoryExpected.getId() , resultCategory.getId() );
        assertEquals( categoryExpected.getName() , resultCategory.getName() );
    }

    @Test
    @DisplayName("Deberia guardar una nueva categoria.")
    void testShouldGetCategoryByName(){
        //Arrange
        CategoryRequest categoryRequest =CategoryRequest.builder().name( testCategory.getName() ).build();

        //Act
        service.save( categoryRequest );

        //Assert
        verify( repository, times(1) ).save( any() );
    }

    @Test
    @DisplayName("Deberia actualizar una categoria existente.")
    void testShouldUpdateAnExistingCategory(){
        //Arrange
        Category categoryToUpdate =testCategories.get(0);
        Long categoryID =categoryToUpdate.getId();
        categoryToUpdate.setName( "Categoria modificada" );
        CategoryRequest categoryRequest =CategoryRequest.builder().name( categoryToUpdate.getName() ).build();

        when( repository.findById( categoryID ) ).thenReturn( Optional.of( categoryToUpdate ) );

        //Act
        service.update( categoryID ,categoryRequest );

        //Assert
        verify( repository, times(1) ).findById( anyLong() );
        verify( repository, times(1) ).save( any() );
    }

    @Test
    @DisplayName("Deberia eliminar una categoria existente.")
    void testShouldDeleteAnExistingCategory(){
        //Arrange
        Category category =testCategories.get(0);
        Long categoryID =testCategories.get(0).getId();
        when( repository.findById( categoryID ) ).thenReturn( Optional.of(category ) );
        //Act
        service.delete( categoryID );

        //Assert
        verify( repository, times(1 ) ).findById( anyLong() );
        verify( repository, times( 1 ) ).delete( any(Category.class) );
    }
}
