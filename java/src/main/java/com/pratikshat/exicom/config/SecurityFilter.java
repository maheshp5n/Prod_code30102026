package com.pratikshat.exicom.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UserLoginUpdates;

@Component
public class SecurityFilter extends OncePerRequestFilter {

	@Autowired
	com.pratikshat.exicom.repository.UserLoginUpdateRepository dbLoginUpdate;

	List<ApiCallLog> logs=new ArrayList<ApiCallLog>();
	
	private static final Logger logger=LoggerFactory.getLogger(SecurityFilter.class);
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		LoginUser user=(LoginUser) authentication.getPrincipal();


//		
//		String sessionid=RequestContextHolder.currentRequestAttributes().getSessionId();
//		UserLoginUpdates u= dbLoginUpdate.findByUserEmailid(user.getUsername());
//		if(!u.getLastSessionId().equalsIgnoreCase(sessionid)) {
//			new SecurityContextLogoutHandler().logout(request, response, authentication);
//			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorised");
//		}
//		else {
//			filterChain.doFilter(request, response);
//		}
//		
//		logger.info("endpoint call ::  {} ",request.getRequestURI());
//		Map<String,String[]> rp=request.getParameterMap();
//		rp.keySet().forEach(ky->{
//			String pMap[] =rp.get(ky);
//			for(String p:pMap) {
//				logger.info("{} : {}",ky,p);
//			}
//		});
		
		filterChain.doFilter(request, response);
//		logger.info("Response :: "+response.getStatus()+" "+response.getContentType());
	}
	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) {
		String path=request.getServletPath();
		if(!path.startsWith("/api/"))
			return true;
		if(path.startsWith("/api/login")) {
			return true;
		}
		if(path.startsWith("/api")) {
			return false;
		}
		return true;
	}

	private boolean findLogRecent(String user,String api){
	// 	if(logs!=null&&logs.size()>0){
	// 		for(ApiCallLog lg:logs){
	// 			if(lg.getUserEmailId().equalsIgnoreCase(user)&&lg.getCallingApi().equalsIgnoreCase(api)){
	// 				Date d=new Date();
	// 				if((d.getTime()- lg.getCallingDate().getTime())<(1000*60)){
	// 					return true;
	// 				}
	// 				else 
	// 					return false;
	// 			}
	// 			else
	// 				return false;
	// 		}
	// 	}
	// 	else
	// 		return false;
		return true;
	 }

}
