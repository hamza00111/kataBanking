package com.kata.bank.domain.account.operations.exceptions;

import com.kata.bank.domain.account.Amount;

public class WithdrawalOperationException extends RuntimeException {

    public WithdrawalOperationException(Amount withdrawalAmount) {
        super(String.format("Withdrawal of %s failed. Please retry later.", withdrawalAmount.moneyFormat()));
    }
}
