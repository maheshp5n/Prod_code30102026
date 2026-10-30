package com.pratikshat.exicom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.CityRecentVisit;
@Repository
public interface CityRecentVisitRepo extends JpaRepository<CityRecentVisit, Long> {
	CityRecentVisit findByCityNameAndVisitoryId(String cityName,String visitorId);
}
