package com.bookstore.service;

import java.util.List;

import com.bookstore.model.Role;
import com.bookstore.model.User;

public interface UserService {
	List<User>getAllUsers();
	User getUser(long id);
	User updateUser(long id,User user);
	User createUser(long groupId,User user);
	List<User>getUsersByGroupId(long id);
	void deleteUser(long id);
	List<User>getAllUsersByRoleId(long id);
	List<Role>getAllRolesByUserId(long id);
	User addUser(long roleId, User user );
	void deleteUserFromRoles(long roleId, long userId);
	
	

}
