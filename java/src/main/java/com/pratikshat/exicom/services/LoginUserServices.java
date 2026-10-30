package com.pratikshat.exicom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.repository.LoginUserRepository;

@Service
public class LoginUserServices implements UserDetailsService {

	@Autowired
	LoginUserRepository dbLoginUser;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		LoginUser user=dbLoginUser.findByUsername(username);
		if(user==null)throw new UsernameNotFoundException(username);
		
		return user;
	}
}
