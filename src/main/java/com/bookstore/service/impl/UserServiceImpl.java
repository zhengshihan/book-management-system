package com.bookstore.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bookstore.exception.GroupNotFoundException;
import com.bookstore.exception.RoleNotFoundException;
import com.bookstore.exception.UserNotFoundException;
import com.bookstore.model.Role;
import com.bookstore.model.User;
import com.bookstore.repository.GroupRepository;
import com.bookstore.repository.RoleRepository;
import com.bookstore.repository.UserRepository;
import com.bookstore.service.UserService;

@Service
public class UserServiceImpl implements UserService,UserDetailsService {
	Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
    private RoleRepository roleRepository;
	
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

	@Override
	public List<User> getAllUsersByRoleId(long id) {
		// TODO Auto-generated method stub
		if(!roleRepository.existsById(id)) {
			throw new RoleNotFoundException(id);
		}
		List<User> users = userRepository.findUsersByRolesId(id); 
		return users;
	}

	@Override
	public List<Role> getAllRolesByUserId(long id) {
		// TODO Auto-generated method stub
		if(!userRepository.existsById(id)) {
			throw new UserNotFoundException(id);
		}
		List<Role> roles = roleRepository.findRolesByUsersId(id);
		return roles;
	}

	@Override
	public User addUser(long roleId, User user) {
		// TODO Auto-generated method stub
		User addedUser = roleRepository.findById(roleId).map(
				role -> {
					long userId = user.getId();
				     
				      if (userId != 0L) {
				        User _user = userRepository.findById(userId)
				            .orElseThrow(() -> new UserNotFoundException(userId));
				        role.addUser(_user);
				        roleRepository.save(role);
				        return _user;
				      }
				      role.addUser(user);
				      return userRepository.save(user);		      
				}
				
				).orElseThrow(() -> new RoleNotFoundException(roleId));
		return addedUser;
				
	}

	@Override
	public void deleteUserFromRoles(long roleId, long userId) {
		// TODO Auto-generated method stub
		Role role = roleRepository.findById(roleId)
		        .orElseThrow(() -> new RoleNotFoundException(roleId));
		    
		    role.removeUser(userId);
		    roleRepository.save(role);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
Optional<User> opt = userRepository.findUserByUsername(username);
		
		if(opt.isEmpty())
				throw new UsernameNotFoundException("User with username: " +username +" not found !");
		else {
			User user = opt.get();
			return new org.springframework.security.core.userdetails.User(
					user.getUsername(),
					user.getPassword(),
					user.getRoles()
					.stream()
					.map(role-> new SimpleGrantedAuthority(role.getRoleName()))
					.collect(Collectors.toSet())
		    );
		}
	}
	
	

}
