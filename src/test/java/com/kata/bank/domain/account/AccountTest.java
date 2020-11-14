package com.kata.bank.domain.account;

import com.kata.bank.domain.account.operations.deposit.DepositOperation;
import com.kata.bank.domain.account.operations.withdrawal.WithdrawalOperation;
import com.kata.bank.domain.account.operations.deposit.DepositOperationException;
import com.kata.bank.domain.account.operations.withdrawal.WithdrawalOperationException;
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
        Operation expectedOperation = depositOperationOf(of(1000));
        when(operationRepository.addDeposit(of(1000)))
                .thenReturn(expectedOperation);

        account.deposit(of(1000));

        verify(operationRepository).addDeposit(of(1000));
        assertEquals(of(1000), account.currentBalance());
    }

    @Test
    public void should_deposit_500() {
        Amount depositAmount = of(500);
        Operation expectedOperation = depositOperationOf(depositAmount);
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedOperation);

        account.deposit(of(500));

        verify(operationRepository).addDeposit(of(500));
        assertEquals(of(500), account.currentBalance());
    }

    @Test
    public void should_withdraw_350() {
        Amount withdrawalAmount = of(350);
        Operation expectedOperation = withdrawalOperationOf(withdrawalAmount);
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedOperation);

        account.withdraw(withdrawalAmount);

        verify(operationRepository).addWithdrawal(withdrawalAmount);
        assertEquals(of(-350), account.currentBalance());
    }

    @Test
    public void should_withdraw_1200() {
        Amount withdrawalAmount = of(1200);
        Operation expectedOperation = withdrawalOperationOf(withdrawalAmount);
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedOperation);

        account.withdraw(withdrawalAmount);

        verify(operationRepository).addWithdrawal(of(1200));
        assertEquals(of(-1200), account.currentBalance());
    }

    @Test
    public void should_add_statement_of_deposit_1000() {
        Amount depositAmount = of(1000);
        Operation expectedOperation = depositOperationOf(depositAmount);
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedOperation);

        account.deposit(depositAmount);

        verify(operationsHistoryPrinter).addStatement(expectedOperation, depositAmount);
    }

    @Test
    public void should_add_statement_of_withdraw_1000() {
        Amount withdrawalAmount = of(1000);
        Operation expectedOperation = withdrawalOperationOf(withdrawalAmount);
        when(operationRepository.addWithdrawal(withdrawalAmount))
                .thenReturn(expectedOperation);

        account.withdraw(withdrawalAmount);

        verify(operationsHistoryPrinter).addStatement(expectedOperation, amountOf(-1000));
    }

    @Test
    public void should_not_add_any_statement_if_deposit_fails() {
        expectedException.expect(DepositOperationException.class);
        expectedException.expectMessage("Deposit of 1000.00 failed. Please retry later.");

        account.deposit(of(1000));
        verify(operationsHistoryPrinter, times(0)).addStatement(any(), any());
    }

    @Test
    public void should_not_add_any_statement_if_withdrawal_fails() {
        expectedException.expect(WithdrawalOperationException.class);
        expectedException.expectMessage("Withdrawal of 1000.00 failed. Please retry later.");

        account.withdraw(of(1000));
        verify(operationsHistoryPrinter, times(0)).addStatement(any(), any());
    }

    @Test
    public void should_update_balance_after_each_operation() {
        Amount depositAmount = of(1000);
        Operation expectedDepositOperation = depositOperationOf(depositAmount);
        when(operationRepository.addDeposit(depositAmount))
                .thenReturn(expectedDepositOperation);

        Amount withdrawalAmount = of(500);
        Operation expectedWithdrawalOperation = withdrawalOperationOf(withdrawalAmount);
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

    private Operation depositOperationOf(Amount amount) {
        return new DepositOperation(amount, TODAY);
    }

    private Operation withdrawalOperationOf(Amount amount) {
        return new WithdrawalOperation(amount, TODAY);
    }

    private Amount of(long value) {
        return amountOf(value);
    }
}