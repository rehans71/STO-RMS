package com.sto.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sto.entities.Role;
import com.sto.entities.Users;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	private String currentUser;
	private String listOfRoles;
	
	public String getCurrentUser() {
		return currentUser;
	}


	@Autowired
	private UserService userService;
	

	//I am not connected to Database, so all users will be stored here.
	//In your production app. these users must be stored in database.

//	User users = new User("test", "test", "ROLE_USER");


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	System.out.println("UserName from the DB: "+username);
    	
        Users userFromDB = findUserFromDatabase(username);
//        System.out.println("UserID from the DB: "+userFromDB.getUserID()
//        		+"/"+userFromDB.getPassword()+"/"+
//        		userFromDB.getRoles().get(0).getRoleName());
//        List<Role> userRolesFromDB = userFromDB.getRoles();
//        for (Role roles : userFromDB.getRoles()) {
//			listOfRoles = roles.getRoleName();
//    
//			if(userFromDB.getRoles().size() > 1)
//			listOfRoles = listOfRoles.concat(",");
//		}
//        String listOfRoles = userFromDB.getRoles().get(0).getRoleName();
//        System.out.println("list of Roles are: " + listOfRoles);
//        System.out.println(userFromDB.getUserID()+":"+userFromDB.getPassword()+"cedric-role-is:"+listOfRoles);
        return new org.springframework.security.core.userdetails.User(userFromDB.getUserID(), userFromDB.getPassword(),
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ADMIN,ROLE_USER"));
    }

    //here put your own implementation to fetch user from Database or from anywhere else you want.
    private Users findUserFromDatabase(String username) {
		Users user = userService.findUsersByUserID(username); //our own User model class
		currentUser = user.getFirstName() + " " + user.getLastName();
        //if user is not found, null will be returned back and Authentication will fail(spring will take care of that, you
        // dont need to worry about it), you just need to help spring with finding the user.
        return user;
    }
    
}
