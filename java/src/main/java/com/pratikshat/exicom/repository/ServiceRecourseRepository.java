package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.EmployeeDetails;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.model.response.ServiceResourseResponse;

@Repository
public interface ServiceRecourseRepository extends JpaRepository<ServiceRecource, Long>,ServiceRecourseRepositoryCustome {
	int countByServiceResourceId(long serviceResourceId);
	ServiceRecource findByServiceResourceId(long serviceResourceId);
	@Query("select sr from ServiceRecource sr join sr.user join sr.location loc left join loc.legalEntity lg  where sr.user=:user")
	ServiceRecource findByUser(@Param("user")EmployeeDetails user);
	int countByLocation(Location location);
	int countByContact(com.pratikshat.exicom.entity.ContactDetails contact);
	@Query("select count(sr.serviceResourceId) from ServiceRecource sr join sr.user where sr.user=:user")
	int countByUser(@Param("user")com.pratikshat.exicom.entity.EmployeeDetails user);
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseForList(sr.serviceResourceId,sr.name,act.name, sr.user.emailId) from ServiceRecource sr join sr.contact ct join ct.account act join sr.location lc join ct.legalEntity le where le.legalEntityId=:legalEntityId and (sr.businessDomain.businessDomainId=:businessDomainId or sr.businessDomain.businessDomainId=3)")
	java.util.List<com.pratikshat.exicom.model.response.ServiceResourseForList> getServiceResourseList(@Param("legalEntityId")long legalEntityId, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseResponse(res) from ServiceRecource res left join res.user left join res.location left join res.createdBy left join res.modifiedBy where res.businessDomain.businessDomainId=:businessDomainId or res.businessDomain.businessDomainId=3")
	Page<com.pratikshat.exicom.model.response.ServiceResourseResponse> getByAll(Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseResponse(res) from ServiceRecource res left join res.user left join res.location left join res.createdBy left join res.modifiedBy where res.serviceResourceId=:resourseId")
	com.pratikshat.exicom.model.response.ServiceResourseResponse getByResourseId(@Param("resourseId")long resourseId);
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseResponse(res) from ServiceRecource res left join res.user left join res.location left join res.createdBy left join res.modifiedBy join ServiceResourceRecentVisit rvi on rvi.serviceResourceId=res.serviceResourceId where rvi.visitorId=:emailId and (res.businessDomain.businessDomainId=:businessDomainId or res.businessDomain.businessDomainId=3)")
	Page<ServiceResourseResponse> getRecentVisit(@Param("emailId")String emailId,Pageable page, @Param("businessDomainId")int businessDomainId);
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseForList(sr.serviceResourceId,sr.name,act.name,  sr.user.emailId) from ServiceRecource sr join sr.contact ct join ct.account act join sr.location lco join ct.legalEntity sc where sc.legalEntityId=:legalentityId and (sr.businessDomain.businessDomainId=:businessDomainId or sr.businessDomain.businessDomainId=3)")
	java.util.List<com.pratikshat.exicom.model.response.ServiceResourseForList> getServiceResourseListByLegalEntity(@Param("legalentityId")long legalentityId, @Param("businessDomainId")int businessDomainId);

	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.claimStatus clm left join sa.status sas left join sa.serviceResource sar left join sa.pendingResponsibility spr left join sa.createdBy cb left join sa.modifiedBy mb left join sa.ownerDetails onr left join sa.circleManager cm left join sa.caseDetails cs left join cs.assets ast left join ast.site st left join st.circle cl left join st.ssa zn where sar.serviceResourceId =:id")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentByServiceId(@Param("id") long id, Pageable page);
	
//	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(loc) from ServiceRecource res join res.location loc where res.serviceResourceId=:id")
//	Page<com.pratikshat.exicom.model.response.LocationResponse> getLocationByServiceResource(@Param("id") long id, Pageable page);

	// created by shyamu
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseForList(sr.serviceResourceId,sr.name,act.name,  sr.user.emailId) from ServiceRecource sr join sr.contact ct join ct.account act join sr.location lc join ct.legalEntity le where sr.businessDomain.businessDomainId=:businessDomainId or sr.businessDomain.businessDomainId=3")
	java.util.List<com.pratikshat.exicom.model.response.ServiceResourseForList> getServiceResourceForList(@Param("businessDomainId")int businessDomainId);
	
	//close by shyamu
	
	
	@Query("select sr from ServiceRecource sr join sr.user where sr.user.emailId=:email")
	ServiceRecource getByEmail(@Param("email")String email);
	
	
	@Query(value = "SELECT sr.`service_resource_id` AS Id,sr.`name` AS `Name` FROM `service_recource` sr WHERE sr.`user_email_id` IN :list",nativeQuery = true)
	List<Map<String, Object>> getServiceResource(@Param("list") List<String> list);
	
	
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseResponse(sr) from ServiceRecource sr join sr.contact ct join ct.account act join sr.location lco join ct.legalEntity sc where sc.legalEntityId=:legalentityId and sr.name=:name")
	com.pratikshat.exicom.model.response.ServiceResourseResponse getServiceResourseByLegalEntityAndName(@Param("legalentityId")long legalentityId, @Param("name")String name);
	
	@Query(value="SELECT sr.`name`,lg.`legal_entity_name`,AVG(sar.`engrating`) as avg_rating FROM `sa_eng_rating` sar JOIN `service_appointment` sa ON sar.sa_id=sa.`service_appointment_id`\r\n" + 
			"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" + 
			"	JOIN `contact_details` ct ON ct.`contact_id` =sr.`contact_contact_id`\r\n" + 
			"	JOIN `legal_entity` lg ON lg.`legal_entity_id`=ct.`legal_entity_id`\r\n" + 
			"	WHERE sr.`business_domain_business_domain_id`=:businessDomainId or sr.`business_domain_business_domain_id`=3 \r\n" +
			"	GROUP BY sr.`name`,lg.`legal_entity_name`",nativeQuery = true,countQuery = "SELECT COUNT(*) FROM (SELECT sr.`name`,lg.`legal_entity_name`,AVG(sar.`engrating`) FROM `sa_eng_rating` sar JOIN `service_appointment` sa ON sar.sa_id=sa.`service_appointment_id`\r\n" + 
					"	JOIN `service_recource` sr ON sr.`service_resource_id`=sa.`service_resource_service_resource_id`\r\n" + 
					"	JOIN `contact_details` ct ON ct.`contact_id` =sr.`contact_contact_id`\r\n" + 
					"	JOIN `legal_entity` lg ON lg.`legal_entity_id`=ct.`legal_entity_id`\r\n" + 
					"	WHERE sr.`business_domain_business_domain_id`=:businessDomainId or sr.`business_domain_business_domain_id`=3 \r\n" +
					"	GROUP BY sr.`name`,lg.`legal_entity_name`) sa")
	Page<Map<String,Object>> getServiceResourceRating(Pageable page, @Param("businessDomainId")int businessDomainId);
	
	
	@Query(value = "SELECT `service_appointment_id` AS serviceAppointmentId,appointment.`manual_id` AS manualId,`fault_observed` AS faultObserved,\r\n"
			+ "			`punch_point_exiom` AS punchPointExiom ,`punch_point_customer` AS punchPointCustomer,`visit_date` AS visitDate,\r\n"
			+ "			`customer_representative_name` AS customerRepresentativeName,`customer_representative_contact` AS customerRepresentativeContact,\r\n"
			+ "			`remark` AS remark,pr.`pending_responsibility_name` AS pendingResponsibilityName,pr.`pending_responsibility_id` AS pendingResponsibilityId,\r\n"
			+ "			ca.`id` AS caseId,ca.`manual_id` AS caseIdManual ,ca.`case_reason_reason_id` AS caseReason,\r\n"
			+ "			product.`manual_id` AS assetsPartCode,product.`product_description` AS assetDescription,\r\n"
			+ "			lentity.`legal_entity_id` AS serviceCenterId,lentity.`manual_id` AS serviceCenterManualId,lentity.`legal_entity_name` AS serviceCenterName,\r\n"
			+ "			serRes.`name` AS serviceResourceName, serRes.`service_resource_id` AS serviceResourceId,\r\n"
			+ "			assets.`manual_id` AS assetsSapSerialNumber,ac.`sap_customer_number` AS accountSapNumber, ac.`name` AS accountName,\r\n"
			+ "			ct.`case_type_id` AS caseType,ct.`case_types_name` AS caseTypeName,cst.`case_status_name` AS caseStatus,\r\n"
			+ "			site.`id` AS siteId,site.`manual_id` AS siteIdManual,site.`site_name` AS siteName,\r\n"
			+ "			cby.`first_name` AS createdByFirstName,cby.`middle_name` AS createdByMiddleName, cby.`last_name` AS createdByLastName,cby.`email_id` AS createByEmailId,\r\n"
			+ "			mby.`email_id` AS modifiedByEmailId,mby.`first_name` AS modifiedByFirstName,mby.`middle_name` AS modifiedByMiddleName, mby.`last_name` AS modifiedByLastName,\r\n"
			+ "			sa.`id` AS ssaId,employee.`contact_id` AS employeeCode,embp.`business_place_name` AS businessPlace,bld.`boarding_name` AS grade,\r\n"
			+ "			own.`first_name` AS ownerFirstName,own.`middle_name` AS ownerMiddleName, own.`last_name` AS ownerLastName,own.`email_id` AS ownerEmailId,\r\n"
			+ "			sa.`name` AS ssaName, contact.`contact_id` AS contactId,`due_date` AS dueDate,`due_time` AS dueTime,\r\n"
			+ "			`t_flag` AS tFlag,contact.`first_name` AS contactFirstName,contact.`middle_name` AS contactMiddleName, contact.`last_name` AS contactLastName,\r\n"
			+ "			contact.`email_id` AS circleManagerContactEmailId, contact.`mobile` AS contactPhoneNumber,appointment.`approved_by_finance`AS approvedByFinance,\r\n"
			+ "			ser.`status_name` AS serviceAppointmentStatusName,ser.`status_id` AS serviceAppointmentStatusId,appointment.`is_approved_by_finance` AS isApprovedByFinanceCheck,\r\n"
			+ "			cd.`circle_name` AS circleName,contact.`first_name` AS circleManagerContactFirstName,contact.`middle_name` AS circleManagerContactMiddleName,\r\n"
			+ "			contact.`last_name` AS circleManagerContactLastName,contact.`contact_id` AS circleManagerContactId,\r\n"
			+ "			cstatus.`claim_status_id` AS claimStatusId,appointment.`fixed_payout` AS fixedPayout,\r\n"
			+ "			cstatus.`claim_status_name` AS claimStatusName,\r\n"
			+ "			appointment.`cost_center` AS costCenter,appointment.`wcc` AS wcc,\r\n"
			+ "			appointment.`wcc_id` AS wccId ,appointment.`wcc_manual_id` AS wccManualId,appointment.`acknowledged_by_name` AS acknowledgedByName,\r\n"
			+ "			appointment.`acknowledged_by_phone` AS acknowledgedByPhone,appointment.`created_date` AS createDate\r\n"
			+ "			\r\n"
			+ "			FROM `bt_service_appointment` appointment \r\n"
			+ "			LEFT JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id` =appointment.`pending_responsibility_pending_responsibility_id`\r\n"
			+ "			LEFT JOIN `bt_case` ca ON ca.`id`=appointment.`case_details_id`\r\n"
			+ "			LEFT JOIN `bt_products` product ON product.`id`=appointment.`product_id`\r\n"
			+ "			LEFT JOIN `legal_entity` lentity ON lentity.`legal_entity_id`= ca.`service_center_legal_entity_id`\r\n"
			+ "			LEFT JOIN `service_recource` serRes ON serRes.`service_resource_id` =appointment.`service_resource_service_resource_id`\r\n"
			+ "			LEFT JOIN `contact_details` getCon ON getCon.`contact_id` =serRes.`contact_contact_id`\r\n"
			+ "			LEFT JOIN `expense_management_business_place` embp ON embp.`business_place_id`=getCon.`business_place_business_place_id`\r\n"
			+ "			LEFT JOIN `boarding_loading_details` bld ON bld.`boarding_name` =getCon.`grade_name`\r\n"
			+ "			JOIN `bt_assets` assets ON assets.`id`=ca.`bt_assets_id`\r\n"
			+ "			JOIN `accounts` ac ON ac.`sap_customer_number`=assets.`account_sap_customer_number`\r\n"
			+ "			JOIN `case_types` ct ON ct.`case_type_id` =ca.`case_type_case_type_id`\r\n"
			+ "			JOIN `case_status_details` cst ON cst.`id`=ca.`status_id`\r\n"
			+ "			JOIN `bt_site_details` site ON site.`id` =ca.`site_id`\r\n"
			+ "			JOIN `employee_details` cby ON cby.`email_id`=appointment.`created_by_email_id`\r\n"
			+ "			JOIN `employee_details` mby ON mby.`email_id`=appointment.`last_modified_by_email_id`\r\n"
			+ "			JOIN `employee_details` own ON own.`email_id`=appointment.`owner_details_email_id`\r\n"
			+ "			JOIN `ssa` sa ON sa.`id` =site.`ssa_id`\r\n"
			+ "			JOIN `business_domain` bd ON bd.`business_domain_id` = appointment.`business_domain_business_domain_id`\r\n"
			+ "			JOIN `service_appointment_status` ser ON ser.`status_id`=`status_status_id`\r\n"
			+ "			JOIN `circle_details` cd ON cd.`circle_name` =appointment.`circle_circle_name`\r\n"
			+ "			JOIN `contact_details` contact ON contact.`contact_id` =appointment.`circle_manager_contact_id`\r\n"
			+ "			JOIN `claim_status` cstatus ON cstatus.`claim_status_id`=appointment.`claim_status_claim_status_id`\r\n"
			+ "			JOIN `contact_details` employee ON employee.`contact_id` =own.`contact_contact_id`"
			+ "WHERE serRes.`service_resource_id` =:id\r\n",nativeQuery = true,countQuery ="SELECT COUNT(*) FROM `bt_service_appointment` appointment \r\n"
					+ "			LEFT JOIN `pending_responsibility` pr ON pr.`pending_responsibility_id` =appointment.`pending_responsibility_pending_responsibility_id`\r\n"
					+ "			LEFT JOIN `bt_case` ca ON ca.`id`=appointment.`case_details_id`\r\n"
					+ "			LEFT JOIN `bt_products` product ON product.`id`=appointment.`product_id`\r\n"
					+ "			LEFT JOIN `legal_entity` lentity ON lentity.`legal_entity_id`= ca.`service_center_legal_entity_id`\r\n"
					+ "			LEFT JOIN `service_recource` serRes ON serRes.`service_resource_id` =appointment.`service_resource_service_resource_id`\r\n"
					+ "			LEFT JOIN `contact_details` getCon ON getCon.`contact_id` =serRes.`contact_contact_id`\r\n"
					+ "			LEFT JOIN `expense_management_business_place` embp ON embp.`business_place_id`=getCon.`business_place_business_place_id`\r\n"
					+ "			LEFT JOIN `boarding_loading_details` bld ON bld.`boarding_name` =getCon.`grade_name`\r\n"
					+ "			JOIN `bt_assets` assets ON assets.`id`=ca.`bt_assets_id`\r\n"
					+ "			JOIN `accounts` ac ON ac.`sap_customer_number`=assets.`account_sap_customer_number`\r\n"
					+ "			JOIN `case_types` ct ON ct.`case_type_id` =ca.`case_type_case_type_id`\r\n"
					+ "			JOIN `case_status_details` cst ON cst.`id`=ca.`status_id`\r\n"
					+ "			JOIN `bt_site_details` site ON site.`id` =ca.`site_id`\r\n"
					+ "			JOIN `employee_details` cby ON cby.`email_id`=appointment.`created_by_email_id`\r\n"
					+ "			JOIN `employee_details` mby ON mby.`email_id`=appointment.`last_modified_by_email_id`\r\n"
					+ "			JOIN `employee_details` own ON own.`email_id`=appointment.`owner_details_email_id`\r\n"
					+ "			JOIN `ssa` sa ON sa.`id` =site.`ssa_id`\r\n"
					+ "			JOIN `business_domain` bd ON bd.`business_domain_id` = appointment.`business_domain_business_domain_id`\r\n"
					+ "			JOIN `service_appointment_status` ser ON ser.`status_id`=`status_status_id`\r\n"
					+ "			JOIN `circle_details` cd ON cd.`circle_name` =appointment.`circle_circle_name`\r\n"
					+ "			JOIN `contact_details` contact ON contact.`contact_id` =appointment.`circle_manager_contact_id`\r\n"
					+ "			JOIN `claim_status` cstatus ON cstatus.`claim_status_id`=appointment.`claim_status_claim_status_id`\r\n"
					+ "			JOIN `contact_details` employee ON employee.`contact_id` =own.`contact_contact_id`"
					+ "WHERE serRes.`service_resource_id` =:id" )
	Page<Map<String, Object>> getBtServiceAppointmentByServiceId(@Param("id") long id, Pageable page);
}
