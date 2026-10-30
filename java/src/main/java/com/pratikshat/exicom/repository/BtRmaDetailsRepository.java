package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCase;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.BtRMADetails;
import com.pratikshat.exicom.model.response.BtCommissioningAssetMasterUpdateResponse;
import com.pratikshat.exicom.model.response.BtRmaResponse;

@Repository
public interface BtRmaDetailsRepository extends JpaRepository<BtRMADetails, Long>,BtRmaDetailsRepositoryCustome {
   
	BtRMADetails findByManualId(String manualId);
	BtRMADetails findById(long rmaid);
    
    @Query(value = "SELECT  rma.`id` AS id, rma.`created_date` AS createdDate, rma.`last_modified_date` AS lastModifiedDate,  \r\n"
    		+ "    		     			rma.`close_date` AS closeDate, rma.`manual_id` AS manualId, rma.`rma_status` AS rmaStatus, \r\n"
    		+ "    		     			rma.`created_by_email_id` AS createdEmailId, rma.`last_modified_by_email_id` AS modifiedEmailId, \r\n"
    		+ "    		     			rma.`case_details_id` AS caseId, btcase.`manual_id` AS caseManualId, rma.`owner_email_id` AS ownerEmailId,  \r\n"
    		+ "    		     			rma.`service_appointment_service_appointment_id` AS serviceappointmentId, ser.`manual_id` AS serviceAppManualId, \r\n"
    		+ "    		     			ast.id AS AssetId, ast.`manual_id` AS AssetSerialNumber, btcase.`missing_assets` AS missingAsset, \r\n"
    		+ "    		     			btP.`manual_id` AS productPartCode,btP.`id` AS productID,\r\n"
    		+ "    		     			st.id AS siteId, st.manual_id AS siteManualId, st.site_name AS siteName,\r\n"
    		+ "    		     			st.circle_circle_name\r\n"
    		+ "    		     			\r\n"
    		+ "    		     				FROM `btrmadetails` rma \r\n"
    		+ "    		     				JOIN `bt_case` btcase ON  btcase.`id` =rma.`case_details_id` \r\n"
    		+ "    		     		LEFT JOIN `bt_service_appointment` ser ON ser.`service_appointment_id` =rma.`service_appointment_service_appointment_id`\r\n"
    		+ "    		     		LEFT JOIN `bt_assets` ast ON ast.id=btcase.`bt_assets_id` LEFT JOIN `bt_products` btP ON ast.`products_id`=btP.`id`\r\n"
    		+ "    		     		JOIN `bt_site_details` st ON st.id=btcase.`site_id`\r\n"
    		+ "    		     		",nativeQuery = true,
    		countQuery = "SELECT COUNT(*)\r\n"
    				+ "    		     				FROM `btrmadetails` rma \r\n"
    	    		+ "    		     				JOIN `bt_case` btcase ON  btcase.`id` =rma.`case_details_id` \r\n"
    	    		+ "    		     		LEFT JOIN `bt_service_appointment` ser ON ser.`service_appointment_id` =rma.`service_appointment_service_appointment_id`\r\n"
    	    		+ "    		     		LEFT JOIN `bt_assets` ast ON ast.id=btcase.`bt_assets_id` LEFT JOIN `bt_products` btP ON ast.`products_id`=btP.`id`\r\n"
    	    		+ "    		     		JOIN `bt_site_details` st ON st.id=btcase.`site_id`")
    Page<Map<String, Object>> getAll(Pageable pageable);
    
