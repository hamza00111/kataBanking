package com.kata.bank.domain.account.operations;

import com.kata.bank.domain.account.Amount;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.*;

public class OperationRepositoryTest {
    private static final String TODAY = "10/11/2020";

    private OperationRepository operationRepository;

    @Mock private Clock clock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        operationRepository = new OperationRepository(clock);
    }

    @Test
    public void should_store_deposit_operation_of_1500() {
        Mockito.when(clock.todaysDateAsString())
                .thenReturn(TODAY);

        operationRepository.addDeposit(Amount.amountOf(1500));

        assertEquals(1, operationRepository.allOperations().size());
        assertEquals(new Operation(Amount.amountOf(1500), TODAY),
                operationRepository.allOperations().get(0));
    }

    @Test
    public void should_store_withdrawal_operation_of_1500() {
        Mockito.when(clock.todaysDateAsString())
                .thenReturn(TODAY);

        operationRepository.addWithdrawal(Amount.amountOf(1500));

        assertEquals(1, operationRepository.allOperations().size());
        assertEquals(new Operation(Amount.amountOf(-1500), TODAY),
                operationRepository.allOperations().get(0));
    }
}