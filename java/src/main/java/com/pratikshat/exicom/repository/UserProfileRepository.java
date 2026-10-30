package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface UserProfileRepository extends JpaRepository<com.pratikshat.exicom.entity.UserProfile, Long>,UserProfileRepositoryCustome {
	public static final String VIEW="viewRights";
	public static final String VIEW_ALL="viewAllRights";
	public static final String CREATE="createRights";
	public static final String EDIT="editRights";
	public static final String DELETE ="deleteRights";
	
	@Query("select new com.pratikshat.exicom.model.response.ObjectAccessResponse(acc.id,prp.id,obj.objectName,prp.profileName,obj.objectUrl,prp.profileDescription,acc.viewRights,acc.viewAllRights,acc.createRights,acc.editRights,acc.deleteRights,obj.objectLableName) from UserProfileObjectAccess acc join acc.object obj join acc.userProfile prp where prp.id=:profileId")
	List<com.pratikshat.exicom.model.response.ObjectAccessResponse> getObjectsAccessByProfileId(@Param("profileId")long profileID);
	@Query("select new com.pratikshat.exicom.model.response.UINavigationModuleResponse(coalesce(nm.uiModulesId,0),coalesce(nm.name,''),coalesce(nm.url,''),coalesce(nm.icon,''),coalesce(nm.title,0),coalesce(nm.variant,''),coalesce(nm.parent.uiModulesId,0)) from UserNavigationModule um join um.navigationModules nm where um.userProfile=:userProfile and um.uiActive=true")
	List<com.pratikshat.exicom.model.response.UINavigationModuleResponse> getNavigationModuleByProfile(@Param("userProfile")com.pratikshat.exicom.entity.UserProfile profile);
	@Query("select new com.pratikshat.exicom.model.response.UINavigationModuleResponse(coalesce(nm.uiModulesId,0),coalesce(nm.name,''),coalesce(nm.url,''),coalesce(nm.icon,''),coalesce(nm.title,0),coalesce(nm.variant,''),coalesce(nm.parent.uiModulesId,0)) from UINavigationModules nm where nm.businessDomain.businessDomainId=:businessDomainId or nm.businessDomain.businessDomainId=3")
	List<com.pratikshat.exicom.model.response.UINavigationModuleResponse> getNavigationModule(@Param("businessDomainId")int businessDomainId);
	
	@Query("select upf from UserProfile upf where upf.businessDomain.businessDomainId=3 or upf.businessDomain.businessDomainId=:businessDomainId")
	List<com.pratikshat.exicom.entity.UserProfile> getAll(@Param("businessDomainId")int businessDomainId);
	
	@Query(value="SELECT rg.`group_name` as 'role'"
			+ "FROM  `lproduct_request` pr\r\n" + 
			"JOIN `login_user_roles` role ON role.`login_username`= pr.`owner_details_email_id`\r\n" + 
			"JOIN `user_roles` ur ON ur.`authorities`=role.`authority`\r\n" + 
			"JOIN `role_group` rg ON rg.`id`=ur.`role_group_id`\r\n" + 
			" WHERE pr.`location_product_request_id`=:productrequestid",nativeQuery=true)
	Map<Object,String> getuserRole(@Param("productrequestid")long productrequestid);
}
