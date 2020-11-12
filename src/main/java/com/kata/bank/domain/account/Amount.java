package com.kata.bank.domain.account;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import java.util.Objects;

public class Amount {

    public static final String AMOUNT_FORMAT = "#.00";

    private final Long value;
    private DecimalFormat decimalFormatter = new DecimalFormat(AMOUNT_FORMAT);

    public Amount(long amount) {
        this.value = amount;
    }

    public static Amount amountOf(long amount) {
        return new Amount(amount);
    }

    public Amount negative() {
        return amountOf(-value);
    }

    public Amount plus(Amount amount) {
        return new Amount(amount.value + value);
    }

    public boolean isNegative() {
        return this.value < 0;
    }

    public String moneyFormat() {
        decimalFormatter.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        return decimalFormatter.format(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Amount amount = (Amount) o;
        return value.equals(amount.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
