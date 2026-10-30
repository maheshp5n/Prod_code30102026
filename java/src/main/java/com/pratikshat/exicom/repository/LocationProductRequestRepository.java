package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.LegalEntityReceintVisit;
import com.pratikshat.exicom.model.request.LegalEntityResponse;
import com.pratikshat.exicom.model.response.LocationProductRequestItemResponse;
@Repository
public interface LocationProductRequestRepository extends JpaRepository<com.pratikshat.exicom.entity.LProductRequest, Long>,LocationProductRequestRepositoryCustome {
//	LocationProductRequestItemResponse findIdAndVisitorId(long lpRequestItemId,String visitorId);
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where lpr.locationProductRequestStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByAll(Pageable page);
	
//	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where sl.owner in :userlist or dl.owner in :userlist")
//	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByAll(@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where lpr.locationProductRequestStatus !='Archived' and (sl.locationId in :childsLoc or dl.locationId in :childsLoc)")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByAllWithChilds(@Param("childsLoc")java.util.List<Long> ids, Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where lpr.locationProductRequestStatus in :status")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByStatus(@Param("status")java.util.List<String> status,Pageable page);
	
//	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where lpr.locationProductRequestStatus in :status and (sl.owner in :userlist or dl.owner in :userlist)")
//	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByStatus(@Param("status")java.util.List<String> status,@Param("userlist")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails> userList,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where lpr.locationProductRequestStatus in :status and (sl.locationId in :idlist or dl.locationId in :idlist)")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByStatus(@Param("status")java.util.List<String> status,@Param("idlist")java.util.List<Long> idlist,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where sl.locationId=:locationId and lpr.locationProductRequestStatus!=:status and lpr.locationProductRequestStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getBySourceLocation(@Param("locationId")long locationId,@Param("status")String status,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl where dl.locationId=:locationId and lpr.locationProductRequestStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByDestinationLocation(@Param("locationId")long locationId,Pageable page);
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl left join lpr.ownerDetails own left join lpr.createdBy cr left join lpr.modifiedBy mdf where lpr.locationProductRequestId=:productRequestId and lpr.locationProductRequestStatus !='Archived'")
	com.pratikshat.exicom.model.response.LocationProductRequestResponse getByProductRequestId(@Param("productRequestId")long productRequestId);
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestItemResponse(item) from LProductRequestLineItem item join item.locationProductRequest prd join item.product pd where prd.locationProductRequestId=:productRequestId and prd.locationProductRequestStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestItemResponse> getProductRequestItemsByRequestId(@Param("productRequestId")long productRequestId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestItemResponse(item) from LProductRequestLineItem item join item.locationProductRequest prd join item.product pd where prd.locationProductRequestId=:productRequestId and item.lineItemStatus IS NULL and prd.locationProductRequestStatus !='Archived'")
	List<com.pratikshat.exicom.model.response.LocationProductRequestItemResponse> getProductRequestItemsByRequestIdForTransfer(@Param("productRequestId")long productRequestId);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl left join dl.owner onr join ServiceRecource sr on sr.location=dl where sr.user.emailId=:emailId and lpr.locationProductRequestStatus not in ('Draft','Archived')")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getByOwnerAll(@Param("emailId")String emailId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestResponse(lpr) from LProductRequest lpr left join lpr.sourceLocation sl left join lpr.destinationLocation dl left join sl.owner onr join ServiceRecource sr on sr.location=sl where sr.user.emailId=:emailId and lpr.locationProductRequestStatus not in ('Draft','Archived')")
	Page<com.pratikshat.exicom.model.response.LocationProductRequestResponse> getForOwnerAll(@Param("emailId")String emailId,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestPendingToReceive(titem) from LPTransferItem titem join titem.lpTranfer trns join trns.locationProductRequest pr join pr.sourceLocation sl join pr.destinationLocation dl join dl.owner onr join titem.product prd join titem.lpRequestLineItem rtiem join ServiceRecource sr on sr.location=dl where pr.locationProductRequestId=:lprequestId and sr.user.emailId=:emailId and titem.transferQuantity>titem.receivedQuantity and pr.locationProductRequestStatus !='Archived'")
	java.util.List<com.pratikshat.exicom.model.response.LocationProductRequestPendingToReceive> getPendingItemToReceive(@Param("lprequestId")long lprequestId,@Param("emailId")String emailId);
	
	@Query("select litems.productSerialNumber from LPTransferLineItem litems join litems.transferItem item where litems.received=false and item.lpTransferItemId=:transferLineItemId")
	java.util.List<String> getPendingLineItemToReceive(@Param("transferLineItemId")long transferLineItemId);
	@Query("select new com.pratikshat.exicom.model.response.LocationProductTransferItemResponse(item) from LPTransferItem item join item.product prd join item.lpTranfer trn join trn.locationProductRequest preq where preq.locationProductRequestId=:productRequestId and preq.locationProductRequestStatus !='Archived'")
	Page<com.pratikshat.exicom.model.response.LocationProductTransferItemResponse> getLocationProductTransferByProductRequestId(@Param("productRequestId")long productRequestId,Pageable page);
//    shagufta
	@Query("select new com.pratikshat.exicom.model.response.LocationProductRequestItemResponse(item) from LProductRequestLineItem item join item.locationProductRequest prd join item.product pd left join item.createdBy cby left join item.modifiedBy mby where item.locationProductRequestLineItemId=:productRequestItemId and prd.locationProductRequestStatus !='Archived'")
	com.pratikshat.exicom.model.response.LocationProductRequestItemResponse getProductRequestItemsById(@Param("productRequestItemId")long productRequestItemId);
	
}
