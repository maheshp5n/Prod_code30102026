package com.pratikshat.exicom.repository;

import com.pratikshat.exicom.entity.BtSiteDetails;
import com.pratikshat.exicom.entity.CircleDetails;
import com.pratikshat.exicom.entity.CustomerDeatils;
import com.pratikshat.exicom.entity.ZoneDetails;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/*@Author --Deepak Punetha
  Date 01-06-2021*/
@Repository
public interface BtSiteDetailsRepository extends JpaRepository<BtSiteDetails, Long>, BtSiteDetailsRepositoryCustome {

	@Query(value = "SELECT btsite.id as siteId,btsite.manual_id AS siteManualId,btsite.site_name AS siteName,btsite.infra_id AS infraId,btsite.cluster_district AS clusterDistrict,btsite.cmp AS siteCmp,btsite.site_description AS siteDescription,btsite.site_address AS siteAddress,\r\n"
			+ "	circ.`zone_battery_zone_name` AS zoneName,btsite.customer AS customer,btsite.last_modified_date AS lastModifiedDate,\r\n"
			+ "	btsite.created_date AS createdDate,btsite.`rjil_jc_name` AS RjilJcName,\r\n"
			+ "	paccount.sap_customer_number AS primaryAccountSapNumber ,paccount.name AS primaryAccountName,\r\n"
			+ "	owner.email_id AS ownerEmailId,owner.first_name AS ownerFirstName,owner.middle_name AS ownerMiddleName,\r\n"
			+ "	owner.last_name AS ownerLastName,account.sap_customer_number AS accountSapNumber ,\r\n"
			+ "	account.name AS accountName,serv.legal_entity_id AS serviceCenterId,serv.legal_entity_name as serviceCenterName,ser.service_resource_id AS serviceResourceId,\r\n"
			+ "	ser.name AS serviceResourceName ,circ.circle_name AS circleName,circ.circle_name AS circleId,ssa.id AS ssaId ,\r\n"
			+ "	ssa.name AS ssaName,crdby.email_id AS createdEmailId,crdby.first_name AS createdFirstName,crdby.middle_name AS createdMiddleName,\r\n"
			+ "	crdby.last_name AS createdLastName,mody.first_name AS lastModifyFirstName,mody.middle_name AS lastModifyMiddleName,\r\n"
			+ "	mody.last_name AS lastModifyLastName,mody.email_id AS lastModifyEmailId  FROM bt_site_details btsite left JOIN\r\n"
			+ "	accounts paccount ON paccount.sap_customer_number=btsite.primary_account_sap_customer_number left JOIN employee_details owner ON owner.email_id=btsite.owner_email_id\r\n"
			+ "	left JOIN accounts account ON account.sap_customer_number=btsite.account_sap_customer_number left JOIN legal_entity serv ON serv.legal_entity_id=btsite.service_center_legal_entity_id  \r\n"
			+ "	left JOIN service_recource ser ON ser.service_resource_id= btsite.service_resource_service_resource_id left JOIN circle_details circ\r\n"
			+ "	ON circ.circle_name=btsite.circle_circle_name left JOIN ssa ssa ON ssa.id=btsite.ssa_id left JOIN employee_details crdby ON crdby.email_id=btsite.created_by_email_id\r\n"
			+ "	left JOIN employee_details mody ON mody.email_id=btsite.last_modified_by_email_id", nativeQuery = true, countQuery = "SELECT count(*) from bt_site_details btsite left JOIN\r\n"
					+ "			accounts paccount ON paccount.sap_customer_number=btsite.primary_account_sap_customer_number  left JOIN employee_details owner ON owner.email_id=btsite.owner_email_id\r\n"
					+ "			left JOIN accounts account ON account.sap_customer_number=btsite.account_sap_customer_number left JOIN legal_entity serv ON serv.legal_entity_id=btsite.service_center_legal_entity_id  \r\n"
					+ "		left JOIN service_recource ser ON ser.service_resource_id= btsite.service_resource_service_resource_id left  JOIN circle_details circ\r\n"
					+ "			ON circ.circle_name=btsite.circle_circle_name left JOIN ssa ssa ON ssa.id=btsite.ssa_id"
					+ "		left	JOIN employee_details mody ON mody.email_id=btsite.last_modified_by_email_id")
	Page<Map<String, Object>> getAllPage(Pageable pageable);


