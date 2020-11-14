package com.kata.bank.domain.account.operations;

import com.kata.bank.domain.account.Amount;

import java.util.Objects;

public abstract class Operation {

    private String date;
    private Amount amount;

    public Operation(Amount amount, String date) {
        this.date = date;
        this.amount = amount;
    }

    public Amount newBalance(Amount balance) {
        return balance.plus(amount);
    }

    public String date() {
        return date;
    }

    public String amount() {
        return amount.moneyFormat();
    }

    public abstract String type();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Operation operation = (Operation) o;
        return date.equals(operation.date) &&
                amount.equals(operation.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, amount);
    }
}
