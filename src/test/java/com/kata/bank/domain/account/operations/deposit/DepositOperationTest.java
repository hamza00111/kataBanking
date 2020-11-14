package com.kata.bank.domain.account.operations.deposit;

import com.kata.bank.domain.account.Amount;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepositOperationTest {

    private static final String DATE = "10/10/2020";

    @Test
    public void given_balance_1000_after_deposit_of_500_new_balance_should_be_1500() {
        DepositOperation depositOperation = new DepositOperation(of(500), DATE);

        assertEquals(of(1500), depositOperation.newBalance(of(1000)));
    }

    private Amount of(long value) {
        return Amount.amountOf(value);
    }
}