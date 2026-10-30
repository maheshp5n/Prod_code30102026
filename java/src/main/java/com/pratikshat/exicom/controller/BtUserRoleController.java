
package com.pratikshat.exicom.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.internal.build.AllowSysOut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
import com.pratikshat.exicom.entity.UserRoles;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.UserRoleResponse;
import com.pratikshat.exicom.repository.UserProfileRepository;

@RestController
@RequestMapping("bt/api/userroles")
public class BtUserRoleController {

	@Autowired
	com.pratikshat.exicom.repository.UserRoleRepository dbUserroles;
	@Autowired
	com.pratikshat.exicom.repository.UserProfileRepository dbProfile;

	@PersistenceContext
	EntityManager _em;

	@Autowired
	JdbcTemplate db;

	int businessDomainId = 1;

	@GetMapping
	public java.util.List<com.pratikshat.exicom.model.response.UserRoleResponse> getUserRoleResponse() {
		return dbUserroles.getUserRoles(businessDomainId);

	}

	@PostMapping
	@Transactional
	public UserRoleResponse createnewrole(@RequestBody UserRoleResponse roles, @CurrentUser LoginUser user,
			HttpServletResponse res) throws java.io.IOException {
		if (dbProfile.hasObjectAccess(user.getUsername(), UserRoles.class, UserProfileRepository.CREATE)) {
			UserRoles _roles = new com.pratikshat.exicom.entity.UserRoles();

			_roles.setAuthorities(roles.getAuthorities());
			_roles.setParentUserRoles(new com.pratikshat.exicom.entity.UserRoles(roles.getParentUserAuthorities()));
			_roles.setBusinessDomain(new BusinessDomain(businessDomainId));
			if (roles.getRoleGroup() != null && roles.getRoleGroup() != 0)
				_roles.setRoleGroup(new RoleGroup(roles.getRoleGroup()));

			dbUserroles.save(_roles);
		} else {
			res.sendError(HttpServletResponse.SC_FORBIDDEN, "You are not authorised");
			return null;
		}
		return roles;
	}

	@DeleteMapping
	@Transactional(rollbackFor = { BadRequestException.class, UnauthoriseException.class,
			Exception.class }, propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED)
	public ResponseEntity<?> deleteRoles(@RequestParam("role") String role, @CurrentUser LoginUser user,
			HttpServletResponse response) throws java.io.IOException, BadRequestException {

		if (dbProfile.hasObjectAccess(user.getUsername(), UserRoles.class, UserProfileRepository.EDIT)) {
			try {
//				dbUserroles.delete(new com.pratikshat.exicom.entity.UserRoles(role));
				
				_em.createQuery("delete from UserRoles ur where ur.authorities=?1").setParameter(1, role)
						.executeUpdate();
			} catch (Exception e) {
				throw new BadRequestException("Role contain child");
			}

			return ResponseEntity.ok("{\"ok\":\"ok\"}");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"message\":\"You are not authorised\"}");
		}
	}

	@GetMapping("rolegroup")
	public List<Map<String, Object>> getRoleGroup(@CurrentUser LoginUser user, HttpServletResponse response)
			throws IOException {

		if (dbProfile.getAccountAndRoleGroup(user).get("roleGroup").equalsIgnoreCase("ADMIN")) {
			return dbUserroles.getRoleGroup();
		}
//		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
//		while(ite.hasNext()) {
//			LoginUserRoles rl=ite.next();
//			String role=rl.getAuthority();
//			
////			if(rl.getAuthority().equalsIgnoreCase("ADMIN")) {
////				return dbUserroles.getRoleGroup();
////			}
//		}
		return new ArrayList<Map<String, Object>>();

	}

	@GetMapping("role")
	public List<Map<String, Object>> getRoles(@CurrentUser LoginUser user, HttpServletResponse response)
			throws IOException {
		if (dbProfile.getAccountAndRoleGroup(user).get("roleGroup").equalsIgnoreCase("ADMIN")) {
			return dbUserroles.getRoles(businessDomainId);
		}
//		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
//		while(ite.hasNext()) {
//			LoginUserRoles rl=ite.next();
//			
//			
//			
//			if(rl.getAuthority().equalsIgnoreCase("ADMIN")) {
//				return dbUserroles.getRoles(businessDomainId);
//			}
//		}
		return new ArrayList<Map<String, Object>>();

	}

	@PutMapping("updaterolegroup")
	public Map<String, Object> updateroleGroup(@RequestBody Map<String, Object> role, @CurrentUser LoginUser user,
			HttpServletResponse response) throws IOException {

		if (dbProfile.getAccountAndRoleGroup(user).get("roleGroup").equalsIgnoreCase("ADMIN")) {
			db.update("update user_roles set role_group_id=? where authorities=?", role.get("role_group_id"),
					role.get("authorities"));
			return new HashMap<String, Object>();
		}

//		Iterator<LoginUserRoles> ite=(Iterator<LoginUserRoles>)user.getAuthorities().iterator();
//		
//		while(ite.hasNext()) {
//			LoginUserRoles rl=ite.next();
//			if(rl.getAuthority().equalsIgnoreCase("ADMIN")) {
//				db.update("update user_roles set role_group_id=? where authorities=?",role.get("role_group_id"),role.get("authorities"));
//				return new HashMap<String,Object>();
//			}
//		}
		response.sendError(HttpServletResponse.SC_FORBIDDEN);
		return null;
	}
}
