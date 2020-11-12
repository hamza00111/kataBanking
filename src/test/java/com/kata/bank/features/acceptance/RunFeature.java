package com.kata.bank.features.acceptance;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:com.kata.bank.features",
glue = "com.kata.bank.features.acceptance.steps")
public class RunFeature {
}
