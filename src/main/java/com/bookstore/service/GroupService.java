package com.bookstore.service;

import java.util.List;

import com.bookstore.model.Group;


public interface GroupService {
	List<Group> getAllUsers();
	Group getGroup(long id);
	void deleteGroup(long id);
	Group updateGroup(long id, Group group);
	Group createGroup(Group group);


}
