package com.sto.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Role {

	@Id
	@GeneratedValue
	private Long id;
	private String roleName;
	@ManyToMany(mappedBy = "roles", cascade=CascadeType.ALL)
	private List<Users> users;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	


	public Role() {
	}

	public Role(String roleName, List<Users> users) {
		super();
		this.roleName = roleName;
		this.users = users;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<Users> getUsers() {
		if(users == null){
			this.users = new ArrayList<Users>();
		}
		return users;
	}

	public void setUsers(List<Users> users) {
		this.users = users;
	}

}
