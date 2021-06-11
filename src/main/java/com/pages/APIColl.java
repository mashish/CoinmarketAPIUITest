package com.pages;
import io.restassured.response.Response;


import org.junit.Assert;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;


import com.qa.factory.APIFactory;

import java.text.ParseException;
import java.util.HashMap;
import static org.hamcrest.Matchers.*;


public class APIColl {

    APIFactory apiFactory;
    public APIColl () {
    	apiFactory = new APIFactory();
    }

    private static String currID=null;
    private static Response response=null;

//
    //@Step("Retrieve Currency ID")
    public void retrieveCurrencyID (String currency_symbol){
        try {
            String endPoint = "/cryptocurrency/map";
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("symbol", currency_symbol);
            Response response = apiFactory.executeRequest(endPoint, APIFactory.RequestMethod.GET, parameter, 200);

            currID =  apiFactory.getResponseValue(response, "data.id[0]");
            System.out.println("____________CURR ID _____"+currID );
            Assert.assertTrue("Currency ID value is Null", currID!=null);
            Assert.assertTrue("Error while execute the request",
                    Integer.parseInt(apiFactory.getResponseValue(response, "status.error_code"))==0);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    //@Step("Convert Currency")
    public void convertToCurrency(String currency_symbol, String convertTo_Currency, int amount){
        try{
            String endPoint = "/tools/price-conversion";
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("id", currID);
            parameter.put("amount", String.valueOf(amount));
            parameter.put("convert", convertTo_Currency);

            response = apiFactory.executeRequest(endPoint, APIFactory.RequestMethod.GET, parameter, 200);

            String attribute = "data.quote." + convertTo_Currency.toUpperCase() +".price";
            String convrtedAmount  =  apiFactory.getResponseValue(response, attribute);
            Assert.assertTrue("Failed to convert amount '"+amount +"', from " +  currency_symbol + " to " + convertTo_Currency, !convrtedAmount.isEmpty());
            Assert.assertTrue("Error while execute the request",
                    Integer.parseInt(apiFactory.getResponseValue(response, "status.error_code"))==0);

        }catch (Exception e){
            e.printStackTrace();
            throw  e;
        }
    }

    //@Step("Retrieve Technical Document")
    public void retrieveTechnicalDoc (int currency_ID){
        try {
            String endPoint = "/cryptocurrency/info";
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("id", String.valueOf(currency_ID));
            response = apiFactory.executeRequest(endPoint, APIFactory.RequestMethod.GET, parameter, 200);

            String attribute = "data." + currency_ID + ".urls.website[0]";
            String techDocWebsite = apiFactory.getResponseValue(response, attribute);

            Assert.assertTrue("Technical Documentation Website is Null", techDocWebsite!=null);
            Assert.assertTrue("Error while execute the request",
                    Integer.parseInt(apiFactory.getResponseValue(response, "status.error_code"))==0);

        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

//    @Step("Verify Fields")
    public void verifyFields (int currency_ID, String expectedField, String expectedValues){
        try {
            String attribute;
            switch (expectedField.toLowerCase()){
                case "technical_doc":
                    attribute = "data." + currency_ID + ".urls." + expectedField.toLowerCase() + "[0]";
                    break;
                case "tags":
                    attribute = "data." + currency_ID + "." + expectedField.toLowerCase() + "[0]";
                    break;
                default:
                    attribute = "data." + currency_ID + "." + expectedField.toLowerCase();
            }


            String value = String.valueOf(apiFactory.getResponseValue(response, attribute));
            Assert.assertTrue(expectedField+ "-" + expectedValues+" mismatch", expectedValues.equalsIgnoreCase(value));
            System.out.println("**********"+ expectedField + "---" + expectedValues+" validated successfully**********");
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }
    
    public void checkMineandResponseCode(String currID) throws ParseException {
    	String attribute;
    	Assert.assertTrue(response.getStatusCode()==200);
        int dataLength = ((HashMap)(response.jsonPath().get("data"))).size();
       
        	attribute = "data." + currID + ".tags"  + "[0]";
        	String value = String.valueOf(apiFactory.getResponseValue(response, attribute));
        	Assert.assertTrue("Mimable tag present",value.equalsIgnoreCase("mineable"));
        	
//            if(((List)response.jsonPath().get("data.'"+i+"'.tags")).contains("mineable")){
//                System.out.println("**** " + response.jsonPath().get("data.'"+i+"'.name") +": currency Mime Test PASS" +  "response.getStatusCode() : " + response.getStatusCode());
//            }
        
    }

}