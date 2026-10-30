package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CircleRecentVisitRepository extends JpaRepository<com.pratikshat.exicom.entity.CircleRecentVisit, Long> {
	com.pratikshat.exicom.entity.CircleRecentVisit findByCircleNameAndVisitorId(String circleName,String userName);
}
