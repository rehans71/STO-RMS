/**
 * 
 */
package com.sto.rms.web.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sto.rms.entities.EmployeeSchedule;
import com.sto.rms.model.EmployeeCalendar;
import com.sto.rms.model.EmployeeStatusEvent;
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
		
		int year = NumberUtils.toInt(strYear, Calendar.getInstance().get(Calendar.YEAR));
		model.addAttribute("year", year);
		
		List<EmployeeCalendar> calendars = this.calendarService.getEmployeeCalendarsByYear(year);
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
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			e1.setStart(simpleDateFormat.format(employeeSchedule.getFromDate()));
			e1.setEnd(simpleDateFormat.format(employeeSchedule.getToDate()));
			e1.setBorderColor("#f39c12");
			e1.setBackgroundColor("#f39c12");
			events.add(e1);
		}
				
		return events;
	}

}
