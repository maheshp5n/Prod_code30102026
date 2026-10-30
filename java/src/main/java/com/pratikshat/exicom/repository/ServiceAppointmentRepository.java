package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LPTransferReceiveItem;
import com.pratikshat.exicom.entity.ServiceAppointment;
import com.pratikshat.exicom.entity.ServiceRecource;

@Repository
public interface ServiceAppointmentRepository extends JpaRepository<ServiceAppointment, Long>,ServiceAppointmentRepositoryCustom {
	int countByServiceAppointmentId(long serviceAppointmentId);
	int countByServiceAppointmentIdAndStatus_StatusNameIn(long serviceAppointmentId,java.util.List<String> status);
	int countByCaseDetails_caseIdAndStatus_StatusNameIn(long caseId,java.util.List<String>status);
	
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.claimStatus clm left join sa.status ss where sa.ownerDetails.emailId=:emailId or cs.ownerDetails.emailId=:emailId")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getByOwner(@Param("emailId")String userEmailId,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa,esg,sart) from ServiceAppointment sa left join sa.claimStatus clm left join sa.status sas left join sa.serviceResource sar left join sa.pendingResponsibility spr left join sa.createdBy cb left join sa.modifiedBy mb left join sa.ownerDetails onr left join sa.circleManager cm left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join ESignatureOnSA esg on esg.id=sa left join SaEngRating sart on sart.sa=sa where sa.serviceAppointmentId=:serviceAppointmentId")
	com.pratikshat.exicom.model.response.ServiceAppointmentResponse getByAppointmentId(@Param("serviceAppointmentId")long appointmentid);
	@Query("select onr.emailId from ServiceAppointment sa join sa.ownerDetails onr where sa.serviceAppointmentId=:serviceAppointmentId")
	String getOwnerEmailId(@Param("serviceAppointmentId")long serviceAppointmentId);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss join ServiceAppointmentRecentVisit rvi on rvi.serviceAppointmentId=sa.serviceAppointmentId where rvi.visitorId=:emailId")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getRecentVisit(@Param("emailId")String emailId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where clm.claimStatusName in :claimstatus")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentListByClaimStatus(@Param("claimstatus")List<String> claimStatus,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where clm.claimStatusName in :claimstatus and (sa.ownerDetails in :userlis or cs.ownerDetails in :userlis)")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentListByClaimStatus(@Param("userlis")List<com.pratikshat.exicom.entity.EmployeeDetails>userlis, @Param("claimstatus")List<String> claimStatus,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where ss.statusName in :status")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentListByStatus(@Param("status")List<String> status,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where ss.statusName in :status and (sa.ownerDetails in :userlist or cs.ownerDetails in :userlist)")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentListByStatus(@Param("userlist")List<com.pratikshat.exicom.entity.EmployeeDetails> userlis, @Param("status")List<String> status,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationResponse(lc) from ServiceAppointment sa join sa.serviceResource sr join sr.location lc where sa.serviceAppointmentId=:serviceAppointmentId")
	com.pratikshat.exicom.model.response.LocationResponse getLocationByServiceAppointment(@Param("serviceAppointmentId")long serviceAppointmentId);

	// created by shyamu
	@Query("select new com.pratikshat.exicom.model.response.ServiceResourseResponse(res) from ServiceAppointment sa join sa.serviceResource res where sa.serviceAppointmentId=:serviceAppointmentId")
	Page<com.pratikshat.exicom.model.response.ServiceResourseResponse> getServiceResourseByServiceAppointment(@Param("serviceAppointmentId") long serviceAppointmentId,Pageable page);
