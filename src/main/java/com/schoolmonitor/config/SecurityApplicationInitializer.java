/**
 * 
 */
package com.schoolmonitor.config;

import javax.servlet.ServletContext;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.web.multipart.support.MultipartFilter;

/**
 * @author PrabhjeetS
 * @version 1.0
   Feb 24, 2020
 */

	public class SecurityApplicationInitializer extends AbstractSecurityWebApplicationInitializer {

	  
	public SecurityApplicationInitializer() {
		super(SecurityConfig.class);
	}

	

		@Override
	    protected void beforeSpringSecurityFilterChain(ServletContext servletContext) {
	        insertFilters(servletContext, new MultipartFilter());
	    }
	}


