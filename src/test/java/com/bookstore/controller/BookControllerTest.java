package com.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bookstore.model.Book;
import com.bookstore.service.BookService;

import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(SpringExtension.class)
@WebMvcTest(value = BookController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class BookControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	private static final ObjectMapper om = new ObjectMapper();
	
	Book mockBook = new Book(10001,"Lee", "Love","This is a book");
	
	String exampleBookJson = "{\"id\":10001,\"author\":\"Lee\",\"title\":\"Love\",\"description\":\"This is a book\"}";
	String exampleBookJson1 = "[{\"id\":10001,\"author\":\"Lee\",\"title\":\"Love\",\"description\":\"This is a book\"}]";
	
	@Test
	public void getBook()throws Exception{
		Mockito.when(bookService.getBook("Lee", 10001)).thenReturn(mockBook);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookstore/Lee/books/10001").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		JSONAssert.assertEquals(exampleBookJson, result.getResponse().getContentAsString(), false);
	}
	@Test
	public void createBook()throws Exception{
		Book book = new Book(10001,"Lee", "Love","This is a book");
		Mockito.when(bookService.createBook(Mockito.anyString(), Mockito.any(Book.class))).thenReturn(book);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/bookstore/Lee/books").content(exampleBookJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		assertEquals("http://localhost/bookstore/Lee/books/10001", response.getHeader(HttpHeaders.LOCATION));
		
	}

	@Test
	public void deleteBook()throws Exception{
		doNothing().when(bookService).deleteBook("Lee", Long.valueOf(10001));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/bookstore/Lee/books/10001");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.NO_CONTENT.value(),response.getStatus());
	}
    @Test
    public void updateBook() throws Exception {

    	Book book = new Book(10001,"Lee", "Love","This is a book");

        Mockito.when(bookService.updateBook(Mockito.anyString(), Mockito.anyLong(), Mockito.any(Book.class))).thenReturn(book);
        
        String bookString = om.writeValueAsString(book);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/bookstore/Lee/books/10001")
                .contentType(MediaType.APPLICATION_JSON).content(bookString);
        

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(exampleBookJson, result.getResponse().getContentAsString(), false);

    }
	@Test
	public void searchBook()throws Exception{
		List<Book> books = Arrays.asList(mockBook);
		Mockito.when(bookService.searchBookByAuthorAndTitle("Lee", "Love")).thenReturn(books);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookstore/search").param("author", "Lee").param("title", "Love").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		JSONAssert.assertEquals(exampleBookJson1, result.getResponse().getContentAsString(), false);
	}
	
}
