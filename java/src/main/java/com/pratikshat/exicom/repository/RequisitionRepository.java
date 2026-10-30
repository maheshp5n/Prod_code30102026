package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Requisition;
import com.pratikshat.exicom.model.response.OppertunityResponse;
import com.pratikshat.exicom.model.response.RequisitionResponse;

@Repository
public interface RequisitionRepository extends JpaRepository<Requisition,Long>,RequisitionCustomRepository {

	@Query("select new com.pratikshat.exicom.model.response.RequisitionResponse(rr) from Requisition rr left join rr.owner onr left join rr.createdBy cb left join rr.modifiedBy mb left join rr.requisitionStatus rs left join rr.fromWarehouse fw left join rr.toWarehouse tw left join rr.requisitionProductType rpt left join rr.requisitionApprovalStatus ras left join fw.legalEntity fwle left join tw.legalEntity twle left join fwle.legalEntityType flet where rr.id=:id")
	RequisitionResponse getByRequisitionId(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.RequisitionResponse(rr) from Requisition rr left join rr.owner onr left join rr.createdBy cb left join rr.modifiedBy mb where onr.emailId=:emailId")
	Page<RequisitionResponse> getRequisitionByOwner(@Param("emailId")String emailId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.RequisitionResponse(rr) from Requisition rr left join rr.owner onr left join rr.createdBy cb left join rr.modifiedBy mb join RequisitionRecentVisit rvi on rvi.requisitionId=rr.id where rvi.visitorId=:emailId")
	Page<RequisitionResponse> getRequisitionRecentVisitList(@Param("emailId")String emailId,Pageable page);
	
	Requisition findById(long requisitionId);
}
