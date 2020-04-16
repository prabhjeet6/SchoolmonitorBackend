
package com.schoolmonitor.multitenacy;

import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.engine.jdbc.connections.spi.AbstractDataSourceBasedMultiTenantConnectionProviderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;

/**
 * @author PrabhjeetS
 * @version 1.0 November 21, 2019
 */

/**
 * DataSourceBasedMultiTenantConnectionProviderImpl is subclass of Class
 * AbstractDataSourceBasedMultiTenantConnectionProviderImpl
 */
@Component
public class DataSourceBasedMultiTenantConnectionProviderImpl
		extends AbstractDataSourceBasedMultiTenantConnectionProviderImpl {

	@Qualifier("multitenancyDataSourceMap")
	@Autowired
	private Map<String, DataSource> multitanencyDataSourceMap;
	public Map<String, DataSource> getMultitanencyDataSourceMap() {
		return multitanencyDataSourceMap;
	}

	public void setMultitanencyDataSourceMap(Map<String, DataSource> multitanencyDataSourceMap) {
		this.multitanencyDataSourceMap = multitanencyDataSourceMap;
	}

	private static final long serialVersionUID = 1L;

	

	

	@Override
	protected DataSource selectAnyDataSource() {

		DataSource dataSource = this.multitanencyDataSourceMap.values().iterator().next();

		return dataSource;

	}

	@Override
	protected DataSource selectDataSource(String tenantIdentifier) {
		DataSource dataSource=null; 
		for(String key:this.multitanencyDataSourceMap.keySet()) {
			key.equalsIgnoreCase(tenantIdentifier);
			dataSource= this.multitanencyDataSourceMap.get(key);
			break;
		}

		return dataSource;

	}

}
