package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BoardingExpense;
import com.pratikshat.exicom.model.response.BoardingExpenseResponse;

@Repository
public interface BoardingExpenseRepository extends JpaRepository<BoardingExpense, Long>, BoardingExpenseCustomeRepository {

	@Query("select new com.pratikshat.exicom.model.response.BoardingExpenseResponse(be) from BoardingExpense be left join be.expenseManagement em left join be.city ct left join ct.cityCatagory cc left join be.createdBy cb left join be.modifiedBy mb left join be.ownerDetails onr where be.isDeleted=false and be.id=:id")
	BoardingExpenseResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.BoardingExpenseResponse(be) from BoardingExpense be left join be.expenseManagement em where be.isDeleted=false and em.expenseManagementId=:id")
	Page<BoardingExpenseResponse> getByExpenseManagmentId(@Param("id")long expenseManagmentId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.BoardingExpenseResponse(be) from BoardingExpense be left join be.expenseManagement em where be.isDeleted=false and em.expenseManagementId=:id")
	List<BoardingExpenseResponse> getListByExpenseManagmentId(@Param("id")long expenseManagmentId);
}
