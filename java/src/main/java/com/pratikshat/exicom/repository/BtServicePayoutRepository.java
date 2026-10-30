package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtServicePayout;

@Repository
public interface BtServicePayoutRepository extends JpaRepository<BtServicePayout, Long>{
	
	@Query(value = "SELECT btsp.`id` AS id , btsp.`name` AS `name` FROM `bt_service_payout` btsp ",nativeQuery = true)
	List<Map<String, Object>> forListForPayout();

}
