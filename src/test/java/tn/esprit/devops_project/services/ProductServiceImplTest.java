package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.ProductRepository;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockRepository stockRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void addProduct() {
        Product product = new Product();
        Stock stock = new Stock();
        Long idStock = 1L;

        when(stockRepository.findById(idStock)).thenReturn(Optional.of(stock));
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.addProduct(product, idStock);

        assertNotNull(savedProduct);
        assertEquals(stock, savedProduct.getStock());

        verify(stockRepository, times(1)).findById(idStock);
        verify(productRepository, times(1)).save(product);
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

    // Add similar tests for other methods

}