    @Query(value = "SELECT  \r\n"
    		+ "    		 	rma.`id` AS id, rma.`created_date` AS createdDate, rma.`last_modified_date` AS lastModifiedDate,  \r\n"
    		+ "    		 	rma.`close_date` AS closeDate, rma.`manual_id` AS manualId, rma.`rma_status` AS rmaStatus, \r\n"
    		+ "    		 	rma.`created_by_email_id` AS createdEmailId, rma.`last_modified_by_email_id` AS modifiedEmailId, \r\n"
    		+ "    		 	rma.`case_details_id` AS caseId, btcase.`manual_id` AS caseManualId, rma.`owner_email_id` AS ownerEmailId,  \r\n"
    		+ "    		 	rma.`service_appointment_service_appointment_id` AS serviceappointmentId, ser.`manual_id` AS serviceManualId, \r\n"
    		+ "    		 	ast.id AS AssetId, ast.`manual_id` AS AssetSerialNumber, btcase.`missing_assets` AS missingAsset,  \r\n"
    		+ "    		        btP.`manual_id` AS productPartCode,btP.`id` AS productID, \r\n"
    		+ "    		     	btsite.id AS siteId, btsite.manual_id AS siteManualId, btsite.site_name AS siteName, btsite.circle_circle_name,\r\n"
    		+ "    		 	CONCAT(IFNULL(cby.`first_name`,' '),IFNULL(cby.`middle_name`,' '),IFNULL(cby.`last_name`,''))AS `createdByName`, \r\n"
    		+ "    		 	CONCAT(IFNULL(mody.`first_name`,' '),IFNULL(mody.`middle_name`,' '),IFNULL(mody.`last_name`,''))AS `modifiedByName`, \r\n"
    		+ "    		 	CONCAT(IFNULL(cby.`first_name`,' '),IFNULL(cby.`middle_name`,' '),IFNULL(cby.`last_name`,''))AS `ownerName`, \r\n"
    		+ "    		 	btsite.`customer` AS siteTechName,ct.`contact_id` AS customerId,  \r\n"
    		+ "    		 	CONCAT(IFNULL(ct.`salutation`,' '),IFNULL(ct.`first_name`,' '),IFNULL(ct.`middle_name`,' '),IFNULL(ct.`last_name`,''))AS customerName, \r\n"
    		+ "    		 	casetype.`case_type_id` AS caseTypeId, casetype.`case_types_name` AS caseTypeName,inven.`in_date` AS BRCReceivedDate,inven.`id` AS BRCId,inven.`manual_id` AS BRCManualId, \r\n"
    		+ "    		  rm.`remarks` AS rmaRemarksA, rj.`remarks` AS rmaRemarksR,  \r\n"
    		+ "    		 	btcase.`created_date` AS createDate, \r\n"
    		+ "    		 (CASE  \r\n"
    		+ "    		     			WHEN rma.`rma_status` LIKE 'closed%' THEN CONCAT(TIMESTAMPDIFF(DAY,rma.`created_date`,inven.`in_date`), \r\n"
    		+ "    		 ' Days ',FLOOR(TIMESTAMPDIFF(HOUR,rma.`created_date`,inven.`in_date`) MOD 24), \r\n"
    		+ "    		 ' Hours ',FLOOR(TIMESTAMPDIFF(MINUTE,rma.`created_date`,inven.`in_date`) MOD 60),' Minutes') \r\n"
    		+ "    		 							ELSE 			   CONCAT(TIMESTAMPDIFF(DAY,rma.`created_date`,NOW()), \r\n"
    		+ "    		 			' Days ',FLOOR(TIMESTAMPDIFF(HOUR,rma.`created_date`,NOW()) MOD 24), \r\n"
    		+ "    		 			' Hours ',FLOOR(TIMESTAMPDIFF(MINUTE,rma.`created_date`,NOW()) MOD 60),' Minutes') \r\n"
    		+ "    		 					END) AS SLATime \r\n"
    		+ "    		 		FROM `btrmadetails` rma \r\n"
    		+ "    		 		JOIN `bt_case` btcase ON  btcase.`id` =rma.`case_details_id` \r\n"
    		+ "    		 		JOIN `bt_site_details` btsite ON btsite.`id`=btcase.`site_id` \r\n"
    		+ "    		 		JOIN `contact_details` ct ON  ct.`contact_id`=btcase.`contact_person_contact_id` \r\n"
    		+ "    		 		JOIN `case_types` casetype ON casetype.`case_type_id` =btcase.`case_type_case_type_id` \r\n"
    		+ "    		 		LEFT JOIN `bt_customer_inventory_in` inven ON inven.`rma_id`=rma.`id` \r\n"
    		+ "    		 		LEFT JOIN `bt_service_appointment` ser ON ser.`service_appointment_id` =rma.`service_appointment_service_appointment_id` \r\n"
    		+ "    		 		LEFT JOIN employee_details cby ON rma.created_by_email_Id=cby.email_id  \r\n"
    		+ "    		 		LEFT JOIN employee_details mody ON rma.last_modified_by_email_Id=mody.email_id  \r\n"
    		+ "    		      LEFT JOIN `bt_rma_change_status_log` rm ON rm.`rma_manual_id`= rma.`manual_id`  \r\n"
    		+ "	 		      LEFT JOIN `bt_rma_reject_log` rj ON rj.`rma_manual_id`= rma.`manual_id`  \r\n"
    		+ "	 		      LEFT JOIN `bt_assets` ast ON ast.id=btcase.`bt_assets_id` LEFT JOIN `bt_products` btP ON ast.`products_id`=btP.`id` \r\n"
    		+ "    		 		WHERE rma.`id`=:rmaId",nativeQuery = true)
    Map<String, Object> getByRmaId(@Param("rmaId") long id );
    
    BtRMADetails findByCaseDetails(BtCase caseId);
    
    @Query("select new com.pratikshat.exicom.model.response.BtRmaResponse(rma) from BtRMADetails rma join rma.caseDetails btcase left join rma.serviceAppointment service where btcase.id=:id" )
	BtRmaResponse getRmaByCaseId(@Param("id") long id);

	@Query("select rma from BtRMADetails rma join rma.caseDetails cs join cs.btAssets ast join ast.products prd where ast.manualId=:serialNumber and prd=:products and rma.rmaStatus = :rmastatus")
	List<BtRMADetails> getByAssetsSerialnumberAndProduct(@Param("serialNumber") String serialNumber,@Param("products") BtProducts products,@Param("rmastatus")String rmastatus);
    
	@Query(value="select rm.`manual_id` AS 'rmaManualId', rm.`id` AS 'rmaId' FROM `btrmadetails` rm WHERE rm.rma_status!='Closed' AND rm.`manual_id` LIKE :search", nativeQuery=true)
	Page<Map<String, Object>> getRmaList(@Param("search")String search,Pageable page);
}
