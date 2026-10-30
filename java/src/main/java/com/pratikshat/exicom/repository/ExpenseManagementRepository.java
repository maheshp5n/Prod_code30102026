package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ExpenseAccountsOnly;
import com.pratikshat.exicom.entity.ExpenseManagement;
import com.pratikshat.exicom.model.response.ExpenseManagementResponse;

@Repository
public interface ExpenseManagementRepository
		extends JpaRepository<ExpenseManagement, Long>, ExpenseManagementCustomRepository {

	@Query("select em from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.costCenter cc left join em.status es left join em.businessArea ba left join em.businessPlace bp left join em.createdBy cby left join em.modifiedBy mby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where em.isDeleted=false and em.expenseManagementId =:id")
	ExpenseManagement findByExpenseManagementId(@Param("id") long id);

	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.costCenter cc left join em.status es left join em.businessArea ba left join em.businessPlace bp left join em.createdBy cby left join em.modifiedBy mby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where em.isDeleted=false and em.expenseManagementId =:id")
	ExpenseManagementResponse getById(@Param("id") long id);

	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where em.isDeleted=false and es.statusName not in ('Draft','Submitted')")
	Page<ExpenseManagementResponse> getAll(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where  em.isDeleted=false and es.statusName not in ('Draft','Submitted') and onr in :owner")
	Page<ExpenseManagementResponse> getAll(@Param("owner")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> owners,Pageable page);

	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where  em.isDeleted=false and es.statusName in :status")
	Page<ExpenseManagementResponse> getByStatus(@Param("status")List<String> status, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where  em.isDeleted=false and es.statusName in :status and onr in :owner")
	Page<ExpenseManagementResponse> getByStatus(@Param("owner")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> owners, @Param("status")List<String> status, Pageable page);

	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr join ExpenseManagementRecentVisit rs on em.expenseManagementId=rs.expenseManagementId left join ExpenseAccountsOnly aco on aco.expense=em where em.isDeleted=false and rs.visitorId=:visitorId")
	Page<ExpenseManagementResponse> getRecentVisitByVisitor(@Param("visitorId") String visitorId, Pageable page);

	@Query("select aco from ExpenseAccountsOnly aco where aco.expense=:expenseManagement")
	ExpenseAccountsOnly findByExpense(@Param("expenseManagement")ExpenseManagement expenseManagement);

	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where  em.isDeleted=false and es.statusName='Draft' and onr.emailId=:userEmailId")
	Page<ExpenseManagementResponse> getDraftExpesneByUser(@Param("userEmailId")String userEmailId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em where  em.isDeleted=false and onr.emailId=:userEmailId")
	Page<ExpenseManagementResponse> getSubmittedExpesneByUser(@Param("userEmailId")String userEmailId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em join ToBeApproved tobe on tobe.objectId=em.expenseManagementId and tobe.ObjectName='ExpenseManagement' where  em.isDeleted=false and tobe.status=true and tobe.action in ('Approved','Approve') and tobe.assignedTo.emailId in :assignToEmailId")
	Page<ExpenseManagementResponse> getApprovedExpesneByUser(@Param("assignToEmailId")List<String> assignToEmailId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em join ToBeApproved tobe on tobe.objectId=em.expenseManagementId and tobe.ObjectName='ExpenseManagement' where  em.isDeleted=false and tobe.status=true and tobe.action in ('Rejected','Reject') and tobe.assignedTo.emailId in :assignToEmailId")
	Page<ExpenseManagementResponse> getRejectedExpesneByUser(@Param("assignToEmailId")List<String> assignToEmailId, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.ExpenseManagementResponse(em,aco) from ExpenseManagement em left join em.businessDomain bd left join em.contact cnt left join cnt.grade gd left join em.expensePurpose ep left join em.expenseType et left join em.status es left join em.createdBy cby left join em.owner onr left join ExpenseAccountsOnly aco on aco.expense=em join ToBeApproved tobe on tobe.objectId=em.expenseManagementId and tobe.ObjectName='ExpenseManagement' where  em.isDeleted=false and tobe.status=false and tobe.assignedTo.emailId in :assignToEmailId")
	Page<ExpenseManagementResponse> getToBeApprovedExpesneByUser(@Param("assignToEmailId")List<String> assignToEmailId, Pageable page);
	
	
	@Query(value="SELECT \r\n" + 
			"SUM(dd.`Gen Exp`) AS `Gen Exp`,\r\n" + 
			"SUM(dd.`SA Exp`) AS `SA Exp`\r\n" + 
			"FROM (\r\n" + 
			"SELECT \r\n" + 
			"IFNULL(COUNT(1),0) AS `Gen Exp`,\r\n" + 
			"0 AS `SA Exp`\r\n" + 
			"FROM `expense_management` em\r\n" + 
			"WHERE em.`status_status_id`!=8\r\n" + 
			"AND em.`owner_email_id`=:user \r\n" + 
			"AND YEAR(em.`created_date`)=YEAR(NOW()) \r\n" + 
			"AND MONTH(em.`created_date`)=MONTH(NOW())\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `Gen Exp`,\r\n" + 
			"IFNULL(COUNT(1),0) AS `SA Exp`\r\n" + 
			"FROM `service_appointment` sa\r\n" + 
			"JOIN `contact_details` cd ON cd.`email_id`=sa.`owner_details_email_id`\r\n" + 
			"WHERE cd.`contact_type_type_id`=1\r\n" + 
			"AND sa.`owner_details_email_id`=:user \r\n" + 
			"AND sa.`claim_status_claim_status_id`!=2\r\n" +
			"AND YEAR(sa.`create_date`)=YEAR(NOW()) \r\n" + 
			"AND MONTH(sa.`create_date`)=MONTH(NOW())) AS dd", nativeQuery=true)
	Map<String,Object> getGenAndSAExpense_mtd(@Param("user")String user);
	
	@Query(value="SELECT \r\n" + 
			"SUM(dd.`Gen Exp`) AS `Gen Exp`,\r\n" + 
			"SUM(dd.`SA Exp`) AS `SA Exp`\r\n" + 
			"FROM (\r\n" + 
			"SELECT \r\n" + 
			"IFNULL(COUNT(1),0) AS `Gen Exp`,\r\n" + 
			"0 AS `SA Exp`\r\n" + 
			"FROM `expense_management` em\r\n" + 
			"WHERE em.`status_status_id`!=8\r\n" + 
			"AND em.`owner_email_id`=:user \r\n" + 
			"AND DATE_FORMAT(em.`created_date`,'%Y-%m-%d') \r\n" + 
			"BETWEEN \r\n" + 
			"	CASE\r\n" + 
			"		WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()-1),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"		ELSE DATE_FORMAT(CONCAT(YEAR(NOW()),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"	END\r\n" + 
			"AND \r\n" + 
			"	CASE\r\n" + 
			"		WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()),'-03','-31'),'%Y-%m-%d')\r\n" + 
			"		ELSE DATE_FORMAT(CONCAT(YEAR(NOW())+1,'-03','-31'),'%Y-%m-%d')\r\n" + 
			"	END\r\n" + 
			"UNION ALL\r\n" + 
			"SELECT \r\n" + 
			"0 AS `Gen Exp`,\r\n" + 
			"IFNULL(COUNT(1),0) AS `SA Exp`\r\n" + 
			"FROM `service_appointment` sa\r\n" + 
			"JOIN `contact_details` cd ON cd.`email_id`=sa.`owner_details_email_id`\r\n" + 
			"WHERE cd.`contact_type_type_id`=1\r\n" + 
			"AND sa.`claim_status_claim_status_id`!=2\r\n" + 
			"AND sa.`owner_details_email_id`=:user \r\n" + 
			"AND DATE_FORMAT(sa.`create_date`,'%Y-%m-%d') \r\n" + 
			"BETWEEN \r\n" + 
			"	CASE\r\n" + 
			"		WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()-1),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"		ELSE DATE_FORMAT(CONCAT(YEAR(NOW()),'-04','-01'),'%Y-%m-%d')\r\n" + 
			"	END\r\n" + 
			"AND \r\n" + 
			"	CASE\r\n" + 
			"		WHEN MONTH(NOW())<4 THEN DATE_FORMAT(CONCAT(YEAR(NOW()),'-03','-31'),'%Y-%m-%d')\r\n" + 
			"		ELSE DATE_FORMAT(CONCAT(YEAR(NOW())+1,'-03','-31'),'%Y-%m-%d')\r\n" + 
			"	END\r\n" + 
			") AS dd", nativeQuery=true)
	Map<String,Object> getGenAndSAExpense_ytd(@Param("user")String user);
}
