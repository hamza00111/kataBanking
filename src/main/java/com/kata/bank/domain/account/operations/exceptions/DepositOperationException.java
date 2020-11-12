package com.kata.bank.domain.account.operations.exceptions;

import com.kata.bank.domain.account.Amount;

public class DepositOperationException extends RuntimeException {

    public DepositOperationException(Amount depositAmount) {
        super(String.format("Deposit of %s failed. Please retry later.", depositAmount.moneyFormat()));
    }
}
