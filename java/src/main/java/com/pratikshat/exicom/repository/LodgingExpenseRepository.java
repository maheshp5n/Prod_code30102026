package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LodgingExpense;
import com.pratikshat.exicom.model.response.LodgingExpenseResponse;

@Repository
public interface LodgingExpenseRepository extends JpaRepository<LodgingExpense, Long>,LodgingExpenseCustomeRepository {

	@Query("select new com.pratikshat.exicom.model.response.LodgingExpenseResponse(le) from LodgingExpense le left join le.expenseManagement em left join le.city ct left join ct.cityCatagory cc left join le.createdBy cb left join le.modifiedBy mb left join le.ownerDetails onr where le.isDeleted=false and le.id=:id")
	LodgingExpenseResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.LodgingExpenseResponse(le) from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and em.expenseManagementId=:id")
	Page<LodgingExpenseResponse> getByExpenseManagmentId(@Param("id")long expenseManagmentId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LodgingExpenseResponse(le) from LodgingExpense le left join le.expenseManagement em where le.isDeleted=false and em.expenseManagementId=:id")
	List<LodgingExpenseResponse> getListByExpenseManagmentId(@Param("id")long expenseManagmentId);
}
