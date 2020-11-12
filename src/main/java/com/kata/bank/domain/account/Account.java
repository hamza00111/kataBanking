package com.kata.bank.domain.account;

import com.kata.bank.domain.account.operations.exceptions.DepositOperationException;
import com.kata.bank.domain.account.operations.exceptions.WithdrawalOperationException;
import com.kata.bank.domain.account.operations.Operation;
import com.kata.bank.domain.account.operations.OperationRepository;
import com.kata.bank.domain.account.operations.printer.OperationsHistoryPrinter;

import static com.kata.bank.domain.account.Amount.amountOf;

public class Account {

    private Amount balance = amountOf(0);

    private OperationRepository operationRepository;
    private OperationsHistoryPrinter operationsHistoryPrinter;

    public Account(OperationRepository operationRepository,
                   OperationsHistoryPrinter operationsHistoryPrinter) {
        this.operationRepository = operationRepository;
        this.operationsHistoryPrinter = operationsHistoryPrinter;
    }

    public void deposit(Amount depositAmount) {
        Operation operation = operationRepository.addDeposit(depositAmount);

        if(operation == null) throw new DepositOperationException(depositAmount);

        updateBalanceAfter(operation);
        operationsHistoryPrinter.addStatement(operation, balance);
    }

    public void withdraw(Amount withdrawalAmount) {
        Operation operation = operationRepository.addWithdrawal(withdrawalAmount);

        if(operation == null) throw new WithdrawalOperationException(withdrawalAmount);

        updateBalanceAfter(operation);
        operationsHistoryPrinter.addStatement(operation, balance);
    }

    public void printHistory() {
        operationsHistoryPrinter.print();
    }

    public Amount currentBalance() {
        return balance;
    }

    private void updateBalanceAfter(Operation operation) {
        balance = operation.newBalance(balance);
    }

}
