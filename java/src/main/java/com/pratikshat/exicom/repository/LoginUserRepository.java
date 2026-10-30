package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.LoginUser;

@Repository
public interface LoginUserRepository extends JpaRepository<LoginUser, String>,LoginUserCustomeRepository {
	LoginUser findByUsername(String userName);
	
	@Query("select new com.pratikshat.exicom.model.response.UserForList(em.emailId,em.firstName,em.lastName,em.middleName) from EmployeeDetails em join em.contact ct join ct.legalEntity le join LoginUser usr on usr.username=em.emailId where usr.enabled=:enabled and le.legalEntityId=:legalEntityId and (em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3)")
	List<com.pratikshat.exicom.model.response.UserForList> getUserByLegalEntity(@Param("enabled")boolean enabled, @Param("legalEntityId")long legalEntityId, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.UserForList(em.emailId,em.firstName,em.lastName,em.middleName) from EmployeeDetails em join em.contact ct join LoginUser usr on usr.username=em.emailId where usr.enabled=:enabled and ct.legalEntity is null and (em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3)")
	List<com.pratikshat.exicom.model.response.UserForList> getUserWithNoLegalEntity(@Param("enabled")boolean enabled, @Param("businessDomainId")int businessDomainId);
	
	@Query("select count(lu) from LoginUserRoles lr join  lr.loginUser lu where lu.username=:useremailId and lr.authority in :userauthority")
	int getCountOfUserName(@Param("useremailId")String userEmailId,@Param("userauthority")List<String> authority);
	int countByUsername(String username);
	@Query("select new com.pratikshat.exicom.model.response.UserResponse(l,em) from LoginUser l join EmployeeDetails em on em.emailId=l.username left join em.contact ct left join ct.account ac left join ct.contactDesignation ccdes left join em.emptype ety left join em.userProfile profile left join em.manager manager left join em.approvalManager aprmanager left join em.createdBy cby left join em.modifiedBy mby where em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3")
	Page<com.pratikshat.exicom.model.response.UserResponse> getUserList(Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.UserResponse(coalesce(em.emailId,''),coalesce(ac.name,''),coalesce(ct.contactId,0),coalesce(ct.firstName,''),coalesce(ct.lastName,''),coalesce(ct.middleName,''),coalesce(ac.sapCustomerNumber,''),coalesce(ccdes.designationName,''),coalesce(ct.emailId,''),coalesce(ct.employeeCode,''),coalesce(ety.businessDomainId,''),coalesce(ety.businessDomainName,''),coalesce(l.accountNonExpired,false),coalesce(l.accountNonLocked,false),coalesce(l.credentialsNonExpired,false),coalesce(l.enabled,false),coalesce(profile.profileName,''),coalesce(manager.emailId,''),coalesce(manager.firstName,''),coalesce(manager.middleName,''),coalesce(manager.lastName,''),coalesce(aprmanager.emailId,''),coalesce(aprmanager.firstName,''),coalesce(aprmanager.middleName,''),coalesce(aprmanager.lastName,''), coalesce(cby.firstName,''), coalesce(cby.middleName,''), coalesce(cby.lastName,''), cby.emailId, em.createdDate, coalesce(mby.firstName,''), coalesce(mby.middleName,''), coalesce(mby.lastName,''), mby.emailId, em.modifiedDate) from LoginUser l join EmployeeDetails em on em.emailId=l.username left join em.contact ct left join ct.account ac left join ct.contactDesignation ccdes left join em.emptype ety left join em.userProfile profile left join em.manager manager left join em.approvalManager aprmanager left join em.createdBy cby left join em.modifiedBy mby where em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3")
	Page<com.pratikshat.exicom.model.response.UserResponse> getAllUser(Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select new com.pratikshat.exicom.model.response.UserResponse(coalesce(em.emailId,''),coalesce(ac.name,''),coalesce(ct.contactId,0),coalesce(ct.firstName,''),coalesce(ct.lastName,''),coalesce(ct.middleName,''),coalesce(ac.sapCustomerNumber,''),coalesce(ccdes.designationName,''),coalesce(ct.emailId,''),coalesce(ct.employeeCode,''),coalesce(ety.businessDomainId,''),coalesce(ety.businessDomainName,''),coalesce(l.accountNonExpired,false),coalesce(l.accountNonLocked,false),coalesce(l.credentialsNonExpired,false),coalesce(l.enabled,false),coalesce(profile.profileName,''),coalesce(manager.emailId,''),coalesce(manager.firstName,''),coalesce(manager.middleName,''),coalesce(manager.lastName,''),coalesce(aprmanager.emailId,''),coalesce(aprmanager.firstName,''),coalesce(aprmanager.middleName,''),coalesce(aprmanager.lastName,''), coalesce(cby.firstName,''), coalesce(cby.middleName,''), coalesce(cby.lastName,''), cby.emailId, em.createdDate, coalesce(mby.firstName,''), coalesce(mby.middleName,''), coalesce(mby.lastName,''), mby.emailId, em.modifiedDate) from LoginUser l join EmployeeDetails em on em.emailId=l.username left join em.contact ct left join ct.account ac left join ct.contactDesignation ccdes left join em.emptype ety left join em.userProfile profile left join em.manager manager left join em.approvalManager aprmanager left join em.createdBy cby left join em.modifiedBy mby where l.username=:useremailId")
	com.pratikshat.exicom.model.response.UserResponse getByUserEmailId(@Param("useremailId")String useremailId);
	
	@Query("select new com.pratikshat.exicom.model.response.UserResponse(coalesce(em.emailId,''),coalesce(ac.name,''),coalesce(ct.contactId,0),coalesce(ct.firstName,''),coalesce(ct.lastName,''),coalesce(ct.middleName,''),coalesce(ac.sapCustomerNumber,''),coalesce(ccdes.designationName,''),coalesce(ct.emailId,''),coalesce(ct.employeeCode,''),coalesce(ety.businessDomainId,''),coalesce(ety.businessDomainName,''),coalesce(l.accountNonExpired,false),coalesce(l.accountNonLocked,false),coalesce(l.credentialsNonExpired,false),coalesce(l.enabled,false),coalesce(profile.profileName,''),coalesce(manager.emailId,''),coalesce(manager.firstName,''),coalesce(manager.middleName,''),coalesce(manager.lastName,''),coalesce(aprmanager.emailId,''),coalesce(aprmanager.firstName,''),coalesce(aprmanager.middleName,''),coalesce(aprmanager.lastName,''), coalesce(cby.firstName,''), coalesce(cby.middleName,''), coalesce(cby.lastName,''), cby.emailId, em.createdDate, coalesce(mby.firstName,''), coalesce(mby.middleName,''), coalesce(mby.lastName,''), mby.emailId, em.modifiedDate) from LoginUser l join EmployeeDetails em on em.emailId=l.username left join em.contact ct left join ct.account ac left join ct.contactDesignation ccdes left join em.emptype ety left join em.userProfile profile left join em.manager manager left join em.approvalManager aprmanager left join em.createdBy cby left join em.modifiedBy mby where l.enabled=:enabled and (em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3)")
	Page<com.pratikshat.exicom.model.response.UserResponse> getAllUserByEnableStatus(@Param("enabled")boolean enabled,Pageable page, @Param("businessDomainId")int businessDomainId);
	
	@Query("select rol.authority from LoginUserRoles rol join rol.loginUser u where u.username=:userEmailId")
	java.util.List<String> getUserRoles(@Param("userEmailId")String userEmailId);
	
	@Modifying
	@Transactional
	@Query("update LoginUser u set u.enabled=:enable where u.username=:username")
	void updateBlockState(@Param("username")String username,@Param("enable")boolean enable);
	@Modifying
	@Transactional
	@Query("update LoginUser u set u.password=:password where u.username=:username")
	void resetUserPassword(@Param("username")String username,@Param("password")String password);
	
	@Query("select new com.pratikshat.exicom.model.response.UserForList(em.emailId,em.firstName,em.middleName,em.lastName) from EmployeeDetails em join LoginUser usr on usr.username=em.emailId where usr.enabled=:enabled and (em.businessDomain.businessDomainId=:businessDomainId or em.businessDomain.businessDomainId=3)")
	List<com.pratikshat.exicom.model.response.UserForList> getUserforList(@Param("enabled")boolean enabled, @Param("businessDomainId")int businessDomainId);
}
