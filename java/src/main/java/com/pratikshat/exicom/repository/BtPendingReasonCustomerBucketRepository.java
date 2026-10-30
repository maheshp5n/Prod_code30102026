package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtPendingReasonCustomerBucket;
import com.pratikshat.exicom.entity.BtSiteType;

@Repository
public interface BtPendingReasonCustomerBucketRepository extends JpaRepository<BtPendingReasonCustomerBucket, Long> {
	
	@Query("select ct from BtPendingReasonCustomerBucket ct where ct.id=:id")
	BtPendingReasonCustomerBucket getByPendingReasonCustomerWithId(@Param("id") long l);
	
	@Query(value = "SELECT prcb.`id` AS id, prcb.`type_value` AS pendingReasonValue FROM `bt_pending_reason_customer_bucket` prcb",nativeQuery = true)
	public List<Map<String, Object>> getBtPendingReasonCustomerBucket();
	
	

}
