package com.pratikshat.exicom.repository;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.PmSiteAssets;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.repository.custom.PmSiteAssetsRepositoryCustom;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PmSiteAssetsRepository extends JpaRepository<PmSiteAssets,Long>,PmSiteAssetsRepositoryCustom {

    List<PmSiteAssets> findBySiteDetailsAndAssets(SiteDetails siteDetails,Assets assets);

    @Query(value = "SELECT * FROM `pm_site_assets` WHERE `site_details_site_id` = :siteId",nativeQuery = true)
    List<Map<String,Object>> getBySiteId(@Param("siteId")Long siteId);

    @Query(value = "SELECT * FROM `pm_site_assets` WHERE `assets_serial_number` = :sapNumber",nativeQuery = true)
    List<Map<String,Object>> getByAssetsSapNumber(@Param("sapNumber") String sapNumber);

    @Query(value = "SELECT * FROM `pm_site_assets` WHERE `assets_serial_number` = :sapNumber AND `site_details_site_id` = :siteId",nativeQuery = true)
    List<Map<String,Object>> getBySiteIdAndAssets(@Param("siteId")Long siteId,@Param("sapNumber") String assetsSapNumber);

    @Query(value = "SELECT * FROM `pm_site_assets` WHERE `assets_serial_number` = :sapNumber or `site_details_site_id` = :siteId",nativeQuery = true)
    List<Map<String,Object>> getBySiteIdOrAssets(@Param("siteId")Long siteId,@Param("sapNumber") String assetsSapNumber);
}
