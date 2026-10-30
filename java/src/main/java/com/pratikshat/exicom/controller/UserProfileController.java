package com.pratikshat.exicom.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

@RestController
@RequestMapping("api/userprofile")
public class UserProfileController {
	
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	int businessDomainId=2;
	
	@PostMapping
	public com.pratikshat.exicom.model.request.ProfileRequest postProfile(@RequestBody com.pratikshat.exicom.model.request.ProfileRequest profileRequest,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response) throws java.io.IOException{
			if(!dbProfile.hasObjectAccess(user.getUsername(), "UserProfile", "createRights")) {
				
				response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
				return null;
			}
			try {
				return dbProfile.saveProfile(profileRequest, user, businessDomainId);
			}catch(BadRequestException ex) {
				ex.printStackTrace();
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
				return null;
			}catch(UnauthoriseException ex) {
				ex.printStackTrace();
				response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				return null;
			} catch (Exception ex) {
				ex.printStackTrace();
				response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
				return null;
			}
	}
	@PutMapping("{profileid}")
	public com.pratikshat.exicom.model.request.ProfileRequest updateProfile( 
			@PathVariable("profileid")long profileid,
			@RequestBody com.pratikshat.exicom.model.request.ProfileRequest profileRequest,
			@com.pratikshat.exicom.annotation.CurrentUser com.pratikshat.exicom.entity.LoginUser user,
			HttpServletResponse response
			) throws java.io.IOException{
		
		if(!dbProfile.hasObjectAccess(user.getUsername(), "UserProfile", "editRights")) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorise");
			return null;
		}
	
		try {
			if(profileid!=profileRequest.getProfileId()) {
				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Profile id mismatch");
				return null;
			}
			
			return dbProfile.editProfile(profileRequest, user,businessDomainId);
		}catch(BadRequestException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
			return null;
		}catch(UnauthoriseException ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
			return null;
		} catch (Exception ex) {
			ex.printStackTrace();
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
			return null;
		}
	}
	@GetMapping("{profileid}/navigations")
	public java.util.List<com.pratikshat.exicom.model.response.UINavigationModuleResponse> getUINavigationByProfileid(@PathVariable("profileid")long profileId){
		return dbProfile.getNavigationModuleByProfile(new com.pratikshat.exicom.entity.UserProfile(profileId));
	}
	@GetMapping("navigations")
	public java.util.List<com.pratikshat.exicom.model.response.UINavigationModuleResponse> getAllNavigation(){
		return dbProfile.getNavigationModule(businessDomainId);
	}
}
