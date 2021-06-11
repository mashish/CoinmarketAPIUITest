package Stepdef;


import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;

import java.text.ParseException;
import java.util.Map;

import com.pages.APIColl;
import com.qa.factory.APIFactory;

public class CoinMktAPIStepDef {

    private APIColl apiObj = new APIColl();
    
    @Then("^I should see the following fields for currency id \"(.*)\"$")
    public void iShouldSeeTheFollowingFields(int currency_ID, DataTable fields) {
        for (Map<Object, Object> field : fields.asMaps(String.class, String.class)) {
        	apiObj.verifyFields(currency_ID, (String) field.get("fields"), (String) field.get("values"));
        }
    }
    
    @Then("I validate response and mineable tag is associated with currency id {string}")
    public void i_validate_mineable_tag_is_associated_with_currency_id(String currency) throws ParseException {
    	apiObj.checkMineandResponseCode(currency);
    }

    @When("^I fetch ID for the currency \"(.*)\"$")
    public void iRetrieveTheIDOfCurrency(String currency_symbol) {
        apiObj.retrieveCurrencyID(currency_symbol);
    }

    @Then("^I convert \"(.*)\" to \"(.*)\" for \"(.*)\"$")
    public void iConvertToFor(String currency_symbol, String convertTo_Currency, int amount) {
    	apiObj.convertToCurrency(currency_symbol, convertTo_Currency, amount);
    }

    @When("^I fetch the Technical documentation Website for currency id \"(.*)\"$")
    public void iRetrieveTheTechnicalDocumentationWebsiteForCurrencyWithId(int currency_ID) {
    	apiObj.retrieveTechnicalDoc(currency_ID);
    }

    

    


}