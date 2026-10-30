package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.BusinessDomain;
import com.pratikshat.exicom.entity.EmployeeDetails;

@Repository
public interface AccountsOwnerDetailsRepository extends JpaRepository<EmployeeDetails, String> {
	@Transactional
	@Query("select emp from EmployeeDetails emp left join emp.contact left join emp.emptype tp where emp.emailId=:emailId")
	EmployeeDetails findByEmailId(@Param("emailId")String emailId);
	@Transactional
	@Query("select new com.pratikshat.exicom.model.response.NavResponse(un.uiModulesId,un.name,un.url,un.icon,pt.name,un.orderNumber,rls.defaultRoutingPage) from  UserNavigationModule nv join nv.navigationModules un join nv.loginUserRoles rls left join un.parent pt join LoginUserRoles lur on lur.authority=rls.authorities where (nv.uiActive=null or nv.uiActive=true) and lur.loginUser.username=:userid order by un.orderNumber asc")
	java.util.Set<com.pratikshat.exicom.model.response.NavResponse> getMenueList(@Param("userid")String emailId);
	
	@Query("select new com.pratikshat.exicom.model.response.NavResponse(un.uiModulesId,un.name,un.url,un.icon,pt.name,un.orderNumber,up.defaultPage) from UserNavigationModule nv join nv.navigationModules un left join un.parent pt join nv.userProfile up join EmployeeDetails emp on emp.userProfile=up where (nv.uiActive=null or nv.uiActive=true) and emp.emailId=:username order by un.orderNumber asc")
	java.util.Set<com.pratikshat.exicom.model.response.NavResponse> getMenueListByProfile(@Param("username")String emailId);

	@Query("select new com.pratikshat.exicom.model.response.NavResponse(un.uiModulesId,un.name,un.url,un.icon,pt.name,un.orderNumber,up.defaultPage) from UserNavigationModule nv join nv.navigationModules un join un.businessDomain bdm left join un.parent pt join nv.userProfile up join EmployeeDetails emp on emp.userProfile=up where (nv.uiActive=null or nv.uiActive=true) and emp.emailId=:username and (bdm=:businessDomain or bdm.businessDomainId=3) order by un.orderNumber asc")
	java.util.Set<com.pratikshat.exicom.model.response.NavResponse> getMenueListByProfile(@Param("username")String emailId,@Param("businessDomain") BusinessDomain businessDomain);
	
	EmployeeDetails findByContact(com.pratikshat.exicom.entity.ContactDetails contactDetails);
	
}
