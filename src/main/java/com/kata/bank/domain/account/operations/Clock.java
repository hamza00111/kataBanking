package com.kata.bank.domain.account.operations;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Clock {

    public static final String DATE_FORMAT = "dd/MM/yyyy";

    public String todaysDateAsString() {
        return LocalDate.now(clock()).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    protected java.time.Clock clock() {
        return java.time.Clock.systemDefaultZone();
    }
}
