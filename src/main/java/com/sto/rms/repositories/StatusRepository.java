package com.sto.rms.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sto.rms.entities.EmployeeStatus;

public interface StatusRepository extends JpaRepository<EmployeeStatus, Integer>{

}
