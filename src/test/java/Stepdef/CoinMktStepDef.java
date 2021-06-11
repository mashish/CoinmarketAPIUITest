package Stepdef;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertEquals;

import java.text.ParseException;
import java.util.Map;

import org.junit.Assert;

import com.pages.CoinMarketPage;
import com.qa.factory.DriverFactory;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class CoinMktStepDef {

	private static String title;
	private CoinMarketPage coinMarketPage = new CoinMarketPage(DriverFactory.getDriver());

	@Given("I am on the homepage")
	public void I_am_on_the_homepage() {

		DriverFactory.getDriver()
				.get("https://coinmarketcap.com/");
	}

	@When("I gets the title of the page")
	public void I_gets_the_title_of_the_page() {
		title = coinMarketPage.getLoginPageTitle();
		System.out.println("Page title is: " + title);
	}

	@Then("page title should be {string}")
	public void page_title_should_be(String expectedTitleName) {
		Assert.assertTrue(title.contains(expectedTitleName));
	}



	@Then("I select {string} from the showrows option")
	public void i_select_from_the_showrows_option(String string) throws InterruptedException {
		coinMarketPage.selectShowRowValue();
	
	    
	}
	
	@Then("I validate {string} rows are displayed")
	public void i_validate_rows_are_displayed(int recordexpecrted) {
	    // Write code here that turns the phrase above into concrete actions
		int getnumberofrows=coinMarketPage.getnumberofrows();
		System.out.println("_____________"+getnumberofrows);
		assertEquals(getnumberofrows, recordexpecrted);
		
		
	}
	
	

@Then("I open filter dialog")
public void i_open_filter_dialog() throws InterruptedException {
	coinMarketPage.openfilterdialog();
    
}
	@Then("I select filter criteria")
	public void i_apply_filter(io.cucumber.datatable.DataTable filters) throws InterruptedException {
		
        for (Map<Object, Object> filter : filters.asMaps(String.class, String.class)) {
        	System.out.println("Applied filter " + filter.get("filtername") );
        	System.out.println("Applied filter " + filter.get("MinRange") );
        	System.out.println("Applied filter " + filter.get("MaxRange") );
        
        	coinMarketPage.applyFilters((String) filter.get("filtername"), (String) filter.get("MinRange"), (String) filter.get("MaxRange"));
        }
        coinMarketPage.applyFilterbutton();
        coinMarketPage.showFilterResult();
        Thread.sleep(5000);


	}
	
	@Then("I validate filtering results are correct")
	public void i_validate_filtering_results_are_correct() throws InterruptedException, ParseException {
	    boolean comopreResult=coinMarketPage.checkprice();
	    assertEquals (comopreResult, true);
	    comopreResult=coinMarketPage.checkmarketcap();
	    assertEquals (comopreResult, true);
	   
	 
	}

}
