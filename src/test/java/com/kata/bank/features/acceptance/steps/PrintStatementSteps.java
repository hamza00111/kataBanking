package com.kata.bank.features.acceptance.steps;

import com.kata.bank.domain.account.Account;
import com.kata.bank.domain.account.Amount;
import com.kata.bank.domain.account.operations.Clock;
import com.kata.bank.domain.account.operations.OperationRepository;
import com.kata.bank.domain.account.operations.printer.Console;
import com.kata.bank.domain.account.operations.printer.OperationsHistoryPrinter;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.mockito.*;

import static org.mockito.Mockito.when;

public class PrintStatementSteps {

    @Mock private Console console;
    @Mock private Clock clock;

    private OperationRepository operationRepository;
    private OperationsHistoryPrinter operationsHistoryPrinter;

    private Account account;

    @Given("an account")
    public void anAccount() {
        MockitoAnnotations.openMocks(this);
        operationRepository = new OperationRepository(clock);
        operationsHistoryPrinter = new OperationsHistoryPrinter(console);
        account = new Account(operationRepository, operationsHistoryPrinter);
    }

    @And("he deposits {int} EUR to his account")
    public void heDepositsEURToHisAccount(int deposit) {
        when(clock.todaysDateAsString())
                .thenReturn("10/11/2020");
        account.deposit(Amount.amountOf(Long.valueOf(deposit)));
    }

    @And("he withdraws {int} EUR from his account")
    public void heWithdrawsEURFromHisAccount(int withdraw) {
        when(clock.todaysDateAsString())
                .thenReturn("11/11/2020");
        account.withdraw(Amount.amountOf(Long.valueOf(withdraw)));
    }

    @When("he display operations history")
    public void heDisplayOperationsHistory() {
        account.printHistory();
    }

    @Then("his see following operations:")
    public void hisSeeFollowingOperations(DataTable dataTable) {
        InOrder inOrder = Mockito.inOrder(console);

        inOrder.verify(console).print("|OPERATION | DATE | AMOUNT | BALANCE|");
        inOrder.verify(console).print("|WITHDRAW | 11/11/2020 | -50.00 | 950.00|");
        inOrder.verify(console).print("|DEPOSIT | 10/11/2020 | 1000.00 | 1000.00|");
    }
}
