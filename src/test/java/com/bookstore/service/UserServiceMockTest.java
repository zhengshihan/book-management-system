package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookstore.exception.GroupNotFoundException;
import com.bookstore.model.Group;
import com.bookstore.repository.GroupRepository;
import com.bookstore.service.impl.GroupServiceImpl;






@ExtendWith(SpringExtension.class)
public class UserServiceMockTest {

	@Mock
	private GroupRepository userRepository;
	
	@InjectMocks
	private GroupService userService = new GroupServiceImpl();
	
	@Test
	public void getAllGroups() {
		List<Group>groups = Arrays.asList(
				new Group("user"),
				new Group("admin"));
		when(userRepository.findAll()).thenReturn(groups);
		assertEquals(groups, userService.getAllUsers());
				
	}
	@Test
    public void getGroup() {
		Group group = new Group("user");
    	
    	when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(group));
		assertEquals(group, userService.getGroup(Long.valueOf(1)));
    }
	
    @Test
    public void getGroupNotFound() {
    	
    	GroupNotFoundException exception = assertThrows(
    			GroupNotFoundException.class,
    	           () -> userService.getGroup(Long.valueOf(10001)),
    	           "Book id not found : 10001"
    	    );

    	    assertEquals("Group id not found : 10001", exception.getMessage());
    }
    
    @Test
    public void createGroup() {
    	Group group = new Group("user");
    	
    	when(userRepository.save(group)).thenReturn(group);
		assertEquals(group, userService.createGroup( group));
    }
    
    @Test
    public void updateUser() {
    	Group group = new Group("user");
    	
    	when(userRepository.save(group)).thenReturn(group);
		assertEquals(group, userService.updateGroup(Long.valueOf(1), group));
    }
    
    @Test
    public void deleteUser() {
    	
    	Group group = new Group("user");
    	
    	when(userRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(group));
    	userService.deleteGroup(Long.valueOf(1));

		verify(userRepository, times(1)).deleteById(Long.valueOf(1));
    }
    
	
}
