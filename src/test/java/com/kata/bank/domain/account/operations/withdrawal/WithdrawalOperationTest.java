package com.kata.bank.domain.account.operations.withdrawal;

import com.kata.bank.domain.account.Amount;
import org.junit.Test;

import static org.junit.Assert.*;

public class WithdrawalOperationTest {

    private static final String DATE = "10/10/2020";

    @Test
    public void given_a_balance_of_800_after_withdrawal_of_600_the_new_balance_should_be_200() {
        WithdrawalOperation withdrawalOperation = withdrawalOperation();

        assertEquals(of(200), withdrawalOperation.newBalance(of(800)));
    }

    @Test
    public void type_should_be_withdrawal() {
        assertEquals("WITHDRAW", withdrawalOperation().type());
    }

    private WithdrawalOperation withdrawalOperation() {
        WithdrawalOperation withdrawalOperation = new WithdrawalOperation(of(600), DATE);
        return withdrawalOperation;
    }

    private Amount of(long value) {
        return Amount.amountOf(value);
    }

}