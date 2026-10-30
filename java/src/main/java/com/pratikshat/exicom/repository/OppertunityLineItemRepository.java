package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.OppertunityLineItem;
import com.pratikshat.exicom.model.response.OppertunityLineItemResponse;

@Repository
public interface OppertunityLineItemRepository extends JpaRepository<OppertunityLineItem, Long>{

	@Query("select new com.pratikshat.exicom.model.response.OppertunityLineItemResponse(oli) from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where oli.isDeleted=false and opr.oppertunityId=:oppertunityId")
	List<OppertunityLineItemResponse> getLineItemListByOppertunityId(@Param("oppertunityId")long oppertunityId);
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityLineItemResponse(oli) from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where oli.isDeleted=false and opr.oppertunityId=:oppertunityId")
	Page<OppertunityLineItemResponse> getLineItemPageByOppertunityId(@Param("oppertunityId")long oppertunityId, Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.OppertunityLineItemResponse(oli) from OppertunityLineItem oli left join oli.oppertunity opr left join oli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where oli.id=:id")
	OppertunityLineItemResponse getLineItemById(@Param("id")long id);
}
