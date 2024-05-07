package com.alexdev.apirest.bussinesgreisygu.springboot.services;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.dto.request.ProductRequest;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.mappers.impl.ProductMapper;
import com.alexdev.apirest.bussinesgreisygu.springboot.repositories.ProductRepository;
import com.alexdev.apirest.bussinesgreisygu.springboot.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {
    private Pageable testPageable;
    private List<Product> testProducts;
    private ProductRequest testProductRequest;
    private Product testProduct;

    @Mock
    ProductMapper mapper;

    @Mock
    ProductRepository repository;

    @InjectMocks
    ProductServiceImpl service;

    @BeforeEach
    void initEach(){
        testPageable = PageRequest.of( 0, 5 );
        testProductRequest = ProductRequest.builder()
                .description( "Test product" )
                .price( 123d )
                .available( true )
                .img("image.png")
                .category( 1L )
                .build();

        testProduct = Product.builder()
                .description( "Test product" )
                .price( 123d )
                .available( true )
                .img("image.png")
                .category( Category.builder()
                        .id( 1L )
                        .build() )
                .build();

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
    void testShouldGetAllProductsSuccessfully() {
        //Arrange
        when( repository.findAll( testPageable ) ).thenReturn( new PageImpl<>( testProducts, testPageable, testProducts.size() ));

        //Act
        Page<Product> products = service.findAll( testPageable );

        //Assert
        verify( repository, times(1) ).findAll( testPageable );
        assertEquals( testProducts.size(), products.getTotalElements() );
    }

    @Test
    @DisplayName("Deberia obtener un producto(ID) en especifico")
    void testShouldGetByIDProductsSuccessfully() {
        //Arrange
        Product product = testProducts.get(0);
        when( repository.findById( product.getId() ) ).thenReturn( Optional.of( product ) );

        //Act
        Product productFound = service.findBy( product.getId() );

        //Assert
        verify( repository, times(1) ).findById( product.getId() );
        assertEquals( product.getId() , productFound.getId() );
    }

    @Test
    @DisplayName("Deberia guardar un producto.")
    void testShouldSaveProductSuccessfully() {
        //Arrange
        ProductRequest productToSave = testProductRequest;
        Product savedProduct = testProduct;

        when( mapper.dtoToEntity( productToSave ) ).thenReturn( savedProduct );
        savedProduct.setId( 5L );
        when( repository.save( savedProduct ) ).thenReturn( savedProduct );

        //Act
        service.save( productToSave );

        //Assert
        verify( mapper, times(1) ).dtoToEntity( productToSave );
        verify( repository, times(1) ).save( testProduct );
    }

    @Test
    @DisplayName("Deberia actualizar un producto existente.")
    void testShouldUpdateExistingProductSuccessfully() {
        Product productToUpdate =testProducts.get(0);
        productToUpdate.setPrice(123.23);
        productToUpdate.setAvailable( false );

        ProductRequest productRequest = ProductRequest.builder()
                .description( productToUpdate.getDescription() )
                .price( productToUpdate.getPrice() )
                .available( productToUpdate.getAvailable() )
                .img( productToUpdate.getImg() )
                .category( productToUpdate.getCategory().getId() )
                .build();


        when( mapper.dtoToEntity( productRequest ) ).thenReturn( productToUpdate );
        when( repository.findById( productToUpdate.getId() ) ).thenReturn( Optional.of( testProducts.get(0) ) );
        when( repository.save( productToUpdate ) ).thenReturn( productToUpdate );

        //Act
        service.update(productToUpdate.getId(), productRequest);

        //Assert
        verify( mapper, times( 1 ) ).dtoToEntity( productRequest );
        verify( repository, times(1) ).findById( productToUpdate.getId() );
        verify( repository, times(1) ).save( productToUpdate );
    }

    @Test
    @DisplayName("Deberia modificar la disponibilidad del producto.")
    void testShouldUpdateByAvailableSuccessfully(){
        //Arrange
        boolean available =false;
        Product product =testProducts.get(0);
        Long productID =product.getId();

        when( repository.findById( productID ) ).thenReturn( Optional.of( product ) );
        product.setAvailable( available );
        when( repository.save( product )  ).thenReturn( product );

        //Act
        service.updateAvailableBy( productID, available );

        //Assert
        assertFalse( product.getAvailable() );
        verify( repository, times(1) ).findById( productID );
        verify( repository, times(1) ).save( product );
    }

    @Test
    @DisplayName( "Deberia eliminar un producto existente." )
    void testShouldDeleteExistingProductSuccessfully() {
        //Arrange
        Product product = testProducts.get(0);
        Long productID = product.getId();
        when( repository.findById( productID ) ).thenReturn( Optional.of( product ) );

        //Act
        service.delete( productID );


        //Assert
        verify( repository, times(1) ).findById( productID);
        verify( repository, times(1) ).deleteById( productID);
    }

    @Test
    @DisplayName("Deberia filtrar productos por nombre de categoria")
    void testShouldFilterByCategoryNameSuccessfully(){
        //Arrange
        String category = "Pastas";

        List<Product> productsByCategoryName =testProducts.stream()
                .filter( products -> category.equalsIgnoreCase(products.getCategory().getName()) )
                .toList();

        Page<Product> productPageToReturn = new PageImpl<>(productsByCategoryName, testPageable, testProducts.size());
        when( repository.findByCategoryName( category, testPageable ) ).thenReturn( productPageToReturn );

        //Act
        Page<Product> pageResult =service.filterBy( null, category, null, testPageable );

        //Assert
        verify( repository, times(1) ).findByCategoryName( category, testPageable );

        assertNotNull( pageResult );
        assertEquals( productsByCategoryName.size(), pageResult.getTotalElements() );
    }

    @Test
    @DisplayName("Deberia filtrar productos por disponibilidad")
    void testShouldFilterByAvailable(){
        //Arrange
        Long sizeExpected =2L;
        List<Product> productsByAvailableIfTrue =testProducts.stream()
                .filter(Product::getAvailable)
                .toList();

        List<Product> productsByAvailableIfFalse =testProducts.stream()
                .filter( products -> !products.getAvailable())
                .toList();

        Page<Product> productPageToReturnIfAvailableTrue = new PageImpl<>(productsByAvailableIfTrue, testPageable, testProducts.size());
        Page<Product> productPageToReturnIfAvailableFalse = new PageImpl<>(productsByAvailableIfFalse, testPageable, testProducts.size());

        when( repository.findByAvailable( true, testPageable ) ).thenReturn( productPageToReturnIfAvailableTrue );
        when( repository.findByAvailable( false, testPageable ) ).thenReturn( productPageToReturnIfAvailableFalse );

        //Act
        Page<Product> pageResultTrue =service.filterBy( null , null , true, testPageable );
        Page<Product> pageResultFalse =service.filterBy( null , null , false, testPageable );

        //Assert
        verify(repository, times(1)).findByAvailable( true, testPageable );
        verify(repository, times(1)).findByAvailable( false, testPageable );

        assertNotNull( pageResultTrue );
        assertNotNull( pageResultFalse );
        assertEquals( sizeExpected, pageResultTrue.getTotalElements() );
        assertEquals( sizeExpected, pageResultFalse.getTotalElements() );
    }

    @Test
    @DisplayName("Deberia filtrar productos por disponibilidad, categoria y descripcion")
    void testShouldFilterBy(){
        //Arrange
        Long sizeExpected =1L;
        String description ="Prue";
        String category =testProducts.get(0).getCategory().getName();

        List<Product> productsToReturn =testProducts.stream()
                .filter(Product::getAvailable)
                .filter( product -> product.getDescription().contains( description ))
                .filter( product ->  category.equals( product.getCategory().getName() ))
                .toList();

        Page<Product> productPageToReturn =new PageImpl<>( productsToReturn, testPageable , productsToReturn.size());
        when( repository.findByDescriptionContainingIgnoreCaseAndCategoryNameAndAvailable( description, category, true, testPageable ) )
                .thenReturn( productPageToReturn );
        //Act
        Page<Product> resultProductPage =service.filterBy( description, category, true, testPageable );

        //Assert
        assertNotNull( resultProductPage );
        assertEquals( sizeExpected, resultProductPage.getTotalElements() );
    }

}
