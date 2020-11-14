package com.kata.bank.domain.account.operations.printer;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.deposit.DepositOperation;
import com.kata.bank.domain.account.operations.Operation;
import com.kata.bank.domain.account.operations.withdrawal.WithdrawalOperation;
import org.junit.Test;

import static com.kata.bank.domain.account.Amount.amountOf;
import static org.junit.Assert.*;

public class StatementLineTest {

    public static final String DATE = "06/10/1992";
    private StatementLine statementLine;

    @Test
    public void should_transform_statement_of_deposit_operation_with_balance() {
        statementLine = new StatementLine(depositOperationOf(1000), of(1000));

        assertEquals("|DEPOSIT | 06/10/1992 | 1000.00 | 1000.00|", statementLine.line());
    }

    @Test
    public void should_transform_statement_of_withdrawal_operation_with_balance() {
        statementLine = new StatementLine(withdrawalOperationOf(1000), of(1000));

        assertEquals("|WITHDRAW | 06/10/1992 | -1000.00 | 1000.00|", statementLine.line());
    }

    private Operation depositOperationOf(long value) {
        return new DepositOperation(of(value), DATE);
    }

    private Operation withdrawalOperationOf(long value) {
        return new WithdrawalOperation(of(value), DATE);
    }

    private Amount of(long value) {
        return amountOf(value);
    }
}