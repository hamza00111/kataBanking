package com.kata.bank.domain.account.operations.printer;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.Operation;
import org.junit.Test;

import static org.junit.Assert.*;

public class StatementLineTest {

    private StatementLine statementLine;

    @Test
    public void should_transform_statement_of_deposit_operation_with_balance() {
        statementLine = new StatementLine(depositOperation(), Amount.amountOf(1000));

        assertEquals("|DEPOSIT | 06/10/1992 | 1000.00 | 1000.00|", statementLine.line());
    }

    @Test
    public void should_transform_statement_of_withdrawal_operation_with_balance() {
        statementLine = new StatementLine(withdrawalOperation(), Amount.amountOf(1000));

        assertEquals("|WITHDRAW | 06/10/1992 | -1000.00 | 1000.00|", statementLine.line());
    }

    private Operation depositOperation() {
        return new Operation(Amount.amountOf(1000), "06/10/1992");
    }

    private Operation withdrawalOperation() {
        return new Operation(Amount.amountOf(-1000), "06/10/1992");
    }
}