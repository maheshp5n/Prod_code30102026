package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.QuoteLineItem;
import com.pratikshat.exicom.model.response.OppertunityLineItemResponse;
import com.pratikshat.exicom.model.response.QuoteLineItemResponse;

@Repository
public interface QuoteLineItemRepository extends JpaRepository<QuoteLineItem, Long> {

	@Query("select new com.pratikshat.exicom.model.response.QuoteLineItemResponse(qli) from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where qli.isDeleted=false and qt.id=:quoteId")
	List<QuoteLineItemResponse> getLineItemListByQuoteId(@Param("quoteId")long quoteId);
	
	@Query("select new com.pratikshat.exicom.model.response.QuoteLineItemResponse(qli) from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where qli.isDeleted=false and qt.id=:quoteId")
	Page<QuoteLineItemResponse> getLineItemPageByQuoteId(@Param("quoteId")long quoteId, Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.QuoteLineItemResponse(qli) from QuoteLineItem qli left join qli.quote qt left join qli.priceBookEntries pbe left join pbe.product prd left join pbe.priceBook where qli.id=:id")
	QuoteLineItemResponse getLineItemById(@Param("id")long id);
}
