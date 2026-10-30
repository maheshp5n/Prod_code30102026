package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.QuoteRecentVisit;

@Repository
public interface QuoteRecentVisitRepository extends JpaRepository<QuoteRecentVisit, Long> {

	QuoteRecentVisit findByQuoteIdAndVisitorId(long quoteId,String username);
}
