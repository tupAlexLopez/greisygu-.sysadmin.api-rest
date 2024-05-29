package com.alexdev.apirest.bussinesgreisygu.springboot.controllers;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.ProductRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    private Pageable testPageable;
    private List<Product> testProducts;

    private final ObjectMapper mapper;
    private final MockMvc mockMvc;

    @MockBean
    ProductService service;

    @Autowired
    public ProductControllerTest(ObjectMapper mapper, MockMvc mockMvc) {
        this.mapper = mapper;
        this.mockMvc = mockMvc;
    }

    @BeforeEach
    void initEach(){
        testPageable = PageRequest.of( 0, 5 );
        testProducts = List.of(
                Product.builder()
                        .id(1L)
                        .description("Prueba 2")
                        .price(1200d)
                        .available(true)
                        .category(Category.builder()
                                .id(1L)
                                .name("Pastas")
                                .build())
                        .build(),
                Product.builder()
                        .id(2L)
                        .description("Prueba 3")
                        .price(5432.23)
                        .available(false)
                        .category(Category.builder()
                                .id(1L)
                                .name("Pastas")
                                .build())
                        .build(),
                Product.builder()
                        .id(3L)
                        .description("Prueba 4")
                        .price(3223.12)
                        .available(false)
                        .category(Category.builder()
                                .id(2L)
                                .name("Bebidas")
                                .build())
                        .build(),
                Product.builder()
                        .id(4L)
                        .description("Prueba 5")
                        .price(4321.21)
                        .available(true)
                        .category(Category.builder()
                                .id(2L)
                                .name("Bebidas")
                                .build())
                        .build()
        );
    }

    @Test
    @DisplayName("Deberia listar todos los productos.")
    void testShouldGetAllProductsSuccessfully() throws Exception {
        //Arrange
        Page<Product> productPageToReturn =new PageImpl<>( testProducts, testPageable, testProducts.size() );
        when( service.findAll( testPageable ) ).thenReturn( productPageToReturn );

        //Act
        ResultActions apiResponse =mockMvc.perform( get( "/products" ) );

        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.content").exists() )
                .andExpect( jsonPath("$.totalElements", is( testProducts.size() )) );
    }

    @Test
    @DisplayName("Deberia encontrar un producto en especifico.")
    void testShouldGetProductByIDSuccessfully() throws Exception {
        //Arrange
        Product productExpected =testProducts.get(0);
        when( service.findBy( productExpected.getId() ) ).thenReturn( productExpected );

        //Act
        ResultActions apiResponse =mockMvc.perform( get( "/products/"+productExpected.getId() ) );

        //Assert
        verify( service, times(1)).findBy( anyLong());
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.id", is( productExpected.getId().intValue() )) )
                .andExpect( jsonPath("$.description", is( productExpected.getDescription() )) );
    }

    @Test
    @DisplayName("Deberia encontrar productos por descripcion.")
    void testShouldGetProductByDescriptionSuccessfully() throws Exception {
        //Arrange
        String productDescription = "Prueba";
        List<Product> expectedProducts =testProducts.stream()
                .filter( product -> product.getDescription().contains( productDescription ) )
                .toList();
        Page<Product> productPageToReturn = new PageImpl<>( expectedProducts, testPageable, expectedProducts.size() );
        when( service.filterBy( productDescription, null, null, testPageable ) ).thenReturn( productPageToReturn );

        //Act
        ResultActions apiResponse =mockMvc.perform( get( "/products/search?page=0&description="+productDescription ) );

        //Assert
        verify( service, times(1)).filterBy( productDescription, null, null, testPageable);
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.totalElements", is( 4 )) );
    }

    @Test
    @DisplayName("Deberia encontrar productos por disponibilidad.")
    void testShouldGetProductByAvailableSuccessfully() throws Exception {
        //Arrange
        List<Product> expectedProductsIfAvailableTrue =testProducts.stream()
                .filter( Product::getAvailable )
                .toList();
        List<Product> expectedProductsIfAvailableFalse =testProducts.stream()
                .filter( product -> !product.getAvailable())
                .toList();
        Page<Product> productPageToReturnIfAvailableTrue = new PageImpl<>( expectedProductsIfAvailableTrue, testPageable, expectedProductsIfAvailableTrue.size() );
        Page<Product> productPageToReturnIfAvailableFalse = new PageImpl<>( expectedProductsIfAvailableFalse, testPageable, expectedProductsIfAvailableFalse.size() );

        when( service.filterBy( null, null, true, testPageable ) ).thenReturn( productPageToReturnIfAvailableTrue );
        when( service.filterBy( null, null, false, testPageable ) ).thenReturn( productPageToReturnIfAvailableFalse );

        //Act
        ResultActions apiResponseIfTrue =mockMvc.perform( get( "/products/search?page=0&available="+true ) );
        ResultActions apiResponseIfFalse =mockMvc.perform( get( "/products/search?page=0&available="+false ) );

        //Assert
        apiResponseIfTrue.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.totalElements", is( 2 )) );
        apiResponseIfFalse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.totalElements", is( 2 )) );
    }

    @Test
    @DisplayName("Deberia encontrar productos por nombre de categoria.")
    void testShouldGetProductByCategoryNameSuccessfully() throws Exception {
        //Arrange
        String categoryName ="Pastas";
        List<Product> expectedProducts =testProducts.stream()
                .filter( product -> product.getCategory().getName().equalsIgnoreCase( categoryName ) )
                .toList();
        Page<Product> productPageToReturn = new PageImpl<>( expectedProducts, testPageable, expectedProducts.size() );
        when( service.filterBy( null, categoryName, null, testPageable ) ).thenReturn( productPageToReturn );

        //Act
        ResultActions apiResponse =mockMvc.perform( get( "/products/search?page=0&category="+categoryName ) );

        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.totalElements", is( 2 )) );
    }

    @Test
    @DisplayName("Deberia encontrar productos por nombre de descripcion, categoria y disponibilidad")
    void testShouldFilterByDescriptionAndCategoryNameAndAvailableSuccessfully() throws Exception {
        //Arrange
        String description ="Prueba";
        String categoryName ="Pastas";
        boolean available =true;

        List<Product> expectedProducts =testProducts.stream()
                .filter( product -> product.getAvailable() == available)
                .filter( product -> product.getDescription().contains( description ))
                .filter( product -> product.getCategory().getName().equalsIgnoreCase( categoryName ) )
                .toList();

        Page<Product> productPageToReturn = new PageImpl<>( expectedProducts, testPageable, expectedProducts.size() );
        when( service.filterBy( description, categoryName, available, testPageable ) ).thenReturn( productPageToReturn );

        //Act
        ResultActions apiResponse =mockMvc.perform( get( "/products/search?page=0&description="+ description +"&category="+categoryName+"&available="+available ) );

        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect( jsonPath("$.totalElements", is( 1 )) );
    }

    @Test
    @DisplayName("Deberia guardar un producto.")
    void testShouldSaveProductSuccessfully() throws Exception {
        //Arrange
        Product product =testProducts.get(0);
        ProductRequest productRequest = ProductRequest.builder()
                .description(product.getDescription())
                .price( product.getPrice() )
                .img(product.getImg() )
                .available( product.getAvailable() )
                .category( product.getCategory().getId() )
                .build();
        String jsonRequest = mapper.writeValueAsString( productRequest );

        //Act
        ResultActions apiResponse = mockMvc.perform( post( "/products" )
                .contentType(MediaType.APPLICATION_JSON )
                .content( jsonRequest ));
        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isCreated() )
                .andExpect(jsonPath("$.message", is("Producto correctamente creado.")));
    }

    @Test
    @DisplayName("Deberia actualizar un producto.")
    void testShouldUpdateProductSuccessfully() throws Exception {
        //Arrange
        Product product =testProducts.get(0);
        ProductRequest productRequest = ProductRequest.builder()
                .description("Producto modificado")
                .price( 1234d )
                .img(product.getImg() )
                .available( false )
                .category( 2L )
                .build();
        String jsonRequest = mapper.writeValueAsString( productRequest );

        //Act
        ResultActions apiResponse = mockMvc.perform( put( "/products/"+ product.getId() )
                .contentType(MediaType.APPLICATION_JSON )
                .content( jsonRequest ));
        //Assert
        apiResponse.andDo(print())
                .andExpect( status().isOk() )
                .andExpect(jsonPath("$.message", is("Producto correctamente modificado.")));
    }

    @Test
    @DisplayName("Deberia eliminar un producto")
    void testShouldDeleteProductSuccessfully() throws Exception {
        //Arrange
        Product product =testProducts.get(0);
        when( service.findBy( product.getId() ) ).thenReturn( product );

        //Act
        ResultActions apiResponse =mockMvc.perform( delete("/products/"+ product.getId()) );
        //Assert

        apiResponse
                .andDo(print())
                .andExpect( status().isNoContent() );
    }
}
