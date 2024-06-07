package tn.esprit.devops_project.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import tn.esprit.devops_project.repositories.OperatorRepository;

class InvoiceServiceImplTest {

    
    @InjectMocks
    InvoiceServiceImpl invoiceService;
    @InjectMocks
    OperatorServiceImpl operatorService;

    @Mock
    InvoiceRepository invoiceRepository;

    @Mock
    OperatorRepository operatorRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateCreation;

    {
        try {
            dateCreation = dateFormat.parse("2023-06-01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    Date dateLastModification;

    {
        try {
            dateLastModification = dateFormat.parse("2023-06-01");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void assignOperatorToInvoice() {
        // Arrange
        Long invoiceId = 1L;
        Long operatorId = 1L;

        Invoice invoice = Invoice.builder()
                .idInvoice(invoiceId)
                .amountDiscount(5)
                .amountInvoice(2.5f)
                .dateCreationInvoice(dateCreation)  // Example date string
                .dateLastModificationInvoice(dateLastModification)  // Example date string
                .archived(false)
                .build();
        Set<Invoice> az = new HashSet<>();
        az.add(invoice);
        Operator operator = Operator.builder()
                .idOperateur(operatorId)
                .fname("John")
                .lname("Doe")
                .password("$2a$10$dIJH36hgVGDqr7FBm")
                .invoices(az)
                .build();
        when(operatorRepository.save(Mockito.any(Operator.class)))
                       .thenReturn(operator);
        //Operator op = operatorService.addOperator(operator);
        when(operatorRepository.save(Mockito.any(Operator.class))).thenReturn(operator);
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(operator));


        // Act
        invoiceService.assignOperatorToInvoice(operatorId, invoiceId);

        // Assert
        verify(invoiceRepository, times(1)).findById(invoiceId);
        verify(operatorRepository, times(1)).findById(operatorId);
        verify(operatorRepository, times(1)).save(operator); //
        assert(operator.getInvoices().contains(invoice));
    }

    @Test
    void assignOperatorToInvoice_InvoiceNotFound() {
        // Arrange
        Long invoiceId = 1L;
        Long operatorId = 1L;

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> invoiceService.assignOperatorToInvoice(operatorId, invoiceId));

        verify(invoiceRepository, times(1)).findById(invoiceId);
        verify(operatorRepository, never()).findById(anyLong());
        verify(operatorRepository, never()).save(any(Operator.class));
    }

    @Test
    void assignOperatorToInvoice_OperatorNotFound() {
        // Arrange
        Long invoiceId = 1L;
        Long operatorId = 1L;

        Invoice invoice = Invoice.builder()
                .idInvoice(invoiceId)
                .amountDiscount(5)
                .amountInvoice(2.5f)
                .dateCreationInvoice(dateCreation)  // Example date string
                .dateLastModificationInvoice(dateLastModification)  // Example date string
                .archived(false)
                .build();

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NullPointerException.class, () -> invoiceService.assignOperatorToInvoice(operatorId, invoiceId));

        verify(invoiceRepository, times(1)).findById(invoiceId);
        verify(operatorRepository, times(1)).findById(operatorId);
        verify(operatorRepository, never()).save(any(Operator.class));
    }
}
