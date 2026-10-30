package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.config.MiscellaneousUtils;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.ContactDetails;
import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.GlobalSearchIndex;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UserLoginUpdates;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.PasswordComplexityException;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.request.CreateLoginRequest;
import com.pratikshat.exicom.model.request.Login;
import com.pratikshat.exicom.model.response.NavResponse;
import com.pratikshat.exicom.repository.AccountsOwnerDetailsRepository;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.ForgoPassRepository;
import com.pratikshat.exicom.repository.GlobalSearchIndexRepository;
import com.pratikshat.exicom.repository.LoginUserRepository;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/login")
public class LoginController {

	@Autowired
	private AuthenticationManager authManager;

	@Autowired
	private AccountsOwnerDetailsRepository dbDev;

	@Autowired
	ContactDetailsRepository dbContactDetails;
	@Autowired
	BCryptPasswordEncoder passEncoder;
	@Autowired
	com.pratikshat.exicom.repository.UserRoleRepository dbRoles;
	@Autowired
	com.pratikshat.exicom.repository.LoginUserRepository dbLoginUser;
	@Autowired
	com.pratikshat.exicom.repository.LoginUserRolesRepository dbLoginUserRoles;
	@Autowired
	com.pratikshat.exicom.repository.ServiceRecourseRepository dbServiceResource;
	@Autowired
	com.pratikshat.exicom.repository.LocationRepository dbLocation;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	@Autowired
	com.pratikshat.exicom.repository.UserLoginUpdateRepository dbLoginUpdate;
	@Autowired
	SessionRegistry sessionRegistry;

	@Autowired
	LoginUserRepository dbUserService;

	@Autowired
	ForgoPassRepository dbForgotPass;

	@Autowired
	JdbcTemplate jdbc;

	@Autowired
	GlobalSearchIndexRepository dbGlobalSearch;

	@PostMapping
	@Transactional
	public EmployeeDetails doLogin(@Valid @RequestBody Login user,HttpServletResponse response)throws java.io.IOException {
		if(user.getUserEmailId()==null||user.getUserEmailId().equalsIgnoreCase("")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid request");
			return null;
		}
		user.setUserEmailId(user.getUserEmailId().toLowerCase());
		UserLoginUpdates loginupdate=dbLoginUpdate.findByUserEmailid(user.getUserEmailId());
//		if(loginupdate!=null&&loginupdate.isActive()) {
//			response.sendError(HttpServletResponse.SC_CONFLICT, "You are already logged in. Would you like to sign out all other session ?");
//			return null;
//		}
//		
		UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(user.getUserEmailId(), user.getUserPassword());
		authManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authToken);

		EmployeeDetails emp=dbDev.findByEmailId(user.getUserEmailId());
		if (emp == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User  not found");
			return null;
		}
		if (!isValidPassword(user.getUserPassword())) {
			throw new PasswordComplexityException( "Your password does not meet complexity requirements. Please change your password.");
		}
		if(user.getDeviceId()!=null) {
			emp.setDeviceId(user.getDeviceId());
		}
		if(user.getFcmToken()!=null) {
			emp.setFcmToken(user.getFcmToken());
			dbDev.save(emp);
		}
		java.util.Set<com.pratikshat.exicom.model.response.NavResponse> _nav=new java.util.HashSet<com.pratikshat.exicom.model.response.NavResponse>();
		// java.util.Set<Long> ids=new java.util.HashSet<Long>();
		// java.util.Iterator<com.pratikshat.exicom.model.response.NavResponse> item =dbDev.getMenueListByProfile(user.getUserEmailId()).iterator();
		// while(item.hasNext()) {
		// 	com.pratikshat.exicom.model.response.NavResponse n=item.next();
		// 	_nav.add(n);
		// }
		if(emp.getBusinessDomain()!=null&&emp.getBusinessDomain().getBusinessDomainId()==1){
			_nav=dbDev.getMenueListByProfile(emp.getEmailId(),new BusinessDomain(1));
		}
		if(emp.getBusinessDomain()!=null&&emp.getBusinessDomain().getBusinessDomainId()==2){
			_nav=dbDev.getMenueListByProfile(emp.getEmailId(),new BusinessDomain(2));
		}
		if(emp.getBusinessDomain()!=null&&emp.getBusinessDomain().getBusinessDomainId()==3){
			_nav=dbDev.getMenueListByProfile(emp.getEmailId(),new BusinessDomain(2));
		}

