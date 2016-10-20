/**
 * 
 */
package com.sto.rms.web.controllers;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sto.rms.entities.EmployeeSchedule;
import com.sto.rms.entities.EmployeeStatus;
import com.sto.rms.entities.User;
import com.sto.rms.model.CalendarEntry;
import com.sto.rms.model.EmployeeCalendar;
import com.sto.rms.repositories.EmployeeScheduleRepository;
import com.sto.rms.repositories.EmployeeStatusRepository;
import com.sto.rms.services.CalendarService;
import com.sto.rms.services.SecurityService;

/**
 * @author Siva
 *
 */
@Controller
public class ScheduleController extends BaseController
{	
	@Autowired private CalendarService calendarService;
	@Autowired protected SecurityService securityService;
	@Autowired protected EmployeeStatusRepository employeeStatusRepository;
	@Autowired protected EmployeeScheduleRepository employeeScheduleRepository;
	
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
	
	@RequestMapping(value="/calendar/new", method=RequestMethod.GET)
	public String newCalendar( Model model)
	{
		//list of events
		//list of employees
		//model.addAttribute("events", events);
		
//		User user =  getCurrentUser().getUser();
//		calendarEntry.setEmpid(user.getId());
		CalendarEntry calendarEntry = new CalendarEntry();
		List<User> allUsers = this.securityService.getAllUsers();
		List<EmployeeStatus> allStatus = this.employeeStatusRepository.findAll();
//		calendarEntry.setStatus(allStatus);
//		EmployeeStatus empst = calendarEntry.getStatus().get(0);
//		logger.debug("status values id : {} and desc : {}", empst.getId(), empst.getDescription());
		EmployeeStatus empst = allStatus.get(1);
		logger.debug("status values id : {} and desc : {}", empst.getId(), empst.getDescription());
		
		model.addAttribute("allStatus", allStatus);
		model.addAttribute("allUsers", allUsers);
		model.addAttribute(calendarEntry);

		return "schedule/create_calendar";
	}

	@RequestMapping(value="/calendar/newEntry", method=RequestMethod.POST)
	public String newCalendarEntry(Model model, @ModelAttribute("allStatus") EmployeeStatus employeeStatus, @ModelAttribute("CalendarEntry") CalendarEntry calendarEntry, BindingResult result, RedirectAttributes redirectAttributes)
	{
//		/*get a new cal entry*/
//		CalendarEntry cal = new CalendarEntry();

//		/*set the emp ID*/
//		EmployeeCalendar empCal = new EmployeeCalendar();		
//		empCal.setEmpId(calendarEntry.getEmpid());
		
		/*get User for user name*/
//		User user = securityService.getUserById(calendarEntry.getEmpid());
		User user = calendarEntry.getUser();
		
//		empCal.setEmpName(user.getName());

		/*set EmployeeSchedule */
		EmployeeSchedule empSch = new EmployeeSchedule();
		empSch.setUser(user);
//		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//		Date startDate = new Date();
//		Date endDate = new Date();
//		simpleDateFormat.format(startDate);
//		simpleDateFormat.format(endDate);
//		calendarEntry.setStartDate(startDate);
//		calendarEntry.setEndDate(endDate);
		empSch.setToDate(calendarEntry.getStartDate());
		empSch.setFromDate(calendarEntry.getEndDate());
//		EmployeeStatus status = employeeStatusRepository.findOne(calendarEntry.getStatus().getId());
		empSch.setStatus(calendarEntry.getStatus().get(0));
//		logger.debug("Creating new Calendar entry with id : {} and FromDate : {} and TODate : {} and status", calendarEntry.getEmpid(), calendarEntry.getStartDate(), calendarEntry.getEndDate(), calendarEntry.getStatus().getId());
		logger.debug("Created new Calendar entry with id : {} and FromDate : {} and TODate : {} and status", empSch.getId(), empSch.getFromDate(), empSch.getToDate(), empSch.getStatus().getId());
		employeeScheduleRepository.save(empSch);
		
		redirectAttributes.addFlashAttribute("info", "Entry created successfully");

		
		//list of events
		//list of employees
		//model.addAttribute("events", events);
		
//		return "schedule/calendar";
		return "redirect:/calendar";
	}

//	@RequestMapping(value="/calendar/new", method=RequestMethod.POST)
//	public String createCalendarEntry(CalendarEntry calEntry)
//	{
//		//build EmployeeCalendar object from calEntry
//		return "schedule/create_calendar";
//	}
	
//	@RequestMapping("/calendar/events")
//	@ResponseBody
//	public List<EmployeeStatusEvent> events(Model model, 
//			@RequestParam(value="empId") int empId,
//			@RequestParam(value="year") int year,
//			@RequestParam(value="month") int month) throws Exception
//	{
//		//month comes as zero-based value
//		//month = month + 1;
//		Date date = CommonUtils.buildDate(year, month, 1);
//		Date start = CommonUtils.getStartOfMonth(date);
//		Date end = CommonUtils.getEndOfMonth(date);
//		
//		List<EmployeeSchedule> empSchedules = calendarService.findByEmpStatusByDateRange(empId, start, end);
//		
//		List<EmployeeStatusEvent> events = new ArrayList<>();
//		
//		for (EmployeeSchedule employeeSchedule : empSchedules)
//		{
//			EmployeeStatusEvent e1 = new EmployeeStatusEvent();
//			e1.setTitle(employeeSchedule.getStatus().getDescription());
//			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//			e1.setStart(simpleDateFormat.format(employeeSchedule.getFromDate()));
//			e1.setEnd(simpleDateFormat.format(employeeSchedule.getToDate()));
//			e1.setBorderColor("#f39c12");
//			e1.setBackgroundColor("#f39c12");
//			events.add(e1);
//		}
//				
//		return events;
//	}

}