	@Query(value = "SELECT btsite.id as siteId,btsite.manual_id AS siteManualId,btsite.site_name AS siteName,btsite.infra_id AS infraId,btsite.cluster_district AS clusterDistrict,btsite.cmp AS siteCmp,btsite.site_description AS siteDescription,btsite.site_address AS siteAddress,"
			+"	circ.`zone_battery_zone_name` AS zoneName,btsite.customer AS customer,btsite.last_modified_date AS lastModifiedDate,"
			+ "	btsite.created_date AS createdDate,btsite.`rjil_jc_name` AS RjilJcName,"
			+ "	paccount.sap_customer_number AS primaryAccountSapNumber ,paccount.name AS primaryAccountName,"
			+ "	owner.email_id AS ownerEmailId,owner.first_name AS ownerFirstName,owner.middle_name AS ownerMiddleName,"
			+ "	owner.last_name AS ownerLastName,account.sap_customer_number AS accountSapNumber ,"
			+ "	account.name AS accountName,serv.legal_entity_id AS serviceCenterId,serv.legal_entity_name as serviceCenterName,ser.service_resource_id AS serviceResourceId,"
			+ "	ser.name AS serviceResourceName ,circ.circle_name AS circleName,circ.circle_name AS circleId,ssa.id AS ssaId ,"
			+ "	ssa.name AS ssaName,crdby.email_id AS createdEmailId,crdby.first_name AS createdFirstName,crdby.middle_name AS createdMiddleName,"
			+ "	crdby.last_name AS createdLastName,mody.first_name AS lastModifyFirstName,mody.middle_name AS lastModifyMiddleName,"
			+ "	mody.last_name AS lastModifyLastName,mody.email_id AS lastModifyEmailId  FROM bt_site_details btsite left JOIN"
			+ "	accounts paccount ON paccount.sap_customer_number=btsite.primary_account_sap_customer_number left JOIN employee_details owner ON owner.email_id=btsite.owner_email_id"
			+ "	left JOIN accounts account ON account.sap_customer_number=btsite.account_sap_customer_number left JOIN legal_entity serv ON serv.legal_entity_id=btsite.service_center_legal_entity_id "
			+ "	left JOIN service_recource ser ON ser.service_resource_id= btsite.service_resource_service_resource_id left JOIN circle_details circ"
			+ "	ON circ.circle_name=btsite.circle_circle_name left JOIN ssa ssa ON ssa.id=btsite.ssa_id left JOIN employee_details crdby ON crdby.email_id=btsite.created_by_email_id "
			+ "  left JOIN employee_details mody ON mody.email_id=btsite.last_modified_by_email_id where btsite.id=:siteId", nativeQuery = true)
	Map<String, Object> getBySiteid(@Param("siteId") long siteId);
	
