package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Oppertunity;
import com.pratikshat.exicom.model.response.OppertunityResponse;

@Repository
public interface OppertunityRepository extends JpaRepository<Oppertunity, Long>, OppertunityRepositoryCustom{

	Oppertunity findByOppertunityId(long id);
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityResponse(opr) from Oppertunity opr left join opr.caseDetails cd left join opr.ownerDetails onr left join opr.createdBy cb left join opr.modifiedBy mb where opr.oppertunityId=:id")
	OppertunityResponse getByOppertunityId(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityResponse(op) from Oppertunity op left join op.caseDetails cd left join op.ownerDetails onr left join op.createdBy cb left join op.modifiedBy mb where op.isDeleted=false and op.oppertunityStage.oppertunityStageName=:stage")
	Page<OppertunityResponse> getOppertunityListByCaseStage(@Param("stage")String stage,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityResponse(op) from Oppertunity op left join op.caseDetails cd left join op.ownerDetails onr left join op.createdBy cb left join op.modifiedBy mb where op.isDeleted=false")
	Page<OppertunityResponse> getOppertunityListAll(Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityResponse(op) from Oppertunity op left join op.caseDetails cd left join op.ownerDetails onr left join op.createdBy cb left join op.modifiedBy mb join OppertunityRecentVisit rvi on rvi.oppertunityId=op.oppertunityId where op.isDeleted=false and rvi.visitorId=:emailId")
	Page<OppertunityResponse> getOppertunityRecentVisitList(@Param("emailId")String emailId,Pageable page);
	
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityResponse(op) from Oppertunity op left join op.caseDetails cd left join op.ownerDetails onr left join op.createdBy cb left join op.modifiedBy mb where op.isDeleted=false and op.forcastCategory.oppertunityForecastCategoryName=:forecast")
	Page<OppertunityResponse> getOppertunityListByForecast(@Param("forecast")String forecast,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityResponse(opr) from Oppertunity opr left join opr.ownerDetails onr left join opr.createdBy cb left join opr.modifiedBy mb left join opr.caseDetails cs where opr.isDeleted=false and cs.caseId=:caseId")
	Page<OppertunityResponse> getOppertunityByCaseId(@Param("caseId")long caseId,Pageable Page);
}
