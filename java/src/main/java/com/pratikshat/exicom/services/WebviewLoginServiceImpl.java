package com.pratikshat.exicom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.UserLoginUpdates;
import com.pratikshat.exicom.entity.WebviewLogin;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.AccountsOwnerDetailsRepository;
import com.pratikshat.exicom.repository.WebviewLoginRepository;
import static com.pratikshat.exicom.config.MiscellaneousUtils.*;

import java.util.List;

@Service
public class WebviewLoginServiceImpl implements WebviewLoginService {
	@Autowired
	WebviewLoginRepository dbRepo;
	@Autowired
	com.pratikshat.exicom.repository.UserLoginUpdateRepository dbLoginUpdate;
	@Autowired
	SessionRegistry sessionRegistry; 
	@Autowired
	private AuthenticationManager authManager;
	
	@Autowired
	private AccountsOwnerDetailsRepository dbDev;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	com.pratikshat.exicom.repository.LoginUserRepository dbLoginUser;

	@Override
	public String generateToken(LoginUser user, String usertoken, String callbackurl) {
		String servertoken=randomToken(8).toString();
		StringBuilder bd=new StringBuilder();
		bd.append(user.getUsername());
		bd.append("|");
		bd.append(servertoken);
		bd.append("|");
		bd.append(usertoken);
		bd.append("|");
		bd.append(callbackurl);
		String shahash=sha256(bd.toString());
		WebviewLogin _lg=new WebviewLogin();
		_lg.setCallbackurl(callbackurl);
		_lg.setRnsl(servertoken);
		_lg.setServerHash(shahash);
		_lg.setSto(usertoken);
		_lg.setUserEmailId(user.getUsername());
		dbRepo.save(_lg);
		return shahash;
	}

	@Override
	public EmployeeDetails getRoute(String usertoken, String hash,String callbackurl) throws BadRequestException,UnauthoriseException {
		WebviewLogin _lg=dbRepo.findByStoAndServerHashAndCallbackurl(usertoken, hash, callbackurl);
		if(_lg==null) throw new UnauthoriseException("You are not authorise");
		StringBuilder bd=new StringBuilder();
		bd.append(_lg.getUserEmailId());
		bd.append("|");
		bd.append(_lg.getRnsl());
		bd.append("|");
		bd.append(usertoken);
		bd.append("|");
		bd.append(callbackurl);
		String shahash=sha256(bd.toString());
		if(!shahash.equals(hash)) throw new UnauthoriseException("You are not authorise");
		
		UserLoginUpdates loginupdate=dbLoginUpdate.findByUserEmailid(_lg.getUserEmailId());
		if(loginupdate!=null&&loginupdate.isActive()) {
			try {
			
				List<Object> users= sessionRegistry.getAllPrincipals();
			
				for(Object obj:users) {
					System.out.println(obj.getClass().getName());
					if(obj instanceof com.pratikshat.exicom.entity.LoginUser) {
						LoginUser u=(LoginUser)obj;
					
						if(u.getUsername().equalsIgnoreCase(_lg.getUserEmailId())) {
						
							List<SessionInformation> sinfo=	sessionRegistry.getAllSessions(obj, false);
							for(SessionInformation s:sinfo) {
								s.expireNow();
							}
						}
					}
				}
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
		}
		UserDetails _u=dbLoginUser.findByUsername(_lg.getUserEmailId());
		System.out.println("User "+_lg.getUserEmailId()+" "+_u.getPassword());
		
		UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(_u,null,_u.getAuthorities());
		//authManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		EmployeeDetails emp=dbDev.findByEmailId(_lg.getUserEmailId());
		
		java.util.Set<com.pratikshat.exicom.model.response.NavResponse> _nav=new java.util.HashSet<com.pratikshat.exicom.model.response.NavResponse>();
		java.util.Set<Long> ids=new java.util.HashSet<Long>();
		java.util.Iterator<com.pratikshat.exicom.model.response.NavResponse> item =dbDev.getMenueListByProfile(_lg.getUserEmailId()).iterator();
		while(item.hasNext()) {
			com.pratikshat.exicom.model.response.NavResponse n=item.next();
			_nav.add(n);
		}
		emp.setNav(_nav);
		emp.setObjectAccess(dbProfile.getObjectsAccessByProfileId(emp.getUserProfile().getId()));
		
		if(loginupdate==null) {
			loginupdate=new UserLoginUpdates();
		}
		loginupdate.setActive(true);
		loginupdate.setLastLoginDate(new java.util.Date());
		loginupdate.setLastSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
		loginupdate.setUserEmailid(_lg.getUserEmailId());
		dbLoginUpdate.save(loginupdate);
		
		return emp;
	}


}
