package tn.esprit.devops_project.services;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;
import tn.esprit.devops_project.entities.Invoice;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.InvoiceRepository;
import org.springframework.boot.test.context.SpringBootTest;
import javax.swing.*;
import java.text.ParseException;
import java.util.Date;
import java.util.Optional;
import lombok.Builder;
import tn.esprit.devops_project.repositories.OperatorRepository;
import java.text.SimpleDateFormat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.springframework.test.util.AssertionErrors.*;
@RunWith(SpringRunner.class)
@SpringBootTest

@ExtendWith(MockitoExtension.class)


class ProductServiceImplTest {
    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private InvoiceRepository invoiceRepository;

    @InjectMocks
    private InvoiceServiceImpl invoiceService;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    Date dateCreation;

    {
        try {
            dateCreation = dateFormat.parse("2024-05-05");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    Date dateLastModification;

    {
        try {
            dateLastModification = dateFormat.parse("2024-06-06");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testAssignOperatorToInvoice() {
        // Create an operator
        Operator operator = Operator.builder()
                .idOperateur(1L)
                .fname("John")
                .lname("Doe")
                .password("$2a$10$dIJH36hgVGDqr7FBm")
                .build();

        // Create an invoice
        Invoice invoice = Invoice.builder()
                .idInvoice(1L)
                .amountDiscount(5)
                .amountInvoice(2.5f)
                .dateCreationInvoice(dateCreation)
                .dateLastModificationInvoice(dateLastModification)
                .archived(false)
                .build();

        // Mock the behavior of repository methods
        Mockito.when(operatorRepository.findById(1L)).thenReturn(Optional.of(operator));
        Mockito.when(invoiceRepository.findById(1L)).thenReturn(Optional.of(invoice));

        // Call the method under test
        invoiceService.assignOperatorToInvoice(1L, 1L);

        // Verify the behavior
        Mockito.verify(operatorRepository).findById(1L);
        Mockito.verify(invoiceRepository).findById(1L);
        Mockito.verify(invoiceRepository).save(invoice);

        // Additional assertions
        //assertNotNull(invoice.getOperator());
        //assertEquals(operator.getId(), invoice.getOperator().getId());
    }




}
