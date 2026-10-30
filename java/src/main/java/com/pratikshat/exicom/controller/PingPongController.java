package com.pratikshat.exicom.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UserLoginUpdates;

@RestController
@RequestMapping("api/ping")
public class PingPongController {
	@Autowired
	com.pratikshat.exicom.repository.UserLoginUpdateRepository dbLoginUpdate;
	@GetMapping
	public ResponseEntity<?> ping(@CurrentUser LoginUser user,HttpServletResponse response,HttpServletRequest request) throws java.io.IOException{
		if(user==null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorised");
			return null;
		}
//		String sessionid=RequestContextHolder.currentRequestAttributes().getSessionId();
//		UserLoginUpdates u= dbLoginUpdate.findByUserEmailid(user.getUsername());
//		
//		if(!u.getLastSessionId().equalsIgnoreCase(sessionid)) {
//			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		    if (authentication != null){    
//		        new SecurityContextLogoutHandler().logout(request, response, authentication);
//		    }
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorised");
//			return null;
//		}
//		u.setActive(true);
//		dbLoginUpdate.save(u);
		
		return ResponseEntity.ok("{\"pong\":\"ok\"}");
	}
	@GetMapping("logout")
	public ResponseEntity<?> doLogout(@CurrentUser LoginUser user,HttpServletResponse response,HttpServletRequest request){
		UserLoginUpdates u=dbLoginUpdate.findByUserEmailid(user.getUsername());
		if(u.getLastSessionId().equalsIgnoreCase(RequestContextHolder.currentRequestAttributes().getSessionId())) {
			u.setActive(false);
			dbLoginUpdate.save(u);	
		}
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null){    
	        new SecurityContextLogoutHandler().logout(request, response, authentication);
	    }
		return ResponseEntity.ok("{\"pong\":\"ok\"}");
	}
}
