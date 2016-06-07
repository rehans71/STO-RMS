
package com.sto.rms.model;

import java.util.Date;

import com.sto.rms.utils.CommonUtils;

public class EmployeeStatusDurationVO
{
	private Date startDate;
	private Date endDate;
	private String status;
	private String style;

	public Date getStartDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public Date getEndDate()
	{
		return endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	
	public int getNumberOfDays(){
		return CommonUtils.getNumberOfDaysBetween(startDate, endDate);
	}

}
