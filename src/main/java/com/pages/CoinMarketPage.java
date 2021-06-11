package com.pages;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import com.qa.util.UtilFunc;

//import net.thucydides.core.annotations.Step;

public class CoinMarketPage {

	private WebDriver driver;

	// 1. By Locators: OR

	private By filterValue = By.xpath("//button[contains(text(),'50')]");
	private By showRowsDDL = By.xpath("//body/div[@id='__next']//div[@class='cmc-homepage']/div[1]/div[2]/div[3]/div[1]/div");
	private By filterbtn = By.xpath("(//button[text()='Filters'])[2]");
	private By addfilterbtn = By.xpath("//button[normalize-space()='+ Add Filter']");
	private By marketcapFilterButton = By.xpath("//button[normalize-space()='Market Cap']");
	private By priceFilterButton = By.xpath("//button[normalize-space()='Price']");
	private By minRangeValue = By.cssSelector("input[data-qa-id='range-filter-input-min']");
	private By maxRangeValue = By.cssSelector("input[data-qa-id='range-filter-input-max']");
	private By applyFilterButton = By.xpath("//button[normalize-space()='Apply Filter']");
	private By showFilterResultButton = By.xpath("//button[normalize-space()='Show results']");
	private By priceList = By.xpath("//div[contains(@class, 'price')]/a");
	private By marketpriceList = By.xpath("//div[contains(@class, 'sc-1anv')]/a");
	    

	// 2. Constructor of the page class:
	public CoinMarketPage(WebDriver driver) {
		this.driver = driver;
	}

	// 3. page actions: features(behavior) of the page the form of methods:




//	@Step("Set records to be shown on page")
	public void selectShowRowValue() throws InterruptedException {
		Boolean Display = driver.findElement(showRowsDDL).isDisplayed();
		Thread.sleep(1000);
		WebElement element = driver.findElement(showRowsDDL);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		Thread.sleep(5000);
		driver.findElement(filterValue).click();
		System.out.println("Value selected");
	}
	

//	@Step("Get Number of records ")
	public int getnumberofrows() {
		List<WebElement> rows = driver.findElements(
				By.xpath("//section[@display=\"flex\"]/following-sibling::div[2]/table//tbody/tr"));
		int rowCount = rows.size();
		return rowCount;
	}

	public void openfilterdialog() throws InterruptedException {
	driver.findElement(filterbtn).click();
	Thread.sleep(5000);
	driver.findElement(addfilterbtn).click();
	}
	
//	@Step("Set Filter")
	public void applyFilters(String filterNmae, String minrange,String maxrange) throws InterruptedException {
	
	
		switch (filterNmae.trim().toLowerCase()) {
        case "price":

        	driver.findElement(priceFilterButton).click();
        	UtilFunc.waitAndType(driver, minRangeValue, minrange);
        	UtilFunc.waitAndType(driver, maxRangeValue, maxrange);
        	//driver.findElement(minRangeValue).sendKeys(minrange);
        	//driver.findElement(maxRangeValue).sendKeys(maxrange);
        	System.out.println("Price Filter____"+ filterNmae + "___" + minrange + "___" + maxrange );
        	
        	
        	
            break;
        case "marketcap":
        	System.out.println("marketcarp Filter____"+ filterNmae + "___" + minrange + "___" + maxrange );
        	driver.findElement(marketcapFilterButton).click();
        	UtilFunc.waitAndType(driver, minRangeValue, minrange);
        	UtilFunc.waitAndType(driver, maxRangeValue, maxrange);
        	
        	break;
        
            
    }
		
	}
	
//	@Step("Apply Filter - ")
	public void applyFilterbutton() throws InterruptedException {
		driver.findElement(applyFilterButton).click();
		
	}

//	@Step("Show FIlter Results")
	public void showFilterResult() throws InterruptedException {
		driver.findElement(showFilterResultButton).click();
	}
	
	
//	@Step("Check Filter : Price")
	public boolean checkprice() throws InterruptedException, ParseException {
		System.out.println("**************************************" );
		List<WebElement> listitem=driver.findElements(priceList);
		listitem.size();
		boolean checkresult = true;
		System.out.println("Totakl elements validatred are " + listitem.size());
		
		Iterator<WebElement> itr = listitem.iterator();
		while(itr.hasNext()) {
		    String currencywithsymbol=itr.next().getText();
		    BigDecimal res = parse(currencywithsymbol, Locale.US);
		    if (res.compareTo(BigDecimal.valueOf(100))!=-1)
		    {
		    	checkresult=false;
		    }
		    if (res.compareTo(BigDecimal.valueOf(0))!=1)
		    {
		    	checkresult=false;
		    }
		    
}
			return checkresult;
	}
	
	
//	@Step("Check Filter : Market Cap")
	public boolean checkmarketcap() throws InterruptedException, ParseException {
		System.out.println("**************************************" );
		List<WebElement> listitem=driver.findElements(marketpriceList);
		listitem.size();
		boolean checkresult = true;
		System.out.println("Totakl elements validatred are " + listitem.size());
		
		Iterator<WebElement> itr = listitem.iterator();
		while(itr.hasNext()) {
		    String currencywithsymbol=itr.next().getText();
		    BigDecimal res = parse(currencywithsymbol, Locale.US);
		    if (res.compareTo(BigDecimal.valueOf(1000000000))!=1)
		    {
		    	checkresult=false;
		    }
		    
}
			return checkresult;
	}
	
	
	
	
	public static BigDecimal parse(final String amount, final Locale locale) throws ParseException {
	    final NumberFormat format = NumberFormat.getNumberInstance(locale);
	    if (format instanceof DecimalFormat) {
	        ((DecimalFormat) format).setParseBigDecimal(true);
	    }
	    return (BigDecimal) format.parse(amount.replaceAll("[^\\d.,]",""));
	}

//	@Step("Get Page Title")
	public String getLoginPageTitle() {
		return driver.getTitle();
	}

	

}