	@Query(value = "SELECT btsite.id as siteId,btsite.manual_id AS siteManualId,btsite.site_name AS siteName,btsite.infra_id AS infraId,btsite.cluster_district AS clusterDistrict,btsite.cmp AS siteCmp,btsite.site_description AS siteDescription,btsite.site_address AS siteAddress,"
			+"	circ.`zone_battery_zone_name` AS zoneName,btsite.customer AS customer,btsite.last_modified_date AS lastModifiedDate,"
			+ "	btsite.created_date AS createdDate,btsite.`rjil_jc_name` AS RjilJcName,"
			+ "	paccount.sap_customer_number AS primaryAccountSapNumber ,paccount.name AS primaryAccountName,"
			+ "	owner.email_id AS ownerEmailId,owner.first_name AS ownerFirstName,owner.middle_name AS ownerMiddleName,"
			+ "	owner.last_name AS ownerLastName,account.sap_customer_number AS accountSapNumber ,"
			+ "	account.name AS accountName,serv.legal_entity_id AS serviceCenterId,serv.legal_entity_name as serviceCenterName,ser.service_resource_id AS serviceResourceId,"
			+ "	ser.name AS serviceResourceName ,circ.circle_name AS circleName,circ.circle_name AS circleId,ssa.id AS ssaId ,"
			+ "	ssa.name AS ssaName,crdby.email_id AS createdEmailId,crdby.first_name AS createdFirstName,crdby.middle_name AS createdMiddleName,"
			+ "	crdby.last_name AS createdLastName,mody.first_name AS lastModifyFirstName,mody.middle_name AS lastModifyMiddleName,"
			+ "	mody.last_name AS lastModifyLastName,mody.email_id AS lastModifyEmailId  FROM bt_site_details btsite left JOIN"
			+ "	accounts paccount ON paccount.sap_customer_number=btsite.primary_account_sap_customer_number left JOIN employee_details owner ON owner.email_id=btsite.owner_email_id"
			+ "	left JOIN accounts account ON account.sap_customer_number=btsite.account_sap_customer_number left JOIN legal_entity serv ON serv.legal_entity_id=btsite.service_center_legal_entity_id "
			+ "	left JOIN service_recource ser ON ser.service_resource_id= btsite.service_resource_service_resource_id left JOIN circle_details circ"
			+ "	ON circ.circle_name=btsite.circle_circle_name left JOIN ssa ssa ON ssa.id=btsite.ssa_id left JOIN employee_details crdby ON crdby.email_id=btsite.created_by_email_id "
			+ "  left JOIN employee_details mody ON mody.email_id=btsite.last_modified_by_email_id where btsite.manual_id=:manualId", nativeQuery = true)
	Map<String, Object> getByManualSiteId(@Param("manualId") String manualId);

	int countById(long siteid);

	//////////////// Get Battery Site Details By Manula Id//////////////////////
	@Query(value = "select * from bt_site_details bsa  where bsa.id=:siteId", nativeQuery = true)
	BtSiteDetails getBySiteID(@Param("siteId") String siteId);

	
	@Query(value = "select * from bt_site_details bsa  where bsa.manual_id=:siteManualId",nativeQuery = true)
	BtSiteDetails getExistBatterySite(@Param("siteManualId") String siteManualId);
	
	
	///////////////// Given Circule Details Zone exist or not
	///////////////// check///////////////////////////
	@Query(value = "select zn.zone_name as zoneName from circle_details cd  join zone_details zn on zn.zone_name=cd.zone_battery_zone_name where cd.circle_name=:circuleName", nativeQuery = true)
	Map<String, Object> getZonDetails(@Param("circuleName") String circuleName);

	/////////// Get Customer Detais for check it is exist or not//////////
	@Query(value = "select cd.customer_name as customerName from customer_deatils cd where cd.customer_name=:customerName", nativeQuery = true)
	Map<String, Object> getCustomerDetails(@Param("customerName") String customerName);
	///////////////// Get Circule List//////////////

	@Query(value = "select cd from CircleDetails cd where cd.serviceCenterBattery.legalEntityId=:serviceCenterid or cd.serviceCenterPower.legalEntityId=:serviceCenterid or cd.serviceCenterEv.legalEntityId=:serviceCenterid")
	List<CircleDetails> getCiculeList(@Param("serviceCenterid") long serviceCenterid);

