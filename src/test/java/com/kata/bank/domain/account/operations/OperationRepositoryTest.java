package com.kata.bank.domain.account.operations;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.deposit.DepositOperation;
import com.kata.bank.domain.account.operations.withdrawal.WithdrawalOperation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.kata.bank.domain.account.Amount.amountOf;
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

        operationRepository.addDeposit(of(1500));

        assertEquals(1, operationRepository.allOperations().size());
        assertEquals(deposit(of(1500)),
                operationRepository.allOperations().get(0));
    }

    @Test
    public void should_store_withdrawal_operation_of_1500() {
        Mockito.when(clock.todaysDateAsString())
                .thenReturn(TODAY);

        operationRepository.addWithdrawal(of(1500));

        assertEquals(1, operationRepository.allOperations().size());
        assertEquals(withdrawal(of(1500)),
                operationRepository.allOperations().get(0));
    }

    private Operation deposit(Amount amount) {
        return new DepositOperation(amount, TODAY);
    }

    private Operation withdrawal(Amount amount) {
        return new WithdrawalOperation(amount, TODAY);
    }

    private Amount of(long value) {
        return amountOf(value);
    }
}