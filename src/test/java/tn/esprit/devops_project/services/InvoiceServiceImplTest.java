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
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.entities.Supplier;
import tn.esprit.devops_project.repositories.InvoiceDetailRepository;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;
import tn.esprit.devops_project.repositories.SupplierRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

class InvoiceServiceImplTest {

    @Mock
    private InvoiceRepository invoiceRepository;

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private InvoiceDetailRepository invoiceDetailRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    private Invoice invoice;
    private Supplier supplier;
    private Operator operator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        invoice = new Invoice();
        invoice.setIdInvoice(1L);

        supplier = new Supplier();
        supplier.setIdSupplier(1L);

        operator = new Operator();
        operator.setIdOperateur(1L);
        operator.setInvoices(Set.of(invoice));
    }

    @Test
    void retrieveAllInvoices() {
        Invoice invoice2 = new Invoice();
        invoice2.setIdInvoice(2L);

        List<Invoice> invoiceList = Arrays.asList(invoice, invoice2);

        when(invoiceRepository.findAll()).thenReturn(invoiceList);

        List<Invoice> result = invoiceService.retrieveAllInvoices();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(invoiceRepository, times(1)).findAll();
    }

    @Test
    void cancelInvoice() {
        when(invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.of(invoice));

        invoiceService.cancelInvoice(invoice.getIdInvoice());

        assertTrue(invoice.getArchived());
        verify(invoiceRepository, times(1)).findById(invoice.getIdInvoice());
        verify(invoiceRepository, times(1)).save(invoice);
        verify(invoiceRepository, times(1)).updateInvoice(invoice.getIdInvoice());
    }

    @Test
    void retrieveInvoice() {
        when(invoiceRepository.findById(invoice.getIdInvoice())).thenReturn(Optional.of(invoice));

        Invoice result = invoiceService.retrieveInvoice(invoice.getIdInvoice());

        assertNotNull(result);
        assertEquals(invoice.getIdInvoice(), result.getIdInvoice());
        verify(invoiceRepository, times(1)).findById(invoice.getIdInvoice());
    }





    @Test
    void getTotalAmountInvoiceBetweenDates() {
        Date startDate = new Date();
        Date endDate = new Date();

        float totalAmount = 1000.0f;

        when(invoiceRepository.getTotalAmountInvoiceBetweenDates(startDate, endDate)).thenReturn(totalAmount);

        float result = invoiceService.getTotalAmountInvoiceBetweenDates(startDate, endDate);

        assertEquals(totalAmount, result);
        verify(invoiceRepository, times(1)).getTotalAmountInvoiceBetweenDates(startDate, endDate);
    }
}
