package br.com.consumo.gasolina.runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)

@CucumberOptions(plugin = {"html:target/cucumber", "json:target/cucumber.json"},
features = {"src/test/resources"},
//tags = {"@Teste"},
glue="br.com.consumo.gasolina.steps")

public class TestRunnerConsumoGasolina {

}
