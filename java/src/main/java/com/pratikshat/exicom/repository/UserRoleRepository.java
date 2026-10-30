package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.LoginUserRoles;
import com.pratikshat.exicom.entity.UserRoles;
@Repository
public interface UserRoleRepository extends JpaRepository<com.pratikshat.exicom.entity.UserRoles, String> {
//	@Transactional
//	@Query("select rl from UserRoles rl where rl.businessDomain.businessDomainId=:businessDomainId or rl.businessDomain.businessDomainId=3")
//	List<UserRoles> findAll(@Param("businessDomainId")int businessDomainId);
	@Query("select lr from LoginUserRoles lr where lr.authority=:roleName")
	List<LoginUserRoles> getLoginUserRoleActie(@Param("roleName")String roleName);
	
	@Query(value="SELECT `authorities`,`parent_user_roles_authorities` AS parentUserRoles FROM `user_roles` WHERE `business_domain_business_domain_id`=:businessDomainId OR `business_domain_business_domain_id`=3",nativeQuery = true)
	List<Map<String,Object>> getAll(@Param("businessDomainId")int businessDomainId);
	
	UserRoles findByAuthorities(String authorities);
	
	@Query(value ="SELECT * FROM `user_roles` ur WHERE ur.`authorities`=:role" ,nativeQuery = true )
	UserRoles getByAuthorities(@Param("role") String role);
	
	@Query("select new com.pratikshat.exicom.model.response.UserRoleResponse(coalesce(rl.authorities,''),coalesce(rl.parentUserRoles.authorities,'')) from UserRoles rl where rl.businessDomain.businessDomainId=:businessDomainId or rl.businessDomain.businessDomainId=3")
	java.util.List<com.pratikshat.exicom.model.response.UserRoleResponse> getUserRoles(@Param("businessDomainId")int businessDomainId);
	
	@Query(value="select * from role_group",nativeQuery=true)
	List<Map<String,Object>> getRoleGroup();
	@Query(value="select * from user_roles where business_domain_business_domain_id=:businessDomainId or business_domain_business_domain_id=3",nativeQuery=true)
	List<Map<String,Object>> getRoles(@Param("businessDomainId")int businessDomainId);
	
	@Query(value = "DELETE FROM `user_roles` ur WHERE ur.`authorities`=:role" ,nativeQuery = true)
	String deleteRole(@Param("role") String role );
	
}
