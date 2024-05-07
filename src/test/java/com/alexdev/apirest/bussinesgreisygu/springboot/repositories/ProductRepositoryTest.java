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
    private Product testProduct;
    private List<Product> testProducts;
    private Pageable testPageable;

    private final ProductRepository repository;

    @Autowired
    public ProductRepositoryTest(ProductRepository repository) {
        this.repository = repository;
    }


    @BeforeEach
    void initEach() {
        testPageable = PageRequest.of(0, 5);

        testProduct = Product.builder()
                .description("Producto prueba")
                .price(1234.56)
                .img("image.png")
                .available(true)
                .build();

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
    void testShouldListAllProductsSuccessfully() {
        //Arrange
        repository.saveAll(testProducts);
        Integer expectSize = 5;

        //Act
        List<Product> savedProducts = repository.findAll();

        //Assert
        assertFalse(savedProducts.isEmpty());
        assertEquals(expectSize, savedProducts.size());
    }

    @Test
    @DisplayName("Deberia guardar correctamente en el sistema")
    void testShouldBeSaveToDatabaseSuccessfully() {
        //Arrange
        Product productToSave = testProduct;

        //Act
        Product productSaved = repository.save(productToSave);

        //Assert
        assertNotNull(productSaved.getId());
        assertEquals(productSaved.getDescription(), productToSave.getDescription());
        assertEquals(productSaved.getPrice(), productToSave.getPrice());
        assertEquals(productSaved.getImg(), productToSave.getImg());
        assertEquals(productSaved.getAvailable(), productToSave.getAvailable());
    }

    @Test
    @DisplayName("Deberia modificar un producto existente correctamente.")
    void testShouldUpdateExistingProductSuccessfully() {
        //Arrange
        Product productSaved = repository.save(testProduct);
        String newDescription = "Prueba modificada";
        Double newPrice = 123.67;
        boolean available = false;
        String newImg = "image.jpg";

        //Act
        productSaved.setDescription(newDescription);
        productSaved.setPrice(newPrice);
        productSaved.setImg(newImg);
        productSaved.setAvailable(available);
        Product productUpdated = repository.save(productSaved);

        //Assert
        assertEquals(productUpdated.getId(), productSaved.getId());
        assertEquals(newDescription, productUpdated.getDescription());
        assertEquals(newPrice, productUpdated.getPrice());
        assertEquals(newImg, productUpdated.getImg());
        assertEquals(available, productUpdated.getAvailable());
    }

    @Test
    @DisplayName("Deberia encontrar un producto en especifico(por ID).")
    void testShouldFindProductSuccessfully() {
        //Arrange
        Product savedProduct = repository.save(testProduct);

        //Act
        Optional<Product> productFound = repository.findById(savedProduct.getId());

        //Assert
        assertFalse(productFound.isEmpty());
        assertEquals(savedProduct, productFound.get());
    }

    @Test
    @DisplayName("Deberia eliminar un producto existente correctamente.")
    void testShouldDeleteExistingProductSuccessfully() {
        //Arrange
        Product productToDelete = repository.save(testProduct);

        //Act
        repository.deleteById(productToDelete.getId());
        Optional<Product> productFound = repository.findById(productToDelete.getId());

        //Assert
        assertTrue(productFound.isEmpty());
    }



    @Nested
    @DisplayName("Productos con categorias")
    @Sql("/sql/products/insert-categories.sql")
    class ProductAndCategoryTesting {
        private List<Category> testCategories;
        private List<Product> testProductsWithCategories;

        @BeforeEach
        void initEach() {
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

            repository.saveAll( testProductsWithCategories );
        }

        @Test
        @DisplayName("Deberia guardar un producto con categoria")
        void testShouldSaveProductWithCategorySuccessfully() {
            //Arrange
            Product product = testProduct;
            Category category = testCategories.get(0);
            product.setCategory( category );

            //Act
            Product savedProduct =repository.save( product );

            //Assert
            assertNotNull( savedProduct.getId() );
            assertEquals( product.getCategory().getId(), savedProduct.getCategory().getId() );
            assertEquals( product.getCategory().getName(), savedProduct.getCategory().getName() );
        }
        @Test
        @DisplayName("Deberia actualizar un producto con categoria existente")
        void testShouldUpdateAnExistingProductWithCategorySuccessfully() {
            //Arrange
            Product productToUpdate = testProductsWithCategories.get(0);
            Category category = testCategories.get(1);

            productToUpdate.setAvailable( false );
            productToUpdate.setDescription("Producto modificado");
            productToUpdate.setCategory( category );

            //Act
            Product updatedProduct =repository.save( productToUpdate );

            //Assert
            assertNotNull( updatedProduct.getId() );
            assertEquals(  productToUpdate.getId(), updatedProduct.getId() );
            assertEquals( productToUpdate.getDescription(), updatedProduct.getDescription() );
            assertEquals( productToUpdate.getAvailable(), updatedProduct.getAvailable() );
            assertEquals( productToUpdate.getCategory().getId(), updatedProduct.getCategory().getId() );
            assertEquals( productToUpdate.getCategory().getName(), updatedProduct.getCategory().getName() );
        }

        @Test
        @DisplayName("Deberia filtrar productos por nombre de categoria")
        void testShouldFilterByCategoryName(){
            //Arrange
            String categoryName ="Pastas";
            Integer expectSizeProducts = 2;

            //Act
            Page<Product> productsByCategoryName =repository.findByCategoryName(categoryName, testPageable);

            //Assert
            assertEquals( expectSizeProducts , (int) productsByCategoryName.getTotalElements() );
        }

        @Test
        @DisplayName("Deberia filtrar productos por descripcion")
        void testShouldFilterProductsByDescription(){
            //Arrange
            String description ="prueba";
            Integer expectSizeProducts = 4;

            //Act
            Page<Product> productsByDescription =repository.findByDescriptionContainingIgnoreCase(description, testPageable);

            //Assert
            assertEquals( (int) productsByDescription.getTotalElements(), expectSizeProducts );
        }

        @Test
        @DisplayName("Deberia filtrar productos por disponibilidad")
        void testShouldFilterProductsByAvailable(){
            //Arrange
            Integer expectSizeProductsIfAvailableTrue = 2;
            Integer expectSizeProductsIfAvailableFalse = 2;

            //Act
            Page<Product> productsByAvailableIfTrue =repository.findByAvailable( true , testPageable);
            Page<Product> productsByAvailableIfFalse =repository.findByAvailable( false , testPageable);

            //Assert
            assertEquals( (int) productsByAvailableIfTrue.getTotalElements(), expectSizeProductsIfAvailableTrue );
            assertEquals( (int) productsByAvailableIfFalse.getTotalElements(), expectSizeProductsIfAvailableFalse );
        }

        @Test
        @DisplayName("Deberia filtrar productos por descripcion, categoria y disponibilidad")
        void testShouldFilterProductsByDescriptionAvailableAndCategory(){
            //Arrange
            String description ="prueba";
            String categoryName ="Pastas";
            boolean available =true;
            Integer expectSizeProducts = 1;

            //Act
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
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByDescriptionContainingIgnoreCaseAndCategoryName(description, categoryName ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
        }

        @Test
        @DisplayName("Deberia filtrar productos por descripcion y disponibilidad")
        void testShouldFilterProductsByDescriptionAndAvailable(){
            //Arrange
            String description ="prueba";
            boolean available =true;
            Integer expectSizeProducts = 2;

            //Act
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByDescriptionContainingIgnoreCaseAndAvailable(description, available ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
        }

        @Test
        @DisplayName("Deberia filtrar productos por categoria y disponibilidad")
        void testShouldFilterProductsByCategoryNameAndAvailable(){
            //Arrange
            String categoryName ="Pastas";
            boolean available =true;
            Integer expectSizeProducts = 1;

            //Act
            Page<Product> testPageDescriptionCategoryAndAvailable =repository.findByCategoryNameAndAvailable(categoryName, available ,testPageable);

            //Assert
            assertEquals( expectSizeProducts, (int) testPageDescriptionCategoryAndAvailable.getTotalElements());
        }

        @Test
        @DisplayName("Deberia eliminar todos los productos por una determinada categoria")
        void testShouldDeleteAllProductsByCategoryIdSuccessfully(){
            //Arrange
            Category category = Category.builder()
                    .id(1L)
                    .name("Pastas")
                    .build();
            Integer expectSizeProducts = 0;

            //Act
            repository.deleteAllProductsByCategoryId( category.getId() );
            Page<Product> productsByCategoryName =repository.findByCategoryName( category.getName(), testPageable );

            //Assert
            assertEquals( (int) productsByCategoryName.getTotalElements(), expectSizeProducts );
        }

    }

}
