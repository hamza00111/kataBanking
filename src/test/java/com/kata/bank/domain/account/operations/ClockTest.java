package com.kata.bank.domain.account.operations;

import org.junit.Test;

import java.time.Instant;
import java.time.ZoneId;

import static org.junit.Assert.assertEquals;

public class ClockTest {

    private Clock clock;


    @Test
    public void should_return_10_11_2000() {
        clock = new Clock(){
            @Override
            protected java.time.Clock clock() {
                return getClock("2000-11-10T10:15:30Z");
            }
        };
        assertEquals("10/11/2000", clock.todaysDateAsString());
    }

    @Test
    public void should_return_10_11_2020() {
        clock = new Clock(){
            @Override
            protected java.time.Clock clock() {
                return getClock("2020-11-10T10:15:30Z");
            }
        };
        assertEquals("10/11/2020", clock.todaysDateAsString());
    }

    private java.time.Clock getClock(String instantExpected) {
        return java.time.Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
    }
}