/**
 * 
 */
package com.sto.rms.web.controllers;

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

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sto.rms.entities.EmployeeSchedule;
import com.sto.rms.entities.EmployeeStatus;
import com.sto.rms.entities.User;
import com.sto.rms.model.EmployeeCalendar;
import com.sto.rms.model.EmployeeStatusDurationVO;
import com.sto.rms.model.EmployeeStatusEvent;
import com.sto.rms.model.EmployeeStatusVO;
import com.sto.rms.repositories.UserRepository;
import com.sto.rms.services.CalendarService;
import com.sto.rms.utils.CommonUtils;

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
	public String home(Model model, @RequestParam(value="year", required=false) String strYear)
	{
		if(StringUtils.isBlank(strYear)) {
			strYear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		}
		Map<String, EmployeeStatus> statusCodeMap = new HashMap<>();
		
		List<EmployeeStatus> allEmployeeStatuses = calendarService.getAllEmployeeStatuses();
		for (EmployeeStatus employeeStatus : allEmployeeStatuses)
		{
			statusCodeMap.put(employeeStatus.getCode(), employeeStatus);
		}
		List<EmployeeCalendar> calendars = new ArrayList<>();
				
		//<empId, <Month,(OV,OOO)>>
		Map<Integer, Map<String,List<EmployeeStatusVO>>> statusMap = new HashMap<>();
		
		int year = NumberUtils.toInt(strYear, Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("year", year);
		
		List<EmployeeSchedule> employeesSchedule = calendarService.getEmployeesSchedule(year);
		for (EmployeeSchedule employeeSchedule : employeesSchedule)
		{
			User user = employeeSchedule.getUser();
			Integer userId = user.getId();
			if(!statusMap.containsKey(userId)){
				statusMap.put(userId, new HashMap<>());
			}
			Map<String, List<EmployeeStatusVO>> userMap = statusMap.get(userId);
			Date fromDate = employeeSchedule.getFromDate();
			String month = new SimpleDateFormat("MMMM").format(fromDate).toUpperCase();
			
			if(!userMap.containsKey(month)){
				userMap.put(month, new ArrayList<>());
			}
			List<EmployeeStatusVO> statusList = userMap.get(month);
			
			EmployeeStatus status = employeeSchedule.getStatus();
			//EmployeeStatus employeeStatus = statusCodeMap.get(status);
			EmployeeStatusVO statusVO = new EmployeeStatusVO();
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fromDate);
			statusVO.setMonth(calendar.get(Calendar.MONTH));
			statusVO.setYear(calendar.get(Calendar.YEAR));
			EmployeeStatusDurationVO duration = new EmployeeStatusDurationVO();
			duration.setStartDate(fromDate);
			duration.setEndDate(employeeSchedule.getToDate());
			duration.setStatus(status.getCode());
			duration.setStyle(status.getStyle());
			
			statusVO.getStatusDurations().add(duration );
			statusList.add(statusVO);
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
				Map<String, List<EmployeeStatusVO>> userMap = statusMap.get(userId);
				List<EmployeeStatusVO> employeeStatus = new ArrayList<>();
				for (int j = 0; j < 12; j++) 
				{
					EmployeeStatusVO status = new EmployeeStatusVO();
					status.setYear(year);
					status.setMonth(j);
				    String monthName = getMonthName(j);
				   
					if(userMap.containsKey(monthName) && !userMap.get(monthName).isEmpty()){
						List<EmployeeStatusVO> empStatusList = userMap.get(monthName);
						for (EmployeeStatusVO empStatus : empStatusList)
						{
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
			calendars.add(employeeCalendar);
		}
		
		model.addAttribute("calendars", calendars);
		return "schedule/calendar";
	}

	@RequestMapping("/calendar/events")
	@ResponseBody
	public List<EmployeeStatusEvent> events(Model model, 
			@RequestParam(value="empId") int empId,
			@RequestParam(value="year") int year,
			@RequestParam(value="month") int month) throws Exception
	{
		//month comes as zero-based value
		//month = month + 1;
		Date date = CommonUtils.buildDate(year, month, 1);
		Date start = CommonUtils.getStartOfMonth(date);
		Date end = CommonUtils.getEndOfMonth(date);
		
		List<EmployeeSchedule> empSchedules = calendarService.findByEmpStatusByDateRange(empId, start, end);
		
		List<EmployeeStatusEvent> events = new ArrayList<>();
		
		for (EmployeeSchedule employeeSchedule : empSchedules)
		{
			EmployeeStatusEvent e1 = new EmployeeStatusEvent();
			e1.setTitle(employeeSchedule.getStatus().getDescription());
			e1.setStart(new SimpleDateFormat("yyyy-MM-dd").format(employeeSchedule.getFromDate()));
			e1.setEnd(new SimpleDateFormat("yyyy-MM-dd").format(employeeSchedule.getToDate()));
			e1.setBorderColor("#f39c12");
			e1.setBackgroundColor("#f39c12");
			events.add(e1);
		}
				
		return events;
	}
	
	public static String getMonthName(int month){
	    String[] monthNames = {"JANUARY", "FEBRUARY", "MARCH", "APRIL", "MAY", "JUNE",
	    						"JULY", "AUGUST", "SEPTEMBER", "OCTOBER", "NOVEMBER", "DECEMBER"};
	    return monthNames[month];
	}
}
