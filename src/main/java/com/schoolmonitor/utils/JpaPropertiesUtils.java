package com.schoolmonitor.utils;

import java.util.Properties;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Component
public class JpaPropertiesUtils {
	Properties jpaProperties;
	Environment environment;

	public Properties getJpaProperties() {
		jpaProperties = new Properties();
		jpaProperties.put("hibernate.dialect",
				environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
		jpaProperties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));
		jpaProperties.put("hibernate.ejb.naming_strategy",
				environment.getRequiredProperty("spring.jpa.hibernate.naming.physical-strategy"));
		jpaProperties.put("hibernate.show_sql", environment.getRequiredProperty("spring.jpa.show-sql"));
		return jpaProperties;
	}

	public void setJpaProperties(Properties jpaProperties) {
		this.jpaProperties = jpaProperties;
	}

	public JpaPropertiesUtils setEnvironment(Environment environment) {
		this.environment = environment;
		return this;
	}
}
