package com.kata.bank.domain.account;

import org.junit.Test;

import static org.junit.Assert.*;

public class AmountTest {

    @Test
    public void should_be_equal_when_two_amounts_have_same_value() {
        Amount amount = Amount.amountOf(1000);
        Amount secondAmount = Amount.amountOf(1000);
        assertEquals(amount, secondAmount);
    }

    @Test
    public void should_sum_two_amounts() {
        Amount amount = Amount.amountOf(1000);
        Amount secondAmount = Amount.amountOf(1000);

        assertEquals(Amount.amountOf(2000), amount.plus(secondAmount));
    }

    @Test
    public void should_set_value_to_negative() {
        Amount amount = Amount.amountOf(1000).negative();

        assertEquals(Amount.amountOf(-1000), amount);
    }

    @Test
    public void should_check_if_amount_value_is_negative() {
        Amount amount = Amount.amountOf(-1000);

        assertTrue(amount.isNegative());
    }

}