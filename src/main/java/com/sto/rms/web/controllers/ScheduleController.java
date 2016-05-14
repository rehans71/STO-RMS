/**
 * 
 */
package com.sto.rms.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Siva
 *
 */
@Controller
public class ScheduleController extends BaseController
{	
	@Override
	protected String getHeaderTitle() {
		return "Schedule";
	}
	
	@RequestMapping("/schedule")
	public String home(Model model)
	{
		return "schedule/home";
	}

}
