
package com.schoolmonitor.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author PrabhjeetS
 * @version 1.0 Dec 9, 2019
 */
@Configuration
@ConfigurationProperties(prefix = "schoolmonitor")
public class MultitanentConfigurationProperties {

	private List<DataSourceProperties> customDataSourceProperties;

	public List<DataSourceProperties> getDataSources() {
		return customDataSourceProperties;
	}

	// #TODO: CustomDataSourceProperties is empty
	public void setDataSources(List<DataSourceProperties> customDataSourceProperties) {
		this.customDataSourceProperties = customDataSourceProperties;
	}

	public static class DataSourceProperties //extends org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
	{
		private String tenantIdentifier;
		private String url;
		private String username;
		private String password;
		private String DriverClassName;

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDriverClassName() {
			return DriverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			DriverClassName = driverClassName;
		}

		public String getTenantIdentifier() {
			return tenantIdentifier;
		}

		public void setTenantIdentifier(String tenantIdentifier) {
			this.tenantIdentifier = tenantIdentifier;
		}

	}
}
