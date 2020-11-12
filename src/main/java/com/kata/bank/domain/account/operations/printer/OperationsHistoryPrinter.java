package com.kata.bank.domain.account.operations.printer;

import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.Operation;

import java.util.ArrayList;
import java.util.List;

import static com.kata.bank.domain.account.operations.printer.StatementLine.HEADER;

public class OperationsHistoryPrinter {


    public static final int ON_TOP = 0;

    private Console console;
    private List<String> statementLines = new ArrayList<>();

    public OperationsHistoryPrinter(Console console) {
        this.console = console;
    }

    public void addStatement(Operation operation, Amount balance) {
        this.statementLines.add(ON_TOP, statementOf(operation, balance));
    }

    public void print() {
        console.print(HEADER);
        for (String statementLine : statementLines) {
            console.print(statementLine);
        }
    }

    private String statementOf(Operation operation, Amount balance) {
        return new StatementLine(operation, balance).line();
    }


}
