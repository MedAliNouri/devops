package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.ProductCategory;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;
    @InjectMocks
    private StockServiceImpl stockService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void addStock() {
        Stock stock = new Stock();

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock savedStock = stockService.addStock(stock);

        assertNotNull(savedStock);

        verify(stockRepository, times(1)).save(stock);
    }


    @Test
    void retrieveProduct() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        Product retrievedProduct = productService.retrieveProduct(productId);
        assertNotNull(retrievedProduct);
        assertEquals(product, retrievedProduct);
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void retreiveAllProduct() {
        List<Product> productList = Collections.emptyList();
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> retrievedProducts = productService.retreiveAllProduct();
        assertNotNull(retrievedProducts);
        assertEquals(productList, retrievedProducts);
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void retrieveProductByCategory() {
        ProductCategory category = ProductCategory.ELECTRONICS;
        List<Product> productList = Collections.emptyList();
        when(productRepository.findByCategory(category)).thenReturn(productList);
        List<Product> retrievedProducts = productService.retrieveProductByCategory(category);
        assertNotNull(retrievedProducts);
        assertEquals(productList, retrievedProducts);
        verify(productRepository, times(1)).findByCategory(category);
    }

    @Test
    void deleteProduct() {
        Long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }


}
