/**
 * 
 */
package com.sto.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Owner
 *
 */
@Component
@Transactional
public class DBInitializer {

	@Autowired
	private UserService service;
	
	@PostConstruct
	void init()
	{
		try {
			service.init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
