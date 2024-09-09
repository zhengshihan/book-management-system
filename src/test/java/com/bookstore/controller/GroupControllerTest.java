package com.bookstore.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bookstore.model.Book;
import com.bookstore.model.Group;
import com.bookstore.service.BookService;
import com.bookstore.service.GroupService;
import com.fasterxml.jackson.databind.ObjectMapper;



@ExtendWith(SpringExtension.class)
@WebMvcTest(value = GroupController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class GroupControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	   @Mock
	    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@MockBean
	private GroupService groupService;
	
	private static final ObjectMapper om = new ObjectMapper();
	
	Group mockGroup = new Group(1L,"admin");
	
	String exampleGroupJson = "{\"id\":1,\"groupName\":\"admin\"}";
	String exampleGroupJson1 = "[{\"id\":1,\"groupName\":\"admin\"}]";
	
	@Test
	public void getGroup()throws Exception{
		Mockito.when(groupService.getGroup(1)).thenReturn(mockGroup);
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/bookstore/groups/1").accept(MediaType.APPLICATION_JSON);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		JSONAssert.assertEquals(exampleGroupJson, result.getResponse().getContentAsString(), false);
	}
	
	
	@Test
	public void createGroup()throws Exception{
		Group group = new Group(1L,"admin");
		Mockito.when(groupService.createGroup(Mockito.any(Group.class))).thenReturn(group);
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/bookstore/groups").content(exampleGroupJson)
				.contentType(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		
		assertEquals("http://localhost/bookstore/groups/1", response.getHeader(HttpHeaders.LOCATION));
		
	}

	@Test
	public void deleteGroup()throws Exception{
		doNothing().when(groupService).deleteGroup(Long.valueOf(10001));
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/bookstore/groups/10001");
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		MockHttpServletResponse response = result.getResponse();
		
		assertEquals(HttpStatus.NO_CONTENT.value(),response.getStatus());
	}
    @Test
    public void updateBook() throws Exception {

    	Group group = new Group(1L,"admin");

        Mockito.when(groupService.updateGroup(Mockito.anyLong(), Mockito.any(Group.class))).thenReturn(group);
        
        String groupString = om.writeValueAsString(group);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/bookstore/groups/1")
                .contentType(MediaType.APPLICATION_JSON).content(groupString);
        

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());

        JSONAssert.assertEquals(exampleGroupJson, result.getResponse().getContentAsString(), false);

    }

	
}
