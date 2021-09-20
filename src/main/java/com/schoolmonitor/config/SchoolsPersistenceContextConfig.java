package com.schoolmonitor.config;

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

/**
 * @author PrabhjeetS
 * @version 1.0
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(repositoryBaseClass = BaseRepositoryImpl.class, entityManagerFactoryRef = "schoolsEntityManagerFactory", transactionManagerRef = "schoolsTransactionManager", basePackages = {
		"com.schoolmonitor.repositories.schools" })
public class SchoolsPersistenceContextConfig {

	@Autowired
	JpaPropertiesUtils jpaPropertiesUtils;
	@Value("${spring.schools.datasource.username}")
	String dataUsername;
	@Value("${spring.schools.datasource.password}")
	String password;
	@Value("${spring.schools.datasource.url}")
	String url;
	@Value("${spring.schools.datasource.driver-class-name}")
	String driverClassName;
    
	@Primary
	@Bean(name = "schoolsDataSource")
	public DataSource schoolmonitorDataSource() {

		HikariConfig dataSourceConfig = new HikariConfig();
		dataSourceConfig.setDriverClassName(driverClassName);
		dataSourceConfig.setJdbcUrl(url);
		dataSourceConfig.setUsername(dataUsername);
		dataSourceConfig.setPassword(password);

		return new HikariDataSource(dataSourceConfig);

	}
   @Primary
	@Bean(name = "schoolsEntityManagerFactory")
	public LocalContainerEntityManagerFactoryBean schoolsEntityManagerFactory(
			final @Qualifier("schoolsDataSource") DataSource schoolsDataSource, Environment environment) {

		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		vendorAdapter.setGenerateDdl(true);

		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
		factory.setJpaProperties(jpaPropertiesUtils.setEnvironment(environment).getJpaProperties());
		factory.setJpaVendorAdapter(vendorAdapter);
		factory.setPackagesToScan("com.schoolmonitor.entities.schools");
		factory.setDataSource(schoolsDataSource);
		return factory;
	}
@Primary
	@Bean(name = "schoolsTransactionManager")
	public PlatformTransactionManager schoolsTransactionManager(
			@Qualifier("schoolsEntityManagerFactory") EntityManagerFactory schoolsEntityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(schoolsEntityManagerFactory);
		return txManager;
	}
}
