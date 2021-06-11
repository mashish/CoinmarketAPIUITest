package com.qa.factory;


import io.restassured.builder.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.*;
import java.util.HashMap;
import java.util.Properties;

import org.testng.annotations.Test;

import com.qa.util.ConfigReader;

import static io.restassured.RestAssured.*;

public class APIFactory {
	private ConfigReader configReader;
	Properties prop;
    // Request Method types
    public enum RequestMethod {
        GET,
        POST,
        PUT,
        DELETE
    }
    

    // executes request and returns response
    @Test
    public Response executeRequest(String endPoint, RequestMethod requestMth, HashMap<String, String> parameter, int statusCode) {
        Response response = null;
        try {
//        	baseURI = https://pro-api.coinmarketcap.com
//        		basePath = /v1
//        		endpoint_cryptocurrencyMap = /cryptocurrency/map
//        		endpoint_priceConvertion = /tools/price-conversion
//        		endpoint_metaData = /cryptocurrency/info
//        		accessKey = 
//        	prop = configReader.init_prop();
        	String baseURI = "https://pro-api.coinmarketcap.com";
        			
       
            String basePath = "/v1";
            String accessKey = "cabcc36a-cd0c-45af-95d5-44fd8b7e79bc";
            System.out.println("baseURI : " + baseURI);

            RequestSpecBuilder reqSpecBuild = new RequestSpecBuilder();
            reqSpecBuild.setBaseUri(baseURI);
            reqSpecBuild.setBasePath(basePath);
            reqSpecBuild.setAccept(ContentType.JSON);
            reqSpecBuild.addHeader("X-CMC_PRO_API_KEY", accessKey);
            reqSpecBuild.addParams(parameter);
            RequestSpecification requestSpecification = reqSpecBuild.build();

            ResponseSpecBuilder resSpecBuild = new ResponseSpecBuilder();
            resSpecBuild.expectStatusCode(statusCode);
            resSpecBuild.expectContentType(ContentType.JSON);
            ResponseSpecification responseSpecification = resSpecBuild.build();

            requestSpecification.log().all();
            System.out.println("*************** REQUEST*****************************");

            response = given().spec(requestSpecification).
                    when().request(requestMth.toString(), endPoint.trim());
            response.then().spec(responseSpecification);

            System.out.println("*************** RESPONSE*****************************");
            response.then().log().all();
//            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return response;
    }

    //Get Response value of a attribute
    public String getResponseValue(Response response, String attribute) {
        String responseVal=null;
        try {
            if (response.body().path(attribute)==null)
                responseVal = String.valueOf(responseVal);
            else
                responseVal = response.body().path(attribute).toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
        return responseVal;
    }

}