package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pratikshat.exicom.entity.MSLRequest;
import com.pratikshat.exicom.model.response.MSLRequestResponse;

@Repository
@Transactional(readOnly = true)
public interface MSLRequestRepository extends JpaRepository<MSLRequest,Long>,MSLRequestCustomRepository {

	@Query("select new com.pratikshat.exicom.model.response.MSLRequestResponse(ra) from MSLRequest ra left join ra.owner onr left join ra.createdBy createdby left join ra.modifiedBy modified where ra.id=:id")
	MSLRequestResponse getById(@Param("id")long id);
	MSLRequest findById(long id);
	@Query("select new com.pratikshat.exicom.model.response.MSLRequestResponse(ra) from MSLRequest ra left join ra.owner onr join MSLRequestRecentVisit rvi on rvi.mslId=ra.id where rvi.visitorId=:emailId")
	Page<MSLRequestResponse> getRecentVisit(@Param("emailId")String emailId,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.MSLRequestResponse(ra) from MSLRequest ra left join ra.owner onr left join ra.createdBy createdby left join ra.modifiedBy modified where onr.emailId=:emailId")
	Page<MSLRequestResponse> getByOwner(@Param("emailId")String emailId,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.MSLItemRequestResponse(item.id,pr.productPartCode,pr.pdoductDescription,item.newMSL,item.newHSL) from MSLRequestItem item join item.mslRequest msl join item.products pr where msl.id=:mslid")
	Page<com.pratikshat.exicom.model.response.MSLItemRequestResponse> getReleatedItemByRequestId(@Param("mslid")long id,Pageable apge);
	
	@Query("select new com.pratikshat.exicom.model.response.MSLRequestResponse(ra) from MSLRequest ra left join ra.owner onr left join ra.createdBy createdby left join ra.modifiedBy modified where ra.mslRequestStatus.statusName=:status")
	Page<MSLRequestResponse> getByStatus(@Param("status")String status,Pageable page);
}