		emp.setNav(_nav);
		emp.setObjectAccess(dbProfile.getObjectsAccessByProfileId(emp.getUserProfile().getId()));
		emp.setRoleGroupAndAccount(dbProfile.getAccountAndRoleGroup(dbUserService.findByUsername(emp.getEmailId())));

		if(loginupdate==null) {
			loginupdate=new UserLoginUpdates();
		}
		loginupdate.setActive(true);
		loginupdate.setLastLoginDate(new java.util.Date());
		loginupdate.setLastSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
		loginupdate.setUserEmailid(user.getUserEmailId());
		dbLoginUpdate.save(loginupdate);
		try {
			Map<String,Object> location=jdbc.queryForMap("select IFNULL(location_location_id,0) as location_location_id from service_recource where user_email_id=?",emp.getEmailId());
			if(location!=null)
				emp.setLocationId(Long.parseLong(location.get("location_location_id").toString()));
		}catch(Exception ex) {

		}

		return emp;
	}

	@PostMapping("force")
	@Transactional
	public EmployeeDetails doForceLogin(@Valid @RequestBody Login user,HttpServletResponse response,HttpServletRequest request)throws java.io.IOException {
		if(user.getUserEmailId()==null||user.getUserEmailId().equalsIgnoreCase("")) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Invalid request");
			return null;
		}
		user.setUserEmailId(user.getUserEmailId().toLowerCase());
		UserLoginUpdates loginupdate=dbLoginUpdate.findByUserEmailid(user.getUserEmailId());
