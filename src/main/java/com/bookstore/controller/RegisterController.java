package com.bookstore.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bookstore.model.Group;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.service.GroupService;
import com.bookstore.service.RoleService;
import com.bookstore.service.UserService;

@Controller
public class RegisterController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService; 
	
	@Autowired
	private GroupService groupService;
	// Go to Registration Page
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        List<Role> roles = roleService.getAllRoles();
        
        List<Group> groups = groupService.getAllGroups();
        model.addAttribute("roles", roles);
        model.addAttribute("groups",groups);
        return "registerUser";
    }
    
 // Read Form data to save into DB
 		@PostMapping("/saveUser")
 		public String saveUser(
 	            @ModelAttribute User user,
 	            @RequestParam(value = "roles", required = false) List<Long> roleIds, 
 	            @RequestParam(value = "group", required = false) long groupId,
 	            Model model
 				) 
 		{
 			 Set<Role> roleSet = new HashSet<>();
 			User savedUser = new User();
 		        if (roleIds != null) {
 		            roleSet = roleIds.stream()
 		                             .map(roleId -> roleService.getRole(roleId))  
 		                             .filter(role -> role != null)
 		                             .collect(Collectors.toSet());
 		        }
 		        user.setRoles(roleSet);
 		     
 	        // Save user
 		    User createdUser = userService.createUser(groupId, user);
 		    for(long roleId:roleIds) {
 		         savedUser	= userService.addUser(roleId, createdUser);
 		    }
 		   
 	        
 	        
 	        
 	        Long id = savedUser.getId();
 	        String message = "User '" + id + "' saved successfully!";
 	        model.addAttribute("msg", message);

 	        // Return to the registration page
 	        return "registerUser";
 		}
}
