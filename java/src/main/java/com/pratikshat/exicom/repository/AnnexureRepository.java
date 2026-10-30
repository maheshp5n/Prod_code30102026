package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Annexure;
import com.pratikshat.exicom.model.response.AnnexureResponse;

@Repository
public interface AnnexureRepository extends JpaRepository<Annexure,Long>,AnnexureCustomRepository {

	@Query("select new com.pratikshat.exicom.model.response.AnnexureResponse(anx) from Annexure anx left join anx.vendor vnd left join anx.circle cir left join anx.createdBy cby left join anx.modifiedBy mby left join anx.owner onr where anx.id=:id")
	AnnexureResponse getAnnexureById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.AnnexureResponse(anx) from Annexure anx left join anx.vendor vnd left join anx.circle cir left join anx.createdBy cby left join anx.modifiedBy mby left join anx.owner onr")
	Page<AnnexureResponse> getAnnexurePageable(Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.AnnexureResponse(anx) from Annexure anx left join anx.vendor vnd left join anx.circle cir left join anx.createdBy cby left join anx.modifiedBy mby left join anx.owner onr where onr in :userlist")
	Page<AnnexureResponse> getAnnexurePageable(@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList, Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.AnnexureResponse(anx) from Annexure anx left join anx.vendor vnd left join anx.circle cir left join anx.createdBy cby left join anx.modifiedBy mby left join anx.owner onr join AnnexureRecentVisit rc on anx.id=rc.annexureId where rc.visitorEmailId=:username")
	Page<AnnexureResponse> getAnnexureRecentVisit(@Param("username")String username,Pageable pageable);
}
