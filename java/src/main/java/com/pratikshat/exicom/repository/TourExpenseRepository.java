package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.TourExpense;
import com.pratikshat.exicom.model.response.TourExpenseResponse;

@Repository
public interface TourExpenseRepository extends JpaRepository<TourExpense, Long>,TourExpenseCustomeRepository {

	@Query("select new com.pratikshat.exicom.model.response.TourExpenseResponse(te) from TourExpense te left join te.expenseManagement em left join te.modeOfTravel mt left join te.ownConveyance oc left join te.createdBy cb left join te.modifiedBy mb left join te.ownerDetails onr where te.isDeleted=false and te.id=:id")
	TourExpenseResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.TourExpenseResponse(te) from TourExpense te left join te.expenseManagement em where te.isDeleted=false and em.expenseManagementId=:id")
	Page<TourExpenseResponse> getByExpenseManagmentId(@Param("id")long expenseManagmentId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.TourExpenseResponse(te) from TourExpense te left join te.expenseManagement em where te.isDeleted=false and em.expenseManagementId=:id")
	List<TourExpenseResponse> getListByExpenseManagmentId(@Param("id")long expenseManagmentId);
}
