
package com.sto.rms.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sto.rms.entities.EmployeeSchedule;
import com.sto.rms.entities.EmployeeStatus;
import com.sto.rms.repositories.EmployeeScheduleRepository;
import com.sto.rms.repositories.EmployeeStatusRepository;

/**
 * @author Siva
 *
 */
@Service
@Transactional
public class CalendarService
{
	@Autowired
	private EmployeeStatusRepository employeeStatusRepository;
	
	@Autowired
	private EmployeeScheduleRepository employeeScheduleRepository;
	
	public List<EmployeeStatus> getAllEmployeeStatuses()
	{
		return employeeStatusRepository.findAll();
	}

	public List<EmployeeSchedule> getEmployeesSchedule(int year)
	{
		return employeeScheduleRepository.findByYear(String.valueOf(year));
	}
}
