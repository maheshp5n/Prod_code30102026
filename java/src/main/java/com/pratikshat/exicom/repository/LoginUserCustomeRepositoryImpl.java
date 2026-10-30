package com.pratikshat.exicom.repository;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.model.request.LegalEntityResponse;

@Repository
public class LoginUserCustomeRepositoryImpl implements LoginUserCustomeRepository{

	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public List<com.pratikshat.exicom.model.response.UserResponse> advanceSearch(Map<String,Object> request,LoginUser user, int businessDomainId) throws ParseException {
			StringBuilder queryStringBuilder=new StringBuilder();
			queryStringBuilder.append("select new com.pratikshat.exicom.model.response.UserResponse(coalesce(em.emailId,''),coalesce(ac.name,''),coalesce(ct.contactId,0),coalesce(ct.firstName,''),coalesce(ct.lastName,''),coalesce(ct.middleName,''),coalesce(ac.sapCustomerNumber,''),coalesce(ccdes.designationName,''),coalesce(ct.emailId,''),coalesce(ct.employeeCode,''),coalesce(ety.businessDomainId,''),coalesce(ety.businessDomainName,''),coalesce(l.accountNonExpired,false),coalesce(l.accountNonLocked,false),coalesce(l.credentialsNonExpired,false),coalesce(l.enabled,false),coalesce(profile.profileName,''),coalesce(manager.emailId,''),coalesce(manager.firstName,''),coalesce(manager.middleName,''),coalesce(manager.lastName,''),coalesce(aprmanager.emailId,''),coalesce(aprmanager.firstName,''),coalesce(aprmanager.middleName,''),coalesce(aprmanager.lastName,''), coalesce(cby.firstName,''), coalesce(cby.middleName,''), coalesce(cby.lastName,''), cby.emailId, em.createdDate, coalesce(mby.firstName,''), coalesce(mby.middleName,''), coalesce(mby.lastName,''), mby.emailId, em.modifiedDate, rol.authority) from LoginUser l join EmployeeDetails em on em.emailId=l.username left join em.contact ct left join ct.account ac left join ct.contactDesignation ccdes left join em.emptype ety left join em.userProfile profile left join em.manager manager left join em.approvalManager aprmanager left join em.createdBy cby left join em.modifiedBy mby left join LoginUserRoles rol on rol.loginUser.username=em.emailId where 1=1 and (em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3)");
			
			if(request.get("userId")!=null&&!request.get("userId").equals("")) {
				queryStringBuilder.append(" and em.emailId like :userId");
			}
			if(request.get("name")!=null&&!request.get("name").equals("")) {
				queryStringBuilder.append(" and concat(coalesce(ct.firstName,''),coalesce(ct.middleName,''),' ',coalesce(ct.lastName,'')) like :name");
			}
			if(request.get("account")!=null&&!request.get("account").equals("")) {
				queryStringBuilder.append(" and ac.name like :account");
			}
			if(request.get("accountSapNumber")!=null&&!request.get("accountSapNumber").equals("")) {
				queryStringBuilder.append(" and ac.sapCustomerNumber like :accountSapNumber");
			}
			if(request.get("employeeCode")!=null&&!request.get("employeeCode").equals("")) {
				queryStringBuilder.append(" and ct.employeeCode like :employeeCode");
			}
			
			if(request.get("profile")!=null&&!request.get("profile").equals("")) {
				queryStringBuilder.append(" and profile.profileName like :profile");
			}
			
			if(request.get("role")!=null&&!request.get("role").equals("")) {
				queryStringBuilder.append(" and rol.authority like :role");
			}
			
			if(request.get("manager")!=null&&!request.get("manager").equals("")) {
				queryStringBuilder.append(" and concat(coalesce(manager.firstName,''),coalesce(manager.middleName,''),' ',coalesce(manager.lastName,'')) like :manager");
			}
			
			if(request.get("approverManager")!=null&&!request.get("approverManager").equals("")) {
				queryStringBuilder.append(" and concat(coalesce(aprmanager.firstName,''),coalesce(aprmanager.middleName,''),' ',coalesce(aprmanager.lastName,'')) like :approverManager");
			}
			
			Query qr=entityManager.createQuery(queryStringBuilder.toString(),com.pratikshat.exicom.model.response.UserResponse.class);
			
			qr.setParameter("businessDomainId",businessDomainId);
			
			if(request.get("userId")!=null&&!request.get("userId").equals("")) {
				qr.setParameter("userId","%"+ request.get("userId")+"%");
			}
			if(request.get("name")!=null&&!request.get("name").equals("")) {
				qr.setParameter("name","%"+ request.get("name")+"%");
			}
			if(request.get("account")!=null&&!request.get("account").equals("")) {
				qr.setParameter("account","%"+ request.get("account")+"%");
			}
			if(request.get("accountSapNumber")!=null&&!request.get("accountSapNumber").equals("")) {
				qr.setParameter("accountSapNumber","%"+ request.get("accountSapNumber")+"%");
			}
			if(request.get("employeeCode")!=null&&!request.get("employeeCode").equals("")) {
				qr.setParameter("employeeCode","%"+ request.get("employeeCode")+"%");
			}
			if(request.get("profile")!=null&&!request.get("profile").equals("")) {
				qr.setParameter("profile","%"+ request.get("profile")+"%");
			}
			if(request.get("role")!=null&&!request.get("role").equals("")) {
				qr.setParameter("role","%"+ request.get("role")+"%");
			}
			if(request.get("manager")!=null&&!request.get("manager").equals("")) {
				qr.setParameter("manager","%"+ request.get("manager")+"%");
			}
			if(request.get("approverManager")!=null&&!request.get("approverManager").equals("")) {
				qr.setParameter("approverManager","%"+ request.get("approverManager")+"%");
			}
			return qr.getResultList();
		}
}
