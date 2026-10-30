package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.AnnexureLineItem;
import com.pratikshat.exicom.model.response.AnnexureLineItemResponse;

@Repository
public interface AnnexureLineItemRepository extends JpaRepository<AnnexureLineItem,Long> {

	@Query("select new com.pratikshat.exicom.model.response.AnnexureLineItemResponse(ali) from AnnexureLineItem ali left join ali.annexure anx left join ali.serviceAppointment sa left join ali.glCode gl left join ali.createdBy cby left join ali.modifiedBy mby left join sa.caseDetails cd left join cd.serviceCenter cs left join cd.site st left join st.account where ali.id=:annexureLineItemId")
	AnnexureLineItemResponse getAnnexureLineItemById(@Param("annexureLineItemId")long annexureLineItemId);
	
	
	@Query("select new com.pratikshat.exicom.model.response.AnnexureLineItemResponse(ali) from AnnexureLineItem ali left join ali.annexure anx left join ali.serviceAppointment sa left join ali.glCode gl left join ali.createdBy cby left join ali.modifiedBy mby left join sa.caseDetails cd left join cd.serviceCenter cs left join cd.site st left join st.account where anx.id=:annexureId")
	Page<AnnexureLineItemResponse> getAnnexureLineItemByAnnexureId(@Param("annexureId")long annexureId,Pageable pageable);
	
	
	@Query("select new com.pratikshat.exicom.model.response.AnnexureLineItemResponse(ali) from AnnexureLineItem ali left join ali.annexure anx left join ali.serviceAppointment sa left join ali.glCode gl left join ali.createdBy cby left join ali.modifiedBy mby left join sa.caseDetails cd left join cd.serviceCenter cs left join cd.site st left join st.account where anx.id=:annexureId")
	List<AnnexureLineItemResponse> getAnnexureLineItemListByAnnexureId(@Param("annexureId")long annexureId);
	
	
}
