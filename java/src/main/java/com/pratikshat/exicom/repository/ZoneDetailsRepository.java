package com.pratikshat.exicom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.ZoneDetails;
@Repository
public interface ZoneDetailsRepository extends JpaRepository<ZoneDetails, String> {
	public ZoneDetails findByZoneName(String zoneName);
	@Query(value="SELECT zone_name FROM `zone_details`WHERE domain_id=:id",nativeQuery=true)
	public List<String> findByBuisDomainId(long id);
}
