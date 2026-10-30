package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCommissioningPendingReasonCustomerBucket;

@Repository
public interface BtCommissioningPendingReasonCustomerBucketRepository extends JpaRepository<BtCommissioningPendingReasonCustomerBucket, Long> {

	@Query(value = "SELECT cprcb.`id` AS id, cprcb.`type_value` AS pendingReasonValue FROM `bt_commissioning_pending_reason_customer_bucket` cprcb",nativeQuery = true)
	public List<Map<String, Object>> getBtCommissioningPendingReasonCustomerBucket();
}
