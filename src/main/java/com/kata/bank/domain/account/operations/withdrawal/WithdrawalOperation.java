package com.kata.bank.domain.account.operations.withdrawal;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.Operation;

public class WithdrawalOperation extends Operation {

    private static final String WITHDRAW = "WITHDRAW";

    public WithdrawalOperation(Amount amount, String date) {
        super(amount.negative(), date);
    }

    @Override
    public String type() {
        return WITHDRAW;
    }
}
