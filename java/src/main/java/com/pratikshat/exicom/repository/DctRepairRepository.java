package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.DctRepair;
import com.pratikshat.exicom.model.response.DctRepairItemConsumedResponse;
import com.pratikshat.exicom.model.response.DctRepairResponse;
@Repository
public interface DctRepairRepository extends JpaRepository<DctRepair, Long>,DctRepairRepositoryCustome{

	@Query(value="select product_state_id as id,product_state_name as stateName from product_line_item_product_state",nativeQuery=true)
	List<Map<String,Object>> getProductStatus();
	
	@Query("select new com.pratikshat.exicom.model.response.DctRepairResponse(drp) from DctRepair drp join drp.fProduct prd join drp.repairBy rpby join drp.createdBy cby join drp.modifiedBy mby join drp.owner onr join drp.previousStatus pst join drp.currentStatus cst join drp.jobCard jcard where prd.productPartCode=:partcode and drp.serialNumber=:serialNo")
	Page<DctRepairResponse> getDctRepairByPartcodeAndSerialNo(@Param("partcode")String partcode, @Param("serialNo")String serialNo, Pageable pageable);

	@Query("select new com.pratikshat.exicom.model.response.DctRepairItemConsumedResponse(item) from DctRepairItemConsumed item join item.repair drp join item.product prd where drp.id=:repairId")
	Page<DctRepairItemConsumedResponse> getRepairItemConsumedByRepairId(@Param("repairId")long repairId, Pageable pageable);
	
}
