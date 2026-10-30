package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface AssetsRecentVisitRepository extends JpaRepository<com.pratikshat.exicom.entity.AssetsRecentVisit,Long> {
	com.pratikshat.exicom.entity.AssetsRecentVisit findByAssetsSerialIdAndVisitorId(String assetsSerialNumber,String username);
}
