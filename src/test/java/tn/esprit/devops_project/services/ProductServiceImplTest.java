package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private Stock stock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        stock = new Stock();
        stock.setIdStock(1L);

        product = new Product();
        product.setIdProduct(1L);
        product.setTitle("Test Product");
        product.setStock(stock);
    }

    @Test
    void addProduct() {
        when(stockRepository.findById(stock.getIdStock())).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product, stock.getIdStock());

        assertNotNull(result);
        assertEquals("Test Product", result.getTitle());
        assertEquals(stock, result.getStock());
        verify(stockRepository, times(1)).findById(stock.getIdStock());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void retrieveProduct() {
        when(productRepository.findById(product.getIdProduct())).thenReturn(Optional.of(product));

        Product result = productService.retrieveProduct(product.getIdProduct());

        assertNotNull(result);
        assertEquals("Test Product", result.getTitle());
        verify(productRepository, times(1)).findById(product.getIdProduct());
    }

    @Test
    void retreiveAllProduct() {
        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setTitle("Product 2");

        List<Product> productList = Arrays.asList(product, product2);

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.retreiveAllProduct();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void retrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        product.setCategory(category);

        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setCategory(category);

        List<Product> productList = Arrays.asList(product, product2);

        when(productRepository.findByCategory(category)).thenReturn(productList);

        List<Product> result = productService.retrieveProductByCategory(category);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void deleteProduct() {
        doNothing().when(productRepository).deleteById(product.getIdProduct());

        productService.deleteProduct(product.getIdProduct());

        verify(productRepository, times(1)).deleteById(product.getIdProduct());
    }

    @Test
    void retreiveProductStock() {
        Long stockId = stock.getIdStock();

        Product product2 = new Product();
        product2.setIdProduct(2L);
        product2.setStock(stock);

        List<Product> productList = Arrays.asList(product, product2);

        when(productRepository.findByStockIdStock(stockId)).thenReturn(productList);

        List<Product> result = productService.retreiveProductStock(stockId);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(productRepository, times(1)).findByStockIdStock(stockId);
    }
}
