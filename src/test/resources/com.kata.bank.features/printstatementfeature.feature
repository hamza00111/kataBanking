Feature: Print operations history - As a client of the bank I want to see operations history

  Scenario:  An existing client prints operations history

    Given an account
    And he deposits 1000 EUR to his account
    And he withdraws 50 EUR from his account
    When he display operations history
    Then his see following operations:
      |OPERATION | DATE | AMOUNT | Balance |
      |WITHDRAW | 11/11/2020 | -50.00| 950.00|
      |DEPOSIT | 10/11/2020 | 1000.00| 1000.00|
