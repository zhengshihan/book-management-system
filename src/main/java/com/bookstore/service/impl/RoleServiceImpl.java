package com.bookstore.service.impl;



import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.exception.RoleNotFoundException;
import com.bookstore.model.Role;
import com.bookstore.repository.RoleRepository;
import com.bookstore.service.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public Role getRole(long id) {
		// TODO Auto-generated method stub
		
		return roleRepository.findById(id).orElseThrow(() -> new RoleNotFoundException(id));
		
	}

	@Override
	public Role updateRole(long id, Role role) {
		Role roleUpdated = roleRepository.save(role);	
		return roleUpdated;
	}

	@Override
	public Role createRole(Role role) {
		// TODO Auto-generated method stub
		Role roleCreated = roleRepository.save(role);
		return roleCreated;
	}

	@Override
	public void deleteRole(long id) {
		// TODO Auto-generated method stub
		Optional<Role> role = roleRepository.findById(id);
		if(role.isPresent()) {
			roleRepository.deleteById(id);
		}else {
			throw new RoleNotFoundException(id);
		}
		
	}

	@Override
	public List<Role> getAllRoles() {
		// TODO Auto-generated method stub
		List<Role>roles = roleRepository.findAll();
		return roles;
	}

}
