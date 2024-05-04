package com.alexdev.apirest.bussinesgreisygu.springboot.repositories;

import com.alexdev.apirest.bussinesgreisygu.springboot.models.Category;
import com.alexdev.apirest.bussinesgreisygu.springboot.models.Product;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Repositorio - Productos")
public class ProductRepositoryTest {
    static Product testProduct;

    static List<Category> testCategories;
    static List<Product> testProductsWithCategories;
    static List<Product> testProducts;
    static Pageable testPageable;


    @Autowired
    ProductRepository repository;

    @BeforeAll
    static void init(){
        testPageable =PageRequest.of(0, 5);

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

        testProduct = Product.builder()
                .description("Producto prueba")
                .price(1234.56)
                .img("image.png")
                .available(true)
                .build();


        testProductsWithCategories = List.of(
                Product.builder()
                        .description("Prueba 2")
                        .price(4321d)
                        .available(true)
                        .category(Category.builder()
                                .id(1L)
                                .name("Pastas")
                                .build()
                        )
                        .build(),
                Product.builder()
                        .description("Prueba 3")
                        .price(4321d)
                        .available(false)
                        .category(Category.builder()
                                .id(1L)
                                .name("Pastas")
                                .build()
                        )
                        .build(),
                Product.builder()
                        .description("Prueba 4")
                        .price(7653d)
                        .available(true)
                        .category(Category.builder()
                                .id(2L)
                                .name("Bebidas")
                                .build()
                        )
                        .build(),
                Product.builder()
                        .description("Prueba 5")
                        .price(7653d)
                        .available(false)
                        .category(Category.builder()
                                .id(2L)
                                .name("Bebidas")
                                .build()
                        )
                        .build()
        );

        testProducts = List.of(
                Product.builder()
                        .description("Prueba 2")
                        .price(4321d)
                        .available(true)
                        .build(),
                Product.builder()
                        .description("Prueba 3")
                        .price(4321d)
                        .available(true)
                        .build(),
                Product.builder()
                        .description("Prueba 4")
                        .price(7653d)
                        .available(true)
                        .build(),
                Product.builder()
                        .description("Prueba 5")
                        .price(123.42)
                        .available(false)
                        .build(),
                Product.builder()
                        .description("Prueba 6")
                        .price(5432.12)
                        .available(true)
                        .build()
        );
    }

    @Test
    @DisplayName("Deberia listar todos los productos existentes en el sistema.")
    void testShouldListAllProductsSuccessfully(){
        //Arrange
        List<Product> productsToBeSave = testProducts;
        Integer expectSize =5;

        //Act
        repository.saveAll( productsToBeSave );
        List<Product> savedProducts = repository.findAll();

        //Assert
        assertFalse( savedProducts.isEmpty() );
        assertEquals( expectSize, savedProducts.size() );
    }

    @Test
    @DisplayName("Deberia guardar correctamente en el sistema")
    void testShouldBeSaveToDatabaseSuccessfully(){
        //Arrange
        Product productToSave = testProduct;

        //Act
        Product productSaved =repository.save( productToSave );

        //Assert
        assertNotNull(productSaved.getId());
        assertEquals( productSaved.getDescription(), productToSave.getDescription() );
        assertEquals( productSaved.getPrice(), productToSave.getPrice() );
        assertEquals( productSaved.getImg(), productToSave.getImg() );
        assertEquals( productSaved.getAvailable(), productToSave.getAvailable() );
    }

    @Test
    @DisplayName("Deberia modificar un producto existente correctamente.")
    void testShouldUpdateExistingProductSuccessfully() {
        //Arrange
        Product productSaved = repository.save(testProduct);
        String newDescription = "Prueba modificada";
        Double newPrice = 123.67;
        String newImg = "image.jpg";

        //Act
        productSaved.setDescription(newDescription);
        productSaved.setPrice(newPrice);
        productSaved.setImg(newImg);
        Product productUpdated = repository.save(productSaved);

        //Assert
        assertEquals(productUpdated.getId(), productSaved.getId());
        assertEquals(productUpdated.getDescription(), productSaved.getDescription());
        assertEquals(productUpdated.getPrice(), productSaved.getPrice());
        assertEquals(productUpdated.getImg(), productSaved.getImg());
    }

    @Test
    @DisplayName("Deberia encontrar un producto en especifico.")
    void testShouldFindProductSuccessfully() {
        //Arrange
        Product savedProduct = repository.save(testProduct);

        //Act
        Optional<Product> productFound = repository.findById(savedProduct.getId());
        System.out.println( savedProduct.getId() );

        //Assert
        assertFalse(productFound.isEmpty());
        assertEquals(savedProduct, productFound.get());
    }

