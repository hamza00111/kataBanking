package com.kata.bank.domain.account;

import com.kata.bank.domain.account.operations.exceptions.DepositOperationException;
import com.kata.bank.domain.account.operations.exceptions.WithdrawalOperationException;
import com.kata.bank.domain.account.operations.Operation;
import com.kata.bank.domain.account.operations.OperationRepository;
import com.kata.bank.domain.account.operations.printer.OperationsHistoryPrinter;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.kata.bank.domain.account.Amount.amountOf;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AccountTest {


    public static final String TODAY = "11/11/2020";
    private Account account;
    @Mock
    private OperationRepository operationRepository;
    @Mock
    private OperationsHistoryPrinter operationsHistoryPrinter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        account = new Account(operationRepository, operationsHistoryPrinter);
    }

    @Test
    public void should_deposit_1000() {
        Amount depositAmount = getAmount(1000);
        Operation expectedOperation = new Operation(depositAmount, TODAY);
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedOperation);

        account.deposit(depositAmount);

        verify(operationRepository).addDeposit(getAmount(1000));
        assertEquals(getAmount(1000), account.currentBalance());
    }

    @Test
    public void should_deposit_500() {
        Amount depositAmount = getAmount(500);
        Operation expectedOperation = new Operation(depositAmount, TODAY);
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedOperation);

        account.deposit(getAmount(500));

        verify(operationRepository).addDeposit(getAmount(500));
        assertEquals(getAmount(500), account.currentBalance());
    }

    @Test
    public void should_withdraw_350() {
        Amount withdrawalAmount = getAmount(350);
        Operation expectedOperation = new Operation(withdrawalAmount.negative(), TODAY);
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedOperation);

        account.withdraw(withdrawalAmount);

        verify(operationRepository).addWithdrawal(withdrawalAmount);
        assertEquals(getAmount(-350), account.currentBalance());
    }

    @Test
    public void should_withdraw_1200() {
        Amount withdrawalAmount = getAmount(1200);
        Operation expectedOperation = new Operation(withdrawalAmount.negative(), TODAY);
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedOperation);

        account.withdraw(withdrawalAmount);

        verify(operationRepository).addWithdrawal(getAmount(1200));
        assertEquals(getAmount(-1200), account.currentBalance());
    }

    @Test
    public void should_add_statement_of_deposit_1000() {
        Amount depositAmount = getAmount(1000);
        Operation expectedOperation = new Operation(depositAmount, "11/11/2020");
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedOperation);

        account.deposit(depositAmount);

        verify(operationsHistoryPrinter).addStatement(expectedOperation, depositAmount);
    }

    @Test
    public void should_add_statement_of_withdraw_1000() {
        Amount withdrawalAmount = getAmount(1000);
        Operation expectedOperation = new Operation(withdrawalAmount.negative(), "15/08/2020");
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedOperation);

        account.withdraw(withdrawalAmount);

        verify(operationsHistoryPrinter).addStatement(expectedOperation, amountOf(-1000));
    }

    @Test
    public void should_not_add_any_statement_if_deposit_fails() {
        expectedException.expect(DepositOperationException.class);
        expectedException.expectMessage("Deposit of 1000.00 failed. Please retry later.");

        account.deposit(getAmount(1000));
        verify(operationsHistoryPrinter, times(0)).addStatement(any(), any());
    }

    @Test
    public void should_not_add_any_statement_if_withdrawal_fails() {
        expectedException.expect(WithdrawalOperationException.class);
        expectedException.expectMessage("Withdrawal of 1000.00 failed. Please retry later.");

        account.withdraw(getAmount(1000));
        verify(operationsHistoryPrinter, times(0)).addStatement(any(), any());
    }

    @Test
    public void should_update_balance_after_each_operation() {
        Amount depositAmount = getAmount(1000);
        Operation expectedDepositOperation = new Operation(depositAmount, "10/08/2020");
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedDepositOperation);

        Amount withdrawalAmount = getAmount(500);
        Operation expectedWithdrawalOperation = new Operation(withdrawalAmount.negative(), "15/08/2020");
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedWithdrawalOperation);


        account.deposit(depositAmount);
        account.withdraw(withdrawalAmount);

        InOrder inOrder = Mockito.inOrder(operationsHistoryPrinter);
        inOrder.verify(operationsHistoryPrinter).addStatement(expectedDepositOperation, amountOf(1000));
        inOrder.verify(operationsHistoryPrinter).addStatement(expectedWithdrawalOperation, amountOf(500));
    }

    @Test
    public void should_print_all_operations() {
        account.printHistory();

        verify(operationsHistoryPrinter).print();

    }

    private Amount getAmount(long value) {
        return amountOf(value);
    }
}