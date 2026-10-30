package com.pratikshat.exicom.repository.custom;

import com.pratikshat.exicom.entity.BtServiceOrderReturn;
import com.pratikshat.exicom.entity.LoginUser;
import com.pratikshat.exicom.exception.BadRequestException;
import com.pratikshat.exicom.exception.UnauthoriseException;
import com.pratikshat.exicom.repository.GenericOperation;

public interface BtServiceOrderReturnRepositoryCustom extends GenericOperation<BtServiceOrderReturn>{
    public void addTotalReturndQuantity(Long returnOrderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
    public void addTotalReturndReceivedQuantity(Long returnOrderId,Float quantity,LoginUser user) throws BadRequestException,UnauthoriseException;
}
