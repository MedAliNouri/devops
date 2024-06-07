package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.entities.Stock;
import tn.esprit.devops_project.repositories.StockRepository;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StockServiceImplTest {

    @Mock
    private StockRepository stockRepository;

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
    void retrieveStock() {
        Long stockId = 1L;
        Stock stock = new Stock();
        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));
        Stock retrievedStock = stockService.retrieveStock(stockId);
        assertNotNull(retrievedStock);
        assertEquals(stock, retrievedStock);
        verify(stockRepository, times(1)).findById(stockId);
    }

    @Test
    void retrieveAllStock() {
        List<Stock> stockList = Collections.emptyList();
        when(stockRepository.findAll()).thenReturn(stockList);
        List<Stock> retrievedStocks = stockService.retrieveAllStock();
        assertNotNull(retrievedStocks);
        assertEquals(stockList, retrievedStocks);
        verify(stockRepository, times(1)).findAll();
    }



    @Test
    void deleteStock() {
        Long stockId = 1L;
        doNothing().when(stockRepository).deleteById(stockId);
        stockService.deleteStock(stockId);
        verify(stockRepository, times(1)).deleteById(stockId);
    }





    @Test
    void addProductToStock() {
        Stock stock = new Stock();
        stock.setIdStock(1L);
        Product product = new Product();
        product.setIdProduct(1L);
        product.setStock(stock);
        Set<Product> products = new HashSet<>();
        products.add(product);
        stock.setProducts(products);

        when(stockRepository.save(stock)).thenReturn(stock);

        Stock savedStock = stockService.addStock(stock);

        assertNotNull(savedStock);
        assertEquals(1, savedStock.getProducts().size());
        assertEquals(product, savedStock.getProducts().iterator().next());

        verify(stockRepository, times(1)).save(stock);
    }

    @Test
    void retrieveStockWithProducts() {
        Long stockId = 1L;
        Product product = new Product();
        product.setIdProduct(1L);
        Stock stock = new Stock();
        stock.setIdStock(stockId);
        Set<Product> products = new HashSet<>();
        products.add(product);
        stock.setProducts(products);

        when(stockRepository.findById(stockId)).thenReturn(Optional.of(stock));

        Stock retrievedStock = stockService.retrieveStock(stockId);

        assertNotNull(retrievedStock);
        assertEquals(1, retrievedStock.getProducts().size());
        assertEquals(product, retrievedStock.getProducts().iterator().next());

        verify(stockRepository, times(1)).findById(stockId);
    }
}
