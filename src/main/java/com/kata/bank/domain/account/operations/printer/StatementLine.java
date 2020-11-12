package com.kata.bank.domain.account.operations.printer;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.Operation;

import java.util.Arrays;

public class StatementLine {


    private static final String LIMIT = "|";
    private static final String SEPARATOR = " | ";
    public static final String HEADER = "|OPERATION | DATE | AMOUNT | BALANCE|";

    private Operation operation;
    private Amount balance;

    public StatementLine(Operation operation, Amount balance) {
        this.operation = operation;
        this.balance = balance;
    }

    public String line() {
        String operationType = operation.type();
        String date = operation.date();
        String amount = operation.amount();

        StringBuilder builder = new StringBuilder();
        String line = String.join(SEPARATOR, Arrays.asList(operationType, date, amount, balance.moneyFormat()));
        builder.append(LIMIT).append(line).append(LIMIT);

        return builder.toString();
    }

}
