package com.kata.bank.domain.account.operations.printer;

import com.kata.bank.domain.account.operations.Operation;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static com.kata.bank.domain.account.Amount.amountOf;
import static org.mockito.Mockito.verify;

public class OperationsHistoryPrinterTest {

    private OperationsHistoryPrinter operationsHistoryPrinter;
    @Mock private Console console;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        operationsHistoryPrinter = new OperationsHistoryPrinter(console);
    }

    @Test
    public void should_display_header_when_there_are_no_operations() {
        operationsHistoryPrinter.print();

        verify(console).print("|OPERATION | DATE | AMOUNT | BALANCE|");
    }

    @Test
    public void should_display_deposit_operation() {
        operationsHistoryPrinter.addStatement(depositOperation(), amountOf(500));

        operationsHistoryPrinter.print();

        InOrder inOrder = Mockito.inOrder(console);

        inOrder.verify(console).print("|OPERATION | DATE | AMOUNT | BALANCE|");
        inOrder.verify(console).print("|DEPOSIT | 10/10/2010 | 500.00 | 500.00|");
    }

    @Test
    public void should_display_withdrawal_operation() {
        operationsHistoryPrinter.addStatement(withdrawalOperation(), amountOf(350));

        operationsHistoryPrinter.print();

        InOrder inOrder = Mockito.inOrder(console);

        inOrder.verify(console).print("|OPERATION | DATE | AMOUNT | BALANCE|");
        inOrder.verify(console).print("|WITHDRAW | 12/10/2010 | -150.00 | 350.00|");
    }

    @Test
    public void should_display_all_operations_in_reverse_order() {
        operationsHistoryPrinter.addStatement(depositOperation(), amountOf(500));
        operationsHistoryPrinter.addStatement(withdrawalOperation(), amountOf(350));

        operationsHistoryPrinter.print();

        InOrder inOrder = Mockito.inOrder(console);

        inOrder.verify(console).print("|OPERATION | DATE | AMOUNT | BALANCE|");
        inOrder.verify(console).print("|WITHDRAW | 12/10/2010 | -150.00 | 350.00|");
        inOrder.verify(console).print("|DEPOSIT | 10/10/2010 | 500.00 | 500.00|");
    }

    private Operation depositOperation() {
        return new Operation(amountOf(500), "10/10/2010");
    }

    private Operation withdrawalOperation() {
        return new Operation(amountOf(150).negative(), "12/10/2010");
    }
}