	////////////////// Get Site detailss By Site name or Site Manual Id
	@Query(value = "select btsite.id as batterySiteId, btsite.manual_id AS manuaSitelId,btsite.site_name AS siteName FROM bt_site_details btsite where btsite.site_name like:searchKey or btsite.manual_id like:searchKey", nativeQuery = true,countQuery = "select count(*) as ct FROM bt_site_details btsite where btsite.site_name like:searchKey or btsite.manual_id like:searchKey")
	Page<Map<String, Object>> getSiteBySiteNameOrSiteManualId(@Param("searchKey") String searchKey,Pageable pageable);
	
	@Query(value = "SELECT site.`id` AS siteId,acc.`sap_customer_number` AS accountSapNumber,acc.`name` AS accountName,pacc.`sap_customer_number` AS primaryAccountSapNumber,\r\n"
			+ "	pacc.`name` AS primaryAccountName,own.`email_id` AS ownerEmailId,own.`first_name` AS ownerFirstName,own.`middle_name`  AS ownerMiddleName,\r\n"
			+ "	own.`last_name` AS ownerLastName,site.`site_name` AS siteName,service.`legal_entity_id` AS serviceCenterId,service.`legal_entity_name`AS serviceCenterName,\r\n"
			+ "	site.`infra_id` AS infraId,serRes.`service_resource_id` AS serviceResourceId,serRes.`name` AS serviceResourceName,circle.`circle_name` AS circleId,\r\n"
			+ "	circle.`circle_name` AS circleName,ssa.`id` AS ssaId,ssa.`name` AS ssaName,site.`cluster_district` AS clusterDistrict,site.`site_description` AS siteDescription,\r\n"
			+ "	site.`site_address` AS siteAddress,cby.`email_id` AS createdByEmailId ,cby.`first_name` AS createdByFirstName,cby.`middle_name` AS createdByMiddleName,\r\n"
			+ "	cby.`last_name` AS createdByLastName,mby.`first_name` AS modifiedByFirstName,mby.`middle_name`  AS modifiedByMiddleName,\r\n"
			+ "	mby.`email_id` AS modifiedByEmailId,mby.`last_name` AS modifiedByLastName,site.`last_modified_date` AS modifiedDate,site.`manual_id` AS siteManualId,\r\n"
			+ "	site.`customer` AS customer.site.`indus_id` AS indusId\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "FROM `bt_site_details` site\r\n"
			+ "JOIN `accounts` acc ON acc.`sap_customer_number`=site.`account_sap_customer_number`\r\n"
			+ "LEFT JOIN `accounts` pacc ON pacc.`sap_customer_number`=site.`primary_account_sap_customer_number`\r\n"
			+ "JOIN `employee_details` cby ON cby.`email_id`=site.`created_by_email_id`\r\n"
			+ "JOIN `employee_details` mby ON mby.`email_id`=site.`last_modified_by_email_id`\r\n"
			+ "JOIN `employee_details` own ON own.`email_id`=site.`owner_email_id`\r\n"
			+ "JOIN `legal_entity` service ON service.`legal_entity_id` =site.`service_center_legal_entity_id`\r\n"
			+ "JOIN `service_recource` serRes ON serRes.`service_resource_id`=site.`service_resource_service_resource_id`\r\n"
			+ "JOIN `circle_details` circle ON circle.`circle_name` = site.`circle_circle_name`\r\n"
			+ "JOIN `ssa` ssa ON ssa.`id` =`ssa_id`\r\n"
			+ "WHERE acc.`sap_customer_number` =:sapnumber\r\n",nativeQuery = true)
	List<Map<String, Object>> getSiteDetailsByAccountSapNumber(@Param("sapnumber")String sapnumber);
	
