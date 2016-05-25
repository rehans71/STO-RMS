/**
 * 
 */
package com.sto.rms.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sto.rms.entities.EmployeeSchedule;
import com.sto.rms.entities.EmployeeStatus;
import com.sto.rms.entities.User;
import com.sto.rms.model.EmployeeCalendar;
import com.sto.rms.model.EmployeeStatusVO;
import com.sto.rms.repositories.UserRepository;
import com.sto.rms.services.CalendarService;

/**
 * @author Siva
 *
 */
@Controller
public class ScheduleController extends BaseController
{	
	@Autowired
	private CalendarService calendarService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	protected String getHeaderTitle() {
		return "Calendar";
	}
	
	@RequestMapping("/calendar")
	public String home(Model model)
	{
		Map<String, EmployeeStatus> statusCodeMap = new HashMap<>();
		
		List<EmployeeStatus> allEmployeeStatuses = calendarService.getAllEmployeeStatuses();
		for (EmployeeStatus employeeStatus : allEmployeeStatuses)
		{
			statusCodeMap.put(employeeStatus.getCode(), employeeStatus);
		}
		List<EmployeeCalendar> calendars = new ArrayList<>();
				
		//<empId, <Month,(OV,OOO)>>
		Map<Integer, Map<String,List<EmployeeStatus>>> statusMap = new HashMap<>();
		
		int year = Calendar.getInstance().get(Calendar.YEAR);
		List<EmployeeSchedule> employeesSchedule = calendarService.getEmployeesSchedule(year);
		for (EmployeeSchedule employeeSchedule : employeesSchedule)
		{
			User user = employeeSchedule.getUser();
			Integer userId = user.getId();
			if(!statusMap.containsKey(userId)){
				statusMap.put(userId, new HashMap<>());
			}
			Map<String, List<EmployeeStatus>> userMap = statusMap.get(userId);
			Date fromDate = employeeSchedule.getFromDate();
			String month = new SimpleDateFormat("MMMM").format(fromDate).toUpperCase();
			
			if(!userMap.containsKey(month)){
				userMap.put(month, new ArrayList<>());
			}
			List<EmployeeStatus> statusList = userMap.get(month);
			
			EmployeeStatus status = employeeSchedule.getStatus();
			//EmployeeStatus employeeStatus = statusCodeMap.get(status);
			statusList.add(status);
		}
		
		List<User> users = userRepository.findAll();
		for (User user : users)
		{
			Integer userId = user.getId();
			EmployeeCalendar employeeCalendar = new EmployeeCalendar();
			employeeCalendar.setEmpId(userId);
			employeeCalendar.setEmpName(user.getName());
			
			if(!statusMap.containsKey(userId))
			{
				List<EmployeeStatusVO> employeeStatus = new ArrayList<>();
				for (int j = 1; j <= 12; j++) 
				{
					EmployeeStatusVO status = new EmployeeStatusVO();
					status.setYear(year);
					status.setMonth(j);
					String statusVal = statusCodeMap.get("AV_OFF").getDescription();
					status.setStatus(statusVal);
					status.setStyle(statusCodeMap.get("AV_OFF").getStyle());
					
					employeeStatus.add(status);
				}
				employeeCalendar.setEmployeeStatus(employeeStatus);
			}
			else
			{
				Map<String, List<EmployeeStatus>> userMap = statusMap.get(userId);
				List<EmployeeStatusVO> employeeStatus = new ArrayList<>();
				for (int j = 0; j < 12; j++) 
				{
					EmployeeStatusVO status = new EmployeeStatusVO();
					status.setYear(year);
					status.setMonth(j);
				    String monthName = getMonthName(j);
				   
					if(userMap.containsKey(monthName) && !userMap.get(monthName).isEmpty()){
						EmployeeStatus empStatus = userMap.get(monthName).get(0);
						status.setStatus(empStatus.getDescription());
						status.setStyle(empStatus.getStyle());
					} else {
						String statusVal = statusCodeMap.get("AV_OFF").getDescription();
						status.setStatus(statusVal);
						status.setStyle(statusCodeMap.get("AV_OFF").getStyle());
					}					
					employeeStatus.add(status);
				}
				employeeCalendar.setEmployeeStatus(employeeStatus);
				
			}
			calendars.add(employeeCalendar);
		}
		
		/*for (int i = 1; i <= 10; i++) 
		{
			EmployeeCalendar employeeCalendar = new EmployeeCalendar();
			employeeCalendar.setEmpId(i);
			employeeCalendar.setEmpName("Employee "+i);
			List<EmployeeStatusVO> employeeStatus = new ArrayList<>();
			for (int j = 1; j <= 12; j++) {
				EmployeeStatusVO status = new EmployeeStatusVO();
				status.setYear(year);
				status.setMonth(j);
				String statusVal = statusCodes[new Random().nextInt(statusCodes.length)];
				status.setStatus(statusVal);
				status.setStyle(statusCodeColorMap.get(statusVal));
				employeeStatus.add(status);
			}
			employeeCalendar.setEmployeeStatus(employeeStatus);
			
			calendars.add(employeeCalendar);
		}*/
		model.addAttribute("calendars", calendars);
		return "schedule/home";
	}

	public static String getMonthName(int month){
	    String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
	    						"JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
	    return monthNames[month];
	}
}
