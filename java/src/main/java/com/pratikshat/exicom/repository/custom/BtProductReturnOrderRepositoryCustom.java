package com.pratikshat.exicom.repository.custom;

import java.util.List;
import java.util.Map;

import com.pratikshat.exicom.entity.BtProductReturnOrder;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtProductReturnOrderRepositoryCustom extends GenericOperation<BtProductReturnOrder>{
    public void addReturnedQuantity(BtProductReturnOrder returnOrder,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addReturnedQuantity(Long returnOrderId,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    
    public void subReturnedQuantity(BtProductReturnOrder returnOrder,Float quantity,Boolean customerInventory, LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subReturnedQuantity(Long returnOrderId,Float quantity,Boolean customerIvnentory,LoginUser user) throws BadRequestException,UnauthoriseException;

    public void addReturnReceivedQuantity(BtProductReturnOrder returnOrder,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addReturnReceivedQuantity(Long returnOrderId,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    
    public void subReturnReceivedQuantity(BtProductReturnOrder returnOrder,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void subReturnReceivedQuantity(Long returnOrderId,Float quantity,Boolean customerInventory,LoginUser user) throws BadRequestException,UnauthoriseException;

    public List<Map<String,Object>> advanceSearch(List<Long> locationIds,Map<String,Object> searchparam,LoginUser user) throws BadRequestException,UnauthoriseException;

}
