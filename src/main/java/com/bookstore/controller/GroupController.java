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
import com.bookstore.service.BookService;
import com.bookstore.service.GroupService;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class GroupController {
	
	@Autowired
	private GroupService groupService;

	
	@GetMapping("/bookstore/groups")
	public List<Group> getAllGroups(){
		List<Group> groups = groupService.getAllGroups();
		return groups;
	}
	@GetMapping("/bookstore/groups/{id}")
	public Group getBook(@PathVariable long id) {
		Group group = groupService.getGroup(id);
		return group;
	}
	

	
	@DeleteMapping("/bookstore/groups/{id}")
	public ResponseEntity<Void> deleteGroup(@PathVariable long id){
		groupService.deleteGroup(id);
		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}
	
	@PutMapping("/bookstore/groups/{id}")
	public ResponseEntity<Group>updateGroup(@PathVariable long id, @RequestBody Group group){
		Group groupUpdated = groupService.updateGroup(id, group);
		ResponseEntity<Group> responseEntity = new ResponseEntity<Group>(groupUpdated, HttpStatus.OK);
		return responseEntity;
	}
	
	@PostMapping("/bookstore/groups")
	public ResponseEntity<Void>createGroup(@RequestBody Group group){
		Group createdGroup  = groupService.createGroup(group);
		if(createdGroup == null)
			return ResponseEntity.noContent().build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdGroup.getId()).toUri();
		return ResponseEntity.created(uri).build();
	    
		
	}

}
