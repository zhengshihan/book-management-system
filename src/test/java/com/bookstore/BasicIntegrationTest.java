package com.bookstore;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class BasicIntegrationTest {

public static String basicToken = "Basic Ynl0ZWNhcHRhaW46Ynl0ZWNhcHRhaW4=";
	
	public HttpHeaders getHttpHeader() {
		
		HttpHeaders headers = new HttpHeaders();
	    headers.set(HttpHeaders.AUTHORIZATION, basicToken);
	    headers.setContentType(MediaType.APPLICATION_JSON);
	     
	    return headers;
	}
}
