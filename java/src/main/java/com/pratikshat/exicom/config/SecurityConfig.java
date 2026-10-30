package com.pratikshat.exicom.config;

import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.http.HttpMethod;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.interceptor.AuditorAwareImpl;


@EnableTransactionManagement
@Configuration
@EnableWebSecurity
@EnableJpaAuditing(auditorAwareRef = "auditoerAware")
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationEntryPoint authEntryPoint;
	
	@Value("${file.upload.location}")
	private String fileStorageProperties;
	
//	@Value("${server.port}")
//	private int serverPortNumber;
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEndoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuditorAware<EmployeeDetails> auditoerAware(){
		return new AuditorAwareImpl();
	}
	
	@Bean
	SessionRegistry sessionRegistry() { 
	    return new SessionRegistryImpl(); 
	}
	
	/*@Bean
	public ServletListenerRegistrationBean<HttpSessionEventPublisher> httpSessionEventPublisher() { //(5)
	    return new ServletListenerRegistrationBean<HttpSessionEventPublisher>(new HttpSessionEventPublisher());
	}*/
	
	@Bean
	public AuthenticationManager customAuthenticationManager() throws Exception {
		return authenticationManager();
	}

	@Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
	
	@Bean
	public FileStorageProperties fileStorageProperties() {
		return new FileStorageProperties(fileStorageProperties);
	}

	@Bean
	DefaultFtpSessionFactory defaultFtpSessionFactory(
		@Value("${ftp.username}") String username,
		@Value("${ftp.password}") String pw,
		@Value("${ftp.host}") String host,
		@Value("${ftp.port}") int port) {
		DefaultFtpSessionFactory defaultFtpSessionFactory = new DefaultFtpSessionFactory();
		defaultFtpSessionFactory.setPassword(pw);
		defaultFtpSessionFactory.setUsername(username);
		defaultFtpSessionFactory.setHost(host);
		defaultFtpSessionFactory.setPort(port);
		defaultFtpSessionFactory.setClientMode(FTPClient.PASSIVE_LOCAL_DATA_CONNECTION_MODE);
		return defaultFtpSessionFactory;
	}

	@Bean
	FtpRemoteFileTemplate ftpRemoteFileTemplate(DefaultFtpSessionFactory dsf) {
		return new FtpRemoteFileTemplate(dsf);
	}


	@Override
	public void configure(AuthenticationManagerBuilder authBuilder) throws Exception {
		authBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEndoder());
	}
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

	    http
	        .headers()
	            .frameOptions().sameOrigin()  
	        .and()
	        .sessionManagement()
	            .maximumSessions(1)
	            .sessionRegistry(sessionRegistry())
	            .maxSessionsPreventsLogin(true)
	        .and()
	            .sessionFixation().newSession()
	        .and()
	            .logout()
	        .and()
	            .csrf().disable()
	            .exceptionHandling()
	                .authenticationEntryPoint(authEntryPoint)
	        .and()
	            .authorizeRequests()
	            .antMatchers(
	                "/",
	                "/**.woff2",
	                "/**.ttf",
	                "/**.js",
	                "/**.css",
	                "/assets/**",
	                "/api/login",
	                "/manifest.webmanifest",
	                "/ngsw.json",
	                "/index.html",
	                "/engrating/**",
	                "/bt/api/mobile/dashboard/app/version"
	            ).permitAll()
	            .anyRequest().authenticated();
	}

}
