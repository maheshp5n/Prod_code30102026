package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import com.pratikshat.exicom.entity.Assets;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.PmSiteAssets;
import com.pratikshat.exicom.entity.SiteDetails;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.AssetsRepository;
import com.pratikshat.exicom.repository.PmSiteAssetsRepository;
import com.pratikshat.exicom.repository.SiteDetailsRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.PmSiteAssetsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PmSiteAssetsServiceImpl implements PmSiteAssetsService{

    @Autowired PmSiteAssetsRepository dbPmSite;

    @Autowired UserProfileRepository dbUserProfile;

    @Autowired SiteDetailsRepository dbSite;

    @Autowired AssetsRepository dbAssets;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    @Transactional(rollbackOn = {BadRequestException.class,UnauthoriseException.class,RuntimeException.class,Exception.class})
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(!dbUserProfile.hasObjectAccess(user.getUsername(), PmSiteAssets.class,UserProfileRepository.CREATE))
                throw new UnauthoriseException("You are not authorised");
        if(request.get("siteId")==null||request.get("siteId").toString().equalsIgnoreCase(""))
                throw new BadRequestException("Site id not found");
        try {
            Long.parseLong(request.get("siteId").toString());
        } catch (Exception e) {
            throw new BadRequestException("Invalid siste id, String not allowed only Long/Integer value");
        }

        Optional<SiteDetails> optionalSiteDetails=dbSite.findById(Long.parseLong(request.get("siteId").toString()));
        if(!optionalSiteDetails.isPresent())
                throw new BadRequestException("Invalid site id");
        Optional<Assets> optionalAssets=dbAssets.findById(request.get("assetsSapNumber").toString());
        if(!optionalAssets.isPresent())
                throw new BadRequestException("Invalid assets id");
        
        Assets assets=optionalAssets.get();
        SiteDetails siteDetails=optionalSiteDetails.get();

        if(dbPmSite.findBySiteDetailsAndAssets(siteDetails, assets).size()>0)
            throw new BadRequestException("Site And Assets already exists");

        if(request.get("siteManualId")!=null&&!request.get("siteManualId").toString().equalsIgnoreCase(""))
            if(!siteDetails.getManualSiteId().equalsIgnoreCase(request.get("siteManualId").toString()))
                throw new BadRequestException("System site id and manual site id mismatch");

        if(assets.getSite()!=null){
            if(assets.getSite().getSiteId()!=siteDetails.getSiteId())
                throw new BadRequestException("Assets "+assets.getSerialNumber()+" does not belongs to site "+siteDetails.getSiteName());
        }

        PmSiteAssets pmAssets=new PmSiteAssets();
        pmAssets.setAssets(assets);
        pmAssets.setSiteDetails(siteDetails);
        dbPmSite.saveDetails(pmAssets, user);
        request.put("id", pmAssets.getId());
        request.put("manuaId", pmAssets.getManualId());
        return request;
    }

    @Override
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public List<Map<String, Object>> checkWithAssets(String assetsSapNumber) {
    
        return dbPmSite.getByAssetsSapNumber(assetsSapNumber);
    }

    @Override
    public List<Map<String, Object>> checkWithSiteDetails(Long siteId) {
    
        return dbPmSite.getBySiteId(siteId);
    }

    @Override
    public List<Map<String, Object>> checkWithAssetsAndSite(String assetsSapNumber, Long siteId,String condation) {
        if(condation!=null&&condation.equalsIgnoreCase("or"))
            return dbPmSite.getBySiteIdOrAssets(siteId, assetsSapNumber);
        return dbPmSite.getBySiteIdAndAssets(siteId, assetsSapNumber);
    }
    
}
