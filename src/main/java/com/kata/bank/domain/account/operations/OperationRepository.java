package com.kata.bank.domain.account.operations;


import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.deposit.DepositOperation;
import com.kata.bank.domain.account.operations.withdrawal.WithdrawalOperation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationRepository {

    private List<Operation> operations = new ArrayList<>();
    private Clock clock;

    public OperationRepository(Clock clock) {
        this.clock = clock;
    }

    public Operation addDeposit(Amount depositAmount) {
        Operation deposit = depositOperationOf(depositAmount);
        return addOperation(deposit);
    }

    public Operation addWithdrawal(Amount withdrawalAmount) {
        Operation withdrawal = withdrawalOperationOf(withdrawalAmount);
        return addOperation(withdrawal);
    }

    public List<Operation> allOperations() {
        return Collections.unmodifiableList(operations);
    }

    private String todaysDate() {
        return clock.todaysDateAsString();
    }

    private Operation depositOperationOf(Amount amount) {
        return new DepositOperation(amount, todaysDate());
    }

    private Operation withdrawalOperationOf(Amount amount) {
        return new WithdrawalOperation(amount, todaysDate());
    }

    private Operation addOperation(Operation operation) {
        this.operations.add(operation);
        return operation;
    }
}
