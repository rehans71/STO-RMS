/**
 * 
 */
package com.sto.rms.web.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sto.rms.entities.Permission;
import com.sto.rms.security.SecurityUtil;
import com.sto.rms.services.SecurityService;


/**
 * @author Siva
 *
 */
@Controller
@Secured(SecurityUtil.MANAGE_PERMISSIONS)
public class PermissionController extends BaseController
{
	private static final String viewPrefix = "permissions/";
	
	@Autowired protected SecurityService securityService;
	
	@Override
	protected String getHeaderTitle()
	{
		return "Manage Permissions";
	}
	
	@RequestMapping(value="/permissions", method=RequestMethod.GET)
	public String listPermissions(Model model) {
		List<Permission> list = securityService.getAllPermissions();
		model.addAttribute("permissions",list);
		return viewPrefix+"permissions";
	}

}
