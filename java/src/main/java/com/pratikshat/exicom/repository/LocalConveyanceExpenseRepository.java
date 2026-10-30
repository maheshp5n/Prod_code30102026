package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LocalConveyanceExpense;
import com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse;

@Repository
public interface LocalConveyanceExpenseRepository extends JpaRepository<LocalConveyanceExpense, Long>, LocalConveyanceExpenseCustomeRepository {

	@Query("select new com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse(le) from LocalConveyanceExpense le left join le.expenseManagement em left join le.modeOfTravel mt left join le.ownConveyance oc left join le.createdBy cb left join le.modifiedBy mb left join le.ownerDetails onr where le.isDeleted=false and le.id=:id")
	LocalConveyanceExpenseResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse(le) from LocalConveyanceExpense le left join le.expenseManagement em where le.isDeleted=false and em.expenseManagementId=:id")
	Page<LocalConveyanceExpenseResponse> getByExpenseManagmentId(@Param("id")long expenseManagmentId,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.LocalConveyanceExpenseResponse(le) from LocalConveyanceExpense le left join le.expenseManagement em where le.isDeleted=false and em.expenseManagementId=:id")
	List<LocalConveyanceExpenseResponse> getListByExpenseManagmentId(@Param("id")long expenseManagmentId);
}
