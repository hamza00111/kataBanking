package com.kata.bank.domain.account.operations;


import com.kata.bank.domain.account.Amount;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OperationRepository {

    private List<Operation> operations;
    private Clock clock;

    public OperationRepository(Clock clock) {
        this.clock = clock;
        operations = new ArrayList();
    }

    public Operation addDeposit(Amount depositAmount) {
        Operation deposit = operationOf(depositAmount);
        this.operations.add(deposit);
        return deposit;
    }

    public Operation addWithdrawal(Amount withdrawalAmount) {
        Operation withdrawal = operationOf(withdrawalAmount.negative());
        this.operations.add(withdrawal);
        return withdrawal;
    }

    public List<Operation> allOperations() {
        return Collections.unmodifiableList(operations);
    }

    private String todaysDate() {
        return clock.todaysDateAsString();
    }

    private Operation operationOf(Amount amount) {
        return new Operation(amount, todaysDate());
    }
}
