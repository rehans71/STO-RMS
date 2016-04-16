/**
 * 
 */
package com.sto.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sto.rms.entities.Role;


/**
 * @author Siva
 *
 */
public interface RoleRepository extends JpaRepository<Role, Integer>
{

	Role findByName(String name);

}
