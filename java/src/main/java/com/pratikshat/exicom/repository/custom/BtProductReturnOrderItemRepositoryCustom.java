package com.pratikshat.exicom.repository.custom;


import com.pratikshat.exicom.entity.BtProductReturnOrderItem;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;
import java.util.List;
import java.util.Map;

public interface BtProductReturnOrderItemRepositoryCustom extends GenericOperation<BtProductReturnOrderItem>{

    public void addItem(BtProductReturnOrderItem item,LoginUser user) throws BadRequestException,UnauthoriseException;

    public void addReturnedQuantity(BtProductReturnOrderItem item,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addReturnedQuantity(Long btProductReturnOrderItemId,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    
    public void subReturnedQuantity(BtProductReturnOrderItem item,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subReturnedQuantity(Long btProductReturnOrderItemId,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;

    public void addReturnOrderReceivedQuantity(BtProductReturnOrderItem item,Float quantity,Boolean customerIvnentory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addReturnOrderReceivedQuantity(Long btProductReturnOrderItemId,Float quantity,Boolean customerIvnentory,LoginUser user) throws BadRequestException,UnauthoriseException;
    
    public void subReturnorderReceivedQuantity(BtProductReturnOrderItem item,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subReturnOrderReceivedQuantity(Long btProductReturnOrderItemId,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;

    //ADVANCE SEARCH BY PRODUCT RETURN ORDER ID

    public List<Map<String,Object>> advanceSearchOfItemsByReturnOrderId(Long returnOrderId,List<Long>locationIds,Map<String,Object> request,LoginUser user) throws BadRequestException,UnauthoriseException;


}
