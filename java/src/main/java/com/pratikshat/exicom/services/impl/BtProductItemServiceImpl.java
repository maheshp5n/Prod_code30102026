package com.pratikshat.exicom.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.entity.ServiceRecource;
import com.pratikshat.exicom.entity.UserProfile;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtProductItemRepository;
import com.pratikshat.exicom.repository.ServiceRecourseRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BtProductItemServiceImpl implements BtProductItemService {


    @Autowired
    BtProductItemRepository dbStock;


    @Autowired
    ServiceRecourseRepository dbServiceResource;

    @Autowired
    UserProfileRepository dbProfile;

    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        
        return null;
    }
    @Override
    public Map<String, Object> getSingle(Object id,Long locationIds, LoginUser user) throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {

        ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
        return getPage(page, sr.getLocation().getLocationId(), user);
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, Long locationId,LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL))
            return dbStock.getStock(locationId, page);
        
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW))
            return dbStock.getStock(locationId, dbProfile.getLocationAndChildLocation(user.getUsername()), page);
        
        else
            throw new UnauthoriseException("You are not authorised");
        
    }

    @Deprecated
    @Override
    /**
     * Do not use this method to udpate stock as it will return null and will not take any action to user stock
     * We are not updating any stock manuallay, all stock are mentained by transation with ro and pr pt
     */
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    @Deprecated
    /**
     *  Do not use this method to udpate stock as it will return null and will not take any action to user stock
     * We are not updating any stock manuallay, all stock are mentained by transation with ro and pr pt
     */
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL))
            return dbStock.getStockSarchForList(search);

        else if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW))
            return dbStock.getStockSarchForList(dbProfile.getLocationAndChildLocation(user.getUsername()), search);
        else
            throw new UnauthoriseException("You are not authorised");
    }
    @Override
    public List<Map<String, Object>> searchForList(String search,Long locationId ,LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL))
            return dbStock.getStockSarchForList(locationId,search);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW))
            return dbStock.getStockSarchForList(locationId, dbProfile.getLocationAndChildLocation(user.getUsername()), search);
        else
            throw new UnauthoriseException("You are not authorised");
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return advanceSearch(searchRequest,null, user);
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest,Long locationId,LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL))
                return dbStock.advanceSearch(null, searchRequest, user);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class,UserProfileRepository.VIEW))
                return dbStock.advanceSearch(dbProfile.getLocationAndChildLocation(user.getUsername()), searchRequest, user);
        else
                throw new UnauthoriseException("You are not authorised");
    }
    @Override
    public List<Map<String, Object>> getListOfProductsByLocationid(Long locationId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL))
                return dbStock.getProductListByLocationid(locationId);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW))
                return dbStock.getProductListByLocationid(locationId, dbProfile.getLocationAndChildLocation(user.getUsername()));
        else
                throw new UnauthoriseException("You are not authorised");
    }
    @Override
    public List<Map<String, Object>> getFaultyProductItemForReturnOrder(LoginUser user)
            throws BadRequestException, UnauthoriseException {
        ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL)
            ||dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW))
                return dbStock.getPendingFaultyProductItemforReturnOrder(sr.getLocation().getLocationId());
        else
            throw new UnauthoriseException("You are not authorised");
    }
    @Override
    public List<Map<String, Object>> advanceSearchOfItemByLocationId(Long locationId, Map<String, Object> request,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW_ALL))
                return dbStock.advanceSearch(locationId, null, request, user);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductItem.class, UserProfileRepository.VIEW))
                return dbStock.advanceSearch(locationId, dbProfile.getLocationAndChildLocation(user.getUsername()), request, user);
        else
                throw new UnauthoriseException("You are not authorised");
    }
    @Override
    public Map<String, Object> getStockByLocationIdAndProductId(Long locationId, Long productId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return dbStock.getStockSingle(locationId, productId);
    }

    public List<Map<String,Object>> advanceSearchMyStock(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException{
        ServiceRecource sr=dbServiceResource.getByEmail(user.getUsername());
        return dbStock.advanceSearch(sr.getLocation().getLocationId(), new ArrayList<Long>(), request, user);
    }
    
}
