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
public class EmployeeStatusVO 
{
	private Integer year;
	private Integer month;
	private List<EmployeeStatusDurationVO> statusDurations = new ArrayList<>();
	
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	public List<EmployeeStatusDurationVO> getStatusDurations()
	{
		return statusDurations;
	}
	public void setStatusDurations(List<EmployeeStatusDurationVO> statusDurations)
	{
		this.statusDurations = statusDurations;
	}
	
}
