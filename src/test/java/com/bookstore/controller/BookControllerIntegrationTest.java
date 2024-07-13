package com.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.json.JSONException;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookstore.BasicIntegrationTest;
import com.bookstore.DemoApplication;
import com.bookstore.exception.BookNotFoundException;
import com.bookstore.model.Book;

import com.fasterxml.jackson.core.JsonProcessingException;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(value = OrderAnnotation.class)
@ActiveProfiles("test")
public class BookControllerIntegrationTest extends BasicIntegrationTest{
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	HttpHeaders headers = new HttpHeaders();
	
	@Test
	@Order(1)
	public void addBook() {
		Book book = new Book(10001, "bytecaptain", "Spring Boot Introduction","This is a book");
		HttpEntity<Book>entity = new HttpEntity<>(book,getHttpHeader());
		
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/bookstore/bytecaptain/books"), HttpMethod.POST,entity,String.class);
		String actualString = response.getHeaders().get(HttpHeaders.LOCATION).get(0);
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatusCode().value());
		assertTrue(actualString.contains("/bookstore/bytecaptain/books"));
	}
	
	@Test
	@Order(2)
	public void updateBook() throws JSONException {
		Book book = new Book(1, "bytecaptain", "Spring Boot Introduction updated","This is a book");
		
		HttpEntity<Book> entity = new HttpEntity<>(book, getHttpHeader());
		
		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/bookstore/bytecaptain/books/1"),
				HttpMethod.PUT,entity, String.class);
		
		assertEquals(HttpStatus.OK.value(), response.getStatusCode().value());
		
		String expected = "{\"id\":1,\"author\":\"bytecaptain\",\"title\":\"Spring Boot Introduction updated\",\"description\":\"This is a book\"}";
		
		JSONAssert.assertEquals(expected, response.getBody(), false);
	}
	
	@Test
	@Order(3)
	public void testGetBook() throws JSONException, JsonProcessingException {
		
		HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());
		
		ResponseEntity<String> response1 = restTemplate.exchange(
				createURLWithPort("/bookstore/bytecaptain/books/1"), 
				HttpMethod.GET, entity, String.class);
		
		String expected = "{\"id\":1,\"author\":\"bytecaptain\",\"title\":\"Spring Boot Introduction updated\",\"description\":\"This is a book\"}";
		
		JSONAssert.assertEquals(expected, response1.getBody(),false);
	}
	@Test
	@Order(4)
	public void testSearchBook() throws JSONException, JsonProcessingException {
		
		HttpEntity<String> entity = new HttpEntity<>(null, getHttpHeader());
		
		ResponseEntity<String> response1 = restTemplate.exchange(
				createURLWithPort("/bookstore/search?author=bytecaptain&title=Spring Boot Introduction updated"), 
				HttpMethod.GET, entity, String.class);
		
		String expected = "[{\"id\":1,\"author\":\"bytecaptain\",\"title\":\"Spring Boot Introduction updated\",\"description\":\"This is a book\"}]";
		
		JSONAssert.assertEquals(expected, response1.getBody(),false);
	}
	@Test
	@Order(5)
	public void testDeleteBook() {
		Book book = restTemplate.getForObject(createURLWithPort("/bookstore/bytecaptain/books/1"), Book.class);
		assertNotNull(book);
		
		HttpEntity<String> entity = new HttpEntity<>(null,getHttpHeader());
		ResponseEntity<String> response = restTemplate.exchange(createURLWithPort("/bookstore/bytecaptain/books/1"), HttpMethod.DELETE,entity,String.class);
		assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatusCode().value());
		
		try {
			book = restTemplate.getForObject("/bookstore/bytecaptain/books/1", Book.class);
		} catch (BookNotFoundException e) {
			assertEquals("Book id not found : 1", e.getMessage());
		}
	}
	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + "/api" + uri;
	}
}