//		if(loginupdate!=null&&loginupdate.isActive()) {
//			try {
//			
//				List<Object> users= sessionRegistry.getAllPrincipals();
//			
//				for(Object obj:users) {
//					System.out.println(obj.getClass().getName());
//					if(obj instanceof com.pratikshat.exicom.entity.LoginUser) {
//						LoginUser u=(LoginUser)obj;
//					
//						if(u.getUsername().equalsIgnoreCase(user.getUserEmailId())) {
//						
//							List<SessionInformation> sinfo=	sessionRegistry.getAllSessions(obj, false);
//							for(SessionInformation s:sinfo) {
//								s.expireNow();
//							}
//						}
//					}
//				}
//				
//			}catch(Exception ex) {
//				ex.printStackTrace();
//			}
//			
//		}

		UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(user.getUserEmailId(), user.getUserPassword());
		authManager.authenticate(authToken);
		SecurityContextHolder.getContext().setAuthentication(authToken);
		EmployeeDetails emp=dbDev.findByEmailId(user.getUserEmailId());
		if (emp == null) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User  not found");
			return null;
		}
		if (!isValidPassword(user.getUserPassword())) {
			throw new PasswordComplexityException("Your password does not meet complexity requirements. Please change your password.");
		}
		if(user.getDeviceId()!=null) {
			emp.setDeviceId(user.getDeviceId());
		}
		if(user.getFcmToken()!=null) {
			emp.setFcmToken(user.getFcmToken());
			dbDev.save(emp);
		}
		java.util.Set<com.pratikshat.exicom.model.response.NavResponse> _nav=new java.util.HashSet<com.pratikshat.exicom.model.response.NavResponse>();
		java.util.Set<Long> ids=new java.util.HashSet<Long>();
		java.util.Iterator<com.pratikshat.exicom.model.response.NavResponse> item =dbDev.getMenueListByProfile(user.getUserEmailId()).iterator();
		while(item.hasNext()) {
			com.pratikshat.exicom.model.response.NavResponse n=item.next();
			_nav.add(n);
		}
		emp.setNav(_nav);
		emp.setRoleGroupAndAccount(dbProfile.getAccountAndRoleGroup(dbUserService.findByUsername(emp.getEmailId())));
		emp.setObjectAccess(dbProfile.getObjectsAccessByProfileId(emp.getUserProfile().getId()));

		if(loginupdate==null) {
			loginupdate=new UserLoginUpdates();
		}
		loginupdate.setActive(true);
		loginupdate.setLastLoginDate(new java.util.Date());
		loginupdate.setLastSessionId(RequestContextHolder.currentRequestAttributes().getSessionId());
		loginupdate.setUserEmailid(user.getUserEmailId());
		dbLoginUpdate.save(loginupdate);

		try {
			Map<String,Object> location=jdbc.queryForMap("select IFNULL(location_location_id,0) as location_location_id from service_recource where user_email_id=?",emp.getEmailId());
			if(location!=null)
				emp.setLocationId(Long.parseLong(location.get("location_location_id").toString()));
		}catch(Exception ex) {

		}

		return emp;
	}
	@Deprecated
	@PostMapping("mobile")
	public EmployeeDetails doLoginMobile(@Valid @RequestBody Login user,HttpServletResponse response)throws IOException {
		java.util.List<String> authorityList=new java.util.ArrayList<String>();
		authorityList.add("FIELD_SERVICE_ENG");
		authorityList.add("SCH_SCO");
		if(dbLoginUser.getCountOfUserName(user.getUserEmailId(), authorityList)>0) {
			UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(user.getUserEmailId(), user.getUserPassword());
			authManager.authenticate(authToken);
			SecurityContextHolder.getContext().setAuthentication(authToken);
			EmployeeDetails emp=dbDev.findByEmailId(user.getUserEmailId());
			if(emp!=null) {
				emp.setFcmToken(user.getFcmToken());
				emp.setDeviceId(user.getDeviceId());
				dbDev.saveAndFlush(emp);
			}
			emp.setNav(dbDev.getMenueList(user.getUserEmailId()));
			return emp;
		}
		else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Your are not authorised");
			return null;
		}
	}


	@PostMapping("create")
	@CacheEvict(value="usersForUserList")
	@Transactional(rollbackFor = {Exception.class,RuntimeException.class},isolation=Isolation.READ_UNCOMMITTED,propagation = Propagation.REQUIRES_NEW)
	public ResponseEntity<?> createLogin(@Valid @RequestBody CreateLoginRequest createLoginRequest,HttpServletResponse response,@CurrentUser LoginUser currentUser) throws IOException,Exception {
		java.util.Iterator ite=currentUser.getAuthorities().iterator();
		boolean isValidCurrentUser=false;
		Map<String,String> rls = dbProfile.getAccountAndRoleGroup(currentUser);

		if (!isValidPassword(createLoginRequest.getPassword())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
			return null;
		}

		if(rls.get("roleGroup").equalsIgnoreCase("ADMIN"))
			isValidCurrentUser=true;


		while(ite.hasNext()) {
			LoginUserRoles currentRoles=(LoginUserRoles)ite.next();
			if(currentRoles.getAuthority().equalsIgnoreCase("ADMIN")) {
				isValidCurrentUser=true;
			}
		}
		if(!isValidCurrentUser) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "You are not authorised");
			return null;
		}

		boolean validRequest=false;
		if(createLoginRequest.getAuthorities()==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Authority can not be null");
			return null;
		}
		com.pratikshat.exicom.entity.UserRoles ro=dbRoles.findByAuthorities(createLoginRequest.getAuthorities());
		if(ro==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Authority not found");
			return null;
		}
		if(!createLoginRequest.getPassword().equalsIgnoreCase(createLoginRequest.getConfirmPassword())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Pasword and confirm password did not match");
			return null;
		}
		com.pratikshat.exicom.model.response.LoginUserAccountsDetails userDetails=dbContactDetails.getLoginUserDetailsByContactId(createLoginRequest.getContactId());

		if(userDetails==null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Contact details does not exists");
			return null;
		}
		if(userDetails.getUserEmailId()==null||userDetails.getUserEmailId().length()==0||!MiscellaneousUtils.isValidEmailId(userDetails.getUserEmailId())) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Requested User does not have a valid emailid");
			return null;
		}
		if(userDetails.getAccountType().equalsIgnoreCase("vendor")) {
			//if(createLoginRequest.getAuthorities().equalsIgnoreCase("VENDOR")||createLoginRequest.getAuthorities().equalsIgnoreCase("FIELD_SERVICE_ENG")) {
			//	validRequest=true;
			//}
			//else {
				//response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Requested User can not have this authority");
				//return null;
			//}

			validRequest=true;
		}
		if(userDetails.getAccountType().equalsIgnoreCase("customer")) {
			if(createLoginRequest.getAuthorities().equalsIgnoreCase("CUSTOMER")) {
				validRequest=true;
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Customer can not have this authority");
				return null;
			}
		}
		if(userDetails.getAccountType().equalsIgnoreCase("self")||userDetails.getAccountType().equalsIgnoreCase("company")) {
			validRequest=true;
		}
		if(validRequest) {
			if(dbLoginUser.countByUsername(userDetails.getUserEmailId())!=0) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "User already exists for chosen contact");
				return null;
			}

			ContactDetails contact = dbContactDetails.findByContactId(createLoginRequest.getContactId());
			if(contact==null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Contact details does not exists");
				return null;
			}

			if(contact.getAccount()==null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Account does not exists");
				return null;
			}

			LoginUser _user=new LoginUser();

			_user.setAccountNonExpired(true);
			_user.setAccountNonLocked(true);
			_user.setCredentialsNonExpired(true);
			_user.setEnabled(true);
			_user.setPassword(passEncoder.encode(createLoginRequest.getPassword()));
			_user.setUsername(userDetails.getUserEmailId());
			dbLoginUser.save(_user);
			LoginUserRoles _roles=new LoginUserRoles();
			_roles.setAuthority(createLoginRequest.getAuthorities());
			_roles.setLoginUser(_user);
			dbLoginUserRoles.save(_roles);

			//Save details


			EmployeeDetails _emp=new EmployeeDetails();
			_emp.setEmailId(userDetails.getUserEmailId());
			_emp.setFirstName(userDetails.getUserFirstName());
			_emp.setLastName(userDetails.getUserLastName());
			_emp.setMiddleName(userDetails.getUserMiddleName());
			_emp.setContact(new com.pratikshat.exicom.entity.ContactDetails(createLoginRequest.getContactId()));
			_emp.setEmptype(contact.getAccount().getBusinessDoamin());
			_emp.setCreatedBy(new EmployeeDetails(currentUser.getUsername()));
			_emp.setCreatedDate(new Date());
			_emp.setModifiedBy(new EmployeeDetails(currentUser.getUsername()));
			_emp.setModifiedDate(new Date());
			_emp.setBusinessDomain(new BusinessDomain(contact.getBusinessDomain().getBusinessDomainId()));
			if(createLoginRequest.getApprovarManagerEmailId()!=null) _emp.setApprovalManager(new com.pratikshat.exicom.entity.EmployeeDetails(createLoginRequest.getApprovarManagerEmailId()));
			if(createLoginRequest.getManagerEmailId()!=null) _emp.setManager(new com.pratikshat.exicom.entity.EmployeeDetails(createLoginRequest.getManagerEmailId()));
			_emp.setUserProfile(new com.pratikshat.exicom.entity.UserProfile(createLoginRequest.getUserProfileId()));

			dbDev.save(_emp);

			com.pratikshat.exicom.entity.ServiceRecource _resource=dbServiceResource.getByEmail(_emp.getEmailId());
			if(_resource==null)
				_resource=new com.pratikshat.exicom.entity.ServiceRecource();
			_resource.setActive(true);
			_resource.setContact(new com.pratikshat.exicom.entity.ContactDetails(createLoginRequest.getContactId()));
			_resource.setCreatedBy(new com.pratikshat.exicom.entity.EmployeeDetails(currentUser.getUsername()));
			_resource.setCreatedDate(new java.util.Date());
			//_resource.setLocation(location);
			_resource.setModifiedBy(new com.pratikshat.exicom.entity.EmployeeDetails(currentUser.getUsername()));
			_resource.setModifiedDate(new java.util.Date());
			_resource.setBusinessDomain(new BusinessDomain(contact.getBusinessDomain().getBusinessDomainId()));
			String name="";
			if(userDetails.getUserFirstName()!=null && !userDetails.getUserFirstName().equalsIgnoreCase("")) {
				name = userDetails.getUserFirstName().trim();
			}
			if(userDetails.getUserMiddleName()!=null && !userDetails.getUserMiddleName().equalsIgnoreCase("")) {
				name = name + " "+userDetails.getUserMiddleName().trim();
			}
			if(userDetails.getUserLastName()!=null && !userDetails.getUserLastName().equalsIgnoreCase("")) {
				name = name + " "+ userDetails.getUserLastName().trim();
			}
			_resource.setName(name);
			_resource.setRecourceType(new com.pratikshat.exicom.entity.ResourceType(createLoginRequest.getResourceTypeId()==0?1:createLoginRequest.getResourceTypeId()));
			_resource.setUser(_emp);
			dbServiceResource.save(_resource);

			// Global Search Service Resource
			try {

				ServiceRecource serviceRecource =dbServiceResource.getByEmail(userDetails.getUserEmailId());
				GlobalSearchIndex _search=new GlobalSearchIndex();
				_search.setSearchString(serviceRecource.getName());
				_search.setKeyName("name");
				_search.setObjectName("ServiceRecource");
				_search.setObjectDetailsUrl("serviceResource/"+serviceRecource.getServiceResourceId());
				_search.setObjectIdName("serviceResourceId");
				_search.setObjectIdValue(String.valueOf(serviceRecource.getServiceResourceId()));

				dbGlobalSearch.save(_search);

			}catch(Exception e) {}

			//Service Resource 
			if(createLoginRequest.getLocationId()!=0) {
				com.pratikshat.exicom.entity.Location location=dbLocation.findByLocationId(createLoginRequest.getLocationId());
				if(location==null) {
					throw new BadRequestException("Location is not valid");
				}

				location.setOwner(_emp);
				dbLocation.save(location);

				_resource.setLocation(location);
				dbServiceResource.save(_resource);
			}

		}
		return ResponseEntity.ok("{\"ok\":\"ok\"}");
	}
	@PutMapping("update")
	@CacheEvict(value="usersForUserList")
	public void updateUserInfo(@RequestBody CreateLoginRequest userRequest,@CurrentUser LoginUser currentUser,HttpServletResponse response)throws java.io.IOException {
		if(userRequest.getContactId()==0) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid contact");
		}
		if(dbProfile.hasObjectAccess(currentUser.getUsername(),"EmployeeDetails", "editRights")) {
			com.pratikshat.exicom.entity.EmployeeDetails _emp=dbDev.findByContact(new com.pratikshat.exicom.entity.ContactDetails(userRequest.getContactId()));
			if(_emp==null) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No User found for this contact");
				return;
			}
			_emp.setApprovalManager(new com.pratikshat.exicom.entity.EmployeeDetails(userRequest.getApprovarManagerEmailId()));
			_emp.setManager(new com.pratikshat.exicom.entity.EmployeeDetails(userRequest.getManagerEmailId()));
			_emp.setUserProfile(new com.pratikshat.exicom.entity.UserProfile(userRequest.getUserProfileId()));
			dbDev.save(_emp);

			java.util.List<com.pratikshat.exicom.entity.LoginUserRoles> _roles= dbLoginUserRoles.findByLoginUser(new com.pratikshat.exicom.entity.LoginUser(_emp.getEmailId()));
			if(_roles!=null&&_roles.size()>0) {
				for(com.pratikshat.exicom.entity.LoginUserRoles _role:_roles) {
					dbLoginUserRoles.delete(_role);
				}
				com.pratikshat.exicom.entity.LoginUserRoles _newrole=new com.pratikshat.exicom.entity.LoginUserRoles();
				_newrole.setAuthority(userRequest.getAuthorities());
				_newrole.setLoginUser(new com.pratikshat.exicom.entity.LoginUser(_emp.getEmailId()));
				dbLoginUserRoles.save(_newrole);
			}
		}
		else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise to edit record");
			return;
		}
	}
	@GetMapping("authorities")
	public java.util.List<com.pratikshat.exicom.entity.UserRoles> getRoles(@CurrentUser LoginUser user){
		try {
		return dbRoles.findAll();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	@PostMapping("otp")
	public Map<String,String> getOtp(@RequestBody Map<String,String> req,HttpServletResponse response)throws IOException{
		try {
			dbForgotPass.sendOtp(req.get("email").toString());
			return new HashMap<String,String>();
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		return null;
	}
	@PostMapping("recover")
	public Map<String,String> getRecover(@RequestBody Map<String,String> req,HttpServletResponse response)throws IOException{
		try {
			dbForgotPass.setMailPass(req.get("email").toString(),req.get("otp").toString());
			return new HashMap<String,String>();
		} catch (BadRequestException e) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
		}
		return null;
	}

	@GetMapping("navigation/{bdid}")
	public Set<NavResponse> getNavigationModules(@PathVariable("bdid")int bdid,@CurrentUser LoginUser user){
		return dbDev.getMenueListByProfile(user.getUsername(),new BusinessDomain(bdid));
	}


	private boolean isValidPassword(String password) {

		String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{12,}$";
		return Pattern.compile(passwordPattern).matcher(password).matches();
	}
}
