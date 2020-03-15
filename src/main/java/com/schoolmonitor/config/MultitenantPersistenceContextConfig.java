
package com.schoolmonitor.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.hibernate.MultiTenancyStrategy;
import org.hibernate.cfg.Environment;
import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.schoolmonitor.config.MultitanentConfigurationProperties.DataSourceProperties;
import com.schoolmonitor.multitenacy.CurrentTenantIdentifierResolverImpl;
import com.schoolmonitor.multitenacy.DataSourceBasedMultiTenantConnectionProviderImpl;
import com.schoolmonitor.repositories.BaseRepositoryImpl;

/**
 * @author PrabhjeetS
 * @version 1.0 November 21, 2019
 */

@Configuration
@EnableTransactionManagement
//@EnableConfigurationProperties({ MultitanentConfigurationProperties.class, JpaProperties.class })
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, entityManagerFactoryRef = "multitenancyEntityManager", transactionManagerRef = "multitenancyTransactionManager", basePackages = {
		"com.schoolmonitor.repositories.schoolmonitor" })
public class MultitenantPersistenceContextConfig {

	@Autowired
	private JpaProperties jpaProperties;

	@Autowired
	private MultitanentConfigurationProperties multitenancyProperties;


	
	
	@Bean(name = "multitenancyDataSourceMap")
	public Map<String, DataSource> multitenancyDataSourceMap() {
		Map<String, DataSource> result = new HashMap<>();
		for (DataSourceProperties dataSourceProperties : this.multitenancyProperties.getDataSources()) {
			DataSourceBuilder<?> factory = DataSourceBuilder.create().url(dataSourceProperties.getUrl())
					.username(dataSourceProperties.getUsername()).password(dataSourceProperties.getPassword())
					.driverClassName(dataSourceProperties.getDriverClassName());
			result.put(dataSourceProperties.getTenantIdentifier(), factory.build());
		}
		return result;
	}


   @DependsOn("multitenancyDataSourceMap")
	@Bean("DataSourceBasedMultiTenantConnectionProvider")
	public MultiTenantConnectionProvider multiTenantConnectionProvider(@Qualifier("multitenancyDataSourceMap") Map<String, DataSource> multitanencyDataSourceMap) {
		 DataSourceBasedMultiTenantConnectionProviderImpl connectionProvider=new DataSourceBasedMultiTenantConnectionProviderImpl();
		 connectionProvider.setMultitanencyDataSourceMap(multitanencyDataSourceMap);
		 return connectionProvider;
	}

	

	@Bean("CurrentTenantIdentifierResolverImpl")
	public CurrentTenantIdentifierResolver currentTenantIdentifierResolver() {
		return new CurrentTenantIdentifierResolverImpl();
	}

	@Bean(name = "multitenancyEntityManagerFactoryBean")
	public LocalContainerEntityManagerFactoryBean multitenancyEntityManagerFactoryBean(
			@Qualifier("DataSourceBasedMultiTenantConnectionProvider")MultiTenantConnectionProvider multiTenantConnectionProvider,
			@Qualifier("CurrentTenantIdentifierResolverImpl")CurrentTenantIdentifierResolver currentTenantIdentifierResolver) {

		Map<String, Object> hibernateProps = new LinkedHashMap<>();
		hibernateProps.putAll(this.jpaProperties.getProperties());

		hibernateProps.put(Environment.MULTI_TENANT, MultiTenancyStrategy.DATABASE);
		hibernateProps.put(Environment.MULTI_TENANT_CONNECTION_PROVIDER, multiTenantConnectionProvider);
		hibernateProps.put(Environment.MULTI_TENANT_IDENTIFIER_RESOLVER, currentTenantIdentifierResolver);

		LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();
        
		result.setPackagesToScan("com.schoolmonitor.entities.schoolmonitor");
		result.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
		result.setJpaPropertyMap(hibernateProps);

		return result;
	}

	
	@Bean( name = "multitenancyEntityManager")
	public EntityManagerFactory multitenancyEntityManager(@Qualifier("multitenancyEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean multitanencyEntityManagerFactoryBean) {
		return multitanencyEntityManagerFactoryBean.getObject();
	}

	
	@Bean(name = "multitenancyTransactionManager")
	public PlatformTransactionManager multitenancyTransactionManager(
			@Qualifier("multitenancyEntityManager") EntityManagerFactory multitenancyEntityManager) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(multitenancyEntityManager);
		
		return txManager;
	}
}
