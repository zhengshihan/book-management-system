package com.bookstore.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.exception.GroupNotFoundException;
import com.bookstore.model.Group;
import com.bookstore.repository.GroupRepository;
import com.bookstore.service.GroupService;

@Service
public class GroupServiceImpl implements GroupService {
	
	Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

	@Autowired
	private GroupRepository groupRepository;
	
	@Override
	public List<Group> getAllGroups() {
		// TODO Auto-generated method stub
		logger.trace("Entered get AllGroups method");
		List<Group> groups = groupRepository.findAll();
		return groups;
	}

	
	@Override
	public Group getGroup(long id) {
		// TODO Auto-generated method stub
		return groupRepository.findById(id).orElseThrow(() -> new GroupNotFoundException(id));
	}

	@Override
	public void deleteGroup(long id) {
		// TODO Auto-generated method stub
		Optional<Group> group = groupRepository.findById(id);
		if(group.isPresent()) {
			groupRepository.deleteById(id);
		}else {
			throw new GroupNotFoundException(id);
		}
		
		
	}

	@Override
	public Group updateGroup(long id, Group group) {
		// TODO Auto-generated method stub
		Group groupUpdated = groupRepository.save(group);
		return groupUpdated;
	}

	@Override
	public Group createGroup(Group group) {
		// TODO Auto-generated method stub
		Group createdGroup = groupRepository.save(group);
		return createdGroup;
	}
	

}
