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
    private List<Category> testCategories;
    private Category testCategory;

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
    void testShouldGetAllCategoriesSuccessfully(){
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
    void testShouldGetCategoryByIDSuccessfully(){
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
    void testShouldGetCategoryByNameSuccessfully(){
        //Arrange
        CategoryRequest categoryRequest =CategoryRequest.builder().name( testCategory.getName() ).build();

        //Act
        service.save( categoryRequest );

        //Assert
        verify( repository, times(1) ).save( any() );
    }

    @Test
    @DisplayName("Deberia actualizar una categoria existente.")
    void testShouldUpdateAnExistingCategorySuccessfully(){
        //Arrange
        Category categoryToUpdate =testCategories.get(0);
        Long categoryToUpdateID =categoryToUpdate.getId();
        when( repository.findById( categoryToUpdateID ) ).thenReturn(Optional.of( categoryToUpdate ));

        categoryToUpdate.setName( "Categoria mo dificada" );
        CategoryRequest categoryRequest =CategoryRequest.builder()
                .name( categoryToUpdate.getName() )
                .build();

        when( repository.save( categoryToUpdate ) ).thenReturn( categoryToUpdate );

        //Act
        service.update( categoryToUpdateID ,categoryRequest );

        //Assert
        verify( repository, times(1) ).findById( categoryToUpdateID );
        verify( repository, times(1) ).save( categoryToUpdate );
    }

    @Test
    @DisplayName("Deberia eliminar una categoria existente.")
    void testShouldDeleteAnExistingCategorySuccessfully(){
        //Arrange
        Category category =testCategories.get(0);
        Long categoryID =testCategories.get(0).getId();
        when( repository.findById( categoryID ) ).thenReturn( Optional.of( category ) );

        //Act
        service.delete( categoryID );

        //Assert
        verify( repository, times(1 ) ).findById( anyLong() );
        verify( repository, times( 1 ) ).deleteById( anyLong() );
    }
}
