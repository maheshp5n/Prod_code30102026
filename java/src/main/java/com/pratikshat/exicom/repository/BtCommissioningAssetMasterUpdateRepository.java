package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.pratikshat.exicom.entity.BtCommissioningAssetMasterUpdate;
import com.pratikshat.exicom.model.response.BtAssetMasterUpdateResponse;

import com.pratikshat.exicom.model.response.BtCommissioningAssetMasterUpdateResponse;
import com.pratikshat.exicom.repository.custom.BtCommissioningAssetMasterUpdateCustom;


@Repository
public interface BtCommissioningAssetMasterUpdateRepository extends JpaRepository<BtCommissioningAssetMasterUpdate, Long>,BtCommissioningAssetMasterUpdateCustom{
	
	@Query("select new com.pratikshat.exicom.model.response.BtCommissioningAssetMasterUpdateResponse(camu,asd)"
			+ "from BtCommissioningAssetMasterUpdate camu "
			+ "left join camu.btAssets bta "
			+ "left join camu.btCommissioningFSR cfsr "
			+ "left join BtAssetsWarrantyDetails asd on bta.id=asd.id "
			+ " where cfsr.id=:id "
			+ "ORDER BY camu.srNo ASC")
	Set<BtCommissioningAssetMasterUpdateResponse> getByComAssetMasterId(@Param("id") long id);
	
	@Modifying
	@Query(value="DELETE FROM `bt_commissioning_asset_master_update` WHERE `bt_commissioningfsr_id`=:commFSRId",nativeQuery = true)
	void deleteByCommFSRId(@Param("commFSRId") long commFSRId);
	
}