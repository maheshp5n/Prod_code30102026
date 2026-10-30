package com.pratikshat.exicom.repository;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtAssetMasterUpdate;
import com.pratikshat.exicom.model.response.BtAssetMasterUpdateResponse;

@Repository
public interface BtAssetMasterUpdateRepository extends JpaRepository<BtAssetMasterUpdate, Long>,BtAssetMasterUpdateCustomRepository {

	@Query("select new com.pratikshat.exicom.model.response.BtAssetMasterUpdateResponse(amu,asd)"
			+ "	from BtAssetMasterUpdate amu "
			+ "left join amu.btAssets bta "
			+ "left join amu.btFsr fsr "
			+ "left join BtAssetsWarrantyDetails asd on bta.id=asd.id "
			+ "where fsr.id =:id "
			+ "ORDER BY amu.srNo ASC")
	Set<BtAssetMasterUpdateResponse> getByAssetMasterId(@Param("id") long id);

	@Modifying
	@Query(value = "DELETE FROM `bt_asset_master_update` WHERE `bt_fsr_id`=:serviceFSRId",nativeQuery = true)
	void deleteByCommFSRId(@Param("serviceFSRId") long serviceFSRId);
}
