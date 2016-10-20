package com.sto.rms.web.controllers;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ch.qos.logback.core.util.StatusPrinter;

import com.sto.rms.entities.EmployeeStatus;
import com.sto.rms.repositories.StatusRepository;
import com.sto.rms.security.SecurityUtil;

@Controller
@Secured(SecurityUtil.MANAGE_USERS)
public class StatusController extends BaseController{
	
	@Autowired
	private StatusRepository statusRepository;

	private static final String viewPrefix = "events/";
	@Override
	protected String getHeaderTitle() {
		return "Manage Employees Events";
	}
	
	@RequestMapping(value="/events", method=RequestMethod.GET)
	public String listStatus(Model model){
		List<EmployeeStatus> events = statusRepository.findAll();
		model.addAttribute("events", events);
		return viewPrefix+"events";
		
	}
	
	@RequestMapping(value="/events/new", method=RequestMethod.GET)
	public String createNewEvent(Model model){
		
		EmployeeStatus empStatus = new EmployeeStatus();
		model.addAttribute("empStatus", empStatus);
		
		return viewPrefix+"create_event";
	}
	
	@RequestMapping(value="/events/new", method=RequestMethod.POST)
	public String newEventEntry(Model model, @ModelAttribute("empStatus") EmployeeStatus employeeStatus, RedirectAttributes redirectAttributes){
		
		statusRepository.save(employeeStatus);
		redirectAttributes.addFlashAttribute("info", "Event created successfully");
		return "redirect:/events";
	}
	
	@RequestMapping(value="/events/{id}", method=RequestMethod.GET)
	public String editEventEntryGet(@PathVariable Integer id, Model model){
		EmployeeStatus empStatus = statusRepository.getOne(id);
		model.addAttribute("empStatus", empStatus);
		
		return viewPrefix+"edit_event";
	}
	
	@RequestMapping(value="/events/{id}", method=RequestMethod.POST)
	public String editEventEntryPost(Model model, @ModelAttribute("empStatus") EmployeeStatus employeeStatus, RedirectAttributes redirectAttributes){
		
		statusRepository.save(employeeStatus);
		redirectAttributes.addFlashAttribute("info", "Event modified successfully");
		return "redirect:/events";
	}
	
	@RequestMapping(value="/events/del/{id}", method=RequestMethod.GET)
	public String deleteEventEntryPost(Model model, @PathVariable Integer id){
		EmployeeStatus empStatus = statusRepository.getOne(id);
		
		statusRepository.delete(empStatus);
		return "redirect:/events";
	}
}


