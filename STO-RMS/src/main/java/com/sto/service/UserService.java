package com.sto.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sto.entities.Role;
import com.sto.entities.Users;
import com.sto.repositories.RoleRepository;
import com.sto.repositories.UserRepository;

@Service
@Transactional
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired RoleRepository roleRepository;
	
	
	public Users findUsersByUserID(String name) {
		return userRepository.findUsersByUserID(name);
	}
	
	public void init()
	{
		try {
			Role role = new Role();
//			role.setRoleName("ROLE_USER");
			role.setRoleName("ROLE_ADMIN");
			
			
			
			Users user = new  Users("admin","Cedric","Davis","admin");
			List<Role> listRole = new ArrayList<Role>();
			
			listRole.add(role);
			user.setRoles(listRole);
			user.setEmail("admin@gmail.com");
			roleRepository.save(role);
			userRepository.save(user);
			String getRolesString = user.getRoles().get(0).getRoleName();
			
			System.err.println("User saved:" + user.getUserID()+"/"+user.getPassword()+ " with role as "+ getRolesString);
			
			role.getUsers().add(user);
			user.getRoles().add(role);
			
			roleRepository.save(role);
			
			List<Users> resultList = userRepository.findAll();
			System.out.println("Users: "+resultList.size());
			
			String listRoles = null;
			for (Role roles : user.getRoles()) {
				
				listRoles = roles.getRoleName(); 
			}
			for (Users user2 : resultList) {
				System.out.println(user2.getUserID()+":"+user.getPassword() + " roles-cedric: " + listRoles);
			}
			System.out.println(user.getUserID()+":"+user.getPassword() + " roles-cedric: " + role.getRoleName());
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
