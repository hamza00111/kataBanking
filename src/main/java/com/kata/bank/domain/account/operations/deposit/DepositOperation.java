package com.kata.bank.domain.account.operations.deposit;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.Operation;

public class DepositOperation extends Operation {

    private static final String DEPOSIT = "DEPOSIT";

    public DepositOperation(Amount amount, String date) {
        super(amount, date);
    }

    @Override
    public String type() {
        return DEPOSIT;
    }
}
