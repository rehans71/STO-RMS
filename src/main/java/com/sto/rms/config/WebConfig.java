/**
 * 
 */
package com.sto.rms.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**
 * @author Siva
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{   
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		super.addViewControllers(registry);
		registry.addRedirectViewController("/", "/home");
		
	}
	
	
		
}
