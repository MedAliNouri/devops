package tn.esprit.devops_project.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import tn.esprit.devops_project.entities.Operator;
import tn.esprit.devops_project.repositories.OperatorRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OperatorServiceImplTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorServiceImpl operatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void retrieveAllOperators() {
        List<Operator> operatorList = Collections.emptyList();

        when(operatorRepository.findAll()).thenReturn(operatorList);

        List<Operator> retrievedOperators = operatorService.retrieveAllOperators();

        assertNotNull(retrievedOperators);
        assertEquals(operatorList, retrievedOperators);

        verify(operatorRepository, times(1)).findAll();
    }

    @Test
    void addOperator() {
        Operator operator = new Operator();

        when(operatorRepository.save(operator)).thenReturn(operator);

        Operator savedOperator = operatorService.addOperator(operator);

        assertNotNull(savedOperator);

        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void deleteOperator() {
        Long operatorId = 1L;

        operatorService.deleteOperator(operatorId);

        verify(operatorRepository, times(1)).deleteById(operatorId);
    }

    @Test
    void updateOperator() {
        Operator operator = new Operator();

        when(operatorRepository.save(operator)).thenReturn(operator);

        Operator updatedOperator = operatorService.updateOperator(operator);

        assertNotNull(updatedOperator);

        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void retrieveOperator() {
        Long operatorId = 1L;
        Operator operator = new Operator();

        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(operator));

        Operator retrievedOperator = operatorService.retrieveOperator(operatorId);

        assertNotNull(retrievedOperator);
        assertEquals(operator, retrievedOperator);

        verify(operatorRepository, times(1)).findById(operatorId);
    }


}