    @Test
    @DisplayName("Deberia eliminar un producto existente correctamente.")
    void testShouldDeleteExistingProductSuccessfully() {
        //Arrange
        Product savedProduct = repository.save(testProduct);

        //Act
        repository.deleteById(savedProduct.getId());
        Optional<Product> productFound = repository.findById(savedProduct.getId());

        //Assert
        assertTrue(productFound.isEmpty());
    }



    @Nested
    @DisplayName("Productos con categorias")
    class TestWithDataSQL {

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por nombre de categoria")
        void testShouldFilterByCategoryName(){
            //Arrange
            String categoryName ="Pastas";
            Integer expectSizeProducts = 2;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> productsByCategoryName =repository.findByCategoryName(categoryName, testPageable);

            //Assert
            assertEquals( expectSizeProducts , (int) productsByCategoryName.getTotalElements() );
        }

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por descripcion")
        void testShouldFilterProductsByDescription(){
            //Arrange
            String description ="prueba";
            Integer expectSizeProducts = 4;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> productsByDescription =repository.findByDescriptionContainingIgnoreCase(description, testPageable);

            //Assert
            assertEquals( (int) productsByDescription.getTotalElements(), expectSizeProducts );
        }

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por disponibilidad")
        void testShouldFilterProductsByAvailable(){
            //Arrange
            boolean available = true;
            Integer expectSizeProductsIfAvailableTrue = 2;
            Integer expectSizeProductsIfAvailableFalse = 2;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> productsByAvailableIfTrue =repository.findByAvailable( available , testPageable);
            available = false;
            Page<Product> productsByAvailableIfFalse =repository.findByAvailable( available , testPageable);


            //Assert
            assertEquals( (int) productsByAvailableIfTrue.getTotalElements(), expectSizeProductsIfAvailableTrue );
            assertEquals( (int) productsByAvailableIfFalse.getTotalElements(), expectSizeProductsIfAvailableFalse );
        }

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por descripcion, categoria y disponibilidad")
        void testShouldFilterProductsByDescriptionAvailableAndCategory(){
            //Arrange
            String description ="prueba";
            String categoryName ="Pastas";
            boolean available =true;
            Integer expectSizeProducts = 1;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByDescriptionContainingIgnoreCaseAndCategoryNameAndAvailable(description, categoryName, available ,testPageable);
            categoryName ="Bebidas";
            Page<Product> productsByDescriptionCategoryAndAvailable =repository.findByDescriptionContainingIgnoreCaseAndCategoryNameAndAvailable(description, categoryName, available ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
            assertEquals( expectSizeProducts, (int) productsByDescriptionCategoryAndAvailable.getTotalElements());
        }
        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por descripcion y categoria")
        void testShouldFilterProductsByDescriptionAndCategory(){
            //Arrange
            String description ="prueba";
            String categoryName ="Pastas";
            Integer expectSizeProducts = 2;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByDescriptionContainingIgnoreCaseAndCategoryName(description, categoryName ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
        }

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por descripcion y disponibilidad")
        void testShouldFilterProductsByDescriptionAndAvailable(){
            //Arrange
            String description ="prueba";
            boolean available =true;
            Integer expectSizeProducts = 2;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByDescriptionContainingIgnoreCaseAndAvailable(description, available ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
        }

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia filtrar productos por categoria y disponibilidad")
        void testShouldFilterProductsByCategoryNameAndAvailable(){
            //Arrange
            String categoryName ="Pastas";
            boolean available =true;
            Integer expectSizeProducts = 1;

            //Act
            repository.saveAll( testProductsWithCategories );
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByCategoryNameAndAvailable(categoryName, available ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
        }

        @Test
        @Sql(scripts = "/sql/products/insert-categories.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        @DisplayName("Deberia eliminar todos los productos por una determinada categoria")
        void testShouldDeleteAllProductsByCategoryIdSuccessfully(){
            //Arrange
            Category category = Category.builder()
                    .id(1L)
                    .name("Pastas")
                    .build();
            Integer expectSizeProducts = 0;

            //Act
            repository.saveAll( testProductsWithCategories );
            repository.deleteAllProductsByCategoryId( category.getId() );
            Page<Product> productsByCategoryName =repository.findByCategoryName( category.getName(), testPageable );

            //Assert
            assertEquals( (int) productsByCategoryName.getTotalElements(), expectSizeProducts );
        }

    }

}
