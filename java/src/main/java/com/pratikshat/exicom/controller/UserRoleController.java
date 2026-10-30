package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pratikshat.exicom.annotation.CurrentUser;
import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.RoleGroup;

@RestController
@RequestMapping("api/userroles")
public class UserRoleController {
	@Autowired
	com.pratikshat.exicom.repository.UserRoleRepository dbUserroles;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;
	
	@Autowired
	JdbcTemplate db;
	
	int businessDomainId=2;
	
	@GetMapping
	public java.util.List<com.pratikshat.exicom.model.response.UserRoleResponse> getUserRoleResponse(){
		return dbUserroles.getUserRoles(businessDomainId);
	}
	@PostMapping
	@Transactional
	public com.pratikshat.exicom.model.response.UserRoleResponse createnewrole(
			@RequestBody com.pratikshat.exicom.model.response.UserRoleResponse roles,
			@CurrentUser LoginUser user,HttpServletResponse res)throws java.io.IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "UserRoles", "createRights")) {
			com.pratikshat.exicom.entity.UserRoles _roles=new com.pratikshat.exicom.entity.UserRoles();
			_roles.setAuthorities(roles.getAuthorities());
			_roles.setParentUserRoles(new com.pratikshat.exicom.entity.UserRoles(roles.getParentUserAuthorities()));
			_roles.setBusinessDomain(new BusinessDomain(businessDomainId));
			if(roles.getRoleGroup()!=null&&roles.getRoleGroup()!=0)
				_roles.setRoleGroup(new RoleGroup(roles.getRoleGroup()));
				
			dbUserroles.save(_roles);
		}
		else {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		return roles;
	}
	@DeleteMapping
	@Transactional
	public ResponseEntity<?> deleteRoles(@RequestParam("role")String role,@CurrentUser LoginUser user,HttpServletResponse response)throws java.io.IOException{
		if(dbProfile.hasObjectAccess(user.getUsername(), "UserRoles", "editRights")) {
			if(dbUserroles.getLoginUserRoleActie(role).size()>0){
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"message\":\"You can not delete this role\"}");
			}

			dbUserroles.delete(new com.pratikshat.exicom.entity.UserRoles(role));
			return ResponseEntity.ok("{\"ok\":\"ok\"}");
		}
		else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"You are not authorised\"}");
		}
	}
	@GetMapping("rolegroup")
	public List<Map<String,Object>> getRoleGroup(@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			if(rl.getAuthority().equalsIgnoreCase("ADMIN")) {
				return dbUserroles.getRoleGroup();
			}
		}
		return new ArrayList<Map<String,Object>>();
		
	}
	@GetMapping("role")
	public List<Map<String,Object>> getRoles(@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			if(rl.getAuthority().equalsIgnoreCase("ADMIN")) {
				return dbUserroles.getRoles(businessDomainId);
			}
		}
		return new ArrayList<Map<String,Object>>();
		
	}
	@PutMapping("updaterolegroup")
	public Map<String,Object> updateroleGroup(@RequestBody Map<String,Object> role,@CurrentUser LoginUser user,HttpServletResponse response) throws IOException{
		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
		while(ite.hasNext()) {
			LoginUserRoles rl=ite.next();
			if(rl.getAuthority().equalsIgnoreCase("ADMIN")) {
				db.update("update user_roles set role_group_id=? where authorities=?",role.get("role_group_id"),role.get("authorities"));
				return new HashMap<String,Object>();
			}
		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}
}
