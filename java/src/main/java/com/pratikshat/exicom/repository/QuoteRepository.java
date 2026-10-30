package com.pratikshat.exicom.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.Quote;
import com.pratikshat.exicom.model.response.QuoteResponse;
@Repository
public interface QuoteRepository extends JpaRepository<Quote, Long>, QuoteRepositoryCustom{

	@Query("select new com.pratikshat.exicom.model.response.QuoteResponse(qt) from Quote qt left join qt.oppertunity opr left join opr.caseDetails cd left join opr.accounts acn left join qt.quoteStatus qtst left join qt.billingCountry bc left join qt.billingState bs left join qt.shippingCountry sc left join qt.shippingState ss left join qt.createdBy createdby left join qt.modifiedBy modified left join qt.owner onr where qt.id=:id")
	QuoteResponse getById(@Param("id")long id);
	
	@Query("select new com.pratikshat.exicom.model.response.QuoteResponse(qt) from Quote qt left join qt.oppertunity opr left join opr.caseDetails cd left join opr.accounts acn left join qt.quoteStatus qtst")
	Page<QuoteResponse> getAll(Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.QuoteResponse(qt) from Quote qt left join qt.quoteStatus sts left join qt.oppertunity opr left join opr.caseDetails cd left join opr.accounts acn left join qt.quoteStatus qtst where sts.name=:status")
	Page<QuoteResponse> getQuotesByStatus(@Param("status")String status,Pageable pageable);
	
	@Query("select new com.pratikshat.exicom.model.response.QuoteResponse(qt) from Quote qt left join qt.oppertunity opr left join opr.caseDetails cd left join opr.accounts acn left join qt.quoteStatus qtst join QuoteRecentVisit rc on qt.id=rc.quoteId where rc.visitorId=:username")
	Page<QuoteResponse> getRecentVisits(@Param("username")String username,Pageable page);
	
	@Query("select new com.pratikshat.exicom.model.response.QuoteResponse(qt) from Quote qt left join qt.oppertunity opr left join opr.caseDetails cd left join opr.accounts acn left join qt.quoteStatus qtst where opr.oppertunityId=:oppertunityId")
	Page<QuoteResponse> getAllQuoteByOpportunityId(@Param("oppertunityId")long oppertunityId, Pageable pageable);
}
