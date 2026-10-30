package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtProductItemService extends GenricService {
    public Map<String, Object> getSingle(Object id,Long locationIds, LoginUser user) throws BadRequestException, UnauthoriseException;
    public Page<Map<String, Object>> getPage(Pageable page, Long locationId,LoginUser user) throws BadRequestException, UnauthoriseException;
    public List<Map<String, Object>> searchForList(String search,Long locationId ,LoginUser user) throws BadRequestException, UnauthoriseException;
    public List<Map<String, Object>> advanceSearch(Map<String, Object> searchRequest,Long locationId,LoginUser user) throws BadRequestException, UnauthoriseException;
    public List<Map<String,Object>> getListOfProductsByLocationid(Long locationId,LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getFaultyProductItemForReturnOrder(LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> advanceSearchOfItemByLocationId(Long locationId,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;

    public Map<String,Object> getStockByLocationIdAndProductId(Long locationId,Long productId,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> advanceSearchMyStock(Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;

}
