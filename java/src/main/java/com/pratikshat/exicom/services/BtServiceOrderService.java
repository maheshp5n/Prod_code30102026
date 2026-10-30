package com.pratikshat.exicom.services;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.model.response.BtPacking;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BtServiceOrderService extends GenricService{
    
    public Page<Map<String,Object>> getPage(String filtername,Pageable page,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getPendingProductForServiceOrder(LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getPendingToCustomerInventoryForSO(LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getPendingToCustomerInventoryForSOByProductId(Long productId,LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getPendingToCustomerInventoryForSOByProductId(Long productId,String challanNumber,LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getServiceOrderItem(Long serviceOrderId,LoginUser user) throws BadRequestException,UnauthoriseException;
    public Page<Map<String,Object>> getServiceOrderItem(Long serviceOrderId,Pageable page, LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getServiceOrderItemPendingToReceive(LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getServiceOrderItemPendingToReceive(Long serviceOrderId,LoginUser user) throws BadRequestException,UnauthoriseException;
    public List<Map<String,Object>> getServiceOrderItemPendingToAssign(LoginUser user) throws BadRequestException,UnauthoriseException;

    public void receiveServiceOrderItem(List<Map<String,Object>> serviceOrderitems,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void receiveServiceOrderItem(Map<String, Object> item, LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getRepairCenterList(LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> advanceSearchItem(Long serviceOrderId,Map<String,Object> searchRequest,LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> getServiceOrderItemPendingToAssign(Long serviceOrderId,LoginUser user) throws BadRequestException,UnauthoriseException;

    public BtPacking getPackingSo(Long id);
    public BtPacking getPackingSor(Long id);
    

}
