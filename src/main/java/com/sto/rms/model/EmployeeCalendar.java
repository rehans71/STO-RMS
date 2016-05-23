/**
 * 
 */
package com.sto.rms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Siva
 *
 */
public class EmployeeCalendar 
{
	private Integer empId;
	private String empName;
	private List<EmployeeStatus> employeeStatus = new ArrayList<>();
	
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public List<EmployeeStatus> getEmployeeStatus() {
		return employeeStatus;
	}
	public void setEmployeeStatus(List<EmployeeStatus> employeeStatus) {
		this.employeeStatus = employeeStatus;
	}
	
}
