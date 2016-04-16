/**
 * 
 */
package com.sto.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sto.rms.entities.User;


/**
 * @author Siva
 *
 */
public interface UserRepository extends JpaRepository<User, Integer>
{

	User findByEmail(String email);

}
