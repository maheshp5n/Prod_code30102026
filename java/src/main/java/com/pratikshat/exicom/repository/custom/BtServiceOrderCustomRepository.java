package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtServiceOrder;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtServiceOrderCustomRepository extends GenericOperation<BtServiceOrder> {

    public List<Map<String,Object>> advanceSearch(Map<String,Object> request,List<Long>locationIds,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItem(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemReceived(BtServiceOrder order, Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemReturned(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemAssigned(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemAssignedReturned(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;


    public void addTotalNumberOfItem(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemReceived(Long orderId, Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemReturned(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemAssigned(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalNumberOfItemAssignedReturned(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;

    public void subTotalNumberOfItem(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemReceived(BtServiceOrder order, Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemReturned(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemAssigned(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemAssignedReturned(BtServiceOrder order,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;


    public void subTotalNumberOfItem(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemReceived(Long orderId, Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemReturned(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemAssigned(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subTotalNumberOfItemAssignedReturned(Long orderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;


}
