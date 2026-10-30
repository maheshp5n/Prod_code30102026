package com.pratikshat.exicom.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Repository
public interface BRCRepairInventoryReceiveRepository extends JpaRepository<com.pratikshat.exicom.entity.BRCRepairInventoryReceive, Long>,BRCRepairInventoryReceiveRepositoryCustome {
	@Transactional(readOnly=true)		
	@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr")
	Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getMIDetails(Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr where re.ownerDetails  in :ownerDetails")
	Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getMIDetails(@Param("ownerDetails")java.util.List<com.pratikshat.exicom.entity.EmployeeDetails>ownerDetails, Pageable page);
	
	@Transactional(readOnly=true)		
	@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr left join re.legalEntity lg left join re.site left join re.contact left join re.packingCondition left join re.faultObserved left join re.ownerDetails left join re.modifiedBy left join item.type left join item.engineer left join item.battryBalanceAt left join item.brcBERRemarks where re.receivingId=:receivingId")
	com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse getMIByMiId(@Param("receivingId") long receivingId);
	
	
	//created by shyamu
		@Transactional(readOnly=true)
		int countByReceivingId(long receivingId);
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr left join re.legalEntity lg left join re.site left join re.contact left join re.packingCondition left join re.faultObserved left join re.ownerDetails left join re.modifiedBy left join item.type left join item.engineer left join item.battryBalanceAt left join item.brcBERRemarks where re.receivingId=:receivingId")
		com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse getReceivingId(@Param("receivingId")long receivingId);
		@Transactional(readOnly=true)		
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.type tp left join item.dispatchDetails left join re.product pr left join re.assets ast left join ast.account act left join re.legalEntity lg left join re.caseDetails cs left join re.site st left join re.contact ct ")
		Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getBRCInventoryReceive(Pageable page);
		@Transactional(readOnly=true)	
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.type tp left join item.dispatchDetails left join re.product pr left join re.assets ast left join ast.account act left join re.legalEntity lg left join re.caseDetails cs left join re.site st left join re.contact ct join BRCRepairInventoryReceiveRecentVisit recent on re.receivingId=recent.receivingId where recent.visitorEmailId=:emailId")
		Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getRecentVisit(@Param("emailId")String visitorEmailId, Pageable page);
		@Transactional(readOnly=true)	
		@Query("select new com.pratikshat.exicom.model.response.BRCRepairInventoryForList(re.receivingId,re.receivingName) from BRCRepairInventoryReceive re left join re.repariItem item left join item.dispatchDetails")
		java.util.List<com.pratikshat.exicom.model.response.BRCRepairInventoryForList> getBRCRepairInventoryForList();
		
		@Query("select new com.pratikshat.exicom.model.response.BRCRepariInventoryProductConsumedResponse(pc) from BRCRepariInventoryProductConsumed pc where pc.receiveDetails.receivingId=:receivingId")
		Page<com.pratikshat.exicom.model.response.BRCRepariInventoryProductConsumedResponse> getProductConsumedByMI(@Param("receivingId") long receivingId,Pageable page);
		
		// close by shyamu
		
		@Transactional(readOnly=true)		
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.engineer eng left join eng.user usr left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr where usr.emailId=:emailId")
		Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getMIByEngineer(@Param("emailId")String emailId, Pageable page);
		
		
		@Transactional(readOnly=true)		
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.engineer eng left join eng.user usr left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr where usr.emailId=:emailId and tp.typeName=:type")
		Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getMIByEngineerAndType(@Param("emailId")String emailId, @Param("type")String type, Pageable page);
		
		@Transactional(readOnly=true)		
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.engineer eng left join eng.user usr left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr where tp.typeName=:type and re.ownerDetails in :ownerDetails")
		Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getMIByType(@Param("ownerDetails")List<com.pratikshat.exicom.entity.EmployeeDetails>ownerDetails, @Param("type")String type, Pageable page);
		
		@Transactional(readOnly=true)		
		@Query("select new com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse(re) from BRCRepairInventoryReceive re left join re.repariItem item left join item.engineer eng left join eng.user usr left join item.type tp left join item.dispatchDetails left join re.caseDetails cs left join re.assets ast left join ast.account act left join re.product pr where tp.typeName=:type")
		Page<com.pratikshat.exicom.model.response.BRCInventoryReceiveResponse> getMIByType(@Param("type")String type, Pageable page);

		@Query("select new com.pratikshat.exicom.model.response.BRCRepariInventoryProductConsumedResponse(pc) from BRCRepariInventoryProductConsumed pc")
		Page<com.pratikshat.exicom.model.response.BRCRepariInventoryProductConsumedResponse> getProductConsumed(Pageable page);

}
