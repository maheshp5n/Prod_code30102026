package com.pratikshat.exicom.controller;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ChangePassword;
import com.pratikshat.exicom.model.request.Contacts;
import com.pratikshat.exicom.model.response.SSAResponse;
import com.pratikshat.exicom.model.response.UserForList;
import com.pratikshat.exicom.repository.ContactDetailsRepository;
import com.pratikshat.exicom.repository.LoginUserRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("api/users")
public class UserController {
	final static Logger LOGGER=LogManager.getLogger(UserController.class);
	@Autowired
	LoginUserRepository dbUser;
	@Autowired
	BCryptPasswordEncoder encode;
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbUserProfile;
	
	@Autowired
	ContactDetailsRepository dbContact;
	
	@Autowired
	com.pratikshat.exicom.repository.UserRoleRepository dbRoles;
	
	int businessDomainId=2;
	
	@GetMapping("forlist")
	public java.util.List<com.pratikshat.exicom.model.response.UserForList> getUserForList(){
		return dbUser.getUserforList(true, businessDomainId);
		
	}
	@GetMapping("forlist/{contactid}")
	public java.util.List<com.pratikshat.exicom.model.response.UserForList> getUserForListByContactId(@PathVariable("contactid")long contactid){
		
//		Contacts contacts= dbContact.getContacts(contactid);
//		if(contacts!=null) {
//			if(contacts.getLegalEntityId()!=0) {
//				List<UserForList> contactListWithLegalEntity = dbUser.getUserByLegalEntity(true,contacts.getLegalEntityId());
//				List<UserForList> contactListWithoutLegalEntity = dbUser.getUserWithNoLegalEntity(true);
//				
//				contactListWithLegalEntity.addAll(contactListWithoutLegalEntity);
//				
//				return contactListWithLegalEntity;
//				
//			}else {
//				return dbUser.getUserWithNoLegalEntity(true);
//			}
//		}
		return dbUser.getUserforList(true, businessDomainId);
	}
	@PostMapping("changepassword")
	public Map<String,Object> changePassword(@CurrentUser LoginUser user,@RequestBody ChangePassword changePassword,HttpServletResponse response) throws IOException {
		
		try {
		
			if(!changePassword.getConfirmPassword().equals(changePassword.getNewPassword())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Confirm password does not match with new password");
				return null;
			}
			if (!isValidPassword(changePassword.getNewPassword())) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
				return null;
			}
			
			LoginUser _user=dbUser.findByUsername(user.getUsername());
			if(encode.matches(changePassword.getOldPassword(), _user.getPassword())) {
				_user.setPassword(encode.encode(changePassword.getNewPassword()));
				dbUser.save(_user);
				return new HashMap<String,Object>();
			}
			else {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST,"Wrong password");
			}
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
		}
		return null;
	}
	@GetMapping
	public Page<com.pratikshat.exicom.model.response.UserResponse> getUsers(@CurrentUser LoginUser user,Pageable page,HttpServletResponse response,@RequestParam(value= "filtername",required=false)String filtername) throws IOException{
		java.util.Iterator ite=user.getAuthorities().iterator();
		boolean isUserValid=false;
		while(ite.hasNext()) {
			LoginUserRoles role=(LoginUserRoles)ite.next();
			System.out.println(role.getAuthority());
			
			if(role.getAuthority().equalsIgnoreCase("ADMIN"))
				isUserValid=true;
		}
		if(isUserValid) {
			
			try {
				Page<com.pratikshat.exicom.model.response.UserResponse> users=null;
				if(filtername!=null) {
					if(filtername.equalsIgnoreCase("all")) {
						users=dbUser.getAllUser(page, businessDomainId);
					}
					if(filtername.equalsIgnoreCase("blockeduser")) {
						users=dbUser.getAllUserByEnableStatus(false, page, businessDomainId);
					}
					if(filtername.equalsIgnoreCase("enableduser")) {
						users=dbUser.getAllUserByEnableStatus(true, page, businessDomainId);
					}
				}
				else {
					users=dbUser.getAllUser(page, businessDomainId);
				}
				
				for(int i=0;i<users.getContent().size();i++) {
					java.util.List<String> rol=dbUser.getUserRoles(users.getContent().get(i).getUserName());
					users.getContent().get(i).setUserRole(rol.size()>0?rol.get(0):"");
				}
				
				return users;	
			} catch (Exception ex) {
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
		}else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
	}
	
	@GetMapping("{useremailid}")
	public com.pratikshat.exicom.model.response.UserResponse getUserDetails(@PathVariable("useremailid")String useremailid,HttpServletResponse response) throws Exception{
		com.pratikshat.exicom.model.response.UserResponse users= dbUser.getByUserEmailId(useremailid);
		java.util.List<String> roles=dbUser.getUserRoles(useremailid);
		if(roles!=null)
			users.setUserRole(roles.size()>0?roles.get(0):"");
		return users;
	}
	@PutMapping("enabledisable")
	public ResponseEntity<?> enableDisablekUser(@CurrentUser LoginUser user,@RequestParam("useremailid")String useremailid,@RequestParam("fflag")boolean fflag){
		
		if(dbUserProfile.hasObjectAccess(user.getUsername(), "EmployeeDetails", "editRights")) {
			dbUser.updateBlockState(useremailid, fflag);
			return ResponseEntity.ok("{\"ok\":\"ok\"}");
		}
		else {
			return ResponseEntity.status(HttpServletResponse.SC_FORBIDDEN).body("{\"message\":\"You are not authorised\"}");
		}
	}
	@GetMapping("profile")
	public java.util.List<com.pratikshat.exicom.entity.UserProfile> getProfile(){
		return dbUserProfile.getAll(businessDomainId);
	}
	@GetMapping("profile/{id}")
	public java.util.List<com.pratikshat.exicom.model.response.ObjectAccessResponse> getObjectAccessByProfileid(@PathVariable("id")long id){
		
		return dbUserProfile.getObjectsAccessByProfileId(id);
	}
	@GetMapping("profile/user")
	public java.util.List<com.pratikshat.exicom.model.response.ObjectAccessResponse> getObjectAccessByUsers(@RequestParam("userid")String userid){
		return dbUserProfile.getObjectsAccess(userid);
	}
	@PutMapping("resetpassword")
	public ResponseEntity<?> resetPassword(@RequestBody com.pratikshat.exicom.model.request.ResetPasswordRequest resetRequest,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,HttpServletResponse respnse)throws java.io.IOException{
		
		java.util.Iterator ite=user.getAuthorities().iterator();
		boolean isAdmin=false;
		while(ite.hasNext()) {
			com.pratikshat.exicom.entity.LoginUserRoles _roles=(com.pratikshat.exicom.entity.LoginUserRoles) ite.next();
			if(_roles.getAuthority().equalsIgnoreCase("ADMIN")) {
				isAdmin=true;
				break;
			}
		}
		if(!isAdmin) {
			respnse.sendError(HttpServletResponse.SC_FORBIDDEN,"You are not authorised to reset password");
			return null;
		}
		
		try {
			if(resetRequest.getNewPassword()==null) {
				respnse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide a valid password");
				return null;
			}
//			if(resetRequest.getNewPassword().length()<=4){
//				respnse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Please provide a valid password, Password must have at least 5 characters");
//				return null;
//			}
			if (!isValidPassword(resetRequest.getNewPassword())) {
				respnse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Password must be at least 12 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
				return null;
			}
			if(!resetRequest.getNewPassword().equals(resetRequest.getConfirmPassword())) {
				respnse.sendError(HttpServletResponse.SC_BAD_REQUEST, "Confirm password did not match with password");
				return null;
			}
			
			dbUser.resetUserPassword(resetRequest.getUserName(), encode.encode(resetRequest.getNewPassword()));
			return ResponseEntity.ok("{\"ok\":\"ok\"}");	
		}catch (Exception ex) {
			respnse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
		
	}
	
	@GetMapping("advancesearch")
	public List<com.pratikshat.exicom.model.response.UserResponse> getAdvanceSearchresult(@RequestParam Map<String,Object> request,@CurrentUser LoginUser user,HttpServletResponse  response) throws IOException, ParseException{
		
		java.util.Iterator ite=user.getAuthorities().iterator();
		boolean isUserValid=false;
		while(ite.hasNext()) {
			LoginUserRoles role=(LoginUserRoles)ite.next();
			if(role.getAuthority().equalsIgnoreCase("ADMIN")) {
				isUserValid=true;
			}
		}
		
		if(isUserValid) {
			return 	dbUser.advanceSearch(request, user, businessDomainId);
			
		}else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
	}
	
	@GetMapping("authorities")
	public java.util.List<Map<String,Object>> getRoles(@CurrentUser LoginUser user){
		try {
			LOGGER.info("Authority Calling");
		return dbRoles.getAll(businessDomainId);
		}catch(Exception ex) {
			LOGGER.error("eRROR IN AUTHORITY CALLING",ex);
			ex.printStackTrace();
		}
		return new ArrayList<>();
	}

	private boolean isValidPassword(String password) {
		String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{12,}$";
		return Pattern.compile(passwordPattern).matcher(password).matches();
	}
}
