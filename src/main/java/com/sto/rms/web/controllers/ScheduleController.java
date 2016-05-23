/**
 * 
 */
package com.sto.rms.web.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sto.rms.model.EmployeeCalendar;
import com.sto.rms.model.EmployeeStatus;

/**
 * @author Siva
 *
 */
@Controller
public class ScheduleController extends BaseController
{	
	@Override
	protected String getHeaderTitle() {
		return "Calendar";
	}
	
	@RequestMapping("/calendar")
	public String home(Model model)
	{
		List<EmployeeCalendar> calendars = new ArrayList<>();
		int year = 2016;
		String[] statusCodes = {"AV_OFF","AV_SHIP","OOO_VAC","OOO_SHIP","OOO_DD","OOO_NB"};
		Map<String, String> statusCodeColorMap = new HashMap<>();
		statusCodeColorMap.put("AV_OFF", "btn-info");
		statusCodeColorMap.put("AV_SHIP", "btn-primary");
		statusCodeColorMap.put("OOO_VAC", "btn-danger");
		statusCodeColorMap.put("OOO_SHIP", "btn-warning");
		statusCodeColorMap.put("OOO_DD", "btn-default");
		statusCodeColorMap.put("OOO_NB", "bg-olive");
		
		for (int i = 1; i <= 10; i++) 
		{
			EmployeeCalendar employeeCalendar = new EmployeeCalendar();
			employeeCalendar.setEmpId(i);
			employeeCalendar.setEmpName("Employee "+i);
			List<EmployeeStatus> employeeStatus = new ArrayList<>();
			for (int j = 1; j <= 12; j++) {
				EmployeeStatus status = new EmployeeStatus();
				status.setYear(year);
				status.setMonth(j);
				String statusVal = statusCodes[new Random().nextInt(statusCodes.length)];
				status.setStatus(statusVal);
				status.setStyle(statusCodeColorMap.get(statusVal));
				employeeStatus.add(status);
			}
			employeeCalendar.setEmployeeStatus(employeeStatus);
			
			calendars.add(employeeCalendar);
		}
		model.addAttribute("calendars", calendars);
		return "schedule/home";
	}

}
