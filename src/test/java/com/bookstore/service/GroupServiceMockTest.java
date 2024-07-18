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
public class GroupServiceMockTest {

	@Mock
	private GroupRepository groupRepository;
	
	@InjectMocks
	private GroupService groupService = new GroupServiceImpl();
	
	@Test
	public void getAllGroups() {
		List<Group>groups = Arrays.asList(
				new Group("user"),
				new Group("admin"));
		when(groupRepository.findAll()).thenReturn(groups);
		assertEquals(groups, groupService.getAllGroups());
				
	}
	@Test
    public void getGroup() {
		Group group = new Group("user");
    	
    	when(groupRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(group));
		assertEquals(group, groupService.getGroup(Long.valueOf(1)));
    }
	
    @Test
    public void getGroupNotFound() {
    	
    	GroupNotFoundException exception = assertThrows(
    			GroupNotFoundException.class,
    	           () -> groupService.getGroup(Long.valueOf(10001)),
    	           "Book id not found : 10001"
    	    );

    	    assertEquals("Group id not found : 10001", exception.getMessage());
    }
    
    @Test
    public void createGroup() {
    	Group group = new Group("user");
    	
    	when(groupRepository.save(group)).thenReturn(group);
		assertEquals(group, groupService.createGroup( group));
    }
    
    @Test
    public void updateGroup() {
    	Group group = new Group("user");
    	
    	when(groupRepository.save(group)).thenReturn(group);
		assertEquals(group, groupService.updateGroup(Long.valueOf(1), group));
    }
    
    @Test
    public void deleteGroup() {
    	
    	Group group = new Group("user");
    	
    	when(groupRepository.findById(Long.valueOf(1))).thenReturn(Optional.of(group));
    	groupService.deleteGroup(Long.valueOf(1));

		verify(groupRepository, times(1)).deleteById(Long.valueOf(1));
    }
    
	
}
