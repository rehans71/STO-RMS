/**
 * 
 */
package com.sto.rms.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import com.sto.rms.security.PostAuthorizationFilter;



/**
 * @author Siva
 *
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter
{   
	
	@Autowired
    private MessageSource messageSource;

	@Autowired 
	private PostAuthorizationFilter postAuthorizationFilter;
	
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }
    	
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		super.addViewControllers(registry);
        registry.addViewController("/login").setViewName("public/login");
		registry.addRedirectViewController("/", "/home");
		
	}
	
	@Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}
	
	@Bean 
	public ClassLoaderTemplateResolver emailTemplateResolver(){ 
		ClassLoaderTemplateResolver emailTemplateResolver = new ClassLoaderTemplateResolver(); 
		emailTemplateResolver.setPrefix("email-templates/"); 
		emailTemplateResolver.setSuffix(".html"); 
		emailTemplateResolver.setTemplateMode("HTML5"); 
		emailTemplateResolver.setCharacterEncoding("UTF-8"); 
		emailTemplateResolver.setOrder(2);
		
		return emailTemplateResolver; 
	}
	
	//http://stackoverflow.com/questions/25957879/filter-order-in-spring-boot
	@Bean
	public FilterRegistrationBean securityFilterChain(@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
	    registration.setOrder(Integer.MAX_VALUE - 1);
	    registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
	    return registration;
	}

	@Bean
	public FilterRegistrationBean PostAuthorizationFilterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(postAuthorizationFilter);
	    registrationBean.setOrder(Integer.MAX_VALUE);
	    return registrationBean;
	}
		
}
