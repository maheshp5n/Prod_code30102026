package com.pratikshat.exicom;



import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;

import com.pratikshat.exicom.repository.AccountsOwnerDetailsRepository;

import com.pratikshat.exicom.repository.LoginUserRepository;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ServletComponentScan
@SpringBootApplication
@EnableCaching
@EnableWebSocket
@EnableScheduling
@EnableSwagger2
public class ExicomApplication extends SpringBootServletInitializer  implements CommandLineRunner {

	@Autowired
	LoginUserRepository dbUserLogin;
	@Autowired
	AccountsOwnerDetailsRepository dbAcountOwner;
	@Autowired
	BCryptPasswordEncoder encode;
	@Autowired
	com.pratikshat.exicom.repository.ObjectDeffinitionRepo obRepo;
	@Autowired
	com.pratikshat.exicom.repository.ObjectFieldDefinitionRepo obFieldsRepo;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	public static void main(String[] args) {
		//SpringApplication.run(ExicomApplication.class, args);
		SpringApplication exicomApp=new SpringApplication(ExicomApplication.class);
		exicomApp.addListeners(new ApplicationPidFileWriter("pid"));
		exicomApp.run(args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(ExicomApplication.class);
	}
	
	@Override
	public void run(String... args) throws Exception {
		int noOfuser=(int) dbUserLogin.count();
		if(noOfuser==0) {
			LoginUser user=new LoginUser();
			user.setAccountNonExpired(true);
			user.setAccountNonLocked(true);
			user.setCredentialsNonExpired(true);
			user.setEnabled(true);
			LoginUserRoles _roles=new LoginUserRoles();
			_roles.setAuthority("ADMIN");
			com.pratikshat.exicom.entity.UserProfile uprofile=new com.pratikshat.exicom.entity.UserProfile();
			uprofile.setProfileName("ADMIN_PROFILE");
			dbProfile.save(uprofile);
			
			user.setPassword(encode.encode("raushan511"));
			user.setUsername("raushan@pratikshat.com");
			dbUserLogin.save(user);
			EmployeeDetails ownerDetails=new EmployeeDetails();
			ownerDetails.setEmailId("raushan@pratikshat.com");
			ownerDetails.setFirstName("Raushan");
			ownerDetails.setLastName("Kumar");
			ownerDetails.setUserProfile(uprofile);
			dbAcountOwner.save(ownerDetails);
			
			EmployeeDetails sap=new EmployeeDetails();
			sap.setEmailId("sap@exicom.in");
			sap.setFirstName("SAP");
			dbAcountOwner.save(sap);
		}
	
	}
	
	
}
