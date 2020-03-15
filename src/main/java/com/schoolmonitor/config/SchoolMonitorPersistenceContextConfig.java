/*package com.schoolmonitor.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.schoolmonitor.repositories.BaseRepositoryImpl;
import com.schoolmonitor.utils.JpaPropertiesUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

*//**
 * @author PrabhjeetS
 * @version 1.0
 *//*
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, entityManagerFactoryRef = "schoolmonitorEntityManagerFactory", transactionManagerRef = "schoolmonitorTransactionManager", basePackages = {
		"com.schoolmonitor.repositories.schoolmonitor" })

public class SchoolMonitorPersistenceContextConfig {
	@Autowired
	JpaPropertiesUtils jpaPropertiesUtils;
	@Value("${spring.schoolmonitor.datasource.username}")
	String dataUsername;
	@Value("${spring.schoolmonitor.datasource.password}")
	String password;
	@Value("${spring.schoolmonitor.datasource.url}")
	String url;
	@Value("${spring.schoolmonitor.datasource.driver-class-name}")
	String driverClassName;

	@Bean(name = "schoolmonitorDataSource")
	@Primary
	public DataSource schoolmonitorDataSource() {

		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(driverClassName);
		dataSourceConfig.setJdbcUrl(url);
		dataSourceConfig.setUsername(dataUsername);
		dataSourceConfig.setPassword(password);

		return new HikariDataSource(dataSourceConfig);

	}

	@Primary
	@Bean(name = "schoolmonitorEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean schoolmonitorEntityManagerFactory(
			final @Qualifier("schoolmonitorDataSource") DataSource schoolmonitorDataSource, Environment environment) {
		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		factory.setJpaProperties(jpaPropertiesUtils.setEnvironment(environment).getJpaProperties());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.schoolmonitor.entities.schoolmonitor");
		factory.setDataSource(schoolmonitorDataSource);
		return factory;
	}

	@Primary
	@Bean(name = "schoolmonitorTransactionManager")
	public PlatformTransactionManager schoolmonitorTransactionManager(
			@Qualifier("schoolmonitorEntityManagerFactory") EntityManagerFactory schoolmonitorEntityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(schoolmonitorEntityManagerFactory);
		return txManager;
	}

}
*/