package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.VendorContractDetails;

@Repository
public interface VendorContractRepository extends JpaRepository<VendorContractDetails,Long> {
	@Query(value="select * from vendor_contract_details",nativeQuery=true)
	public List<Map<String,Object>> getContractDetails(String userEmailId,String poid,String activityNumber);
}
