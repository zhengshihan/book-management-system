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
import com.bookstore.service.BookService;
import com.bookstore.service.GroupService;
import com.bookstore.service.RoleService;
@CrossOrigin(origins = { "http://localhost:3000", "http://localhost:4200" })
@RestController
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	

	@GetMapping("/bookstore/roles/{id}")
	public Role getRole(@PathVariable long id) {
		Role role = roleService.getRole(id);
		return role;
	}
	
	@GetMapping("/bookstore/roles")
	public List<Role> getAllRoles() {
		List<Role> roles = roleService.getAllRoles();
		return roles;
	}

	
	@DeleteMapping("/bookstore/roles/{id}")
	public ResponseEntity<Void> deleteRole(@PathVariable long id){
		roleService.deleteRole(id);
		ResponseEntity<Void> responseEntity = ResponseEntity.noContent().build();
		return responseEntity;
	}
	
	@PutMapping("/bookstore/roles/{id}")
	public ResponseEntity<Role>updateGroup(@PathVariable long id, @RequestBody Role role){
		Role roleUpdated = roleService.updateRole(id, role);
		ResponseEntity<Role> responseEntity = new ResponseEntity<Role>(roleUpdated, HttpStatus.OK);
		return responseEntity;
	}
	
	@PostMapping("/bookstore/roles")
	public ResponseEntity<Void>createGroup(@RequestBody Role role){
		Role createdRole  = roleService.createRole(role);
		if(createdRole == null)
			return ResponseEntity.noContent().build();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdRole.getId()).toUri();
		return ResponseEntity.created(uri).build();
	    
		
	}

}
