package Stepdef;

import org.testng.annotations.DataProvider;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;



@CucumberOptions(
		
		monochrome = true,
		glue = { "Stepdef" },
		features = { "src/test/resources/parallel/APIScenario.feature" }
)

public class ParallelRun extends AbstractTestNGCucumberTests {

}