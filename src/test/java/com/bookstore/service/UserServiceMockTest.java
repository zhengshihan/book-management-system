package com.bookstore.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.bookstore.exception.GroupNotFoundException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.model.Group;
import com.bookstore.model.User;
import com.bookstore.repository.GroupRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.impl.UserServiceImpl;

@ExtendWith(SpringExtension.class)
public class UserServiceMockTest {

    @Mock
    private UserRepository userRepository;
    
    @Mock
    private GroupRepository groupRepository;
    
    @InjectMocks
    private UserService userService = new UserServiceImpl();
    
    @Test
    public void getAllUsers() {
        List<User> users = Arrays.asList(
                new User("user1", "password1", new Group("group1")),
                new User("user2", "password2", new Group("group2")));
        when(userRepository.findAll()).thenReturn(users);
        assertEquals(users, userService.getAllUsers());
    }
    
    @Test
    public void getUserById() {
        User user = new User("user1", "password1", new Group("group1"));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        assertEquals(user, userService.getUser(1L));
    }
    
    @Test
    public void getUserByIdNotFound() {
    	UserNotFoundException exception = assertThrows(
    			UserNotFoundException.class,
    	           () -> userService.getUser(Long.valueOf(10001)),
    	           "User id not found : 10001"
    	    );

    	    assertEquals("User id not found : 10001", exception.getMessage());
    }
    
    @Test
    public void createUser() {
        Group group = new Group("group1");
        User user = new User("user1", "password1", group);
        
        when(groupRepository.findById(1L)).thenReturn(Optional.of(group));
        when(userRepository.save(user)).thenReturn(user);
        
        User createdUser = userService.createUser(1L, user);
        
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    public void updateUser() {
        User user = new User("user1", "password1", new Group("group1"));
        
        when(userRepository.save(user)).thenReturn(user);
        
        User updatedUser = userService.updateUser(1L, user);
        
        assertEquals(user, updatedUser);
        verify(userRepository, times(1)).save(user);
    }
    
    @Test
    public void getUsersByGroupId() {
        List<User> users = Arrays.asList(
                new User("user1", "password1", new Group("group1")),
                new User("user2", "password2", new Group("group1")));
        when(groupRepository.existsById(1L)).thenReturn(true);
        when(userRepository.findByGroupId(1L)).thenReturn(users);
        
        assertEquals(users, userService.getUsersByGroupId(1L));
    }
    

    
    @Test
    public void deleteUser() {
        User user = new User("user1", "password1", new Group("group1"));
        
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        userService.deleteUser(1L);
        
        verify(userRepository, times(1)).deleteById(1L);
    }
    

}
