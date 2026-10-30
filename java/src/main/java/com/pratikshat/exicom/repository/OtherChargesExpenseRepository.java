package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OtherChargesExpense;
import com.pratikshat.exicom.model.response.OtherChargesExpenseResponse;


@Repository
public interface OtherChargesExpenseRepository extends JpaRepository<OtherChargesExpense, Long>,OtherChargesExpenseCustomeRepository {

	@Query("select new com.pratikshat.exicom.model.response.OtherChargesExpenseResponse(oc) from OtherChargesExpense oc left join oc.expenseManagement em left join oc.otherChargesType oct left join oc.createdBy cb left join oc.modifiedBy mb left join oc.ownerDetails onr where oc.isDeleted=false and oc.id=:id")
	OtherChargesExpenseResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.OtherChargesExpenseResponse(oc) from OtherChargesExpense oc left join oc.expenseManagement em where oc.isDeleted=false and em.expenseManagementId=:id")
	Page<OtherChargesExpenseResponse> getByExpenseManagmentId(@Param("id")long expenseManagmentId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.OtherChargesExpenseResponse(oc) from OtherChargesExpense oc left join oc.expenseManagement em where oc.isDeleted=false and em.expenseManagementId=:id")
	List<OtherChargesExpenseResponse> getListByExpenseManagmentId(@Param("id")long expenseManagmentId);
}
