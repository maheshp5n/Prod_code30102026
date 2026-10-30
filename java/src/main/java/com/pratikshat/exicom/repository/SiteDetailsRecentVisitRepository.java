package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface SiteDetailsRecentVisitRepository extends JpaRepository<com.pratikshat.exicom.entity.SiteDetailsRecentVisit, Long> {
	com.pratikshat.exicom.entity.SiteDetailsRecentVisit findBySiteIdAndVisitorId(long siteId,String username);
}
