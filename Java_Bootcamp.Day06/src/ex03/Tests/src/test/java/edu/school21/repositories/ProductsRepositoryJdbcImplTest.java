package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private ProductsRepositoryJdbcImpl productsMethods;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(1L, "prod1", BigDecimal.valueOf(11.11)),
            new Product(2L, "prod2", BigDecimal.valueOf(12.11)),
            new Product(3L, "prod3", BigDecimal.valueOf(13.11)),
            new Product(4L, "prod4", BigDecimal.valueOf(14.11)),
            new Product(5L, "prod5", BigDecimal.valueOf(15.11)),
            new Product(6L, "prod6", BigDecimal.valueOf(16.11))
    );

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(1L, "prod1", BigDecimal.valueOf(11.11));
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1L, "It was awesome", BigDecimal.valueOf(666.00));
    final Product SAVE_PRODUCT = new Product(7L, "new prod", BigDecimal.valueOf(666.00));

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.HSQL)
                .setScriptEncoding("UTF-8")
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();

        productsMethods = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void testProductsRepositoryJdbcImplFindAll() {
        List<Product> productsList = productsMethods.findAll();
        assertEquals(productsList, EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    void testProductsRepositoryJdbcImplFindById() {
        Optional<Product> product = productsMethods.findById(1L);
        assertTrue(product.isPresent());
        assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, product.get());
    }

    @Test
    void testProductsRepositoryJdbcImplUpdate() {
        Product product = new Product(1L, "It was awesome", BigDecimal.valueOf(666));
        productsMethods.update(product);

        Optional<Product> updatedProd = productsMethods.findById(1L);
        assertTrue(updatedProd.isPresent());
        assertTrue(updatedProd.get().equals(EXPECTED_UPDATED_PRODUCT));
    }

    @Test
    void testProductsRepositoryJdbcImplDelete() {
        productsMethods.delete(1L);
        Optional<Product> product = productsMethods.findById(1L);
        assertTrue(product.isEmpty());
    }

    @Test
    void testProductsRepositoryJdbcImplSave() {
        productsMethods.save(SAVE_PRODUCT);
        Optional<Product> product = productsMethods.findById(7L);
        assertTrue(product.isPresent());
    }
}