	@Query(value = "SELECT site.`id` AS siteId,acc.`sap_customer_number` AS accountSapNumber,acc.`name` AS accountName,pacc.`sap_customer_number` AS primaryAccountSapNumber,\r\n"
			+ "	pacc.`name` AS primaryAccountName,own.`email_id` AS ownerEmailId,own.`first_name` AS ownerFirstName,own.`middle_name`  AS ownerMiddleName,\r\n"
			+ "	own.`last_name` AS ownerLastName,site.`site_name` AS siteName,service.`legal_entity_id` AS serviceCenterId,service.`legal_entity_name`AS serviceCenterName,\r\n"
			+ "	site.`infra_id` AS infraId,serRes.`service_resource_id` AS serviceResourceId,serRes.`name` AS serviceResourceName,circle.`circle_name` AS circleId,\r\n"
			+ "	circle.`circle_name` AS circleName,ssa.`id` AS ssaId,ssa.`name` AS ssaName,site.`cluster_district` AS clusterDistrict,site.`site_description` AS siteDescription,\r\n"
			+ "	site.`site_address` AS siteAddress,cby.`email_id` AS createdByEmailId ,cby.`first_name` AS createdByFirstName,cby.`middle_name` AS createdByMiddleName,\r\n"
			+ "	cby.`last_name` AS createdByLastName,mby.`first_name` AS modifiedByFirstName,mby.`middle_name`  AS modifiedByMiddleName,\r\n"
			+ "	mby.`email_id` AS modifiedByEmailId,mby.`last_name` AS modifiedByLastName,site.`last_modified_date` AS modifiedDate,site.`manual_id` AS siteManualId,\r\n"
			+ "	site.`customer` AS customer,site.`indus_id` AS indusId\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "FROM `bt_site_details` site\r\n"
			+ "JOIN `accounts` acc ON acc.`sap_customer_number`=site.`account_sap_customer_number`\r\n"
			+ "LEFT JOIN `accounts` pacc ON pacc.`sap_customer_number`=site.`primary_account_sap_customer_number`\r\n"
			+ "JOIN `employee_details` cby ON cby.`email_id`=site.`created_by_email_id`\r\n"
			+ "JOIN `employee_details` mby ON mby.`email_id`=site.`last_modified_by_email_id`\r\n"
			+ "JOIN `employee_details` own ON own.`email_id`=site.`owner_email_id`\r\n"
			+ "JOIN `legal_entity` service ON service.`legal_entity_id` =site.`service_center_legal_entity_id`\r\n"
			+ "JOIN `service_recource` serRes ON serRes.`service_resource_id`=site.`service_resource_service_resource_id`\r\n"
			+ "JOIN `circle_details` circle ON circle.`circle_name` = site.`circle_circle_name`\r\n"
			+ "JOIN `ssa` ssa ON ssa.`id` =`ssa_id`\r\n"
			+ "\r\n"
			+ "WHERE acc.`sap_customer_number` =:sapnumber\r\n",nativeQuery = true,
			countQuery ="SELECT count(*) FROM `bt_site_details` site\r\n"
					+ "JOIN `accounts` acc ON acc.`sap_customer_number`=site.`account_sap_customer_number`\r\n"
					+ "LEFT JOIN `accounts` pacc ON pacc.`sap_customer_number`=site.`primary_account_sap_customer_number`\r\n"
					+ "JOIN `employee_details` cby ON cby.`email_id`=site.`created_by_email_id`\r\n"
					+ "JOIN `employee_details` mby ON mby.`email_id`=site.`last_modified_by_email_id`\r\n"
					+ "JOIN `employee_details` own ON own.`email_id`=site.`owner_email_id`\r\n"
					+ "JOIN `legal_entity` service ON service.`legal_entity_id` =site.`service_center_legal_entity_id`\r\n"
					+ "JOIN `service_recource` serRes ON serRes.`service_resource_id`=site.`service_resource_service_resource_id`\r\n"
					+ "JOIN `circle_details` circle ON circle.`circle_name` = site.`circle_circle_name`\r\n"
					+ "JOIN `ssa` ssa ON ssa.`id` =`ssa_id`" )
	Page<Map<String, Object>> getSiteDetailsByAccountId(@Param("sapnumber")String sapnumber,Pageable page);
}

