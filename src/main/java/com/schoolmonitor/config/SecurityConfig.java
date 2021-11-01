package com.schoolmonitor.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.schoolmonitor.security.JwtConfigurer;
import com.schoolmonitor.security.JwtTokenProvider;

/**
 * @author PrabhjeetS
 * @version 1.0
 */

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(Arrays.asList("http://localhost:8088","http://localhost:8080",
				"ec2-13-233-113-58.ap-south-1.compute.amazonaws.com", "http://localhost:4000"));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));

		configuration.setAllowedHeaders(
				Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept", "Authorization"));
		configuration.setAllowCredentials(true);// using Spring Security
		configuration.setMaxAge(3600L);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.cors().configurationSource(corsConfigurationSource())

		.and().csrf().disable().headers().frameOptions().disable().and().httpBasic().disable()
		.authorizeRequests().antMatchers("/auth/**").permitAll()
		
		.antMatchers("/v2/api-docs").permitAll()
        .antMatchers("/configuration/ui").permitAll()
        .antMatchers("/swagger-resources/**").permitAll()
        .antMatchers("/configuration/security").permitAll()
        .antMatchers("/swagger-ui.html").permitAll()
        .antMatchers("/swagger-ui/*").permitAll()
        .antMatchers("/webjars/**").permitAll()
        .antMatchers("/v2/**").permitAll()

		
		.antMatchers("/schoolmonitor/FeesManagment/**").hasAuthority("Student User")
		.antMatchers("/schoolmonitor/TeacherConsole/**").hasAuthority("Teacher User")
		.antMatchers("/schoolmonitor/AdminConsole/**").hasAuthority("Administrator")
		.antMatchers("/schoolmonitor/AttendanceManagment/**").permitAll()
		.antMatchers("/schoolmonitor/CourseManagment/**", "/schoolmonitor/studentDataUpload").permitAll()
		.antMatchers("/schoolmonitor/schoolDomains").anonymous()
		.antMatchers("/schoolmonitor/ResultManagment/**").hasAuthority("Student User").anyRequest()
		.authenticated().and().apply(new JwtConfigurer(jwtTokenProvider));

	}

	@Bean(name = "passwordEncoder")
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
