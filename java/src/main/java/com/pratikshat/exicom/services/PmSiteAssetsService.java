package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

public interface PmSiteAssetsService extends GenricService{
    List<Map<String,Object>> checkWithAssets(String assetsSapNumber);
    List<Map<String,Object>> checkWithSiteDetails(Long siteId);
    List<Map<String,Object>> checkWithAssetsAndSite(String assetsSapNumber,Long siteId,String condation);
}
