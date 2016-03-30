package com.sto.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sto.entities.Users;

public interface UserRepository extends JpaRepository<Users, Long>{
	
	@Query("select u from Users u where u.userID=?1")

//	Users findUsersByName(String name);
	Users findUsersByUserID(String userID);
	
}
