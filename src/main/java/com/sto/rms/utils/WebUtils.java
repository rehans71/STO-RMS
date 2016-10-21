/**
 * 
 */
package com.sto.rms.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 *
 */
public class WebUtils
{
	private WebUtils()
	{
	}
	
	public static String getURLWithContextPath(HttpServletRequest request)
	{
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
	
	
}
