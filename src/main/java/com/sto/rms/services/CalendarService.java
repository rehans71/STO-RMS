
package com.sto.rms.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sto.rms.entities.EmployeeSchedule;
import com.sto.rms.entities.EmployeeStatus;
import com.sto.rms.entities.User;
import com.sto.rms.model.EmployeeCalendar;
import com.sto.rms.model.EmployeeStatusDurationVO;
import com.sto.rms.model.EmployeeStatusVO;
import com.sto.rms.repositories.EmployeeScheduleRepository;
import com.sto.rms.repositories.EmployeeStatusRepository;
import com.sto.rms.repositories.UserRepository;
import com.sto.rms.utils.CommonUtils;

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
	
	@Autowired
	private UserRepository userRepository;
	
	public List<EmployeeStatus> getAllEmployeeStatuses()
	{
		return employeeStatusRepository.findAll();
	}

	public List<EmployeeSchedule> getEmployeesSchedule(int year)
	{
		return employeeScheduleRepository.findByYear(String.valueOf(year));
	}
	
	public List<EmployeeSchedule> findByEmpStatusByDateRange(Integer empId, Date start, Date end)
	{
		return employeeScheduleRepository.findByEmpStatusByDateRange(empId, start, end);
	}
	
	public List<EmployeeCalendar> getEmployeeCalendarsByYear(int year)
	{
		Map<String, EmployeeStatus> statusCodeMap = this.getStatusCodeMap();
		
		List<EmployeeCalendar> calendars = new ArrayList<>();

		//<empId, <Month,(OV,OOO)>>
		Map<Integer, Map<String,EmployeeStatusVO>> statusMap = this.getEmployeeSchedules(year);
		
		List<User> users = userRepository.findAll();
		for (User user : users)
		{
			EmployeeCalendar employeeCalendar = this.buildEmployeeCalendar(year, user, statusMap,statusCodeMap);
				
			calendars.add(employeeCalendar);
		}
		
		return calendars;
	}
	
	private Map<Integer, Map<String,EmployeeStatusVO>> getEmployeeSchedules(int year)
	{
		//<empId, <Month,(OV,OOO)>>
		Map<Integer, Map<String,EmployeeStatusVO>> statusMap = new HashMap<>();
		
		List<EmployeeSchedule> employeesSchedule = this.getEmployeesSchedule(year);
		
		for (EmployeeSchedule employeeSchedule : employeesSchedule)
		{
			User user = employeeSchedule.getUser();
			Integer userId = user.getId();
			if(!statusMap.containsKey(userId)){
				statusMap.put(userId, new HashMap<>());
			}
			Map<String, EmployeeStatusVO> userMap = statusMap.get(userId);
			Date fromDate = employeeSchedule.getFromDate();
			String month = new SimpleDateFormat("MMMM").format(fromDate).toUpperCase();
			
			if(!userMap.containsKey(month)){
				EmployeeStatusVO statusVO = new EmployeeStatusVO();

				Calendar calendar = Calendar.getInstance();
				calendar.setTime(fromDate);
				statusVO.setMonth(calendar.get(Calendar.MONTH));
				statusVO.setYear(calendar.get(Calendar.YEAR));
				
				userMap.put(month, statusVO);
			}
			EmployeeStatusVO statusVO = userMap.get(month);
			
			EmployeeStatus status = employeeSchedule.getStatus();
			
			EmployeeStatusDurationVO duration = new EmployeeStatusDurationVO();
			duration.setStartDate(fromDate);
			duration.setEndDate(employeeSchedule.getToDate());
			duration.setStatus(status.getCode());
			duration.setStyle(status.getStyle());
			
			statusVO.getStatusDurations().add(duration );
			
		}
		return statusMap;
	}
	
	private EmployeeCalendar buildEmployeeCalendar(
							int year, User user, 
							Map<Integer, Map<String,EmployeeStatusVO>> statusMap,
							Map<String, EmployeeStatus> statusCodeMap)
	{		
		Integer userId = user.getId();
		EmployeeCalendar employeeCalendar = new EmployeeCalendar();
		employeeCalendar.setEmpId(userId);
		employeeCalendar.setEmpName(user.getName());
		
		if(!statusMap.containsKey(userId))
		{
			List<EmployeeStatusVO> employeeStatus = new ArrayList<>();
			for (int j = 0; j < 12; j++) 
			{
				EmployeeStatusVO status = new EmployeeStatusVO();
				status.setYear(year);
				status.setMonth(j);
				
				EmployeeStatusDurationVO durationVO = new EmployeeStatusDurationVO();
				
				Date date = CommonUtils.buildDate(year, j, 1);
				Date startDate = CommonUtils.getStartOfMonth(date);
				Date endDate = CommonUtils.getEndOfMonth(date);
				durationVO.setStartDate(startDate);
				durationVO.setEndDate(endDate);
				String statusVal = statusCodeMap.get("AV_OFF").getDescription();
				durationVO.setStatus(statusVal);
				durationVO.setStyle(statusCodeMap.get("AV_OFF").getStyle());
				
				status.getStatusDurations().add(durationVO);
				
				employeeStatus.add(status);
			}
			employeeCalendar.setEmployeeStatus(employeeStatus);
		}
		else
		{
			Map<String, EmployeeStatusVO> userMap = statusMap.get(userId);
			List<EmployeeStatusVO> employeeStatus = new ArrayList<>();
			for (int j = 0; j < 12; j++) 
			{
				EmployeeStatusVO status = new EmployeeStatusVO();
				status.setYear(year);
				status.setMonth(j);
			    String monthName = CommonUtils.getMonthName(j);
			   
				if(userMap.containsKey(monthName) && userMap.get(monthName) != null)
				{
					EmployeeStatusVO empStatus = userMap.get(monthName);
					
					List<EmployeeStatusDurationVO> statusDurations = empStatus.getStatusDurations();
					Collections.sort(statusDurations, new Comparator<EmployeeStatusDurationVO>()
					{

						@Override
						public int compare(EmployeeStatusDurationVO o1, EmployeeStatusDurationVO o2)
						{
							return o1.getStartDate().compareTo(o2.getStartDate());
						}
					});
					int start = 1;
					for (EmployeeStatusDurationVO employeeStatusDurationVO : statusDurations)
					{
						Calendar calendar = Calendar.getInstance();
						calendar.setTime(employeeStatusDurationVO.getStartDate());
						int day = calendar.get(Calendar.DATE);
						if(day > start)
						{
							EmployeeStatusDurationVO durationVO = new EmployeeStatusDurationVO();
							
							Date startDate = CommonUtils.buildDate(year, j, start);
							Date endDate = CommonUtils.buildDate(year, j, day-1);
							durationVO.setStartDate(startDate);
							durationVO.setEndDate(endDate);
							
							durationVO.setStatus(statusCodeMap.get("AV_OFF").getDescription());
							durationVO.setStyle(statusCodeMap.get("AV_OFF").getStyle());
							
							status.getStatusDurations().add(durationVO);
						}
						EmployeeStatusDurationVO durationVO = new EmployeeStatusDurationVO();
						
						//Date date = CommonUtils.buildDate(year, j, 1);
						Date startDate = employeeStatusDurationVO.getStartDate();
						Date endDate = employeeStatusDurationVO.getEndDate();
						durationVO.setStartDate(startDate);
						durationVO.setEndDate(endDate);
						
						durationVO.setStatus(employeeStatusDurationVO.getStatus());
						durationVO.setStyle(employeeStatusDurationVO.getStyle());
						
						status.getStatusDurations().add(durationVO);
						
						calendar.setTime(employeeStatusDurationVO.getEndDate());
						day = calendar.get(Calendar.DATE);
						
						start = day + 1;
					}
					Calendar mycal = new GregorianCalendar(year, j, 1);
					int daysInMonth = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
					if(start <= daysInMonth)
					{
						EmployeeStatusDurationVO durationVO = new EmployeeStatusDurationVO();
						
						Date startDate = CommonUtils.buildDate(year, j, start);
						Date endDate = CommonUtils.buildDate(year, j, daysInMonth);
						durationVO.setStartDate(startDate);
						durationVO.setEndDate(endDate);
						
						durationVO.setStatus(statusCodeMap.get("AV_OFF").getDescription());
						durationVO.setStyle(statusCodeMap.get("AV_OFF").getStyle());
						
						status.getStatusDurations().add(durationVO);
					}
					
					
				} else {
					EmployeeStatusDurationVO durationVO = new EmployeeStatusDurationVO();
					
					Date date = CommonUtils.buildDate(year, j, 1);
					Date startDate = CommonUtils.getStartOfMonth(date);
					Date endDate = CommonUtils.getEndOfMonth(date);
					durationVO.setStartDate(startDate);
					durationVO.setEndDate(endDate);
					
					durationVO.setStatus(statusCodeMap.get("AV_OFF").getDescription());
					durationVO.setStyle(statusCodeMap.get("AV_OFF").getStyle());
					
					status.getStatusDurations().add(durationVO);
				}
				employeeStatus.add(status);
			}
			
			employeeCalendar.setEmployeeStatus(employeeStatus);
			
		}
			
		return employeeCalendar;
	}
	
	private Map<String, EmployeeStatus> getStatusCodeMap()
	{
		Map<String, EmployeeStatus> statusCodeMap = new HashMap<>();
		
		List<EmployeeStatus> allEmployeeStatuses = this.getAllEmployeeStatuses();
		for (EmployeeStatus employeeStatus : allEmployeeStatuses)
		{
			statusCodeMap.put(employeeStatus.getCode(), employeeStatus);
		}
		return statusCodeMap;
	}
}
