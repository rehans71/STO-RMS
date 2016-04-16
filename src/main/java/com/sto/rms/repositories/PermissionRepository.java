/**
 * 
 */
package com.sto.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sto.rms.entities.Permission;


/**
 * @author Siva
 *
 */
public interface PermissionRepository extends JpaRepository<Permission, Integer>
{

}
