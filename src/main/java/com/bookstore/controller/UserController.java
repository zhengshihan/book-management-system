package com.bookstore.controller;

import java.util.List;
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriBuilder;

import com.bookstore.model.Book;
import com.bookstore.model.Group;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.service.BookService;
import com.bookstore.service.GroupService;
import com.bookstore.service.UserService;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class UserController {
	
	@Autowired
	private UserService userService;

	
	@GetMapping("/bookstore/groups/{groupId}/users")
	public List<User> getAllUsersByGroup(@PathVariable long groupId){
		List<User> users = userService.getUsersByGroupId(groupId);
		return users;
	}
	@GetMapping("/bookstore/users/{id}")
	public User getUser(@PathVariable long id) {
		User user = userService.getUser(id);
		return user;
	}
	
	
	@DeleteMapping("/bookstore/users/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable long id){
		userService.deleteUser(id);
		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}
	
	
	@PutMapping("/bookstore/users/{id}")
	public ResponseEntity<User>updateUser(@PathVariable long id, @RequestBody User user){
		User userUpdated = userService.updateUser(id, user);
		ResponseEntity<User> responseEntity = new ResponseEntity<User>(userUpdated, HttpStatus.OK);
		return responseEntity;
	}
	
	@PostMapping("/bookstore/groups/{groupId}/users")
	public ResponseEntity<Void>createUser(@PathVariable long groupId,@RequestBody User user){
		User createdUser  = userService.createUser(groupId,user);
		if(createdUser== null)
			return ResponseEntity.noContent().build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	    
		
	}
	
	@GetMapping("/bookstore/roles/{roleId}/users")
	public List<User> getUsersByRole(@PathVariable long roleId ){
		List<User> users = userService.getAllUsersByRoleId(roleId);
		return users;
	}
	
	@GetMapping("/bookstore/users/{userId}/roles")
	public List<Role> getRolesByUser(@PathVariable long userId){
		List<Role> roles = userService.getAllRolesByUserId(userId);
		return roles;
	}
	
	@PostMapping("/bookstore/roles/{roleId}/users")
	public ResponseEntity<Void>addUser(@PathVariable long roleId,@RequestBody User user){
		User addedUser  = userService.addUser(roleId,user);
		if(addedUser== null)
			return ResponseEntity.noContent().build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(addedUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	    
		
	}
	@DeleteMapping("/bookstore/roles/{roleId}/users/{userId}")
	public ResponseEntity<Void> deleteUserFromRole(@PathVariable long roleId, @PathVariable long userId){
		userService.deleteUserFromRoles(roleId, userId);
		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}
	

}
