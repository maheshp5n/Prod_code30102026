package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.RequisitionProductDetails;
import com.pratikshat.exicom.model.response.RequisitionProductDetailsResponse;

@Repository
public interface RequisitionProductDetailsRepository extends JpaRepository<RequisitionProductDetails, Long> {
	
	@Query("select new com.pratikshat.exicom.model.response.RequisitionProductDetailsResponse(rr) from RequisitionProductDetails rr left join rr.products left join rr.requisition rq where rq.id=:id")
	Set<RequisitionProductDetailsResponse> getByRequisitionId(@Param("id")long id); 
	
	@Modifying
	@Transactional
	int deleteByRequisition_Id(long requisitionId);
}
