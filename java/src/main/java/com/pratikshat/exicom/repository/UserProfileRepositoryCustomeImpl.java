
package com.pratikshat.exicom.repository;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.SummaryPageFilter;
import com.pratikshat.exicom.entity.UserNavigationModule;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.entity.UserProfileObjectAccess;
import com.pratikshat.exicom.entity.UserSummaryPageFilter;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.request.ProfileRequest;

@Repository
@Transactional
public class UserProfileRepositoryCustomeImpl implements UserProfileRepositoryCustome {
	@javax.persistence.PersistenceContext
	javax.persistence.EntityManager em;

	@Autowired
	JdbcTemplate jdbc;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_UNCOMMITTED, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class,
			SQLException.class })
	public List<String> getListOfUserAndChildByUser(String user) {
		List<String> users = new java.util.ArrayList<String>();
		List<String> userRoles = em.createQuery(
				"select user.authority from LoginUserRoles user join user.loginUser luser where luser.username=?1",
				String.class).setParameter(1, user).getResultList();
		for (String userRole : userRoles) {
			for (String rol : getChildRoles(userRole, em)) {
				// System.out.println("Role => "+rol);
				users.addAll(getUserByRoles(rol, em));
			}
		}
		users.add(user);

		return users;
	}

	@Transactional
	public List<com.pratikshat.exicom.model.response.ObjectAccessResponse> getObjectsAccess(String userId) {
		return em.createQuery(
				"select new com.pratikshat.exicom.model.response.ObjectAccessResponse(acc.id,prp.id,obj.objectName,prp.profileName,obj.objectUrl,prp.profileDescription,acc.viewRights,acc.viewAllRights,acc.createRights,acc.editRights,acc.deleteRights) from UserProfileObjectAccess acc join acc.object obj join acc.userProfile prp join EmployeeDetails emp on emp.userProfile=prp where emp=?1",
				com.pratikshat.exicom.model.response.ObjectAccessResponse.class)
				.setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(userId))
				.getResultList();
	}

	private List<String> getChildRoles(String roles, javax.persistence.EntityManager _em) {
		List<String> _roles = new java.util.ArrayList<String>();
		List<String> _inRoles = _em
				.createQuery("select roles.authorities from UserRoles roles where roles.parentUserRoles=?1",
						String.class)
				.setParameter(1, new com.pratikshat.exicom.entity.UserRoles(roles)).getResultList();
		for (String rl : _inRoles) {
			_roles.add(rl);
			for (String r : getChildRoles(rl, _em)) {
				_roles.add(r);
			}
		}
		return _roles;
	}

	private List<String> getUserByRoles(String roles, javax.persistence.EntityManager _em) {
		java.util.List<String> _roleUsers = _em.createQuery(
				"select distinct u.username from LoginUserRoles roles join roles.loginUser u where roles.authority=?1",
				String.class).setParameter(1, roles).getResultList();
		// System.out.println(_roleUsers);
		return _roleUsers;
	}

	@Override
	@Transactional
	public boolean hasObjectAccess(String userEmailId, String objectName, String rightsName) {
		String hql = String.format(
				"select acc.%s from UserProfileObjectAccess acc join acc.object obj join acc.userProfile prp join EmployeeDetails emp on emp.userProfile=prp where emp=?1 and obj.objectName=?2",
				rightsName);
		try {
			return em.createQuery(hql, Boolean.class)
					.setParameter(1, new com.pratikshat.exicom.entity.EmployeeDetails(userEmailId))
					.setParameter(2, objectName).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			// ex.printStackTrace();
		}
		return false;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class })
	public ProfileRequest saveProfile(ProfileRequest profileRquest, LoginUser user, int businessDomainId)
			throws BadRequestException, UnauthoriseException {
		com.pratikshat.exicom.entity.UserProfile _profile = null;
		try {
			_profile = em
					.createQuery("select u from UserProfile u where u.profileName=?1",
							com.pratikshat.exicom.entity.UserProfile.class)
					.setParameter(1, profileRquest.getProfileName()).getSingleResult();
			if (_profile != null) {
				throw new BadRequestException("User profile already exists");
			}
		} catch (javax.persistence.NoResultException ex) {

		} catch (javax.persistence.NonUniqueResultException ex) {
			throw new BadRequestException("User Profile already Exists");
		}
		String defaultpage = null;
		try {
			System.out.println("Module Id " + profileRquest.getProfileId());
			defaultpage = em.createQuery("select p.url from UINavigationModules p where p.uiModulesId=?1", String.class)
					.setParameter(1, profileRquest.getDefaultpage() == 0 ? 1 : profileRquest.getDefaultpage())
					.getSingleResult();
		} catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
		}
		_profile = new com.pratikshat.exicom.entity.UserProfile();
		_profile.setProfileName(profileRquest.getProfileName());
		_profile.setProfileDescription(profileRquest.getProfileDescription());
		_profile.setDefaultPage(defaultpage);
		_profile.setBusinessDomain(new BusinessDomain(businessDomainId));
		em.persist(_profile);
		if (profileRquest.getObjectAccessRights() != null && profileRquest.getObjectAccessRights().size() > 0) {
			for (com.pratikshat.exicom.model.request.ProfileObjectAccessRequest access : profileRquest
					.getObjectAccessRights()) {
				com.pratikshat.exicom.entity.UserProfileObjectAccess _access = new com.pratikshat.exicom.entity.UserProfileObjectAccess();
				_access.setCreateRights(access.isCreateRights());
				_access.setDeleteRights(access.isDeleteRights());
				_access.setEditRights(access.isEditRights());
				_access.setObject(new com.pratikshat.exicom.entity.ObjectDefinition(access.getObjectName()));
				_access.setUserProfile(_profile);
				_access.setViewAllRights(access.isViewAllRights());
				_access.setViewRights(access.isViewRights());
				em.persist(_access);
			}
		}
		if (profileRquest.getUiId() != null && profileRquest.getUiId() != null) {
			for (long uiId : profileRquest.getUiId()) {
				com.pratikshat.exicom.entity.UserNavigationModule _uiNavi = new com.pratikshat.exicom.entity.UserNavigationModule();
				_uiNavi.setNavigationModules(new com.pratikshat.exicom.entity.UINavigationModules(uiId));
				_uiNavi.setUserProfile(_profile);
				_uiNavi.setUiActive(true);
				em.persist(_uiNavi);

				// FILTER PAGE
				List<SummaryPageFilter> lstPageFilter = new ArrayList<SummaryPageFilter>();
				lstPageFilter = em
						.createQuery("select flt from SummaryPageFilter flt where flt.navigationModule=?1",
								SummaryPageFilter.class)
						.setParameter(1, new com.pratikshat.exicom.entity.UINavigationModules(uiId))
						.getResultList();
				if (lstPageFilter != null && lstPageFilter.size() > 0) {
					for (SummaryPageFilter flt : lstPageFilter) {
						UserSummaryPageFilter uflt = new UserSummaryPageFilter();
						uflt.setUserProfile(_profile);
						uflt.setPageFilter(flt);
						em.persist(uflt);
					}
				}
			}
		}

		profileRquest.setProfileId(_profile.getId());
		return profileRquest;
	}

	@Override
	@Transactional(isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRES_NEW, rollbackFor = {
			BadRequestException.class, UnauthoriseException.class, RuntimeException.class, Exception.class })
	public ProfileRequest editProfile(ProfileRequest profileRequest, LoginUser user, int busnessdomainId)
			throws BadRequestException, UnauthoriseException {
		com.pratikshat.exicom.entity.UserProfile _profile = null;
		try {
			_profile = em
					.createQuery("select u from UserProfile u where u.id=?1",
							com.pratikshat.exicom.entity.UserProfile.class)
					.setParameter(1, profileRequest.getProfileId()).getSingleResult();
		} catch (javax.persistence.NoResultException ex) {
			throw new BadRequestException(String.format("No result found for %s", profileRequest.getProfileName()));
		}
		String defaultpage = null;
		try {

			defaultpage = em.createQuery("select p.url from UINavigationModules p where p.uiModulesId=?1", String.class)
					.setParameter(1, profileRequest.getDefaultpage() == 0 ? 1 : profileRequest.getDefaultpage())
					.getSingleResult();
		} catch (javax.persistence.NoResultException | javax.persistence.NonUniqueResultException ex) {
		}
		_profile.setProfileDescription(profileRequest.getProfileDescription());
		_profile.setProfileName(profileRequest.getProfileName());
		_profile.setDefaultPage(defaultpage);
		em.persist(_profile);
		if (profileRequest.getObjectAccessRights() != null && profileRequest.getObjectAccessRights().size() > 0) {
			for (com.pratikshat.exicom.model.request.ProfileObjectAccessRequest access : profileRequest
					.getObjectAccessRights()) {
				com.pratikshat.exicom.entity.UserProfileObjectAccess _access = new com.pratikshat.exicom.entity.UserProfileObjectAccess();
				try {
					_access = em
							.createQuery(
									"select s from UserProfileObjectAccess s where s.userProfile=?1 and s.object=?2",
									com.pratikshat.exicom.entity.UserProfileObjectAccess.class)
							.setParameter(1, _profile)
							.setParameter(2, new com.pratikshat.exicom.entity.ObjectDefinition(access.getObjectName()))
							.getSingleResult();
				} catch (javax.persistence.NoResultException ex) {
					_access = new com.pratikshat.exicom.entity.UserProfileObjectAccess();
				}
				_access.setCreateRights(access.isCreateRights());
				_access.setDeleteRights(access.isDeleteRights());
				_access.setEditRights(access.isEditRights());
				_access.setObject(new com.pratikshat.exicom.entity.ObjectDefinition(access.getObjectName()));
				_access.setUserProfile(_profile);
				_access.setViewAllRights(access.isViewAllRights());
				_access.setViewRights(access.isViewRights());
				em.persist(_access);
			}
		}
		// em.createQuery("delete from UserNavigationModule um where
		// um.userProfile=?1").setParameter(1,_profile).executeUpdate();

		em.createQuery(
				"update UserNavigationModule um set um.uiActive=?1 where um.userProfile=?2 and um.navigationModules in (select nvg from UINavigationModules nvg where nvg.businessDomain=?3) ")
				.setParameter(1, false)
				.setParameter(2, _profile)
				.setParameter(3, new BusinessDomain(busnessdomainId))
				.executeUpdate();
		if (profileRequest.getUiId() != null && profileRequest.getUiId().size() > 0) {
			for (long uiId : profileRequest.getUiId()) {
				com.pratikshat.exicom.entity.UserNavigationModule _uiNavi = null;
				try {
					_uiNavi = em.createQuery(
							"select um from UserNavigationModule um where um.userProfile=?1 and um.navigationModules=?2",
							UserNavigationModule.class)
							.setParameter(1, _profile)
							.setParameter(2, new com.pratikshat.exicom.entity.UINavigationModules(uiId))
							.getSingleResult();
				} catch (NoResultException ex) {
					_uiNavi = new com.pratikshat.exicom.entity.UserNavigationModule();
				} catch (NonUniqueResultException e) {
					e.printStackTrace();
					throw new BadRequestException("Non unique result for UserNavigationModule");
				}

				_uiNavi.setNavigationModules(new com.pratikshat.exicom.entity.UINavigationModules(uiId));
				_uiNavi.setUserProfile(_profile);
				_uiNavi.setUiActive(true);
				em.persist(_uiNavi);

				// FILTER PAGE
				List<SummaryPageFilter> lstPageFilter = new ArrayList<SummaryPageFilter>();
				lstPageFilter = em
						.createQuery("select flt from SummaryPageFilter flt where flt.navigationModule=?1",
								SummaryPageFilter.class)
						.setParameter(1, new com.pratikshat.exicom.entity.UINavigationModules(uiId))
						.getResultList();
				if (lstPageFilter != null && lstPageFilter.size() > 0) {
					for (SummaryPageFilter flt : lstPageFilter) {
						UserSummaryPageFilter uflt = null;
						try {
							uflt = em.createQuery(
									"select uflt from UserSummaryPageFilter uflt where uflt.userProfile=?1 and uflt.pageFilter=?2",
									UserSummaryPageFilter.class)
									.setParameter(1, _profile)
									.setParameter(2, flt)
									.getSingleResult();
						} catch (Exception ex) {
							uflt = new UserSummaryPageFilter();
							uflt.setUserProfile(_profile);
							uflt.setPageFilter(flt);
							em.persist(uflt);
						}
					}
				}

			}
		}
		return profileRequest;
	}

	@Override
	public boolean hasObjectAccess(String userEmailId, Class cls, String rightsName) {
		if (userEmailId == null) {
			return false;
		}
		if (cls == null)
			return false;
		if (rightsName == null)
			return false;
		String classpathfullName = cls.getName();
		int lastDot = classpathfullName.lastIndexOf(".");
		String onlyClassName = classpathfullName.substring(lastDot + 1);

		return hasObjectAccess(userEmailId, onlyClassName, rightsName);

	}

	@Override
	public Map<String, String> getAccountAndRoleGroup(LoginUser user) {
		Map<String, String> result = new HashMap<String, String>();
		try {
			Map<String, Object> account = jdbc.queryForMap(
					"SELECT account_id FROM `contact_details` WHERE `email_id`=?", new Object[] { user.getUsername() });
			result.put("accountSapNumber", account.get("account_id").toString());

		} catch (Exception ex) {

		}
		String role = "";
		if (user.getAuthorities() != null) {
			Iterator<LoginUserRoles> usrRole = (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
			while (usrRole.hasNext()) {
				LoginUserRoles rl = usrRole.next();
				role = rl.getAuthority();
				break;
			}
		} else {
			List<Map<String, Object>> _rllist = jdbc
					.queryForList("select authority from login_user_roles where login_username=?", user.getUsername());
			if (_rllist != null && _rllist.size() > 0) {
				if (_rllist.get(0).get("authority") != null)
					role = _rllist.get(0).get("authority").toString();
			}
		}

		try {
			Map<String, Object> roleG = jdbc.queryForMap(
					"SELECT rg.`group_name` FROM  `user_roles` rl JOIN `role_group` rg ON rl.`role_group_id`=rg.`id` WHERE rl.`authorities`=?",
					new Object[] { role });
			result.put("roleGroup", roleG.get("group_name").toString());
		} catch (Exception ex) {

		}
		if (result.get("accountSapNumber") == null)
			result.put("accountSapNumber", "");
		if (result.get("roleGroup") == null)
			result.put("roleGroup", "");
		return result;
	}

	@Override
	public List<String> getSameroleUsers(LoginUser user) {
		String role = "";
		List<String> result = new ArrayList<String>();
		Iterator<LoginUserRoles> userRoles = (Iterator<LoginUserRoles>) user.getAuthorities().iterator();
		while (userRoles.hasNext()) {
			role = userRoles.next().getAuthority();
			break;
		}
		List<Map<String, Object>> lst = jdbc
				.queryForList("select `login_username` from `login_user_roles` where `authority`=?", role);
		if (lst != null && lst.size() > 0) {
			for (Map<String, Object> s : lst) {

				result.add(s.get("login_username").toString());
			}
		}
		return result;
	}

	@Override
	public boolean samroleUsers(String userFirst, String userSecond) {
		String roleOne = getRole(userFirst);
		String roleTwo = getRole(userSecond);
		if (roleOne == null || roleTwo == null)
			return false;
		if (roleOne.equalsIgnoreCase(roleTwo))
			return true;

		return false;
	}

	private String getRole(String user) {
		List<Map<String, Object>> lst = jdbc
				.queryForList("select authority from login_user_roles where login_username=?", user);
		if (lst == null || lst.size() == 0)
			return null;
		return lst.get(0).get("authority").toString();
	}

	@Override
	public List<Long> getLocationAndChildLocation(String userEmailId) {
		Map<String, Object> uLocation = jdbc.queryForMap(
				"select `location_location_id` from `service_recource` where `user_email_id`=?", userEmailId);
		List<Long> loc = new ArrayList<Long>();
		long locId = Long.parseLong(uLocation.get("location_location_id").toString());
		loc.add(locId);
		loc.addAll(getChilds(locId));
		System.out.println(loc);
		return loc;
	}

	List<Long> getChilds(Long parentId) {
		List<Map<String, Object>> childs = new ArrayList<Map<String, Object>>();
		List<Long> _ch = new ArrayList<Long>();
		try {
			childs = jdbc.queryForList("select `location_id` from `location` where `parent_location_location_id`=?",
					parentId);
			if (childs != null && childs.size() > 0) {
				for (Map<String, Object> c : childs) {
					_ch.add(Long.parseLong(c.get("location_id").toString()));
					_ch.addAll(getChilds(Long.parseLong(c.get("location_id").toString())));
				}
			}
		} catch (Exception ex) {

		}
		return _ch;
	}

	@Override
	public List<String> getUsersInMainRoleGroup(String groupName) {
		List<String> re = new ArrayList<String>();
		// List<Map<String,Object>> lst=jdbc.queryForList("select
		// rl.authorities,rg.group_name,lrol.login_username from user_roles rl join
		// role_group rg on rl.role_group_id=rg.id join login_user_roles lrol on
		// lrol.authority=rl.authorities where rg.group_name=?",groupName);
		List<Map<String, Object>> lst = jdbc.queryForList(
				"select lrol.login_username from user_roles rl join role_group rg on rl.role_group_id=rg.id join login_user_roles lrol on lrol.authority=rl.authorities where rg.group_name=?",
				groupName);
		if (lst != null && lst.size() > 0) {
			for (Map<String, Object> ob : lst) {
				if (ob.get("login_username") != null)
					re.add(ob.get("login_username").toString());
			}
		}
		return re;
	}

}
