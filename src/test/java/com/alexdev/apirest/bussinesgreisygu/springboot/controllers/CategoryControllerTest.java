package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.CategoryRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
    private Category testCategory;
    private List<Category> testCategories;

    private final ObjectMapper mapper;
    private final MockMvc mvc;
    @MockBean
    CategoryService service;

    @Autowired
    public CategoryControllerTest(ObjectMapper mapper, MockMvc mvc) {
        this.mapper = mapper;
        this.mvc = mvc;
    }

    @BeforeEach
    void initEach(){
        testCategory = Category.builder()
                .id( 3L )
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
    @DisplayName("Deberia listar todas las categorias")
    void testShouldGetAllCategoriesSuccessfully() throws Exception {
        //Arrange
        when( service.findAll() ).thenReturn( testCategories );

        //Act
        ResultActions apiResponse =mvc.perform( get( "/categories" ) );

        //Assert
        apiResponse
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.size()", is( testCategories.size() )));

    }
    @Test
    @DisplayName("Deberia listar una categoria en especifico (por ID)")
    void testShouldGetCategoryByIDSuccessfully() throws Exception {
        //Arrange
        when( service.findBy( testCategory.getId() ) ).thenReturn( testCategory );

        //Act
        ResultActions apiResponse =mvc.perform( get( "/categories/"+testCategory.getId() ) );

        //Assert
        apiResponse
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.id", is( testCategory.getId().intValue() )))
                .andExpect( jsonPath("$.name", is( testCategory.getName() )));

    }
    @Test
    @DisplayName("Deberia listar una categoria en especifico (por nombre)")
    void testShouldGetCategoryByNameSuccessfully() throws Exception {
        //Arrange
        when( service.findBy( testCategory.getName() ) ).thenReturn( testCategory );

        //Act
        ResultActions apiResponse =mvc.perform( get( "/categories/search?name="+testCategory.getName() ) );

        //Assert
        apiResponse
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.id", is( testCategory.getId().intValue() )))
                .andExpect( jsonPath("$.name", is( testCategory.getName() )));

    }
    @Test
    @DisplayName("Deberia guardar una categoria correctamente.")
    void testShouldSaveCategorySuccessfully() throws Exception {
        //Arrange
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name( testCategory.getName() ).build();
        String jsonRequest =mapper.writeValueAsString( categoryRequest );
        willDoNothing().given( service ).save( any(CategoryRequest.class) );

        //Act
        ResultActions apiResponse =mvc.perform( post( "/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content( jsonRequest ));

        //Assert
        apiResponse
                .andDo(print())
                .andExpect( status().isCreated() );
    }

    @Test
    @DisplayName("Deberia actualizar una categoria correctamente.")
    void testShouldUpdateAnExistingCategorySuccessfully() throws Exception {
        //Arrange
        Category category = testCategory;
        CategoryRequest categoryRequest = CategoryRequest.builder()
                .name( category.getName() ).build();
        String jsonRequest =mapper.writeValueAsString( categoryRequest );
        willDoNothing().given( service ).update( category.getId(), categoryRequest );

        //Act
        ResultActions apiResponse =mvc.perform( put( "/categories/"+ category.getId() )
                .contentType(MediaType.APPLICATION_JSON)
                .content( jsonRequest ));

        //Assert
        apiResponse
                .andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.message", is("Categoria correctamente modificada.")) );
    }

    @Test
    @DisplayName("Deberia eliminar una categoria correctamente.")
    void testShouldDeleteAnExistingCategorySuccessfully() throws Exception {
        //Arrange
        Long categoryID = testCategories.get(0).getId();
        willDoNothing().given( service ).delete( categoryID );

        //Act
        ResultActions apiResponse =mvc.perform( delete( "/categories/"+ categoryID ));

        //Assert
        apiResponse
                .andDo(print())
                .andExpect( status().isNoContent() );
    }
}
