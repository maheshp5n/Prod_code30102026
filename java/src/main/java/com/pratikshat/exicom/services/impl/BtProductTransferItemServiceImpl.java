package com.pratikshat.exicom.services.impl;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductTransferItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.BtProductTransferItemRepository;
import com.pratikshat.exicom.repository.UserProfileRepository;
import com.pratikshat.exicom.services.BtProductTransferItemService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BtProductTransferItemServiceImpl implements BtProductTransferItemService {

    @Autowired
    BtProductTransferItemRepository dbTransferItem;

    @Autowired
    UserProfileRepository dbProfile;

    @Override
    public List<Map<String, Object>> getTransferItemDetails(Long transferId, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        return dbTransferItem.getItems(transferId);
    }

    @Override
    public Page<Map<String, Object>> getTransferItemDetails(Long transferId, LoginUser user, Pageable page)
            throws BadRequestException, UnauthoriseException {
        
         return dbTransferItem.getItems(transferId,page);
    }

    /*
    *

    * METHOD IS NOT IN USE
    */ 
    @Deprecated
    @Override
    public Map<String, Object> getSingle(Object id, LoginUser user) throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<Map<String, Object>> getPage(Pageable page, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> saveObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Map<String, Object> updateObject(Map<String, Object> request, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> searchForList(String search, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest, LoginUser user)
            throws BadRequestException, UnauthoriseException {
        
        return null;
    }

    @Override
    public List<Map<String, Object>> advanceSearchOfItemByproduct(Long transferId, Map<String, Object> request,
            LoginUser user) throws BadRequestException, UnauthoriseException {
        if(dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW_ALL))
            return dbTransferItem.getAdvanceSerachByProductTransferId(transferId, null, request, user);
        else if (dbProfile.hasObjectAccess(user.getUsername(), BtProductTransferItem.class, UserProfileRepository.VIEW))
            return dbTransferItem.getAdvanceSerachByProductTransferId(transferId, dbProfile.getLocationAndChildLocation(user.getUsername()), request, user);
        else
            throw new UnauthoriseException("You are not authorised");
    }
    
}
