package com.bookstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bookstore.exception.GroupNotFoundException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.model.User;
import com.bookstore.repository.GroupRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

public class UserServiceImpl implements UserService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Override
	public List<User> getAllUsers() {
		
		logger.trace("Entered get AllUsers method");
		
		List<User> users = userRepository.findAll();
		
		// TODO Auto-generated method stub
		return users;
	}

	@Override
	public User getUser(long id) {
		// TODO Auto-generated method stub
		return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
	}

	
	@Override
	public User updateUser(long id, User user) {
		// TODO Auto-generated method stub
		User userUpdated = userRepository.save(user);
		return userUpdated;
	}

	@Override
	public User createUser(long groupId, User user) {
		// TODO Auto-generated method stub
		User createdUser = groupRepository.findById(groupId).map(group -> {
			user.setGroup(group);
			return userRepository.save(user);
		}).orElseThrow(() -> new GroupNotFoundException(groupId));
		return createdUser;
	}

	@Override
	public List<User> getUsersByGroupId(long id) {
		// TODO Auto-generated method stub
		if(!groupRepository.existsById(id)) {
			throw new GroupNotFoundException(id);
		}
		List<User> users = userRepository.findByGroupId(id);
		return users;
	}

	@Override
	public void deleteUser(long id) {
		// TODO Auto-generated method stub
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent()) {
			userRepository.deleteById(id);
		}else {
			throw new UserNotFoundException(id);
		}
		
	}
	
	

}
