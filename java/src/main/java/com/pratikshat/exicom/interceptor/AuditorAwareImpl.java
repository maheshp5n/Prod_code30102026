package com.pratikshat.exicom.interceptor;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;

public class AuditorAwareImpl implements AuditorAware<EmployeeDetails> {

	@Override
	public Optional<EmployeeDetails> getCurrentAuditor() {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		    if (authentication == null || !authentication.isAuthenticated()) {
		      return Optional.of(new EmployeeDetails("adminsfdc@exicom.in"));
		    }
		    LoginUser user=(LoginUser)authentication.getPrincipal();
		    
		    return Optional.of(new EmployeeDetails(user.getUsername()));
	}
}
