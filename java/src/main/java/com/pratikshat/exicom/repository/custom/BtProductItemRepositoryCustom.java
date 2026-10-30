package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductItem;
import com.pratikshat.exicom.entity.BtProducts;
import com.pratikshat.exicom.entity.Location;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtProductItemRepositoryCustom extends GenericOperation<BtProductItem> {
    void addQuantity(BtProductItem item,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;
    void addQuantity(Long btProductItemId,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;
    void addQuantity(BtProducts btProducts,Location location,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;

    void addQuantity(BtProducts btProducts,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;

    void subQuantity(BtProductItem item,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;
    void subQuantity(Long btProductItemId,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;
    void subQuantity(BtProducts btProducts,Location location,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;

    void subQuantity(BtProducts btProducts,Float quantity,Boolean isCustomer,int stateId,LoginUser user) throws BadRequestException,UnauthoriseException;

    List<Map<String,Object>> advanceSearch(List<Long> locationIds,Map<String,Object> search,LoginUser user) throws BadRequestException,UnauthoriseException;

    List<Map<String,Object>> advanceSearch(Long locationId,List<Long> locationIds,Map<String,Object> search,LoginUser user) throws BadRequestException,UnauthoriseException;


    
}
