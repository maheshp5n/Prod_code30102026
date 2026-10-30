package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtReturnOrderService extends GenricService {
    
    Page<Map<String,Object>> getItemByReturnOrderId(Long returnOrderId,LoginUser user,Pageable page) throws BadRequestException,UnauthoriseException;
    List<Map<String,Object>> getListItemByReturnOrderId(Long returnOrderId,LoginUser user) throws BadRequestException,UnauthoriseException;

    List<Map<String,Object>> advanceSearchOfItemsByReturnOrderId(Long returnOrderId,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getPendingToreceive(LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getPendingToreceive(Long returnOrderId,LoginUser user) throws BadRequestException,UnauthoriseException;

    public void receiveItem(List<Map<String,Object>> request,LoginUser user) throws BadRequestException,UnauthoriseException;


    Page<Map<String,Object>> getPageByFilterName(String filtername,Pageable page,LoginUser user ) throws BadRequestException,UnauthoriseException;

    public void addItem(Long returnOrderId,Map<String,Object> item,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void deleteItem(Long returnOrderId,Long returnOrderItemId,LoginUser user) throws BadRequestException,UnauthoriseException;

    BtPacking getPacking(Long id);
}
