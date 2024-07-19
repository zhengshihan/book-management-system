package com.bookstore.service;

import java.util.List;

import com.bookstore.model.Role;
import com.bookstore.model.User;

public interface RoleService {
	Role getRole(long id);
	Role updateRole(long id,Role role);
	Role createRole(Role role);
	void deleteRole(long id);
	List<Role> getAllRoles();

	
	

}