//	//close by shyamu
	
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.circle cl left join st.ssa zn left join sa.claimStatus clm left join sa.status ss where cs.caseId=:caseId")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentByCaseId(@Param("caseId")long caseId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.circle cl left join st.ssa zn left join sa.claimStatus clm left join sa.status ss where sa.ownerDetails in :ownerList or cs.ownerDetails in :ownerList")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getAll(@Param("ownerList")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> empList, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.circle cl left join st.ssa zn left join sa.claimStatus clm left join sa.status ss")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getAll(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where (onr.emailId=:emailId or cs.ownerDetails.emailId=:emailId) and clm.claimStatusId in :status")
	List<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentListByStatusForAnnexure(@Param("status")List<Long> status, @Param("emailId")String emailId);
	
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where (onr.emailId=:emailId or cs.ownerDetails.emailId=:emailId) and clm.claimStatusId in :status")
	List<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getServiceAppointmentListByClaimStatusForAnnexure(@Param("status")List<Long> status, @Param("emailId")String emailId);
	
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(COUNT(1),0) AS `total count`,\r\n" + 
			"IFNULL(t.`open count`,0) AS `open count`\r\n" + 
			"FROM `service_appointment` sa\r\n" + 
			"JOIN `case_details` cd ON cd.`case_id`=sa.`case_details_case_id`\r\n" + 
			"CROSS JOIN (\r\n" + 
			"	SELECT \r\n" + 
			"	COUNT(1) AS `open count` \r\n" + 
			"	FROM `service_appointment` sa \r\n" + 
			"	JOIN `case_details` cd ON cd.`case_id`=sa.`case_details_case_id`\r\n" + 
			"	WHERE sa.`status_status_id` IN (1,2,3)\r\n" + 
			"	AND (sa.`owner_details_email_id` IN (:userList)\r\n" + 
			"		OR cd.`owner_details_email_id` IN (:userList))\r\n" + 
			"	AND YEAR(sa.`create_date`)=YEAR(NOW()) \r\n" + 
			"	AND MONTH(sa.`create_date`)=MONTH(NOW())) t\r\n" + 
			"WHERE (sa.`owner_details_email_id` IN (:userList)\r\n" + 
			"	OR cd.`owner_details_email_id` IN (:userList))\r\n" + 
			"AND YEAR(sa.`create_date`)=YEAR(NOW()) \r\n" + 
			"AND MONTH(sa.`create_date`)=MONTH(NOW())\r\n" + 
			"GROUP BY t.`open count`", nativeQuery=true)
	Map<String,Object> getServiceAppointment_open_vs_total_mtd(@Param("userList")List<String> userList);
	
	@Query(value="SELECT \r\n" + 
			"IFNULL(COUNT(1),0) AS `total count`,\r\n" + 
			"IFNULL(t.`open count`,0) AS `open count`\r\n" + 
			"FROM `service_appointment` sa\r\n" + 
			"JOIN `case_details` cd ON cd.`case_id`=sa.`case_details_case_id`\r\n" + 
			"CROSS JOIN (\r\n" + 
			"	SELECT \r\n" + 
			"	COUNT(1) AS `open count` \r\n" + 
			"	FROM `service_appointment` sa \r\n" + 
			"	JOIN `case_details` cd ON cd.`case_id`=sa.`case_details_case_id`\r\n" + 
			"	WHERE sa.`status_status_id` IN (1,2,3)\r\n" + 
			"	AND (sa.`owner_details_email_id` IN (:userList)\r\n" + 
			"		OR cd.`owner_details_email_id` IN (:userList))) t\r\n" + 
			"WHERE (sa.`owner_details_email_id` IN (:userList)\r\n" + 
			"	OR cd.`owner_details_email_id` IN (:userList))\r\n" + 
			"AND DATE_FORMAT(sa.`create_date`,'%Y-%m-%d') \r\n" + 
			"	BETWEEN \r\n" + 
			"		CASE\r\n" + 
			"			WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()-1),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"			ELSE DATE_FORMAT(CONCAT(YEAR(NOW()),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"		END\r\n" + 
			"	AND \r\n" + 
			"		CASE\r\n" + 
			"			WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()),'-03','-31'),'%Y-%m-%d')\r\n" + 
			"			ELSE DATE_FORMAT(CONCAT(YEAR(NOW())+1,'-03','-31'),'%Y-%m-%d')\r\n" + 
			"		END\r\n" + 
			"GROUP BY t.`open count`", nativeQuery=true)
	Map<String,Object> getServiceAppointment_open_vs_total_ytd(@Param("userList")List<String> userList);
	
	@Query("select new com.pratikshat.exicom.model.response.ServiceAppointmentResponse(sa) from ServiceAppointment sa left join sa.caseDetails cs join sa.serviceResource res left join res.contact contact left join contact.account account left join cs.assets ast left join cs.site st left join st.account acct left join st.circle cl left join st.ssa zn left join sa.ownerDetails onr left join sa.claimStatus clm left join sa.status ss where account.sapCustomerNumber=:emailId and sa.engAssigned=false")
	Page<com.pratikshat.exicom.model.response.ServiceAppointmentResponse> getVendorServiceAppointment(@Param("emailId")String emailId,Pageable page);
	
//	okproductserial number details from lptranfer recive line item .....................Shagufta
	@Query(value="SELECT litem.`modified_date` AS `ModifiedDate`,\r\n" + 
			"			litem.`owner_email_id` AS `OwnerEmail`,\r\n" + 
			"			litem.`product_serial_number` AS `Serial`\r\n" + 
			"			FROM `lptransfer_receive_item` item\r\n" + 
			"			JOIN `lptransfer_receive_line_item` litem  ON item.`receive_item_id` = litem.`receive_item_receive_item_id`\r\n" + 
			"			WHERE litem.`product_serial_number` LIKE :okserialnumber ORDER BY litem.`modified_date` DESC LIMIT 2", nativeQuery=true)
	List<Map<String,Object>> getRecievedetailsofTransferItems(@Param("okserialnumber")String okserialnumber);
	
}